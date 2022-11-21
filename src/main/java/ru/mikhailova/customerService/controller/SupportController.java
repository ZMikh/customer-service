package ru.mikhailova.customerService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mikhailova.customerService.controller.dto.ClaimCreateRequestDto;
import ru.mikhailova.customerService.controller.dto.ClaimDto;
import ru.mikhailova.customerService.controller.dto.ClaimUpdateRequestDto;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimUpdate;
import ru.mikhailova.customerService.service.SupportService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/support/claim")
@RequiredArgsConstructor
public class SupportController {
    private final SupportService service;
    private final ClaimMapper mapper;

    @GetMapping("/get-all")
    public List<ClaimDto> getAll() {
        return mapper.toDtoList(service.getAllClaims());
    }

    @GetMapping("/get-by-id/{id}")
    public ClaimDto getById(@PathVariable Long id) {
        return mapper.toDto(service.getClaimById(id));
    }

    @PostMapping("/create")
    public ClaimDto add(@RequestBody ClaimCreateRequestDto claimCreateRequestDto,
                        @PathParam("executorId") Long executorId) {
        Claim claim = mapper.toEntity(claimCreateRequestDto);
        return mapper.toDto(service.addClaim(claim, executorId));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteClaimById(id);
    }

    @PatchMapping("/update-by-id/{id}")
    public ClaimDto update(@PathVariable Long id, @RequestBody ClaimUpdateRequestDto claimUpdateRequestDto) {
        ClaimUpdate claimUpdate = mapper.toDomain(claimUpdateRequestDto);
        return mapper.toDto(service.updateClaimById(id, claimUpdate));
    }
}
