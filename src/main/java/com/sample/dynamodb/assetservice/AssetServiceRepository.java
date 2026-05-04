package com.sample.dynamodb.assetservice;

import com.sample.dynamodb.DynamoUtils;
import com.sample.dynamodb.assetservice.domain.AssetService;
import com.sample.dynamodb.assetservice.domain.AssetServiceStatus;
import com.sample.dynamodb.assetservice.dynamoentity.AssetServiceDynamoEntity;
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
public class AssetServiceRepository {

    private static final String DYNAMO_TABLE = "fleet";

    private final DynamoDbClient dynamoDbClient;

    public void save(AssetService assetService) {

        AssetServiceDynamoEntity assetServiceDynamoEntity = AssetServiceDynamoEntity.from(assetService);

        // GSI Sparse Index, (OPEN 인 경우)
        if (assetService.getServiceStatus() != null && assetService.getServiceStatus().equals(AssetServiceStatus.OPEN)) {
            assetServiceDynamoEntity.setGsi1Pk(assetService.getServiceStatus().toString());
        }

        DynamoDbTable<AssetServiceDynamoEntity> table = getDynamoTable();
        table.putItem(assetServiceDynamoEntity);
    }

    public List<AssetService> getItemsStatusOpen() {
        DynamoDbTable<AssetServiceDynamoEntity> table = getDynamoTable();
        DynamoDbIndex<AssetServiceDynamoEntity> gsi1Index = table.index("GSI1");

        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue("OPEN").build());
        SdkIterable<Page<AssetServiceDynamoEntity>> results = gsi1Index.query(r -> r.queryConditional(condition));

        return results.stream()
                .flatMap(page -> page.items().stream())
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private DynamoDbTable<AssetServiceDynamoEntity> getDynamoTable() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        return enhancedClient.table(DYNAMO_TABLE, TableSchema.fromBean(AssetServiceDynamoEntity.class));
    }

    private AssetService toDomain(AssetServiceDynamoEntity entity) {
        AssetServiceStatus serviceStatus = entity.getServiceStatus() != null ? AssetServiceStatus.valueOf(entity.getServiceStatus()) : null;

        return AssetService.builder()
                .id(DynamoUtils.getId(entity.getSk()))
                .assetId(DynamoUtils.getId(entity.getPk()))
                .serviceStatus(serviceStatus)
                .serviceCreateDate(entity.getServiceCreateDate())
                .serviceDate(entity.getServiceDate())
                .build();
    }
}
