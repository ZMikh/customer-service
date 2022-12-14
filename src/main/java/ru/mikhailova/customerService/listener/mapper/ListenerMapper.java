package ru.mikhailova.customerService.listener.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.controller.dto.ClaimStartRequestDto;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.listener.dto.ClaimResolutionDto;
import ru.mikhailova.customerService.service.ClaimResult;

@Component
public class ListenerMapper {
    private final ModelMapper mapper;

    public ListenerMapper() {
        this.mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Claim startRequestDtoToClaim(ClaimStartRequestDto dto) {
        return mapper.map(dto, Claim.class);
    }
    public ClaimResult resolutionDtoToClaimResult(ClaimResolutionDto dto) {
        return mapper.map(dto, ClaimResult.class);
    }
}
