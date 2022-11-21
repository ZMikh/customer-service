package ru.mikhailova.customerService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_id")
    @SequenceGenerator(name = "claim_id", sequenceName = "claim_sequence", allocationSize = 1)
    private Long id;

    private String customerContactInfo;

    private LocalDateTime claimRegistrationTime;

    @Enumerated(value = EnumType.STRING)
    private ClaimType claimType;

    @Enumerated(value = EnumType.STRING)
    private ClaimState claimState;

    private String description;

    private LocalDateTime claimFinishedTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_id")
    private ClaimExecutor executor;
}
