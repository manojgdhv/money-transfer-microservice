package com.moneytransfer.service;

import com.moneytransfer.domain.Transfer;
import com.moneytransfer.domain.TransferRequest;
import com.moneytransfer.repository.RepositoryFactory;
import com.moneytransfer.repository.transfer.TransferRepository;

import java.util.List;

public class TransferService {
    private final RepositoryFactory repositoryFactory;
    private final AccountService accountService;

    public TransferService() {
        this.repositoryFactory = new RepositoryFactory();
        this.accountService = new AccountService();
    }

    public Transfer transfer(TransferRequest transferRequest) {
        accountService.validateAccountId(transferRequest.sourceAccountId);
        accountService.validateAccountId(transferRequest.destinationAccountId);
        return getTransferRepository().transfer(transferRequest);
    }

    public List<Transfer> getTransfers(String accountId) {
        accountService.validateAccountId(accountId);
        return getTransferRepository().getTransfers(accountId);
    }

    public List<Transfer> getTransfers() {
        return getTransferRepository().getTransfers();
    }

    TransferRepository getTransferRepository() {
        return repositoryFactory.getTransferRepository();
    }

}