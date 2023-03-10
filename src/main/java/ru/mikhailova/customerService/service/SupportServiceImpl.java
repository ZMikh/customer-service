package ru.mikhailova.customerService.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimExecutor;
import ru.mikhailova.customerService.repository.ClaimRepository;
import ru.mikhailova.customerService.repository.ExecutorRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final ClaimRepository claimRepository;
    private final ExecutorRepository executorRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;


    @Transactional
    @Override
    public void startSupport(Claim claim) {
        claim.setClaimCreatedTime(LocalDateTime.now());

        List<ClaimExecutor> claimExecutors = executorRepository.findAll();
        Collections.shuffle(claimExecutors);
        ClaimExecutor executor = claimExecutors.stream()
                .filter(claimExecutor -> claimExecutor.getGeneralSpecialist().equals(true))
                .findAny()
                .orElseThrow();
        claim.setExecutor(executor);

        Claim savedClaim = claimRepository.save(claim);

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", savedClaim.getId());
        runtimeService.startProcessInstanceByMessage("new_claim_message", variables);
    }

    @Transactional
    @Override
    public Claim registerClaim(Long id, ClaimRegister claimRegister) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setIsAssigned(claimRegister.getIsAssigned());

        if (Boolean.TRUE.equals(claimRegister.getIsAssigned()) && claim.getExecutor().getGeneralSpecialist()) {
            List<ClaimExecutor> claimExecutors = executorRepository.findAll();
            Collections.shuffle(claimExecutors);
            ClaimExecutor executor = claimExecutors.stream()
                    .filter(claimExecutor -> claimExecutor.getGeneralSpecialist().equals(false))
                    .findAny()
                    .orElseThrow();
            claim.setExecutor(executor);
        }

        Task task = taskService.createTaskQuery()
                .taskDefinitionKey("claimRegistration")
                .processVariableValueEquals("id", claim.getId())
                .singleResult();
        if (task == null) {
            throw new RuntimeException();
        }
        taskService.setVariable(task.getId(), "isAssigned", claimRegister.getIsAssigned());
        taskService.complete(task.getId());
        return claim;
    }

    @Transactional
    @Override
    public Claim executeBasicClaim(Long id, ClaimAnswer claimAnswer) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setClaimAnswer(claimAnswer.getClaimAnswer());
        claim.setClaimFinishedTime(LocalDateTime.now());

        Task task = taskService.createTaskQuery()
                .taskDefinitionKey("basicClaimExecution")
                .processVariableValueEquals("id", claim.getId())
                .singleResult();
        if (task == null) {
            throw new RuntimeException();
        }
        taskService.complete(task.getId());
        return claim;
    }

    @Transactional
    @Override
    public Claim executeAssignedClaim(Long id, ClaimAnswer claimAnswer) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setClaimAnswer(claimAnswer.getClaimAnswer());
        claim.setClaimFinishedTime(LocalDateTime.now());

        Task task = taskService.createTaskQuery()
                .taskDefinitionKey("specificClaimExecution")
                .processVariableValueEquals("id", claim.getId())
                .singleResult();
        if (task == null) {
            throw new RuntimeException();
        }
        taskService.complete(task.getId());

        return claim;
    }
    @Transactional
    @Override
    public void addClaimResolutionResult(Long id, ClaimResult claimResult) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        claim.setQueryIsSolved(claimResult.getQueryIsSolved());
        claim.setClientResponseOnClaimAnswer(claimResult.getClientResponseOnClaimAnswer());

        Map<String, Object> variables = new HashMap<>();
        variables.put("queryIsSolved", claim.getQueryIsSolved());
        variables.put("clientResponseOnClaimAnswer", claim.getClientResponseOnClaimAnswer());

        runtimeService.createMessageCorrelation("claim_resolution_message")
                .processInstanceVariableEquals("id", id)
                .setVariables(variables)
                .correlateWithResult();
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
        claim.setNotes(claimUpdate.getNotes());
        claimRepository.save(claim);
        return claim;
    }
}
