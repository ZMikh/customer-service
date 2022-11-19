package ru.mikhailova.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "support_id")
    @SequenceGenerator(name = "support_id", sequenceName = "support_sequence", allocationSize = 1)
    private Long id;
    private LocalDateTime claimRegistrationTime;
    @Enumerated(value = EnumType.STRING)
    private ClaimType claimType;
    @Enumerated(value = EnumType.STRING)
    private ClaimState claimState;
    private String description;
    private ClaimExecutor executorId;

}
