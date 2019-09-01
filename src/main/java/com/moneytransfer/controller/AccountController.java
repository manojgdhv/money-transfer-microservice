package com.moneytransfer.controller;

import com.moneytransfer.domain.Account;
import com.moneytransfer.service.AccountService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(Constant.ACCOUNTS)
public class AccountController {

    private final AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GET
    @Path(Constant.ACCOUNT_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam(Constant.ACCOUNT_ID_PATH_PARAM) String accountId) {
        return accountService.getAccount(accountId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account addAccount(Account account) {
        return accountService.addAccount(account);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account updateAccount(Account account) {
        return accountService.updateAccount(account);
    }

    @DELETE
    @Path(Constant.ACCOUNT_ID)
    public void deleteAccount(@PathParam(Constant.ACCOUNT_ID_PATH_PARAM) String accountId) {
        accountService.deleteAccount(accountId);
    }

}