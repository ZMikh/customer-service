package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Параметры для запроса при обновлении заявки")
public class ClaimUpdateRequestDto {
    @ApiModelProperty("Примечание")
    private String notes;
}
