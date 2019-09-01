package com.moneytransfer.domain;

import java.util.Arrays;

public enum TransactionStatus {
    PENDING(1),
    FINISHED(2),
    DECLINED(3);

    private int value;
    TransactionStatus(int value){
        this.value = value;
    }

    public static TransactionStatus getValue(int value) {
        return Arrays.stream(TransactionStatus.values())
                .filter(transactionStatus -> transactionStatus.value == value)
                .findAny().orElseThrow(() -> new IllegalArgumentException("Invalid value."));
    }

    public int getValue() {
        return value;
    }
}
