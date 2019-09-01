package com.moneytransfer.service;

import com.moneytransfer.domain.Account;
import com.moneytransfer.exception.AccountNotFoundException;
import com.moneytransfer.repository.account.AccountRepository;
import com.moneytransfer.repository.RepositoryFactory;

import java.util.List;

public class AccountService {
    private final RepositoryFactory repositoryFactory;

    public AccountService() {
        this.repositoryFactory = new RepositoryFactory();
    }

    public AccountService(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    public List<Account> getAllAccounts() {
        return getAccountRepository().getAllAccounts();
    }

    public void deleteAccount(String accountId) {
        getAccountRepository().deleteAccount(accountId);
    }

    public Account getAccount(String accountId) {
        validateAccountId(accountId);
        return getAccountRepository().getAccount(accountId);
    }

    public Account addAccount(Account account) {
        return getAccountRepository().addAccount(account);
    }

    public Account updateAccount(Account account) {
        validateAccountId(account.accountId);
        return getAccountRepository().updateAccount(account);
    }

    void validateAccountId(String accountId) {
        if (!getAccountRepository().isAccountExists(accountId)) {
            throw new AccountNotFoundException(String.format("Account [%s] doesn't exist in the system", accountId));
        }
    }

    private AccountRepository getAccountRepository() {
        return repositoryFactory.getAccountRepository();
    }
}