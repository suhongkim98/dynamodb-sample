package com.sample.dynamodb.service.domain;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public class Service {

    private UUID id;
    private ServiceStatus serviceStatus;
    private LocalDateTime serviceCreateDate;
    private LocalDateTime serviceDate;

    @Builder
    public Service(UUID id, ServiceStatus serviceStatus, LocalDateTime serviceCreateDate, LocalDateTime serviceDate) {
        this.id = id;
        this.serviceStatus = serviceStatus;
        this.serviceCreateDate = serviceCreateDate;
        this.serviceDate = serviceDate;

        if (this.serviceStatus == null) {
            this.serviceStatus = ServiceStatus.IN_PROGRESS;
        }
        if (this.serviceCreateDate == null) {
            this.serviceCreateDate = LocalDateTime.now();
        }
        if (this.serviceDate == null) {
            this.serviceDate = LocalDateTime.now();
        }
    }
}
