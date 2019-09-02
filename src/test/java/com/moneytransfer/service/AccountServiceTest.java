package com.moneytransfer.service;

import com.moneytransfer.domain.Account;
import com.moneytransfer.exception.AccountNotFoundException;
import com.moneytransfer.repository.RepositoryFactory;
import com.moneytransfer.repository.account.AccountRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AccountServiceTest {
    private AccountService accountService;
    private RepositoryFactory repositoryFactory;
    private AccountRepository accountRepository;


    @BeforeEach
    void setUp() {
        repositoryFactory = mock(RepositoryFactory.class);
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(repositoryFactory);
    }

    @AfterEach
    void tearDown() {
        reset(repositoryFactory, accountRepository);
        accountService = null;
    }

    @Test
    void getAllAccountsTest() {
        //Given
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);

        List<Account> accounts = new ArrayList<>();
        final Account a1 = new Account();
        a1.accountId = "1";
        accounts.add(a1);

        final Account a2 = new Account();
        a2.accountId = "2";
        accounts.add(a2);

        when(accountRepository.getAllAccounts()).thenReturn(accounts);

        //When
        final List<Account> actualList = accountService.getAllAccounts();

        //Then
        verify(repositoryFactory, times(1)).getAccountRepository();
        verify(accountRepository, times(1)).getAllAccounts();
        assertEquals(accounts, actualList);
    }

    @Test
    void deleteAccountTest() {
        //Given
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);
        String accountId = "123";

        //When
        accountService.deleteAccount(accountId);

        //Then
        verify(repositoryFactory, times(1)).getAccountRepository();
        verify(accountRepository, times(1)).deleteAccount(accountId);
    }

    @Test
    void getAccountTest() {
        //Given
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);

        final Account a1 = new Account();
        a1.accountId = "1";

        when(accountRepository.getAccount(a1.accountId)).thenReturn(a1);
        when(accountRepository.isAccountExists(a1.accountId)).thenReturn(true);

        //When
        final Account actual = accountService.getAccount(a1.accountId);

        //Then
        verify(repositoryFactory, times(2)).getAccountRepository();
        verify(accountRepository, times(1)).getAccount(a1.accountId);
        assertEquals(a1, actual);
    }

    @Test
    void getAccountWithInvalidAccountTest() {
        //Given
        final String accountId = "1";
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);
        when(accountRepository.isAccountExists(accountId)).thenReturn(false);

        //Then
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(accountId));
    }

    @Test
    void addAccountTest() {
        //Given
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);

        final Account a1 = new Account();
        a1.accountId = "1";

        when(accountRepository.addAccount(a1)).thenReturn(a1);

        //When
        final Account actual = accountService.addAccount(a1);

        //Then
        verify(repositoryFactory, times(1)).getAccountRepository();
        verify(accountRepository, times(1)).addAccount(a1);
        assertEquals(a1, actual);
    }

    @Test
    void updateAccountTest() {
        //Given
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);

        final Account a1 = new Account();
        a1.accountId = "1";

        when(accountRepository.updateAccount(a1)).thenReturn(a1);
        when(accountRepository.isAccountExists(a1.accountId)).thenReturn(true);


        //When
        final Account actual = accountService.updateAccount(a1);

        //Then
        verify(repositoryFactory, times(2)).getAccountRepository();
        verify(accountRepository, times(1)).updateAccount(a1);
        assertEquals(a1, actual);
    }

    @Test
    void updateAccountWithInvalidAccountIdTest() {
        //Given
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);

        final Account a1 = new Account();
        a1.accountId = "1";

        when(accountRepository.isAccountExists(a1.accountId)).thenReturn(false);

        //Then
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.updateAccount(a1));
        verify(repositoryFactory, times(1)).getAccountRepository();
    }

    @Test
    void validateAccountIdWithInvalidTest() {
        //Given
        final String accountId = "1";
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);
        when(accountRepository.isAccountExists(accountId)).thenReturn(false);

        //Then
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.validateAccountId(accountId));
    }

    @Test
    void validateAccountIdWithValidTest() {
        //Given
        final String accountId = "1";
        when(repositoryFactory.getAccountRepository()).thenReturn(accountRepository);
        when(accountRepository.isAccountExists(accountId)).thenReturn(true);

        //Then
        Assertions.assertDoesNotThrow(() -> accountService.validateAccountId(accountId));
    }

}