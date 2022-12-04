package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Парамтры для запроса при обновлении заявки")
public class ClaimUpdateRequestDto {
    @ApiModelProperty("Описание заявки")
    private String description;
}
