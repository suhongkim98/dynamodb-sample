package com.sample.dynamodb.asset;

import com.sample.dynamodb.asset.domain.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/assets")
    public void createAsset(@RequestBody CreateAssetRequest payload) {
        assetService.createAsset(payload);
    }

    @GetMapping("/assets/low-batteries")
    public List<Asset> getLowBatteries() {
        return assetService.getLowBatteries();
    }
}
