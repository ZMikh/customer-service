package ru.mikhailova.customerService.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.delegate.mapper.StateMapper;
import ru.mikhailova.customerService.service.stateTransition.StateTransitionService;

@Component
@RequiredArgsConstructor
public class StateTransitionDelegate implements JavaDelegate {
    private final StateTransitionService service;
    private final StateMapper mapper;

    @Override
    public void execute(DelegateExecution execution) {
        Long id = (Long) execution.getVariable("id");
        String activityId = execution.getCurrentActivityId();
        service.claimStateTransit(mapper.getStateByEventId(activityId), id);
    }
}
