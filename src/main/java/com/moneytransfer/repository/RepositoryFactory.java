package com.moneytransfer.repository;

import com.moneytransfer.repository.account.AccountRepository;
import com.moneytransfer.repository.account.H2AccountRepository;
import com.moneytransfer.repository.transfer.H2TransferRepository;
import com.moneytransfer.repository.transfer.TransferRepository;

public class RepositoryFactory {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public RepositoryFactory() {
        transferRepository = new H2TransferRepository();
        accountRepository = new H2AccountRepository();
    }

    public TransferRepository getTransferRepository() {
        return transferRepository;
    }
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }
}