package com.mogikanensoftware.xml.service.data.sm;

import java.io.Serializable;
import java.util.Arrays;

public class ValidationParamInfo implements Serializable {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((folderPath == null) ? 0 : folderPath.hashCode());
		result = prime * result + Arrays.hashCode(xsdUrls);
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
		ValidationParamInfo other = (ValidationParamInfo) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (folderPath == null) {
			if (other.folderPath != null)
				return false;
		} else if (!folderPath.equals(other.folderPath))
			return false;
		if (!Arrays.equals(xsdUrls, other.xsdUrls))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ValidationParamInfo [xsdUrls=" + Arrays.toString(xsdUrls) + ", folderPath=" + folderPath + ", fileName="
				+ fileName + "]";
	}

}
