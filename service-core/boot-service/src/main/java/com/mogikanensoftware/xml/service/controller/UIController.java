package com.mogikanensoftware.xml.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import com.mogikanensoftware.xml.service.data.sm.ValidationParamInfo;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.Constants;

@Controller
public class UIController extends AbstractController{

	

	@RequestMapping("/home")
	public String home(Model model) throws ServiceManagerException {
		return "home";
	}

	@RequestMapping("/results")
	public String viewResults(Model model) throws ServiceManagerException {

		Iterable<Result> results = serviceManager.listResults();

		model.addAttribute("results", results);
		return "results";
	}

	@GetMapping("/items/resultId/{resultId}")
	public String viewItemsByResultId(@PathVariable Long resultId, Model model)
			throws ServiceManagerException {
		
		
		Iterable<Item> items = serviceManager.listItems(resultId);

		model.addAttribute("items", items);
		return "items";
	}

	@GetMapping("/items/fileName/{fileName}")
	public String viewItemsByFileName(@PathVariable String fileName, Model model)
			throws ServiceManagerException {
				
		Iterable<Item> items = serviceManager.listItems(fileName);

		model.addAttribute("items", items);
		return "items";
	}
	
	@PostMapping("/uploadFile2Validate")
	public String uploadFile2Validate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate, 
			RedirectAttributes redirectAttributes, Model model) throws Exception {
		
		ValidationParamInfo paramInfo = prepareValidationParams(xmlFileToValidate, new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD });
		ValidationResult validationResult = serviceManager.performValidation(paramInfo);
				
		model.addAttribute("validatedTargetName", validationResult.getTargetName());
		
		return "upload";
	}
	
	@GetMapping("/upload")
	public String validateFile(Model model) throws Exception {
			
		return "upload";
	}
}
