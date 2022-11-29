package ru.mikhailova.customerService.service;

import lombok.Data;

/**
 * Параметры обновления заявки
 */
@Data
public class ClaimUpdate {
    /**
     * Описание заявки
     */
    private String description;
}
