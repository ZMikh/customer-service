package ru.mikhailova.customerService.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClaimCreateRequestDto {
    private String customerContactInfo;
    private LocalDateTime claimRegistrationTime;
    private String claimType;
    private String description;
}
