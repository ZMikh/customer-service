package ru.mikhailova.customerService.service.claimAnswer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.repository.ClaimRepository;

import java.util.Optional;

@Service
public class SendToCustomerClaimAnswerMessageServiceImpl implements SendToCustomerClaimAnswerMessageService {
    private final ClaimRepository claimRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;
    private final ClaimMapper mapper;

    public SendToCustomerClaimAnswerMessageServiceImpl(ClaimRepository claimRepository,
                                                       KafkaTemplate<String, Object> kafkaTemplate,
                                                       @Value("${kafka.topic.claim-executor-answer}") String topic,
                                                       ClaimMapper mapper) {
        this.claimRepository = claimRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public void sendToCustomerClaimAnswer(Long id) {
        Optional<Claim> claim = claimRepository.findById(id);
        kafkaTemplate.send(topic, mapper.toClaimAnswerToCustomerDto(claim.orElseThrow()));
    }
}
