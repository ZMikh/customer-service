package ru.mikhailova.customerService.service.claimRegistrationInformation;

/**
 * Отправка заявителю уведомления о получении заявки
 */
public interface SendToCustomerClaimAcceptedMessageService {
    /**
     * Отправка заявителю уведомления
     * @param id идентификатор заявки
     */
    void sendCustomerClaimAcceptance(Long id);

}
