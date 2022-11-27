package ru.mikhailova.customerService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.listener.dto.NewClaimDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewClaimMessageListener {
    private final RuntimeService service;

    @KafkaListener(topics = "${kafka.topic.new-claim}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void newClaimListen(JsonNode dto) throws JsonProcessingException {
        NewClaimDto claimDto = new ObjectMapper().treeToValue(dto, NewClaimDto.class);
        log.info("New claim message from {}", claimDto);

        service.createMessageCorrelation("newClaim")
                .processInstanceVariableEquals("id", claimDto.getId())
                .correlateWithResult();
    }
}
