package com.tal.coupons.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.exceptions.beans.ErrorBean;

/**
 * This class handles all the exceptions thrown out from various parts of the program.
 * @author Tal Livny
 *
 */
@Provider
public class ExceptionsHandler extends Exception implements ExceptionMapper<Throwable> 
{
    @Override
    public Response toResponse(Throwable exception) 
    {
    	ErrorType errorType = null;
    	String errorMessage = null;
    	int errorCode;
    	ErrorBean errorBean = null;
    	
    	System.out.println("Just entered into the exceptions mapper");
    	if (exception instanceof ApplicationException){
    		ApplicationException e = (ApplicationException) exception;
    		
    		errorType = e.getErrorType();
    		errorCode = errorType.getInternalErrorCode();
    		errorMessage = e.getMessage();
    		errorBean = new ErrorBean(errorCode, errorMessage);
    		
    		
    		return Response.status(700).entity(errorBean).build();
    	}
    	
    	System.out.println("Returning 500 as the http status");
        return Response.status(500).entity("General failure").build();
    }
}
