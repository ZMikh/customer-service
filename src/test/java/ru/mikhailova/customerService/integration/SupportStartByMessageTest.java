package ru.mikhailova.customerService.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import ru.mikhailova.customerService.controller.dto.ClaimStartRequestDto;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimState;
import ru.mikhailova.customerService.repository.ClaimRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class SupportStartByMessageTest extends AbstractIntegrationTest {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka.topic.new-claim}")
    private String newClaimTopic;

    @Test
    void checkStartProcessByReceivingMessageFromNewClaimTopic() throws InterruptedException {
        ClaimStartRequestDto dto = new ClaimStartRequestDto();
        dto.setClaimType("ENQUIRY");
        dto.setCustomerContactInfo("+00000000");
        dto.setDescription("Change payer");

        kafkaTemplate.send(newClaimTopic, dto);
        Thread.sleep(1000);

        Claim receivedClaim = claimRepository.findAll().stream()
                .findAny()
                .orElseThrow();
        assertThat(receivedClaim.getClaimState()).isEqualTo(ClaimState.CREATED);
        assertThat(receivedClaim.getClaimCreatedTime()).isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }
}
