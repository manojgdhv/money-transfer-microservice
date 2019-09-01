package com.moneytransfer.repository.account;

import com.moneytransfer.domain.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> getAllAccounts();
    Account getAccount(String accountId);
    Account addAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccount(String accountId);
    boolean isAccountExists(String accountId);
}
