package ru.mikhailova.customerService.domain;

/**
 * Статус заявки
 */
public enum ClaimState {
    /**
     * Новая заявка
     */
    NEW,
    /**
     * Заявка сформирована
     */
    CREATED,
    /**
     * Заявка принята
     */
    ACCEPTED,
    /**
     * Заявка назначена
     */
    ASSIGNED,
    /**
     * Заявка обработана
     */
    PROCESSED,
    /**
     * Вопрос по заявке не решен
     */
    NOT_RESOLVED,
    /**
     * Заявка выполнена
     */
    FINISHED
}
