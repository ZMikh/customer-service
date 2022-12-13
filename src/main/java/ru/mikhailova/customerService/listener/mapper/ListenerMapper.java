package ru.mikhailova.customerService.listener.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.controller.dto.ClaimStartRequestDto;
import ru.mikhailova.customerService.domain.Claim;

@Component
public class ListenerMapper {
    private final ModelMapper mapper;

    public ListenerMapper() {
        this.mapper = new ModelMapper();
    }

    public Claim toClaim(ClaimStartRequestDto dto) {
        return mapper.map(dto, Claim.class);
    }
}
