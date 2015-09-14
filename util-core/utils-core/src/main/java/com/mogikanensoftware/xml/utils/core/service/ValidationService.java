package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;

public interface ValidationService {

	void validate(File xmlFileToValidate, File xsdFile) throws ValidationServiceException; 
}
