package com.sample.dynamodb.asset;

import com.sample.dynamodb.DynamoUtils;
import com.sample.dynamodb.asset.domain.Asset;
import com.sample.dynamodb.asset.domain.AssetStatus;
import com.sample.dynamodb.asset.domain.AssetType;
import com.sample.dynamodb.asset.dynamoentity.AssetDynamoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AssetRepository {

    private static final String DYNAMO_TABLE = "fleet";

    private final DynamoDbClient dynamoDbClient;

    public void save(Asset asset) {
        AssetDynamoEntity assetDynamoEntity = AssetDynamoEntity.from(asset);

        // GSI Sparse Index, (LOW_BATTERY 인 경우)
        if (asset.getStatus() != null && asset.getStatus().equals(AssetStatus.LOW_BATTERY)) {
            assetDynamoEntity.setGsi1Pk(asset.getStatus().toString());
        }

        DynamoDbTable<AssetDynamoEntity> table = getDynamoTable();
        table.putItem(assetDynamoEntity);
    }

    public List<Asset> getLowBatteries() {
        DynamoDbTable<AssetDynamoEntity> table = getDynamoTable();
        DynamoDbIndex<AssetDynamoEntity> gsi1Index = table.index("GSI1");

        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue("LOW_BATTERY").build());
        SdkIterable<Page<AssetDynamoEntity>> results = gsi1Index.query(r -> r.queryConditional(condition));

        return results.stream()
                .flatMap(page -> page.items().stream())
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private DynamoDbTable<AssetDynamoEntity> getDynamoTable() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        return enhancedClient.table(DYNAMO_TABLE, TableSchema.fromBean(AssetDynamoEntity.class));
    }

    private Asset toDomain(AssetDynamoEntity entity) {
        AssetType assetType = entity.getAssetType() != null ? AssetType.valueOf(entity.getAssetType()) : null;
        AssetStatus assetStatus = entity.getStatus() != null ? AssetStatus.valueOf(entity.getStatus()) : null;

        return Asset.builder()
                .id(DynamoUtils.getId(entity.getPk()))
                .assetType(assetType)
                .status(assetStatus)
                .battery(entity.getBattery())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .build();
    }
}
