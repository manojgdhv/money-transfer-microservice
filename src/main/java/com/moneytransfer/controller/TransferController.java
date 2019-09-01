package com.moneytransfer.controller;

import com.moneytransfer.domain.Transfer;
import com.moneytransfer.domain.TransferRequest;
import com.moneytransfer.service.TransferService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(Constant.TRANSFERS)
public class TransferController {
    private final TransferService transferService;

    public TransferController() {
        transferService = new TransferService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transfer transfer(TransferRequest transferRequest){
        return transferService.transfer(transferRequest);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transfer> getAllTransfers(){
        return transferService.getTransfers();
    }

    @GET
    @Path(Constant.ACCOUNT_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transfer> getTransfers(@PathParam(Constant.ACCOUNT_ID_PATH_PARAM) String accountId){
        return transferService.getTransfers(accountId);
    }
}
