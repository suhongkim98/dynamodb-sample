package com.sample.dynamodb.asset;

import com.sample.dynamodb.asset.domain.Asset;
import com.sample.dynamodb.asset.domain.AssetStatus;
import com.sample.dynamodb.asset.dynamoentity.AssetDynamoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

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

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        DynamoDbTable<AssetDynamoEntity> table = enhancedClient.table(DYNAMO_TABLE, TableSchema.fromBean(AssetDynamoEntity.class));
        table.putItem(assetDynamoEntity);
    }
}
