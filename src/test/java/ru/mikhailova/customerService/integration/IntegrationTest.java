package ru.mikhailova.customerService.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mikhailova.customerService.controller.dto.ClaimDto;
import ru.mikhailova.customerService.controller.dto.ClaimStartRequestDto;
import ru.mikhailova.customerService.repository.ClaimRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private ClaimRepository claimRepository;
    private TypeReference<List<ClaimDto>> typeReference = new TypeReference<List<ClaimDto>>() {
    };

    @BeforeEach
    void setUp() throws Exception {
        ClaimStartRequestDto dto = new ClaimStartRequestDto();
        dto.setClaimType("OFFER");
        dto.setCustomerContactInfo("+147896325");
        dto.setDescription("Application upgrade");

        String start = performStart(dto, String.class);
    }

    @AfterEach
    void tearDown() {
        claimRepository.deleteAll();
    }

    @Test
    void couldStartSupport() {

    }

    @Test
    void couldGetAllClaims() throws Exception {
        List<ClaimDto> claims = performGetAll("/get-all", typeReference);

        assertThat(claims).isNull();
    }

    @Test
    void couldGetClaimById() {


    }
}
