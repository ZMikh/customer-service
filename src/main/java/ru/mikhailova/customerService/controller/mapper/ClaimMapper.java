package ru.mikhailova.customerService.controller.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.controller.dto.*;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.listener.dto.ClaimResolutionDto;
import ru.mikhailova.customerService.service.ClaimAnswer;
import ru.mikhailova.customerService.service.ClaimRegister;
import ru.mikhailova.customerService.service.ClaimUpdate;

import java.util.ArrayList;
import java.util.List;

import static ru.mikhailova.customerService.domain.ClaimState.NEW;

@Component
public class ClaimMapper {
    private final ModelMapper mapper;

    public ClaimMapper() {
        this.mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(ClaimStartRequestDto.class, Claim.class)
                .addMapping(dto -> NEW, Claim::setClaimState);
    }

    public ClaimDto toClaimDto(Claim claim) {
        return mapper.map(claim, ClaimDto.class);
    }

    public ClaimUpdate toClaimUpdate(ClaimUpdateRequestDto dto) {
        return mapper.map(dto, ClaimUpdate.class);
    }

    public List<ClaimDto> toClaimDtoList(List<Claim> claims) {
        List<ClaimDto> dtos = new ArrayList<>();
        for (Claim claim : claims) {
            ClaimDto dto = mapper.map(claim, ClaimDto.class);
            dtos.add(dto);
        }
        return dtos;
    }

    public Claim toClaim(ClaimStartRequestDto dto) {
        return mapper.map(dto, Claim.class);
    }

    public ClaimRegisterResponseDto toClaimRegisterResponseDto(Claim claim) {
        return mapper.map(claim, ClaimRegisterResponseDto.class);
    }

    public ClaimRegister toClaimRegister(ClaimRegisterRequestDto dto) {
        return mapper.map(dto, ClaimRegister.class);
    }

    public ClaimResolutionDto toClaimResolutionDto(Claim claim) {
        return mapper.map(claim, ClaimResolutionDto.class);
    }

    public ClaimAnswer toClaimAnswer(ClaimAnswerRequestDto dto) {
        return mapper.map(dto, ClaimAnswer.class);
    }
    public ClaimAnswerToCustomerDto toClaimAnswerToCustomerDto(Claim claim) {
        return mapper.map(claim, ClaimAnswerToCustomerDto.class);
    }
}
