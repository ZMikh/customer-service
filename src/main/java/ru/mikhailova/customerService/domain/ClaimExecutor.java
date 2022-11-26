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
@NamedEntityGraph(name = "Executor.claims",
        attributeNodes = @NamedAttributeNode(value = "claims"))
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
     * Список заявок исполнителя
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "executor")
    private List<Claim> claims;
}
