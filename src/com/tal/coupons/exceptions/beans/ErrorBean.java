package com.tal.coupons.exceptions.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class provides an object which contain various error messages and codes to be handled by the ExceptionsHandler class
 * @author Tal Livny
 */
@XmlRootElement
public class ErrorBean {
	
	private int internalErrorCode;
	private String message;
	
	public ErrorBean() {
	}

	public ErrorBean(int internalErrorCode, String message) {
		this.setInternalErrorCode(internalErrorCode);
		this.setMessage(message);
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public void setInternalErrorCode(int internalErrorCode) {
		this.internalErrorCode = internalErrorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

