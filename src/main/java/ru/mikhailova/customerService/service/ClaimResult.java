package ru.mikhailova.customerService.service;

import lombok.Data;

/**
 * Решение клиента по ответу на заявку/обращение
 */
@Data
public class ClaimResult {
    /**
     * Решение по заявке принято клиентом
     */
    private Boolean queryIsSolved;
    /**
     * Ответ клиента на решение по заявке
     */
    private String clientResponseOnClaimAnswer;
}
