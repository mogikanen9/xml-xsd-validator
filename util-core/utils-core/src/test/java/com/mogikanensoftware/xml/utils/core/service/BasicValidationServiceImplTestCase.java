package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;

import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;

import junit.framework.TestCase;

public class BasicValidationServiceImplTestCase extends TestCase {

	public void testValidate() {
		try{
		ValidationService validationService = new BasicValidationServiceImpl();
		
			String xsdFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/xsd/SimpleXMLSchema1.xsd").toString();
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/SimpleFile1.xml").toString();
			
			System.out.println("xsdFilePath->"+xsdFilePath);
			System.out.println("xmlFilePath->"+xmlFilePath);
			
			File xsdFile = new File(xsdFilePath);
			File xmlFile = new File(xmlFilePath);
			validationService.validate(xmlFile, xsdFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		
	}

}
