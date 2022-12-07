package ru.mikhailova.customerService.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("Параметры ответа при регистрации заявки")
public class ClaimRegisterResponseDto {
    @ApiModelProperty("Идентификатор заявки")
    private Long id;
    @ApiModelProperty("Контактные данные клиента")
    private String customerContactInfo;
    @ApiModelProperty("Время отправки обращения")
    private LocalDateTime claimCreatedTime;
    @ApiModelProperty("Тип заявки")
    private String claimType;
    @ApiModelProperty("Статус заявки")
    private String claimState;
    @ApiModelProperty("Описание заявки")
    private String description;
    @ApiModelProperty("Исполнитель заявки")
    private ClaimExecutorDto executor;
    @ApiModelProperty("Заявка переназначена на специалиста по работе со специфичными запросами")
    private Boolean isAssigned;
}
