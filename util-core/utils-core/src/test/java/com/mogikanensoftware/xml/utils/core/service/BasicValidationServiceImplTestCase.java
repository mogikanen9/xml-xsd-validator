package com.mogikanensoftware.xml.utils.core.service;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;

import junit.framework.TestCase;

public class BasicValidationServiceImplTestCase extends TestCase {

	private static final Logger logger = LogManager.getLogger(BasicValidationServiceImplTestCase.class);

	public void testValidate() {
		try {
			ValidationService validationService = new BasicValidationServiceImpl();

			String xsdFilePath = BasicValidationServiceImplTestCase.class
					.getResource("/xml/core/xsd/SimpleXMLSchema1.xsd").getFile();
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/SimpleFile1.xml")
					.getFile();

			logger.info("xsdFilePath->" + xsdFilePath);
			logger.info("xmlFilePath->" + xmlFilePath);

			File xsdFile = new File(xsdFilePath);
			File xmlFile = new File(xmlFilePath);
			ValidationResult result = validationService.validate(xmlFile, xsdFile);

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

}
