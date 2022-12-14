package ru.mikhailova.customerService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.controller.dto.ClaimStartRequestDto;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.listener.mapper.ListenerMapper;
import ru.mikhailova.customerService.service.SupportService;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewClaimMessageListener {
    private final SupportService service;
    private final ListenerMapper mapper;

    @KafkaListener(topics = "${kafka.topic.new-claim}", groupId = "${spring.kafka.consumer.group-id}")
    public void newClaimListen(JsonNode dto) throws JsonProcessingException {
        ClaimStartRequestDto claimDto = new ObjectMapper().treeToValue(dto, ClaimStartRequestDto.class);
        Claim claim = mapper.startRequestDtoToClaim(claimDto);
        service.startSupport(claim);
        log.info("New claim message with id: {} and parameters {}", claim.getId(), claimDto);
    }
}
