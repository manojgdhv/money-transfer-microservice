package com.moneytransfer.config;

import com.moneytransfer.controller.MoneyTransferController;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MoneyTransferApplication extends Application {
    private Set<Object> controllers;

    public MoneyTransferApplication() {
        controllers = new HashSet<Object>();
        controllers.add(new MoneyTransferController());
    }

    @Override
    public Set<Object> getSingletons() {
        return controllers;
    }
}