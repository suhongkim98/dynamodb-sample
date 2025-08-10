package com.sample.dynamodb.asset;

import com.sample.dynamodb.asset.domain.Asset;
import com.sample.dynamodb.asset.domain.AssetStatus;
import com.sample.dynamodb.asset.domain.AssetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public void createAsset(CreateAssetRequest payload) {
        AssetType assetType = AssetType.valueOf(payload.assetType());
        AssetStatus assetStatus = AssetStatus.valueOf(payload.assetStatus());

        Asset asset = Asset.builder()
                .assetType(assetType)
                .status(assetStatus)
                .longitude(payload.longitude())
                .latitude(payload.latitude())
                .build();

        assetRepository.save(asset);
    }
}
