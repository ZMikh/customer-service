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
    private String customerContactInfo;
    /**
     * Время регистрации заявки
     */
    private LocalDateTime claimRegistrationTime;
    /**
     * Тип заявки
     */
    @Enumerated(value = EnumType.STRING)
    private ClaimType claimType;
    /**
     * Статус заявки
     */
    @Enumerated(value = EnumType.STRING)
    private ClaimState claimState;
    /**
     * Описание заявки
     */
    private String description;
    /**
     * Заявка переназначенна на специалиста по работе со специфичными запросами
     */
    @Transient
    private Boolean isAssigned;
    /**
     * Решение по заявке принято клиентом
     */
    @Transient
    private Boolean queryIsSolved;
    /**
     * Время завершения заявки
     */
    private LocalDateTime claimFinishedTime;
    /**
     * Исполнитель заявки
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private ClaimExecutor executor;
}
