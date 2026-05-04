package com.sample.dynamodb.assetservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AssetService {

    private String id;
    private String assetId;
    private AssetServiceStatus serviceStatus;
    private LocalDateTime serviceCreateDate;
    private LocalDateTime serviceDate;

    @Builder
    public AssetService(String id, String assetId, AssetServiceStatus serviceStatus, LocalDateTime serviceCreateDate, LocalDateTime serviceDate) {
        this.id = id;
        this.assetId = assetId;
        this.serviceStatus = serviceStatus;
        this.serviceCreateDate = serviceCreateDate;
        this.serviceDate = serviceDate;

        if (assetId == null) {
            throw new RuntimeException("assetId is null");
        }
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.serviceStatus == null) {
            this.serviceStatus = AssetServiceStatus.OPEN;
        }
        if (this.serviceCreateDate == null) {
            this.serviceCreateDate = LocalDateTime.now();
        }
    }
}
