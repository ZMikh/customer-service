package ru.mikhailova.customerService.service.claimResolution;

/**
 * Получение уведомления о решении вопроса от заявителя
 */
public interface ClaimResolutionService {
    /**
     * Получение от заявителя результата по обработке заявки
     * @param id идентификатор заявки
     */
    void claimResolveResult(Long id);
}
