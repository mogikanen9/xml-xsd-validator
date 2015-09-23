package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

public interface ValidationService {

	ValidationResult validate(File xmlFileToValidate, File[] xsdFiles) throws ValidationServiceException; 
}
