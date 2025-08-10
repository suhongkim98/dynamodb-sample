package com.sample.dynamodb.asset;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/assets")
    public void createAsset(@RequestBody CreateAssetRequest payload) {
        assetService.createAsset(payload);
    }
}
