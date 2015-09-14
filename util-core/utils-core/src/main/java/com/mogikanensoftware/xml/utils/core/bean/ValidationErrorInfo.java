package com.mogikanensoftware.xml.utils.core.bean;

public class ValidationErrorInfo extends AbstractBean {

	@Override
	public String toString() {
		return "ValidationErrorInfo [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
	private static final long serialVersionUID = 1L;

	private String errorMessage;
	private String errorCode;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
