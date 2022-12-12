package ru.mikhailova.customerService.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.ResultActions;
import ru.mikhailova.customerService.controller.dto.*;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimState;
import ru.mikhailova.customerService.listener.dto.ClaimResolutionDto;
import ru.mikhailova.customerService.repository.ClaimRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClaimIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private ClaimRepository claimRepository;

    private final TypeReference<List<ClaimDto>> typeReference = new TypeReference<List<ClaimDto>>() {
    };
    private Claim claim;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.new-claim}")
    private String newClaimTopic;

    @Value("${kafka.topic.claim-client-resolution}")
    private String claimClientResolutionTopic;


    @BeforeEach
    void setUp() throws Exception {
        ClaimStartRequestDto dto = new ClaimStartRequestDto();
        dto.setClaimType("OFFER");
        dto.setCustomerContactInfo("+147896325");
        dto.setDescription("Application upgrade");

        String responseAsString = performStart(dto);
        claim = claimRepository.findById(getId(responseAsString)).orElseThrow();
    }

    @AfterEach
    void tearDown() {
        claimRepository.deleteAll();
    }

    @Test
    void couldRegisterClaim() throws Exception {
        ClaimRegisterRequestDto dto = new ClaimRegisterRequestDto();
        dto.setIsAssigned(true);

        ClaimRegisterResponseDto responseDto = performRegistration(claim.getId(), dto, ClaimRegisterResponseDto.class);

        assertThat(responseDto.getClaimCreatedTime()).isBetween(
                LocalDateTime.of(2022, 12, 6, 0, 0, 0),
                LocalDateTime.of(2023, 12, 6, 0, 0, 0)
        );
        assertThat(responseDto.getClaimState()).isEqualTo(ClaimState.ASSIGNED.toString());
    }

    @Test
    void couldExecuteGeneralClaim() throws Exception {
        //when
        ClaimRegisterRequestDto registerRequestDto = new ClaimRegisterRequestDto();
        registerRequestDto.setIsAssigned(false);
        performRegistration(claim.getId(), registerRequestDto, ClaimRegisterResponseDto.class);

        ClaimAnswerRequestDto answerRequestDto = new ClaimAnswerRequestDto();
        answerRequestDto.setClaimAnswer("Will be updated");

        //when
        ClaimDto dto = performExecuteBasic(claim.getId(), answerRequestDto, ClaimDto.class);

        //then
        Claim executedClaim = claimRepository.findById(claim.getId()).orElseThrow();
        assertThat(executedClaim.getClaimState()).isEqualTo(ClaimState.PROCESSED);
        assertThat(dto.getClaimState()).isEqualTo(ClaimState.PROCESSED.toString());
    }

    @Test
    void couldExecuteClaimByAnotherExecutor() throws Exception {
        //given
        ClaimRegisterRequestDto dto = new ClaimRegisterRequestDto();
        dto.setIsAssigned(true);
        performRegistration(claim.getId(), dto, ClaimRegisterResponseDto.class);

        ClaimAnswerRequestDto answerRequestDto = new ClaimAnswerRequestDto();
        answerRequestDto.setClaimAnswer("Will be updated");

        //when
        performExecuteAssigned(claim.getId(), answerRequestDto, ClaimDto.class);

        //then
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

        assertThat(dto.getClaimCreatedTime()).isBefore(LocalDateTime.now());
    }

    @Test
    void couldDeleteClaimById() throws Exception {
        performDelete(claim.getId());

        assertThat(claimRepository.existsById(claim.getId())).isFalse();
    }

    @Test
    void couldUpdateClaim() throws Exception {
        ClaimUpdateRequestDto dto = new ClaimUpdateRequestDto();
        dto.setNotes("Change interface");

        ClaimDto claimDto = performUpdate(claim.getId(), dto, ClaimDto.class);

        assertThat(claimDto.getNotes()).isEqualTo("Change interface");
    }

    @Test
    void couldCheckClaimIsNotResolved() throws Exception {
        //given
        performRegistration(claim.getId(), new ClaimRegisterRequestDto(), ClaimRegisterResponseDto.class);

        ClaimAnswerRequestDto answerRequestDto = new ClaimAnswerRequestDto();
        answerRequestDto.setClaimAnswer("Will be updated");
        performExecuteBasic(claim.getId(), answerRequestDto, ClaimDto.class);

        ClaimResolutionDto resolutionDto = new ClaimResolutionDto();
        resolutionDto.setId(claim.getId());
        resolutionDto.setQueryIsSolved(false);

        //when
        kafkaTemplate.send(claimClientResolutionTopic, resolutionDto);
        Thread.sleep(1000);

        //then
        Claim claimResolved = claimRepository.findById(claim.getId()).orElseThrow();
        assertThat(claimResolved.getClaimState()).isEqualTo(ClaimState.NOT_RESOLVED);
    }

    @Test
    void couldCheckClaimIsResolved() throws Exception {
        //given
        ClaimRegisterRequestDto dto = new ClaimRegisterRequestDto();
        dto.setIsAssigned(true);
        performRegistration(claim.getId(), dto, ClaimRegisterResponseDto.class);

        ClaimAnswerRequestDto answerRequestDto = new ClaimAnswerRequestDto();
        answerRequestDto.setClaimAnswer("Won't be updated");
        performExecuteAssigned(claim.getId(), answerRequestDto, ClaimDto.class);

        ClaimResolutionDto resolutionDto = new ClaimResolutionDto();
        resolutionDto.setId(claim.getId());
        resolutionDto.setQueryIsSolved(true);

        //when
        kafkaTemplate.send(claimClientResolutionTopic, resolutionDto);
        Thread.sleep(1000);

        //then
        Claim claimResolved = claimRepository.findById(claim.getId()).orElseThrow();
        assertThat(claimResolved.getClaimState()).isEqualTo(ClaimState.FINISHED);
    }

    @Test
    void couldCheckExceptionHandlerIfClaimAnswerIsNull() throws Exception {
        performRegistration(claim.getId(), new ClaimRegisterRequestDto(), ClaimRegisterResponseDto.class);

        ResultActions resultActions = performClaimAnswerException(claim.getId(), "/assigned/",
                new ClaimAnswerRequestDto(), status().isNotFound());

        assertThat(resultActions.andReturn().getResponse().getContentAsString()).isEqualTo("There's no answer to claim");
    }

    protected Long getId(String responseAsString) {
        String responseWithRemovedPartBeforeId = responseAsString.replace("Claim with id: ", "");
        String stringId = responseWithRemovedPartBeforeId.replace(" is sent to registration", "");
        return Long.parseLong(stringId);
    }
}
