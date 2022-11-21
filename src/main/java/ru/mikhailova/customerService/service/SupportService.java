package ru.mikhailova.customerService.service;

import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimUpdate;

import java.util.List;

public interface SupportService {
    List<Claim> getAllClaims();

    Claim getClaimById(Long id);

    Claim addClaim(Claim claim, Long executorId);

    void deleteClaimById(Long id);

    Claim updateClaimById(Long id, ClaimUpdate claimUpdate);
}
