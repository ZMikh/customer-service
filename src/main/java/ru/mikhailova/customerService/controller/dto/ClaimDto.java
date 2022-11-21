package ru.mikhailova.customerService.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClaimDto {
    private Long id;
    private String customerContactInfo;
    private LocalDateTime claimRegistrationTime;
    private String claimType;
    private String claimState;
    private String description;
    private LocalDateTime claimFinishedTime;
    private ClaimExecutorDto executor;
}
