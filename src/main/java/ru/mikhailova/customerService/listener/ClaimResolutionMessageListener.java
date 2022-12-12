package ru.mikhailova.customerService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.listener.dto.ClaimResolutionDto;

@Component
@RequiredArgsConstructor
public class ClaimResolutionMessageListener {
    private final RuntimeService runtimeService;

    @KafkaListener(topics = "${kafka.topic.claim-client-resolution}", groupId = "${spring.kafka.consumer.group-id}")
    public void claimResolutionListen(JsonNode dto) throws JsonProcessingException {
        ClaimResolutionDto resultDto = new ObjectMapper().treeToValue(dto, ClaimResolutionDto.class);

        runtimeService.createMessageCorrelation("claim_resolution_message")
                .processInstanceVariableEquals("id", resultDto.getId())
                .setVariable("queryIsSolved", resultDto.getQueryIsSolved())
                .correlateWithResult();
    }
}
