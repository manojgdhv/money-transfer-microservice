package com.moneytransfer.controller;

import com.moneytransfer.domain.TransferRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferControllerITest extends IntegrationTest {

    public TransferControllerITest() {
        super(new TransferController());
    }

    @BeforeEach
    void beforeClass(){
        start();
    }

    @AfterEach
    void afterClass(){
        close();
    }

    @Test
    public void postSimpleBody() {
        TransferRequest request = new TransferRequest();
        request.sourceAccountId = "1";
        request.destinationAccountId = "2";
        request.amount = BigDecimal.valueOf(100);
        request.currencyCode = "GBP";
        request.requestId = "123";

        final Response response = newRequest("/api/v1/transfers").request().buildPost(Entity.json(request)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    //TODO: Add more test cases
}