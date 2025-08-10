package com.sample.dynamodb.asset;

public record CreateAssetRequest(String assetType, String assetStatus, double latitude, double longitude) {
}
