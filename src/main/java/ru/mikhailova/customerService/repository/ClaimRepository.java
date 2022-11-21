package ru.mikhailova.customerService.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhailova.customerService.domain.Claim;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    @Override
    @EntityGraph(value = "Executor.withClaims")
    Optional<Claim> findById(Long aLong);

    @Override
    @EntityGraph(value = "Executor.withClaims")
    List<Claim> findAll();
}
