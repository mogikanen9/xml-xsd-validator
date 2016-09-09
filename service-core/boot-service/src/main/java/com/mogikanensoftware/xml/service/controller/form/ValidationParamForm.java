package com.mogikanensoftware.xml.service.controller.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ValidationParamForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min=6, max=255)
	private String xsdUrl1;
	
	
	private String xsdUrl2;

	public String getXsdUrl1() {
		return xsdUrl1;
	}

	public void setXsdUrl1(String xsdUrl1) {
		this.xsdUrl1 = xsdUrl1;
	}

	public String getXsdUrl2() {
		return xsdUrl2;
	}

	public void setXsdUrl2(String xsdUrl2) {
		this.xsdUrl2 = xsdUrl2;
	}

}
