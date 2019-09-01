package com.moneytransfer.domain;

import java.math.BigDecimal;

public class TransferRequest {
    public String requestId;
    public String sourceAccountId;
    public String destinationAccountId;
    public BigDecimal amount;
    public String currencyCode;
    public String reference;
}
