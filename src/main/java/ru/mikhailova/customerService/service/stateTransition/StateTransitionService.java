package ru.mikhailova.customerService.service.stateTransition;

import ru.mikhailova.customerService.domain.ClaimState;

/**
 * Переход между состояниями процесса
 */
public interface StateTransitionService {
    /**
     * Перейти в состояние, соответствующее currentActivityId
     * @param claimState состояние/статус заявки
     * @param id идентификатор заявки
     */
    void claimStateTransit(ClaimState claimState, Long id);
}
