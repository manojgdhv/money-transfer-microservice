package com.moneytransfer.repository.transfer;

import com.moneytransfer.domain.Transfer;
import com.moneytransfer.domain.TransferRequest;

import java.util.List;

public interface TransferRepository {
    Transfer transfer(TransferRequest transferRequest);
    List<Transfer> getTransfers(String accountId);
    List<Transfer> getTransfers();
}
