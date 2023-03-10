package ru.mikhailova.customerService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Исполнитель заявки
 */
@Entity
@Setter
@Getter
public class ClaimExecutor {
    /**
     * Идентификатор исполнителя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "executor_id")
    @SequenceGenerator(name = "executor_id", sequenceName = "executor_sequence", allocationSize = 1)
    private Long id;
    /**
     * Имя исполнителя
     */
    private String name;
    /**
     * Исполнитель является специалистом по общим вопросам
     */
    private Boolean generalSpecialist;
    /**
     * Список заявок исполнителя
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "executor")
    private List<Claim> claims;
}
