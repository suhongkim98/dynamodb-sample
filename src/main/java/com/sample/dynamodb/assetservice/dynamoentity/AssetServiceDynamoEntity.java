package com.sample.dynamodb.assetservice.dynamoentity;

import com.sample.dynamodb.DynamoUtils;
import com.sample.dynamodb.assetservice.domain.AssetService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class AssetServiceDynamoEntity {

    private static final String ASSET_KEY_PREFIX = "ASSET";
    private static final String SERVICE_KEY_PREFIX = "SERVICE";

    private String pk;
    private String sk;

    private String gsi1Pk;

    private String serviceStatus;
    private LocalDateTime serviceCreateDate;
    private LocalDateTime serviceDate;

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

    public static AssetServiceDynamoEntity from(AssetService assetService) {
        String serviceStatus = assetService.getServiceStatus() != null ? assetService.getServiceStatus().toString() : null;
        return AssetServiceDynamoEntity.builder()
                .pk(DynamoUtils.join(ASSET_KEY_PREFIX, assetService.getAssetId()))
                .sk(DynamoUtils.join(SERVICE_KEY_PREFIX, assetService.getId()))
                .serviceStatus(serviceStatus)
                .serviceCreateDate(assetService.getServiceCreateDate())
                .serviceDate(assetService.getServiceDate())
                .build();
    }
}

