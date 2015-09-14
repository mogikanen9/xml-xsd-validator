package com.mogikanensoftware.xml.utils.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.mogikanensoftware.xml.utils.core.bean.ValidationErrorInfo;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

public class BasicValidationServiceImpl implements ValidationService {

	@Override
	public ValidationResult validate(File xmlFileToValidate, File xsdFile) throws ValidationServiceException {

		ValidationResult rs = new ValidationResult();
		
		InputStream inXsd = null;
		InputStream inXml = null;
		CustomSAXErrorHandler customErrorHandelr = new CustomSAXErrorHandler();
		try {
			inXsd = new FileInputStream(xsdFile);
			inXml = new FileInputStream(xmlFileToValidate);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(inXsd));
			Validator validator = schema.newValidator();			
			validator.setErrorHandler(customErrorHandelr);
			StreamSource xmlFile = new StreamSource(inXml);
			validator.validate(xmlFile);
			
			if(customErrorHandelr.getExceptions()!=null && customErrorHandelr.getExceptions().size()>0){
				
				for (SAXException ex:customErrorHandelr.getExceptions()) {
					ValidationErrorInfo errorInfo = new ValidationErrorInfo();
					errorInfo.setErrorMessage(ex.getMessage());
					rs.addErrorInfo(errorInfo);
				}		
			}
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if(e instanceof SAXException){
				ValidationErrorInfo errorInfo = new ValidationErrorInfo();
				errorInfo.setErrorMessage(e.getMessage());
				rs.addErrorInfo(errorInfo);
			}else{
				throw new ValidationServiceException(e);	
			}
			
			

		} finally {
			if (inXsd != null) {
				try {
					inXsd.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (inXml != null) {
				try {
					inXml.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return rs;

	}

}
