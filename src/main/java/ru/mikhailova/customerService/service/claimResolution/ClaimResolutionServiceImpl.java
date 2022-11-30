package ru.mikhailova.customerService.service.claimResolution;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.repository.ClaimRepository;

@Service
public class ClaimResolutionServiceImpl implements ClaimResolutionService {
    private final ClaimRepository claimRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;
    private final ClaimMapper mapper;

    public ClaimResolutionServiceImpl(ClaimRepository claimRepository,
                                      KafkaTemplate<String, Object> kafkaTemplate,
                                      @Value("${kafka.topic.claim-resolution}") String topic,
                                      ClaimMapper mapper) {
        this.claimRepository = claimRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public void claimResolveResult(Long id) {
        Claim claim = claimRepository.findById(id).orElseThrow();
        kafkaTemplate.send(topic, mapper.toClaimResolutionDto(claim));
    }
}
