package ru.mikhailova.customerService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Entity
@Setter
@Getter
@NamedEntityGraph(name = "Executor.withClaims", attributeNodes = @NamedAttributeNode(value = "claims"))
public class ClaimExecutor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "executor_id")
    @SequenceGenerator(name = "executor_id", sequenceName = "executor_sequence", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "executor")
    private List<Claim> claims;
}
