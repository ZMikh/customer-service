package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("Параметры регистрации заявки для отправки клиенту")
public class ClaimRegistrationInfoToCustomerDto {
    @ApiModelProperty("Идентификатор заявки")
    private Long id;
    @ApiModelProperty("Время отправки обращения")
    private LocalDateTime claimCreatedTime;
    @ApiModelProperty("Статус заявки")
    private String claimState;
}
