package com.mogikanensoftware.xml.service.controller;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ValidationParamInfo;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.Constants;

@RestController
public class MainController extends AbstractController {	

	@RequestMapping(value = "/defaultValidate", method = RequestMethod.POST)
	public ValidationResult defaultValidate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate)
			throws Exception {
		return this.validate(xmlFileToValidate,
				new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD });
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ValidationResult validate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate,
			@RequestParam(name = "xsdUrls[]") String[] xsdUrls) throws Exception {
		ValidationParamInfo paramInfo = prepareValidationParams(xmlFileToValidate, xsdUrls);
		return serviceManager.performValidation(paramInfo);
	}

	@RequestMapping(value = "/listResults", method = RequestMethod.GET)
	public Iterable<Result> listResults() throws Exception {
		return serviceManager.listResults();
	}

	@RequestMapping(value = "/listItems", method = RequestMethod.GET)
	public Iterable<Item> listItems() throws Exception {
		return serviceManager.listItems();
	}

	@RequestMapping("/defaultValidateAsync")
	public Callable<ValidationResult> defaultValidateAsync(
			@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate) throws Exception {
		return validateAsync(xmlFileToValidate,
				new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD });
	}

	@RequestMapping("/validateAsync")
	public Callable<ValidationResult> validateAsync(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate,
			@RequestParam(name = "xsdUrls[]") String[] xsdUrls) throws Exception {
		ValidationParamInfo paramInfo = prepareValidationParams(xmlFileToValidate, xsdUrls);
		return () -> serviceManager.performValidation(paramInfo);
	}

}