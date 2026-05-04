package com.sample.dynamodb.assetservice;

import com.sample.dynamodb.assetservice.domain.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssetServiceController {

    private final AssetServiceService assetServiceService;

    @PostMapping("/services")
    public void createService(@RequestBody CreateAssetServiceRequest request) {
        assetServiceService.createServiceStatusOpen(request);
    }

    @GetMapping("services/status-open")
    public List<AssetService> getServicesStatusOpen() {
        return assetServiceService.getServicesStatusOpen();
    }
}
