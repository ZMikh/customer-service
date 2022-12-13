package ru.mikhailova.customerService.service.claimAnswer;

/**
 * Отправка клиенту ответа по решению заявки
 */
public interface SendToCustomerClaimAnswerMessageService {
    /**
     * Отправить клиенту ответ по решению заявки
     * @param id идентификатор заявки
     */
    void sendToCustomerClaimAnswer(Long id);
}
