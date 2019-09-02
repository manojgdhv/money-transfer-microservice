package com.moneytransfer.controller.integration;

import com.moneytransfer.controller.AccountController;
import com.moneytransfer.domain.Account;
import com.moneytransfer.domain.AccountBuilder;
import com.moneytransfer.domain.TransferRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
public class AccountControllerITest extends IntegrationTest {

    public AccountControllerITest() {
        super(new AccountController());
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
    void testGetAllAccounts() {
        final Response response = newRequest("/api/v1/accounts").request().buildGet().invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetAccount() {
        final Response response = newRequest("/api/v1/accounts").request().buildGet().invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testAddAccount() {
        final Account account = new AccountBuilder()
                .withCurrencyCode("GBP")
                .withAccountId("3")
                .withBalance(BigDecimal.valueOf(100))
                .build();


        final Response response = newRequest("/api/v1/accounts").request().buildPost(Entity.json(account)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateAccount() {
        final Account account = new AccountBuilder()
                .withCurrencyCode("GBP")
                .withAccountId("1")
                .withBalance(BigDecimal.valueOf(200))
                .build();


        final Response response = newRequest("/api/v1/accounts").request().buildPut(Entity.json(account)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteAccount() {
        final Response response = newRequest("/api/v1/accounts/1").request().buildDelete().invoke();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }


    //TODO: Add more comprehensive test cases

}