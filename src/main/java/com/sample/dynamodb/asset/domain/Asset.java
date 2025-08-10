package com.sample.dynamodb.asset.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Asset {

    private UUID id;
    private AssetType assetType;
    private AssetStatus status;
    private int battery;
    private double latitude;
    private double longitude;

    @Builder
    public Asset(UUID id, AssetType assetType, AssetStatus status, int battery, double latitude, double longitude) {
        this.id = id;
        this.assetType = assetType;
        this.status = status;
        this.battery = battery;
        this.latitude = latitude;
        this. longitude = longitude;

        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.status == null) {
            this.status = AssetStatus.AVAILABLE;
        }
        if (this.assetType == null) {
            this.assetType = AssetType.EBIKE;
        }
    }
}
