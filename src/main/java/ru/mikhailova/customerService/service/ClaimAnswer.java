package ru.mikhailova.customerService.service;

import lombok.Data;

/**
 * Ответ исполнителя на запрос по заявке
 */
@Data
public class ClaimAnswer {
    /**
     * Ответ на запрос по заявке
     */
    private String claimAnswer;
}
