package com.mogikanensoftware.xml.utils.core.bean;

public class ValidationInfoBean extends AbstractBean{

	private static final long serialVersionUID = 1L;

	private ValidationInfoType infoType;
	private String errorType;
	private String elementName;
	private String message;
	
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	

	public ValidationInfoBean() {
		super();
	}
	
	public ValidationInfoBean(ValidationInfoType infoType, String errorType, String elementName, String message) {
		super();
		this.infoType = infoType;
		this.errorType = errorType;
		this.elementName = elementName;
		this.message = message;
	}
	
	
	public ValidationInfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(ValidationInfoType infoType) {
		this.infoType = infoType;
	}

	

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ValidationInfoBean [infoType=" + infoType + ", errorType=" + errorType + ", elementName=" + elementName
				+ ", message=" + message + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementName == null) ? 0 : elementName.hashCode());
		result = prime * result + ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result + ((infoType == null) ? 0 : infoType.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		ValidationInfoBean other = (ValidationInfoBean) obj;
		if (elementName == null) {
			if (other.elementName != null)
				return false;
		} else if (!elementName.equals(other.elementName))
			return false;
		if (errorType == null) {
			if (other.errorType != null)
				return false;
		} else if (!errorType.equals(other.errorType))
			return false;
		if (infoType != other.infoType)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

}
