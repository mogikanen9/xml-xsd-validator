package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;

import com.mogikanensoftware.xml.utils.core.bean.ValidationErrorInfo;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;

import junit.framework.TestCase;

public class BasicValidationServiceImplTestCase extends TestCase {

	public void testValidate() {
		try{
		ValidationService validationService = new BasicValidationServiceImpl();
		
			String xsdFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/xsd/SimpleXMLSchema1.xsd").getFile();
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/SimpleFile1.xml").getFile();
			
			System.out.println("xsdFilePath->"+xsdFilePath);
			System.out.println("xmlFilePath->"+xmlFilePath);
			
			File xsdFile = new File(xsdFilePath);
			File xmlFile = new File(xmlFilePath);
			ValidationResult result = validationService.validate(xmlFile, xsdFile);
			
			assertNotNull(result);
			assertNotNull(result.getValidationErrors());
			assertTrue(result.getValidationErrors().size()>0);
			
			System.out.println("All validation errors: \n");
			
			for (ValidationErrorInfo info:result.getValidationErrors()) {
				System.out.println(info.toString());	
			}
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		
	}

}
