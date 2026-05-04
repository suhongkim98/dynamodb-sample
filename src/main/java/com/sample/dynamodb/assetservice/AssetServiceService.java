package com.sample.dynamodb.assetservice;

import com.sample.dynamodb.assetservice.domain.AssetService;
import com.sample.dynamodb.assetservice.domain.AssetServiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceService {

    private final AssetServiceRepository assetServiceRepository;

    public void createServiceStatusOpen(CreateAssetServiceRequest request) {
        AssetService assetService = AssetService.builder()
                .serviceStatus(AssetServiceStatus.OPEN)
                .assetId(request.assetId())
                .serviceCreateDate(LocalDateTime.now())
                .build();

        assetServiceRepository.save(assetService);
    }

    public List<AssetService> getServicesStatusOpen() {
        return assetServiceRepository.getItemsStatusOpen();
    }
}
