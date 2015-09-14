package com.mogikanensoftware.xml.utils.core.bean;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult extends AbstractBean{

	@Override
	public String toString() {
		return "ValidationResult [validationErrors=" + validationErrors + "]";
	}

	private static final long serialVersionUID = 1L;

	private List<ValidationErrorInfo> validationErrors;

	public List<ValidationErrorInfo> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationErrorInfo> validationErrors) {
		this.validationErrors = validationErrors;
	}
	
	public void addErrorInfo(ValidationErrorInfo errorInfo){
		if(validationErrors == null){
			validationErrors = new ArrayList<>();
		}
		
		validationErrors.add(errorInfo);
	}
}
