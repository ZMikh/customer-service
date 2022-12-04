package ru.mikhailova.customerService.listener.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Параметры приемки решения по заявке клиентом")
public class ClaimResolutionDto {
    @ApiModelProperty("Идентификатор заявки")
    private Long id;
    @ApiModelProperty("Решение по заявке принято клиентом")
    private Boolean queryIsSolved;
}
