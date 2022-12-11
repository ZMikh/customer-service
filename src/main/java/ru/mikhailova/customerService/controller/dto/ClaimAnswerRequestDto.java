package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Параметры ответа по решению заявки")
public class ClaimAnswerRequestDto {
    @ApiModelProperty("Ответ на запрос по заявке")
    private String claimAnswer;
}
