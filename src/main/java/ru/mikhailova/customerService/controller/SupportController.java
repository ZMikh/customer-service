package ru.mikhailova.customerService.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mikhailova.customerService.controller.dto.*;
import ru.mikhailova.customerService.controller.mapper.ClaimMapper;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.service.ClaimRegister;
import ru.mikhailova.customerService.service.ClaimUpdate;
import ru.mikhailova.customerService.service.SupportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support/claim")
@RequiredArgsConstructor
public class SupportController {
    private final SupportService service;
    private final ClaimMapper mapper;

    @PostMapping("/start")
    @ApiOperation("Старт процесса сервиса поддержки клиентов")
    public String start(@RequestBody ClaimStartRequestDto claimStartRequestDto) {
        Claim claim = mapper.toClaim(claimStartRequestDto);
        service.startSupport(claim);
        return "Claim with id: " + claim.getId() + " is sent to registration";
    }

    @PostMapping("/register/{id}")
    @ApiOperation("Регистрация заявки")
    public ClaimRegisterResponseDto register(@PathVariable Long id,
                                             @RequestBody ClaimRegisterRequestDto claimRegisterRequestDto) {
        ClaimRegister claimRegister = mapper.toClaimRegister(claimRegisterRequestDto);
        return mapper.toClaimRegisterResponseDto(service.registerClaim(id, claimRegister));
    }

    @PostMapping("/execute/basic/{id}")
    @ApiOperation("Выполнение заявки общего типа исполнителем")
    public void executeBasic(@PathVariable Long id) {
        service.executeBasicClaim(id);
    }

    @PostMapping("/execute/assigned/{id}")
    @ApiOperation("Выполнение заявки специалистом по работе со специфичными запросами")
    public void executeAssigned(@PathVariable Long id) {
        service.executeAssignedClaim(id);
    }

    @GetMapping("/get-all")
    @ApiOperation("Получение всех заявок")
    public List<ClaimDto> getAll() {
        return mapper.toClaimDtoList(service.getAllClaims());
    }

    @GetMapping("/get-by-id/{id}")
    @ApiOperation("Получение заявки по идентификатору")
    public ClaimDto getById(@PathVariable Long id) {
        return mapper.toClaimDto(service.getClaimById(id));
    }

    @DeleteMapping("/delete-by-id/{id}")
    @ApiOperation("Удаление заявки по идентификатору")
    public void delete(@PathVariable Long id) {
        service.deleteClaimById(id);
    }

    @PatchMapping("/update-by-id/{id}")
    @ApiOperation("Обновление заявки по идентификатору")
    public ClaimDto update(@PathVariable Long id,
                           @RequestBody ClaimUpdateRequestDto claimUpdateRequestDto) {
        ClaimUpdate claimUpdate = mapper.toClaimUpdate(claimUpdateRequestDto);
        return mapper.toClaimDto(service.updateClaimById(id, claimUpdate));
    }
}
