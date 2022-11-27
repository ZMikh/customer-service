package ru.mikhailova.customerService.listener.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewClaimDto {
    private Long id;
    private String customerContactInfo;
    private LocalDateTime claimRegistrationTime;
    private String claimType;
    private String description;
}
