package ru.mikhailova.customerService.domain;

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
