package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Параметры для запроса при старте процесса")
public class ClaimStartRequestDto {
    @ApiModelProperty("Контактные данные клиента")
    private String customerContactInfo;
    @ApiModelProperty("Тип заявки")
    private String claimType;
    @ApiModelProperty("Описание заявки")
    private String description;
}
