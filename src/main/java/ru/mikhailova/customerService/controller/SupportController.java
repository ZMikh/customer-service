package ru.mikhailova.customerService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mikhailova.customerService.controller.dto.ClaimCreateRequestDto;
import ru.mikhailova.customerService.controller.dto.ClaimDto;
import ru.mikhailova.customerService.controller.dto.ClaimRegisterResponseDto;
import ru.mikhailova.customerService.controller.dto.ClaimUpdateRequestDto;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimUpdate;
import ru.mikhailova.customerService.service.SupportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support/claim")
@RequiredArgsConstructor
public class SupportController {
    private final SupportService service;
    private final ClaimMapper mapper;

    @PostMapping("/start")
    public String start(@RequestBody ClaimCreateRequestDto claimCreateRequestDto) {
        Claim claim = mapper.toClaim(claimCreateRequestDto);
        service.startSupport(claim);
        return "claim with id: " +  claim.getId() + " is sent to registration";
    }

    @PostMapping("/register/{id}")
    public ClaimRegisterResponseDto register(@PathVariable Long id) {
        return mapper.toClaimRegisterResponseDto(service.registerClaim(id));
    }

    @GetMapping("/get-all")
    public List<ClaimDto> getAll() {
        return mapper.toClaimDtoList(service.getAllClaims());
    }

    @GetMapping("/get-by-id/{id}")
    public ClaimDto getById(@PathVariable Long id) {
        return mapper.toClaimDto(service.getClaimById(id));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteClaimById(id);
    }

    @PatchMapping("/update-by-id/{id}")
    public ClaimDto update(@PathVariable Long id,
                           @RequestBody ClaimUpdateRequestDto claimUpdateRequestDto) {
        ClaimUpdate claimUpdate = mapper.toClaimUpdate(claimUpdateRequestDto);
        return mapper.toClaimDto(service.updateClaimById(id, claimUpdate));
    }
}
