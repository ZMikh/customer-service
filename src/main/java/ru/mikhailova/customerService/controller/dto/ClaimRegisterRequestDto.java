package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Параметры для запроса при регистрации заявки")
public class ClaimRegisterRequestDto {
    @ApiModelProperty("Заявка переназначена на специалиста по работе со специфичными запросами")
    private Boolean isAssigned;
}
