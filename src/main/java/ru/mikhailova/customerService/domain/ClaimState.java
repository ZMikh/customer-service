package ru.mikhailova.customerService.domain;

/**
 * Статусы заявки
 */
public enum ClaimState {
    /**
     * Новая заявка
     */
    NEW,
    /**
     * Заяка принята
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
