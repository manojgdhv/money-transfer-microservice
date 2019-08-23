package com.moneytransfer.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/api/hello")
public class MoneyTransferController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{user}")
    public String hello(@QueryParam("user") String user) {
        return user;
    }
}