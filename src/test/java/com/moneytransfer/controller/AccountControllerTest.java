package com.moneytransfer.controller;

import com.moneytransfer.domain.Account;
import com.moneytransfer.domain.AccountBuilder;
import com.moneytransfer.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AccountControllerTest {
    private AccountService accountService;
    private AccountController accountController;

    @BeforeEach
    void setUp(){
        accountService = mock(AccountService.class);
        accountController = new AccountController(accountService);
    }

    @AfterEach
    void tearDown(){
        reset(accountService);
        accountController = null;
    }

    @Test
    void getAllAccountsTest() {
        accountController.getAllAccounts();
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void getAccountTest() {
        final String accountId = "123";
        accountController.getAccount(accountId);
        verify(accountService, times(1)).getAccount(accountId);
    }

    @Test
    void addAccountTest() {
        final Account account = new AccountBuilder()
                .withBalance(BigDecimal.valueOf(1000))
                .withAccountId("123")
                .withCurrencyCode("GBP")
                .build();

        accountController.addAccount(account);
        verify(accountService, times(1)).addAccount(account);
    }

    @Test
    void updateAccountTest() {
        final Account account = new AccountBuilder()
                .withBalance(BigDecimal.valueOf(1000))
                .withAccountId("123")
                .withCurrencyCode("GBP")
                .build();

        accountController.updateAccount(account);
        verify(accountService, times(1)).updateAccount(account);
    }

    @Test
    void deleteAccountTest() {
        final String accountId = "123";
        accountController.deleteAccount(accountId);
        verify(accountService, times(1)).deleteAccount(accountId);
    }
}