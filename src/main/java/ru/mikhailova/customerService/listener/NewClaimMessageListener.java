package ru.mikhailova.customerService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.controller.dto.ClaimStartRequestDto;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewClaimMessageListener {
    private final RuntimeService runtimeService;
    private final ClaimMapper mapper;

    @KafkaListener(topics = "${kafka.topic.new-claim}", groupId = "${spring.kafka.consumer.group-id}")
    public void newClaimListen(JsonNode dto) throws JsonProcessingException {
        ClaimStartRequestDto claimDto = new ObjectMapper().treeToValue(dto, ClaimStartRequestDto.class);
        log.info("New claim message from {}", claimDto);

        runtimeService.createMessageCorrelation("new_claim_message")
                .processInstanceVariableEquals("id", mapper.toClaim(claimDto).getId())
                .correlateWithResult();
    }
}
