package ru.mikhailova.customerService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimExecutor;
import ru.mikhailova.customerService.domain.ClaimUpdate;
import ru.mikhailova.customerService.repository.ClaimRepository;
import ru.mikhailova.customerService.repository.ExecutorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final ClaimRepository claimRepository;
    private final ExecutorRepository executorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Claim getClaimById(Long id) {
        return claimRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Claim addClaim(Claim claim, Long executorId) {
        ClaimExecutor executor = executorRepository.findById(executorId).orElseThrow();
        claim.setExecutor(executor);
        return claimRepository.save(claim);
    }

    @Transactional
    @Override
    public void deleteClaimById(Long id) {
        claimRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Claim updateClaimById(Long id, ClaimUpdate claimUpdate) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setDescription(claimUpdate.getDescription());
        claimRepository.save(claim);
        return claim;
    }
}
