package com.moneytransfer.config;

import com.moneytransfer.controller.AccountController;
import com.moneytransfer.controller.TransferController;
import com.moneytransfer.exception.ExceptionHandler;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MoneyTransferApplication extends Application {
    private final Set<Object> controllers;

    public MoneyTransferApplication() {
        controllers = new HashSet<>();
        controllers.add(new TransferController());
        controllers.add(new AccountController());
        controllers.add(new ExceptionHandler());
    }

    @Override
    public Set<Object> getSingletons() {
        return controllers;
    }
}