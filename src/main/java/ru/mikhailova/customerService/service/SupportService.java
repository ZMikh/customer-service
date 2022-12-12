package ru.mikhailova.customerService.service;

import ru.mikhailova.customerService.domain.Claim;

import java.util.List;

/**
 * Служба по работе с клиентами
 */
public interface SupportService {
    /**
     * Получить все заявки
     *
     * @return список заявок
     */
    List<Claim> getAllClaims();

    /**
     * Получить все заявки
     *
     * @param id идентификатор заявки
     * @return заявка
     */
    Claim getClaimById(Long id);

    /**
     * Удалить заявку
     *
     * @param id идентификатор заявки
     */
    void deleteClaimById(Long id);

    /**
     * Обновить заявку
     *
     * @param id          идентификатор заявки
     * @param claimUpdate параметры подлежащие обновлению
     * @return заявка
     */

    Claim updateClaimById(Long id, ClaimUpdate claimUpdate);

    /**
     * Стартовать работу службы поддержки
     *
     * @param claim заявка
     */
    void startSupport(Claim claim);

    /**
     * Зарегистрировать заявку
     *
     * @param id идентификатор заявки присвоенный на момент старта работы службы поддержки
     * @return заявка
     */
    Claim registerClaim(Long id, ClaimRegister claimRegister);

    /**
     * Выполнить заявку общего типа
     *
     * @param id идентификатор заявки
     * @param claimAnswer ответ на запрос по заявке
     * @return заявка
     */
    Claim executeBasicClaim(Long id, ClaimAnswer claimAnswer);

    /**
     * Выполнить заявку переназначенную на специалиста по работе со специфичными запросами
     *
     * @param id идентификатор заявки
     * @param claimAnswer ответ на запрос по заявке
     * @return заявка
     */
    Claim executeAssignedClaim(Long id, ClaimAnswer claimAnswer);
}
