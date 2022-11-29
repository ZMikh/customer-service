package ru.mikhailova.customerService.controller.dto;

import lombok.Data;

@Data
public class ClaimStartRequestDto {
    private String customerContactInfo;
    private String claimType;
    private String description;
}
