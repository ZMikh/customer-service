package ru.mikhailova.customerService.service.stateTransition;

import ru.mikhailova.customerService.domain.ClaimState;

public interface StateTransitionService {
    void claimStateTransit(ClaimState claimState, Long id);
}
