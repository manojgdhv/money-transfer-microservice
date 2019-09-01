package com.moneytransfer.controller;

interface Constant {
    String BASE_PATH = "/api";

    String TRANSFERS = BASE_PATH +"/v1/transfers";
    String ACCOUNTS = BASE_PATH +"/v1/accounts";

    String ACCOUNT_ID_PATH_PARAM = "accountId";
    String ACCOUNT_ID = "/{" + ACCOUNT_ID_PATH_PARAM + "}";
}
