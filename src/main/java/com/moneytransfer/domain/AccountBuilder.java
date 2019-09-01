package com.moneytransfer.domain;

import java.math.BigDecimal;

public final class AccountBuilder {
    private String accountId;
    private BigDecimal balance;
    private String currencyCode;

    public AccountBuilder withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public AccountBuilder withBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public AccountBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public Account build() {
        final Account account = new Account();
        account.accountId = this.accountId;
        account.balance = this.balance;
        account.currencyCode = this.currencyCode;
        return account;
    }
}
