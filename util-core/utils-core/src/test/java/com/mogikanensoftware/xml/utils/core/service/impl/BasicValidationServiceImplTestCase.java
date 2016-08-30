package com.mogikanensoftware.xml.utils.core.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.Constants;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;
import com.mogikanensoftware.xml.utils.core.service.impl.SAXErrorsParsingServiceImpl;

import junit.framework.TestCase;

public class BasicValidationServiceImplTestCase extends TestCase {

	private static final Logger logger = LogManager.getLogger(BasicValidationServiceImplTestCase.class);

	private String xsdReportManager = BasicValidationServiceImplTestCase.class
				.getResource("/xml/core/xsd/report_manager.xsd").getFile();
	private String xsdReportManagerDt = BasicValidationServiceImplTestCase.class
				.getResource("/xml/core/xsd/report_manager_dt.xsd").getFile();
	
	private URL urlXsdReportManager;
	private URL urlXsdReportManagerDt;
	
	
	public BasicValidationServiceImplTestCase() throws MalformedURLException {
		super();
		
		urlXsdReportManager = new URL(Constants.REPORT_MANAGER_XSD);
		urlXsdReportManagerDt = new URL(Constants.REPORT_MANAGER_DT_XSD);
	}

	@Test
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

	@Test
	public void testValidateValidMR() {
		try {
			
			
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/MR_Final1.xml")
					.getFile();

			ValidationResult result = validateAgainstHRMLocalXsd(xmlFilePath, new String[]{xsdReportManager, xsdReportManagerDt});

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
	
	@Test
	public void testValidateValidMRRemote() {
		try {
			
			
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/MR_Final1.xml")
					.getFile();

			ValidationResult result = validateAgainstHRMRemoteXsd(new File(xmlFilePath), new URL[]{urlXsdReportManager, urlXsdReportManagerDt});

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
	
	@Test
	public void testValidateInvalidMR() {
		try {
			
			
			String xmlFilePath = BasicValidationServiceImplTestCase.class.getResource("/xml/core/files/MR_Invalid.xml")
					.getFile();

			ValidationResult result = validateAgainstHRMLocalXsd(xmlFilePath, new String[]{xsdReportManager, xsdReportManagerDt});

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
	
	protected ValidationResult validateAgainstHRMLocalXsd(String xmlFilePath, String[] xsds) throws ValidationServiceException{
		ValidationService validationService = new BasicValidationServiceImpl(new SAXErrorsParsingServiceImpl());

		File[] xsdFiles = new File[xsds.length];
		for (int i=0;i<xsds.length;i++) {
			logger.info(String.format("xsdFilePath -> %s", xsds[i]));
			xsdFiles[i] = new File(xsds[i]);
		}
	
		logger.info("xmlFilePath->" + xmlFilePath);
		File xmlFile = new File(xmlFilePath);
		
		return validationService.validate(xmlFile, xsdFiles);
	}
	
	protected ValidationResult validateAgainstHRMRemoteXsd(File xmlFile, URL[] xsdFiles) throws ValidationServiceException{
		ValidationService validationService = new BasicValidationServiceImpl(new SAXErrorsParsingServiceImpl());
		
		for (URL url : xsdFiles) {
			logger.info(String.format("remote xsd - >%s", url));
		}
		
		return validationService.validate(xmlFile, xsdFiles);
	}
	
}
