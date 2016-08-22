package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;
import com.mogikanensoftware.xml.utils.core.service.impl.SAXErrorsParsingServiceImpl;

import junit.framework.TestCase;

public class BasicValidationServiceImplTestCase extends TestCase {

	private static final Logger logger = LogManager.getLogger(BasicValidationServiceImplTestCase.class);

	public void testValidate() {
		try {
			ValidationService validationService = new BasicValidationServiceImpl(new SAXErrorsParsingServiceImpl());

			String xsdFilePath = BasicValidationServiceImplTestCase.class
					.getResource("/xml/core/xsd/SimpleXMLSchema1.xsd").getFile();
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/SimpleFile1.xml")
					.getFile();

			logger.info("xsdFilePath->" + xsdFilePath);
			logger.info("xmlFilePath->" + xmlFilePath);

			File xsdFile = new File(xsdFilePath);
			File xmlFile = new File(xmlFilePath);
			ValidationResult result = validationService.validate(xmlFile, new File[]{xsdFile});

			assertNotNull(result);
			assertNotNull(result.getValidationErrors());
			assertTrue(result.getValidationErrors().size() > 0);
			assertTrue(result.getValidationWarnings().size() == 0);
			
			logger.info("All validation errors: \n");

			for (ValidationInfoBean info : result.getValidationErrors()) {
				logger.info(info.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Done");

	}

	public void testValidateValidMR() {
		try {
			
			
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/MR_Final1.xml")
					.getFile();

			ValidationResult result = validateAgainstHRMXsd(xmlFilePath);

			assertNotNull(result);
			assertNotNull(result.getValidationErrors());
			assertTrue(result.getValidationErrors().size() == 0);
			assertTrue(result.getValidationWarnings().size() == 0);
			
			logger.info("All validation errors: \n");

			for (ValidationInfoBean info : result.getValidationErrors()) {
				logger.info(info.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Done");

	}
	
	public void testValidateInvalidMR() {
		try {
			
			
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/MR_Invalid.xml")
					.getFile();

			ValidationResult result = validateAgainstHRMXsd(xmlFilePath);

			assertNotNull(result);
			assertNotNull(result.getValidationErrors());
			assertTrue(result.getValidationErrors().size() > 0);
			assertTrue(result.getValidationWarnings().size() == 0);
			
			logger.info("All validation errors: \n");

			for (ValidationInfoBean info : result.getValidationErrors()) {
				logger.info(info.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Done");

	}
	
	protected ValidationResult validateAgainstHRMXsd(String xmlFilePath) throws ValidationServiceException{
		ValidationService validationService = new BasicValidationServiceImpl(new SAXErrorsParsingServiceImpl());

		String xsdFilePath1 = BasicValidationServiceImplTestCase.class
				.getResource("/xml/core/xsd/report_manager.xsd").getFile();
		String xsdFilePath2 = BasicValidationServiceImplTestCase.class
				.getResource("/xml/core/xsd/report_manager_dt.xsd").getFile();
				

		logger.info("xsdFilePath1->" + xsdFilePath1);
		logger.info("xsdFilePath2->" + xsdFilePath2);
		logger.info("xmlFilePath->" + xmlFilePath);

		File xsdFile1 = new File(xsdFilePath1);
		File xsdFile2 = new File(xsdFilePath2);
		File xmlFile = new File(xmlFilePath);
		return validationService.validate(xmlFile, new File[]{xsdFile1,xsdFile2});

	}
}
