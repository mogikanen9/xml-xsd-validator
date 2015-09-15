package com.mogikanensoftware.xml.utils.core.bean;

import java.util.HashSet;
import java.util.Set;

public class ValidationResult extends AbstractBean {

	private static final long serialVersionUID = 1L;

	private Set<ValidationInfoBean> validationErrors;
	private Set<ValidationInfoBean> validationWarnings;

	public ValidationResult(){
		validationErrors = new HashSet<>();
		validationWarnings = new HashSet<>();
	}
	
	public Set<ValidationInfoBean> getValidationWarnings() {
		return validationWarnings;
	}

	public void setValidationWarnings(Set<ValidationInfoBean> validationWarnings) {
		this.validationWarnings = validationWarnings;
	}

	public Set<ValidationInfoBean> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(Set<ValidationInfoBean> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public void addErrorInfo(ValidationInfoBean errorInfo) {		
		validationErrors.add(errorInfo);
	}

	public void addWarningInfo(ValidationInfoBean warningInfo) {
		validationWarnings.add(warningInfo);
	}

	@Override
	public String toString() {
		return "ValidationResult [validationErrors=" + validationErrors + ", validationWarnings=" + validationWarnings
				+ "]";
	}

}
