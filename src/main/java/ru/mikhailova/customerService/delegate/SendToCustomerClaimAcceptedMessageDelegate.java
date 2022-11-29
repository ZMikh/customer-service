package ru.mikhailova.customerService.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.service.sendToCustomerClaimRegistrationInformation.SendToCustomerClaimAcceptedMessageDelegateService;

@Component
@RequiredArgsConstructor
public class SendToCustomerClaimAcceptedMessageDelegate implements JavaDelegate {
    private final SendToCustomerClaimAcceptedMessageDelegateService sendToCustomerClaimAcceptedMessageDelegateService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable("id");
        sendToCustomerClaimAcceptedMessageDelegateService.sendCustomerClaimAcceptance(id);
    }
}
