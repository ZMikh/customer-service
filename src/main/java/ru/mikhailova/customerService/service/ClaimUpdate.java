package ru.mikhailova.customerService.service;

import lombok.Data;

/**
 * Параметры обновления заявки
 */
@Data
public class ClaimUpdate {
    /**
     * Примечание
     */
    private String notes;
}
