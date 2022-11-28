package ru.mikhailova.customerService.controller.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.controller.dto.ClaimCreateRequestDto;
import ru.mikhailova.customerService.controller.dto.ClaimDto;
import ru.mikhailova.customerService.controller.dto.ClaimRegisterResponseDto;
import ru.mikhailova.customerService.controller.dto.ClaimUpdateRequestDto;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.mikhailova.customerService.domain.ClaimState.NEW;

@Component
public class ClaimMapper {
    private final ModelMapper mapper;

    public ClaimMapper() {
        this.mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(ClaimCreateRequestDto.class, Claim.class)
                .addMapping(dto -> NEW, Claim::setClaimState);
        mapper.typeMap(Claim.class, ClaimRegisterResponseDto.class)
                .addMapping(registrationTime -> LocalDateTime.now(), ClaimRegisterResponseDto::setClaimRegistrationTime);
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

    public Claim toClaim(ClaimCreateRequestDto dto) {
        return mapper.map(dto, Claim.class);
    }

    public ClaimRegisterResponseDto toClaimRegisterResponseDto(Claim claim) {
        return mapper.map(claim, ClaimRegisterResponseDto.class);
    }
}
