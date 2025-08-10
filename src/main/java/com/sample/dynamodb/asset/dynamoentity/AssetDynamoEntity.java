package com.sample.dynamodb.asset.dynamoentity;

import com.sample.dynamodb.DynamoUtils;
import com.sample.dynamodb.asset.domain.Asset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class AssetDynamoEntity {

    private static final String KEY_PREFIX = "ASSET";

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
    public String getSk() {
        return sk;
    }

    @DynamoDbAttribute("GSI1_PK") // GSI 파티션 키 지정
    public String getGsi1Pk() {
        return gsi1Pk;
    }

    public static AssetDynamoEntity from(Asset asset) {
        String assetType = asset.getAssetType() != null ? asset.getAssetType().toString() : null;
        String status = asset.getStatus() != null ? asset.getStatus().toString() : null;

        return AssetDynamoEntity.builder()
                .pk(DynamoUtils.join(KEY_PREFIX, asset.getId()))
                .sk(DynamoUtils.join(KEY_PREFIX, asset.getId()))
                .assetType(assetType)
                .status(status)
                .battery(asset.getBattery())
                .latitude(asset.getLatitude())
                .longitude(asset.getLongitude())
                .build();
    }
}
