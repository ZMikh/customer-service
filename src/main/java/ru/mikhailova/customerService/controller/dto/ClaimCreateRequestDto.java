package ru.mikhailova.customerService.controller.dto;

import lombok.Data;

@Data
public class ClaimCreateRequestDto {
    private String customerContactInfo;
    private String claimType;
    private String description;
}
