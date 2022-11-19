package ru.mikhailova.domain;

import javax.persistence.*;
import java.util.List;
@Entity
public class ClaimExecutor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "executor_id")
    @SequenceGenerator(name = "executor_id", sequenceName = "executor_sequence", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private List<Claim> claims;
}
