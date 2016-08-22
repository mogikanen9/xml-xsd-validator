package com.mogikanensoftware.xml.utils.console;

import java.io.File;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;
import com.mogikanensoftware.xml.utils.core.service.impl.SAXErrorsParsingServiceImpl;

public class MainApp {

	private static final Logger logger = LogManager.getLogger(MainApp.class);

	public static void print(Set<ValidationInfoBean> set) {
		for (ValidationInfoBean validationInfoBean : set) {
			logger.info(validationInfoBean.getInfoType() + "->" + validationInfoBean.getMessage());
		}
	}

	public static void main(String[] args) {

		logger.info("Starting MainApp...");

		if (args.length < 2 || args[0] == null || args[1] == null || args[0].length() == 0 || args[1].length() == 0) {
			logger.fatal("Missing mandatory parameters - both xml file path and xsd file path MUST be specified!");
		} else {

			String xmlFileParam = args[0];			

			File xmlFile = new File(xmlFileParam);
			File[] xsdFiles = new File[args.length - 1];
			for (int i = 0; i < xsdFiles.length; i++) {
				xsdFiles[i] = new File(args[i + 1]);
			}

			if (!xmlFile.exists()) {
				logger.fatal("xmlFile doea not exist ->" + xmlFile.toString());
			} else {

				boolean allXsdExist = true;
				for (File sxdFile : xsdFiles) {
					if (!sxdFile.exists()) {
						logger.fatal("sxdFile doea not exist ->" + sxdFile.toString());
						allXsdExist = false;
					}
				}

				if (logger.isDebugEnabled()) {
					logger.debug("allXsdExist->" + allXsdExist);
				}

				if (allXsdExist) {

					logger.info("About to validate xml file ->" + xmlFile + " agaiinst "+xsdFiles.length+" xsd file(s)");

					ValidationService validationService = new BasicValidationServiceImpl(new SAXErrorsParsingServiceImpl());
					try {
						ValidationResult rs = validationService.validate(xmlFile, xsdFiles);

						if (rs.getValidationErrors() != null && rs.getValidationErrors().size() > 0) {
							logger.info("Errors:\n");
							MainApp.print(rs.getValidationErrors());
						} else {
							logger.info("No errors found.");
						}

						if (rs.getValidationWarnings() != null && rs.getValidationWarnings().size() > 0) {
							logger.info("Warning:\n");
							MainApp.print(rs.getValidationErrors());
						} else {
							logger.info("No warning found.");
						}

					} catch (ValidationServiceException e) {
						logger.error("Uknown error->" + e.getMessage(), e);
					}
				} else {
					logger.fatal("XSD cannot bd found.");
				}

			}

		}

		logger.info("MainApp eneded up.");
		System.exit(0);
	}

}
