package com.mogikanensoftware.xml.service.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mogikanensoftware.xml.service.controller.form.ValidationParamForm;
import com.mogikanensoftware.xml.service.data.sm.ServiceManager;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import com.mogikanensoftware.xml.service.data.sm.ValidationParamInfo;

public abstract class AbstractController {

	private static final Logger logger = LogManager.getLogger(AbstractController.class);
	
	@Autowired
	protected ServiceManager serviceManager;	
	
	protected ValidationParamInfo prepareValidationParams(MultipartFile xmlFileToValidate, String[] xsdUrls)
			throws ServiceManagerException, IOException {

		String fileName = serviceManager.generateTmpFileName(xmlFileToValidate.getOriginalFilename());
		logger.debug(String.format("prepareValidationParams: fileName -> %s", fileName));

		String tmpFolderPath = serviceManager.getTmpFolderPath();
		logger.debug(String.format("prepareValidationParams: tmpFolderPath -> %s", tmpFolderPath));

		Files.copy(xmlFileToValidate.getInputStream(), Paths.get(tmpFolderPath, fileName));

		return new ValidationParamInfo(xsdUrls, tmpFolderPath, fileName);

	}
	
	protected String[] processvalidationParamForm(ValidationParamForm validationParamForm) {
		List<String> xsdUrls = new ArrayList<>();

		if (!StringUtils.isEmpty(validationParamForm.getXsdUrl1())) {
			xsdUrls.add(validationParamForm.getXsdUrl1());
		}

		if (!StringUtils.isEmpty(validationParamForm.getXsdUrl2())) {
			xsdUrls.add(validationParamForm.getXsdUrl2());
		}

		return xsdUrls.toArray(new String[xsdUrls.size()]);
	}
}
