package ru.mikhailova.customerService.service.sendToCustomerClaimRegistrationInformation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.repository.ClaimRepository;

@Service
@Slf4j
public class SendToCustomerClaimAcceptedMessageDelegateServiceImpl implements SendToCustomerClaimAcceptedMessageDelegateService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ClaimRepository repository;
    private final String topic;
    private final ClaimMapper mapper;

    public SendToCustomerClaimAcceptedMessageDelegateServiceImpl(KafkaTemplate<String, Object> kafkaTemplate,
                                                                 ClaimRepository repository,
                                                                 @Value("${kafka.topic.claim-acceptance}") String topic, ClaimMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
        this.topic = topic;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public void sendCustomerClaimAcceptance(Long id) {
        Claim claim = repository.findById(id).orElseThrow();
        log.info("Claim registration details with id: {} is sent", claim.getId());


        kafkaTemplate.send(topic, mapper.toClaimRegisterResponseDto(claim));
    }
}

