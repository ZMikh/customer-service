package ru.mikhailova.customerService.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import ru.mikhailova.customerService.controller.dto.ClaimDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends AbstractIntegrationTest {
    private TypeReference<List<ClaimDto>> typeReference = new TypeReference<List<ClaimDto>>(){};

    @Test
    void couldGetAllClaims() throws Exception {
        List<ClaimDto> claims = performGetAll("/get-all", typeReference);

        assertThat(claims).isNull();
    }
}
