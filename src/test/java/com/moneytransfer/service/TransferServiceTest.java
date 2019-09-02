package com.moneytransfer.service;

import com.moneytransfer.domain.Transfer;
import com.moneytransfer.domain.TransferRequest;
import com.moneytransfer.repository.RepositoryFactory;
import com.moneytransfer.repository.transfer.TransferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TransferServiceTest {
    private TransferService transferService;
    private AccountService accountService;
    private RepositoryFactory repositoryFactory;
    private TransferRepository transferRepository;


    @BeforeEach
    void setUp() {
        repositoryFactory = mock(RepositoryFactory.class);
        accountService = mock(AccountService.class);
        transferRepository = mock(TransferRepository.class);
        transferService = new TransferService(repositoryFactory, accountService);
    }

    @AfterEach
    void tearDown() {
        reset(repositoryFactory, transferRepository, accountService);
        transferService = null;
    }

    @Test
    void transferTest() {
        //Given
        when(repositoryFactory.getTransferRepository()).thenReturn(transferRepository);

        TransferRequest request = new TransferRequest();
        request.sourceAccountId = "1";
        request.destinationAccountId = "2";

        Transfer transfer = new Transfer();
        transfer.sourceAccountId = request.sourceAccountId;
        transfer.destinationAccountId = request.destinationAccountId;

        when(transferRepository.transfer(request)).thenReturn(transfer);

        //When
        final Transfer actual = transferService.transfer(request);

        //Then
        verify(repositoryFactory, times(1)).getTransferRepository();
        verify(accountService, times(2)).validateAccountId(anyString());
        verify(transferRepository, times(1)).transfer(request);

        assertEquals(transfer, actual);
    }

    @Test
    void getTransfersTest() {
        //Given
        when(repositoryFactory.getTransferRepository()).thenReturn(transferRepository);

        Transfer transfer = new Transfer();
        transfer.sourceAccountId = "1";
        transfer.destinationAccountId = "2";

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(transfer);

        when(transferRepository.getTransfers()).thenReturn(transfers);

        //When
        final List<Transfer> actual = transferService.getTransfers();

        //Then
        verify(repositoryFactory, times(1)).getTransferRepository();
        Assertions.assertEquals(transfers, actual);
    }

    @Test
    void getSingleTransfersTest() {
        //Given
        when(repositoryFactory.getTransferRepository()).thenReturn(transferRepository);

        Transfer transfer = new Transfer();
        transfer.sourceAccountId = "1";
        transfer.destinationAccountId = "2";

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(transfer);

        when(transferRepository.getTransfers(transfer.sourceAccountId)).thenReturn(transfers);

        //When
        final List<Transfer> actual = transferService.getTransfers(transfer.sourceAccountId);

        //Then
        verify(repositoryFactory, times(1)).getTransferRepository();
        Assertions.assertEquals(transfers, actual);
    }
}