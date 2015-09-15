package com.mogikanensoftware.xml.utils.console;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;

public class MainApp {

	private static final Logger logger = LogManager.getLogger(MainApp.class);
	
	public static void main(String[] args) {
		
		logger.info("Starting MainApp...");

		if(args.length!=2 || args[0]==null || args[1]==null || args[0].length()==0 || args[1].length()==0){
			logger.fatal("Missing mandatory parameters - both xml file path adn xsd file path MUST be specified!");
		}else{
		
			String xmlFileParam = args[0];		
			String xsdFileParam = args[1];
			
			File xmlFile = new File(xmlFileParam);
			File xsdFile = new File(xsdFileParam);
			
			if(!xmlFile.exists()){
				logger.fatal("xmlFile doea not exist ->"+xmlFile.toString());
			}else if (!xsdFile.exists()){
				logger.fatal("xsdFile doea not exist ->"+xsdFile.toString());
			}else{
			
				logger.info("About to validate xml file ->"+xmlFile+" agaiinst xsd file -> "+xsdFile);
				
				
				ValidationService validationService = new BasicValidationServiceImpl();
				try {
					ValidationResult rs = validationService.validate(xmlFile, xsdFile);
					
					logger.info("rs->"+rs);
					
				} catch (ValidationServiceException e) {
					logger.error("Uknown error->"+e.getMessage(), e);
				}
			}
						
		}
				
		
		
		logger.info("MainApp eneded up.");
		System.exit(0);
	}

}
