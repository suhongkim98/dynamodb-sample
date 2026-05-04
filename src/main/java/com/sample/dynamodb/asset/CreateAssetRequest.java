package com.sample.dynamodb.asset;

public record CreateAssetRequest(String assetType,
                                 int battery,
                                 String assetStatus,
                                 double latitude,
                                 double longitude) {
}
