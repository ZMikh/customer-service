package ru.mikhailova.customerService.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ClaimExecutionDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {

    }
}
