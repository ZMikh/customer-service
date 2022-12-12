package ru.mikhailova.customerService.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.service.claimAnswer.SendToCustomerClaimAnswerMessageService;

@Component
@RequiredArgsConstructor
public class SendToCustomerClaimAnswerMessageDelegate implements JavaDelegate {
    private final SendToCustomerClaimAnswerMessageService sendToCustomerClaimAnswerMessageService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable("id");
        sendToCustomerClaimAnswerMessageService.sendToCustomerClaimAnswer(id);
    }
}
