package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Параметры ответа по заявке для отправки клиенту")
public class ClaimAnswerToCustomerDto {
    @ApiModelProperty("Идентификатор заявки")
    private Long id;
    @ApiModelProperty("Ответ на запрос по заявке")
    private String claimAnswer;
}
