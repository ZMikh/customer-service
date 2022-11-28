package ru.mikhailova.customerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhailova.customerService.domain.ClaimExecutor;

@Repository
public interface ExecutorRepository extends JpaRepository<ClaimExecutor, Long> {
}
