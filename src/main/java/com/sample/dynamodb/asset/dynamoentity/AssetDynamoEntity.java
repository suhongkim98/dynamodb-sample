package com.sample.dynamodb.asset.dynamoentity;

import com.sample.dynamodb.DynamoUtils;
import com.sample.dynamodb.asset.domain.Asset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class AssetDynamoEntity {

    private static final String ASSET_KEY_PREFIX = "ASSET";

    private String pk;
    private String sk;
    private String gsi1Pk;

    private String assetType;
    private String status;
    private Integer battery;
    private Double latitude;
    private Double longitude;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    @DynamoDbSecondarySortKey(indexNames = "GSI1") // 실무에서는 GSI1_SK로 분리하자
    public String getSk() {
        return sk;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI1") // GSI 파티션 키 지정
    @DynamoDbAttribute("GSI1_PK")
    public String getGsi1Pk() {
        return gsi1Pk;
    }

    public static AssetDynamoEntity from(Asset asset) {
        String assetType = asset.getAssetType() != null ? asset.getAssetType().toString() : null;
        String status = asset.getStatus() != null ? asset.getStatus().toString() : null;

        return AssetDynamoEntity.builder()
                .pk(DynamoUtils.join(ASSET_KEY_PREFIX, asset.getId()))
                .sk(DynamoUtils.join(ASSET_KEY_PREFIX, asset.getId()))
                .assetType(assetType)
                .status(status)
                .battery(asset.getBattery())
                .latitude(asset.getLatitude())
                .longitude(asset.getLongitude())
                .build();
    }
}
