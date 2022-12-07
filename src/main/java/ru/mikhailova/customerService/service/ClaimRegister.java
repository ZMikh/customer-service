package ru.mikhailova.customerService.service;

import lombok.Data;

/**
 *  Параметры регистрации заявки
 */
@Data
public class ClaimRegister {
    /**
     * Заявка переназначена на специалиста по работе со специфичными запросами
     */
    private Boolean isAssigned;
}
