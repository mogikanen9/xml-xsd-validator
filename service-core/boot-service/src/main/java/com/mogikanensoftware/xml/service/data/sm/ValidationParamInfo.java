package com.mogikanensoftware.xml.service.data.sm;

import java.io.Serializable;

public class ValidationParamInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String[] xsdUrls; 
	private String folderPath; 
	private String fileName;
	
	
	public ValidationParamInfo() {
		super();
	}
	
	public ValidationParamInfo(String[] xsdUrls, String folderPath, String fileName) {
		super();
		this.xsdUrls = xsdUrls;
		this.folderPath = folderPath;
		this.fileName = fileName;
	}

	public String[] getXsdUrls() {
		return xsdUrls;
	}
	public void setXsdUrls(String[] xsdUrls) {
		this.xsdUrls = xsdUrls;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
