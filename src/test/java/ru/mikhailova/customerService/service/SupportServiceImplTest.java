package ru.mikhailova.customerService.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mikhailova.customerService.domain.Claim;
import ru.mikhailova.customerService.domain.ClaimExecutor;
import ru.mikhailova.customerService.domain.ClaimUpdate;
import ru.mikhailova.customerService.repository.ClaimRepository;
import ru.mikhailova.customerService.repository.ExecutorRepository;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportServiceImplTest {
    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private ExecutorRepository executorRepository;

    @Captor
    private ArgumentCaptor<Claim> captor;
    private SupportServiceImpl service;
    private Claim claim;
    private ClaimExecutor claimExecutor;
    private static final Long TEST_ID = 1L;

    @BeforeEach
    void setUp() {
        claimExecutor = new ClaimExecutor();
        claimExecutor.setName("Isagi");

        claim = new Claim();
        claim.setDescription("payment error");
        claim.setExecutor(claimExecutor);
        service = new SupportServiceImpl(claimRepository, executorRepository);
    }

    @Test
    void couldGetAllClaims() {
        when(claimRepository.findAll()).thenReturn(List.of(claim));

        List<Claim> claims = service.getAllClaims();

        verify(claimRepository, times(1)).findAll();
        assertThat(claims.size()).isEqualTo(1);
    }

    @Test
    void couldGetClaimById() {
        when(claimRepository.findById(any())).thenReturn(ofNullable(claim));

        Claim testClaim = service.getClaimById(TEST_ID);

        verify(claimRepository, times(1)).findById(TEST_ID);
        assertThat(testClaim.getDescription()).isEqualTo("payment error");
    }

    @Test
    void couldAddClaim() {
        when(executorRepository.findById(any())).thenReturn(ofNullable(claimExecutor));

        service.addClaim(claim, TEST_ID);

        verify(claimRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getExecutor().getName()).isEqualTo(claimExecutor.getName());
    }

    @Test
    void couldDeleteClaimById() {
        service.deleteClaimById(TEST_ID);

        verify(claimRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    void couldUpdateClaimById() {
        when(claimRepository.findById(any())).thenReturn(ofNullable(claim));
        ClaimUpdate claimUpdate = new ClaimUpdate();
        claimUpdate.setDescription("missed item");

        service.updateClaimById(TEST_ID, claimUpdate);

        verify(claimRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getDescription()).isEqualTo(claimUpdate.getDescription());
    }
}