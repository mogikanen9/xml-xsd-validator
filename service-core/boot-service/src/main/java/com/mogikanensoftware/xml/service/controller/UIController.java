package com.mogikanensoftware.xml.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManager;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;

@Controller
public class UIController {


	@Autowired
	private ServiceManager serviceManager;
	
	@RequestMapping("/home")
    public String home(Model model) throws ServiceManagerException {
        return "home";
    }
	
	@RequestMapping("/results")
    public String viewResults(Model model) throws ServiceManagerException {
		
		Iterable<Result>  results = serviceManager.listResults();
		
        model.addAttribute("results", results);
        return "results";
    }
	
	@RequestMapping("/items")
    public String viewItems(@RequestParam(name="resultId") Long resultId, Model model) throws ServiceManagerException {
		
		Iterable<Item>  items = serviceManager.listItems(resultId);
		
        model.addAttribute("items", items);
        return "items";
    }
}
