package com.mogikanensoftware.xml.utils.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;
import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoType;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

public class BasicValidationServiceImpl implements ValidationService {

	private static final Logger logger = LogManager.getLogger(BasicValidationServiceImpl.class);

	@Override
	public ValidationResult validate(File xmlFileToValidate, File[] xsdFiles) throws ValidationServiceException {

		if (logger.isDebugEnabled()) {
			logger.debug("xmlFileToValidate->" + xmlFileToValidate);
			logger.debug("xsdFiles->" + xsdFiles);
		}

		InputStream[] inXsds = new InputStream[xsdFiles.length];
		InputStream inXml = null;

		CustomSAXErrorHandler customErrorHandler = new CustomSAXErrorHandler();
		ValidationResult rs = new ValidationResult();

		try {

			for (int i = 0; i < xsdFiles.length; i++) {
				inXsds[i] = new FileInputStream(xsdFiles[i]);
			}

			Source[] schemaSources = new Source[inXsds.length];
			for (int i = 0; i < inXsds.length; i++) {
				schemaSources[i] = new StreamSource(inXsds[i]);
			}

			inXml = new FileInputStream(xmlFileToValidate);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			
			
			//factory.setErrorHandler(customErrorHandler);
			Schema schema = factory.newSchema(schemaSources);

			Validator validator = schema.newValidator();			
			//validator.setErrorHandler(customErrorHandler);
			StreamSource xmlFile = new StreamSource(inXml);
			validator.validate(xmlFile);

			if (logger.isDebugEnabled()) {
				logger.debug("customErrorHandler.getExceptions() is not null ->"
						+ (customErrorHandler.getExceptions() != null));
				if (customErrorHandler.getExceptions() != null) {
					logger.debug("customErrorHandler, found ->" + customErrorHandler.getExceptions().size()
							+ " exceptions/errors");
				}

				logger.debug(
						"customErrorHandler.getWarnings() is not null ->" + (customErrorHandler.getWarnings() != null));
				if (customErrorHandler.getWarnings() != null) {
					logger.debug(
							"customErrorHandler, found ->" + customErrorHandler.getWarnings().size() + " warnings");
				}
			}

			if (customErrorHandler.getExceptions() != null && customErrorHandler.getExceptions().size() > 0) {

				for (SAXException ex : customErrorHandler.getExceptions()) {
					rs.addErrorInfo(new ValidationInfoBean(ValidationInfoType.error, null, ex.getMessage()));
				}
			}

			if (customErrorHandler.getWarnings() != null && customErrorHandler.getWarnings().size() > 0) {
				for (SAXException ex : customErrorHandler.getWarnings()) {
					rs.addWarningInfo(new ValidationInfoBean(ValidationInfoType.warning, null, ex.getMessage()));
				}
			}

		} catch (SAXException | IOException e) {

			if (e instanceof SAXException) {

				logger.warn("Found SAXException not handled by CustomHandler->" + e.getMessage());

				ValidationInfoBean errorInfo = new ValidationInfoBean();
				errorInfo.setMessage(e.getMessage());
				errorInfo.setInfoType(ValidationInfoType.error);
				rs.addErrorInfo(errorInfo);

			} else {
				logger.error(e.getMessage(), e);
				throw new ValidationServiceException(e);
			}

		} finally {
			if (inXsds != null && inXsds.length > 0) {

				for (int i = 0; i < inXsds.length; i++) {
					try {

						inXsds[i].close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}

			if (inXml != null) {
				try {
					inXml.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rs->" + rs);
		}

		return rs;

	}

}
