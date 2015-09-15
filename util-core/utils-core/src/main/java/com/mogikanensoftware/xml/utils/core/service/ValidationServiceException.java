package com.mogikanensoftware.xml.utils.core.service;

public class ValidationServiceException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public ValidationServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ValidationServiceException(String message) {
		super(message);
	}

	public ValidationServiceException(Throwable cause) {
		super(cause);
	}

}
