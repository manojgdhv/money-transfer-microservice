package com.moneytransfer.exception;

import com.moneytransfer.domain.ErrorResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if(exception instanceof WebApplicationException){
            return ((WebApplicationException) exception).getResponse();
        }

        final Status status = getStatus(exception);
        return Response.status(status).entity(new ErrorResponse(status, exception.getMessage())).build();
    }

    private Status getStatus(Exception exception) {
        if (exception instanceof AccountNotFoundException) {
            return Status.NOT_FOUND;
        } else if (exception instanceof InsufficientBalanceException) {
            return Status.BAD_REQUEST;
        } else {
            return Status.INTERNAL_SERVER_ERROR;
        }
    }
}