package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;
import java.net.URL;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

public interface ValidationService {

	ValidationResult validate(File xmlFileToValidate, File[] xsdFiles) throws ValidationServiceException; 
	
	ValidationResult validate(File xmlFileToValidate, URL[] xsdFiles) throws ValidationServiceException;
}
