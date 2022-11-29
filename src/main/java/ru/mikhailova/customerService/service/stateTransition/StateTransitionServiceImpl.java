package ru.mikhailova.customerService.service.stateTransition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimState;
import ru.mikhailova.customerService.repository.ClaimRepository;

@Service
@RequiredArgsConstructor
public class StateTransitionServiceImpl implements StateTransitionService {
    private final ClaimRepository repository;

    @Transactional
    @Override
    public void claimStateTransit(ClaimState claimState, Long id) {
        Claim claim = repository.findById(id).orElseThrow();
        claim.setClaimState(claimState);
        repository.save(claim);
    }
}
