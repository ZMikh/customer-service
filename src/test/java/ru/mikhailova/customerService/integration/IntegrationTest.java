package ru.mikhailova.customerService.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mikhailova.customerService.controller.dto.*;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimExecutor;
import ru.mikhailova.customerService.domain.ClaimState;
import ru.mikhailova.customerService.repository.ClaimRepository;
import ru.mikhailova.customerService.repository.ExecutorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ExecutorRepository executorRepository;
    private final TypeReference<List<ClaimDto>> typeReference = new TypeReference<List<ClaimDto>>() {
    };
    private Claim claim;

    @BeforeEach
    void setUp() throws Exception {
        ClaimStartRequestDto dto = new ClaimStartRequestDto();
        dto.setClaimType("OFFER");
        dto.setCustomerContactInfo("+147896325");
        dto.setDescription("Application upgrade");

        String start = performStart(dto);
        String s = start.replace("Claim with id: ", "");
        String stringId = s.replace(" is sent to registration", "");
        Long id = Long.parseLong(stringId);
        claim = claimRepository.findById(id).orElseThrow();
    }

    protected Long getId(String start) {
        String s = start.replace("Claim with id: ", "");
        String stringId = s.replace(" is sent to registration", "");

        return Long.parseLong(stringId);
    }

    @AfterEach
    void tearDown() {
        claimRepository.deleteAll();
    }


    @Test
    void couldRegisterClaim() throws Exception {
        ClaimRegisterRequestDto dto = new ClaimRegisterRequestDto();
        claim.setExecutor(getRandomExecutor());
        claim.setIsAssigned(true);
        claimRepository.save(claim);
        ClaimRegisterResponseDto responseDto = performRegistration(claim.getId(), dto, ClaimRegisterResponseDto.class);
        assertThat(responseDto.getClaimRegistrationTime()).isBetween(
                LocalDateTime.of(2022, 12, 6, 0, 0, 0),
                LocalDateTime.of(2022, 12, 7, 0, 0, 0)
        );
    }

    @Test
    void couldExecuteGeneralClaim() throws Exception {
        ClaimRegisterRequestDto dto = new ClaimRegisterRequestDto();
        claim.setExecutor(getRandomExecutor());

        performRegistration(claim.getId(), dto, ClaimRegisterResponseDto.class);
        performExecuteBasic(claim.getId());

        Claim executedClaim = claimRepository.findById(claim.getId()).orElseThrow();
        assertThat(executedClaim.getClaimState()).isEqualTo(ClaimState.PROCESSED);
    }

    @Test
    void couldExecuteClaimByAnotherExecutor() throws Exception {
        ClaimRegisterRequestDto dto = new ClaimRegisterRequestDto();
        dto.setIsAssigned(true);
        claim.setExecutor(getRandomExecutor());

        performRegistration(claim.getId(), dto, ClaimRegisterResponseDto.class);
        performExecuteAssigned(claim.getId());

        Claim executedClaim = claimRepository.findById(claim.getId()).orElseThrow();
        assertThat(executedClaim.getClaimFinishedTime()).isNotNull();
    }

    @Test
    void couldGetAllClaims() throws Exception {
        List<ClaimDto> claims = performGetAll(typeReference);

        assertThat(claims).isNotNull();
    }

    @Test
    void couldGetClaimById() throws Exception {
        ClaimDto dto = performGetById(claim.getId(), ClaimDto.class);

        assertThat(dto.getClaimRegistrationTime()).isBefore(LocalDateTime.now());
    }

    @Test
    void couldDeleteClaimById() throws Exception {
        performDelete(claim.getId());

        assertThat(claimRepository.existsById(claim.getId())).isFalse();
    }

    @Test
    void couldUpdateClaim() throws Exception {
        ClaimUpdateRequestDto dto = new ClaimUpdateRequestDto();
        dto.setDescription("Change interface");

        ClaimDto claimDto = performUpdate(claim.getId(), dto, ClaimDto.class);

        assertThat(claimDto.getDescription()).isEqualTo("Change interface");
    }

    protected ClaimExecutor getRandomExecutor() {
        Long executorId = (long) ThreadLocalRandom.current().nextInt(1, executorRepository.findAll().size() + 1);
        return executorRepository.findById(executorId).orElseThrow();
    }
}
