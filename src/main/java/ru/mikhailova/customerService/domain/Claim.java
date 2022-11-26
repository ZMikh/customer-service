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
     * Время завершения заявки
     */
    private LocalDateTime claimFinishedTime;
    /**
     * Исполнитель заявки
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "executor_id")
    private ClaimExecutor executor;
}
