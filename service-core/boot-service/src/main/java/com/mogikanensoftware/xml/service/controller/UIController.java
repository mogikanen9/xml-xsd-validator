package com.mogikanensoftware.xml.service.controller;

import javax.validation.Valid;

import com.mogikanensoftware.xml.service.controller.form.ValidationParamForm;
import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import com.mogikanensoftware.xml.service.data.sm.ValidationParamInfo;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.Constants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SessionAttributes("validationParamForm")
@Controller
public class UIController extends AbstractController {

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
	public String viewItemsByResultId(@PathVariable Long resultId, Model model) throws ServiceManagerException {

		Iterable<Item> items = serviceManager.listItems(resultId);

		model.addAttribute("items", items);
		return "items";
	}

	@GetMapping("/items/fileName/{fileName}")
	public String viewItemsByFileName(@PathVariable String fileName, Model model) throws ServiceManagerException {

		Iterable<Item> items = serviceManager.listItems(fileName);

		model.addAttribute("items", items);
		return "items";
	}

	@PostMapping("/uploadFile2Validate")
	public String uploadFile2Validate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate,
			@ModelAttribute ValidationParamForm validationParamForm, RedirectAttributes redirectAttributes, Model model)
			throws Exception {

		// ValidationParamInfo paramInfo =
		// prepareValidationParams(xmlFileToValidate, new String[] {
		// Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD });

		ValidationParamInfo paramInfo = prepareValidationParams(xmlFileToValidate,
				processvalidationParamForm(validationParamForm));
		ValidationResult validationResult = serviceManager.performValidation(paramInfo);

		model.addAttribute("validatedTargetName", validationResult.getTargetName());

		return "upload";
	}

	@GetMapping("/upload")
	public String validateFileGet(Model model) throws Exception {
		ValidationParamForm validationParamForm = new ValidationParamForm();
		validationParamForm.setXsdUrl1(Constants.REPORT_MANAGER_XSD);
		validationParamForm.setXsdUrl2(Constants.REPORT_MANAGER_DT_XSD);
		model.addAttribute("validationParamForm", validationParamForm);
		return "upload";
	}

	@PostMapping("/upload")
	public String validateFilePost(@Valid ValidationParamForm validationParamForm, BindingResult bindingResult,
			Model model) throws Exception {

		if (!bindingResult.hasErrors()) {
			model.addAttribute("xsdsWereUpdated", Boolean.TRUE);
		}

		return "upload";
	}

	@RequestMapping("/flush")
  	public String goodbye(SessionStatus status) {
		status.setComplete();
		return "home";
  	}
}
