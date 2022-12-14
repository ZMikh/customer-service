package ru.mikhailova.customerService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.listener.dto.ClaimResolutionDto;
import ru.mikhailova.customerService.listener.mapper.ListenerMapper;
import ru.mikhailova.customerService.service.ClaimResult;
import ru.mikhailova.customerService.service.SupportService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClaimResolutionMessageListener {
    private final ListenerMapper mapper;
    private final SupportService service;

    @KafkaListener(topics = "${kafka.topic.claim-client-resolution}", groupId = "${spring.kafka.consumer.group-id}")
    public void claimResolutionListen(JsonNode dto) throws JsonProcessingException {
        ClaimResolutionDto resultDto = new ObjectMapper().treeToValue(dto, ClaimResolutionDto.class);
        ClaimResult claimResult = mapper.resolutionDtoToClaimResult(resultDto);
        service.addClaimResolutionResult(resultDto.getId(), claimResult);
        log.info("Resolution claim message with parameters {}", resultDto);
    }
}
