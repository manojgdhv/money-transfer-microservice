package com.moneytransfer.repository;

public interface SqlConstant {
    String INSERT_TRANSACTION = "insert into transaction_tbl(requestId, timeStamp," +
            " sourceAccountId, destinationAccountId, amount, currencyCode, reference, status) values(?,?,?,?,?,?,?,?)";

    String UPDATE_TRANSACTION_STATUS = "update transaction_tbl set status = ? where requestId = ?";

    String GET_ACCOUNT_BALANCE = "select balance from account_tbl where accountId = ?";
    String DEBIT_BALANCE = "(balance - ?)";
    String DEBIT_ACCOUNT_BALANCE = "update account_tbl set balance = " + DEBIT_BALANCE + " where accountId = ?";
    String CREDIT_BALANCE = "(balance + ?)";
    String CREDIT_ACCOUNT_BALANCE = "update account_tbl set balance = " + CREDIT_BALANCE + " where accountId = ?";
    String GET_TRANSACTION_USING_REQUEST_ID = "select * from transaction_tbl where requestId = ?";
    String GET_TRANSACTION_USING_ACCOUNT_ID = "select * from transaction_tbl where sourceAccountId = ?";
    String GET_TRANSACTIONS = "select * from transaction_tbl";

    String GET_ACCOUNT = "select * from account_tbl where accountId = ?";
    String GET_ALL_ACCOUNTS = "select * from account_tbl";
    String INSERT_ACCOUNT = "insert into account_tbl(accountId, balance, currencyCode) " +
            "values(?,?,?)";
    String UPDATE_ACCOUNT = "update account_tbl set balance = ? , currencyCode = ? where accountId = ?";
    String DELETE_ACCOUNT = "delete from account_tbl where accountId = ?";


}
