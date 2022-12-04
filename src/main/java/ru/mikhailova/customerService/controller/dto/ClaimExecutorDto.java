package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Исполнитель заявки")
public class ClaimExecutorDto {
    @ApiModelProperty("Идентификатор исполнителя заявки")
    private Long id;
    @ApiModelProperty("Имя исполнителя заявки")
    private String name;
}
