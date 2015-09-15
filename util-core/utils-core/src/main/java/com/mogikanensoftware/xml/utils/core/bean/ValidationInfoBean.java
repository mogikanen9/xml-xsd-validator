package com.mogikanensoftware.xml.utils.core.bean;

public class ValidationInfoBean extends AbstractBean{

	private static final long serialVersionUID = 1L;

	private ValidationInfoType infoType;
	private String code;
	private String message;

	public ValidationInfoBean() {
		super();
	}
	
	public ValidationInfoBean(ValidationInfoType infoType, String code, String message) {
		super();
		this.infoType = infoType;
		this.code = code;
		this.message = message;
	}
	
	
	public ValidationInfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(ValidationInfoType infoType) {
		this.infoType = infoType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ValidationInfoBean [infoType=" + infoType + ", code=" + code + ", message=" + message + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
