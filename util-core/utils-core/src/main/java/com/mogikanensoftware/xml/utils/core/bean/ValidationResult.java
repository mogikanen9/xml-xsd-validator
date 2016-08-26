package com.mogikanensoftware.xml.utils.core.bean;

import java.util.HashSet;
import java.util.Set;

public class ValidationResult extends AbstractBean {

	private static final long serialVersionUID = 1L;

	private String targetName;
	private Set<ValidationInfoBean> validationErrors;
	private Set<ValidationInfoBean> validationWarnings;

	public ValidationResult(String targetName){
		validationErrors = new HashSet<>();
		validationWarnings = new HashSet<>();
		this.targetName = targetName;
	}
		
	
	public ValidationResult(String targetName, Set<ValidationInfoBean> validationErrors,
			Set<ValidationInfoBean> validationWarnings) {
		super();
		this.targetName = targetName;
		this.validationErrors = validationErrors;
		this.validationWarnings = validationWarnings;
	}



	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
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
		return "ValidationResult [targetName=" + targetName + ", validationErrors=" + validationErrors
				+ ", validationWarnings=" + validationWarnings + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((targetName == null) ? 0 : targetName.hashCode());
		result = prime * result + ((validationErrors == null) ? 0 : validationErrors.hashCode());
		result = prime * result + ((validationWarnings == null) ? 0 : validationWarnings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationResult other = (ValidationResult) obj;
		if (targetName == null) {
			if (other.targetName != null)
				return false;
		} else if (!targetName.equals(other.targetName))
			return false;
		if (validationErrors == null) {
			if (other.validationErrors != null)
				return false;
		} else if (!validationErrors.equals(other.validationErrors))
			return false;
		if (validationWarnings == null) {
			if (other.validationWarnings != null)
				return false;
		} else if (!validationWarnings.equals(other.validationWarnings))
			return false;
		return true;
	}

}
