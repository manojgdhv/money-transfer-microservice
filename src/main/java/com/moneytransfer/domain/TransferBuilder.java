package com.moneytransfer.domain;

import java.math.BigDecimal;

public class TransferBuilder {
    private long timeStamp;
    private TransactionStatus status;
    private String requestId;
    private String sourceAccountId;
    private String destinationAccountId;
    private BigDecimal amount;
    private String currencyCode;
    private String reference;

    public TransferBuilder withTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public TransferBuilder withStatus(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public TransferBuilder withRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public TransferBuilder withSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
        return this;
    }

    public TransferBuilder withDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
        return this;
    }

    public TransferBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransferBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public TransferBuilder withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public Transfer build() {
        Transfer transfer = new Transfer();
        transfer.timeStamp = this.timeStamp;
        transfer.currencyCode = this.currencyCode;
        transfer.status = this.status;
        transfer.amount = this.amount;
        transfer.reference = this.reference;
        transfer.requestId = this.requestId;
        transfer.sourceAccountId = this.sourceAccountId;
        transfer.destinationAccountId = this.destinationAccountId;
        return transfer;
    }
}