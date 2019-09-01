package com.moneytransfer.domain;

import javax.ws.rs.core.Response.Status;
import java.io.Serializable;

public class ErrorResponse implements Serializable {
    public Status status;
    public String message;

    public ErrorResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }
}
