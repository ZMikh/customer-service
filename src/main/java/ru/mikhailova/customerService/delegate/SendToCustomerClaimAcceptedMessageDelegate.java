package ru.mikhailova.customerService.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.service.claimRegistrationInformation.SendToCustomerClaimAcceptedMessageService;

@Component
@RequiredArgsConstructor
public class SendToCustomerClaimAcceptedMessageDelegate implements JavaDelegate {
    private final SendToCustomerClaimAcceptedMessageService sendToCustomerClaimAcceptedMessageService;

    @Override
    public void execute(DelegateExecution execution) {
        Long id = (Long) execution.getVariable("id");
        sendToCustomerClaimAcceptedMessageService.sendCustomerClaimAcceptance(id);
    }
}
