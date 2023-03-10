package ru.mikhailova.customerService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Заявка/обращение
 */
@Entity
@Setter
@Getter
@NamedEntityGraph(name = "Claim.executor",
        attributeNodes = @NamedAttributeNode(value = "executor"))
public class Claim {
    /**
     * Идентификатор заявки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_id")
    @SequenceGenerator(name = "claim_id", sequenceName = "claim_sequence", allocationSize = 1)
    private Long id;
    /**
     * Контактные данные клиента
     */
    @Column(nullable = false)
    private String customerContactInfo;
    /**
     * Время отправки обращения
     */
    private LocalDateTime claimCreatedTime;
    /**
     * Тип заявки
     */
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ClaimType claimType;
    /**
     * Статус заявки
     */
    @Enumerated(value = EnumType.STRING)
    private ClaimState claimState;
    /**
     * Описание заявки
     */
    @Column(nullable = false)
    private String description;
    /**
     * Заявка переназначена на специалиста по работе со специфичными запросами
     */
    @Transient
    private Boolean isAssigned;
    /**
     * Решение по заявке принято клиентом
     */
    private Boolean queryIsSolved;
    /**
     * Время завершения заявки
     */
    private LocalDateTime claimFinishedTime;
    /**
     * Ответ на запрос по заявке
     */
    private String claimAnswer;
    /**
     * Примечание
     */
    private String notes;
    /**
     * Ответ клиента на решение по заявке
     */
    private String clientResponseOnClaimAnswer;
    /**
     * Исполнитель заявки
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private ClaimExecutor executor;
}
