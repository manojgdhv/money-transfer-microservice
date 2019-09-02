package com.moneytransfer.controller;

import com.moneytransfer.domain.TransferRequest;
import com.moneytransfer.service.TransferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TransferControllerTest {
    private TransferService transferService;
    private TransferController transferController;

    @BeforeEach
    void setUp(){
        transferService = mock(TransferService.class);
        transferController = new TransferController(transferService);
    }

    @AfterEach
    void tearDown(){
        reset(transferService);
        transferController = null;
    }

    @Test
    void transferTest() {
        TransferRequest request = new TransferRequest();
        request.requestId = "123";
        transferController.transfer(request);
        verify(transferService, times(1)).transfer(request);
    }

    @Test
    void getAllTransfersTest() {
        transferController.getAllTransfers();
        verify(transferService, times(1)).getTransfers();
    }

    @Test
    void getTransfersTest() {
        String accountId = "123";
        transferController.getTransfers(accountId);
        verify(transferService, times(1)).getTransfers(accountId);
    }
}