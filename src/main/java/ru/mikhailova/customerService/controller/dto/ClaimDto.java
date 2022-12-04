package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("Заявка")
public class ClaimDto {
    @ApiModelProperty("Идентификатор заявки")
    private Long id;
    @ApiModelProperty("Контактные данные клиента")
    private String customerContactInfo;
    @ApiModelProperty("Время регистрации заявки")
    private LocalDateTime claimRegistrationTime;
    @ApiModelProperty("Тип заявки")
    private String claimType;
    @ApiModelProperty("Статус заявки")
    private String claimState;
    @ApiModelProperty("Описание заявки")
    private String description;
    @ApiModelProperty("Время завершения исполнения заявки")
    private LocalDateTime claimFinishedTime;
    @ApiModelProperty("Исполнитель заявки")
    private ClaimExecutorDto executor;
    @ApiModelProperty("Решение по заявке принято клиентом")
    private Boolean queryIsSolved;
}
