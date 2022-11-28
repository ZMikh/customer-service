package ru.mikhailova.customerService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimExecutor;
import ru.mikhailova.customerService.domain.ClaimUpdate;
import ru.mikhailova.customerService.repository.ClaimRepository;
import ru.mikhailova.customerService.repository.ExecutorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupportServiceImpl implements SupportService {
    private final ClaimRepository claimRepository;
    private final ExecutorRepository executorRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;


    @Transactional
    @Override
    public void startSupport(Claim claim) {
        Claim savedClaim = claimRepository.save(claim);

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", savedClaim.getId());
        runtimeService.startProcessInstanceByKey("support", variables);
    }

    @Transactional
    @Override
    public Claim registerClaim(Long id) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setExecutor(getRandomExecutor());
        claimRepository.save(claim);

        Task task = taskService.createTaskQuery()
                .taskDefinitionKey("claimRegistration")
                .processVariableValueEquals("id", claim.getId())
                .singleResult();
        if (task == null) {
            throw new RuntimeException();
        }
        taskService.complete(task.getId());
        return claim;
    }

    @Transactional(readOnly = true)
    private ClaimExecutor getRandomExecutor() {
        Long executorId = (long) ThreadLocalRandom.current().nextInt(1, executorRepository.findAll().size() + 1);
        return executorRepository.findById(executorId).orElseThrow();
    }

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
