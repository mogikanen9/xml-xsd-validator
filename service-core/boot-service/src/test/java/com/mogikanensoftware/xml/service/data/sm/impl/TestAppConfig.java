package com.mogikanensoftware.xml.service.data.sm.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mogikanensoftware.xml.service.data.dao.ItemRepository;
import com.mogikanensoftware.xml.service.data.dao.ResultRepository;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManager;
import com.mogikanensoftware.xml.service.data.sm.impl.ServiceManagerImpl;
import com.mogikanensoftware.xml.service.data.transform.CustomTransformator;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.Constants;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

@Configuration
public class TestAppConfig {

	
	@Bean
	public ResultRepository getResultRepository(){
		ResultRepository resultRepo = Mockito.mock(ResultRepository.class);
		
		//custom mock 4 save
		Result savedResult = new Result();
		savedResult.setId(System.currentTimeMillis());
		Mockito.when(resultRepo.save((Result)Matchers.any())).thenReturn(savedResult);
		
		return resultRepo;
	}
	
	@Bean
	public ItemRepository getItemRepository(){
		ItemRepository itemRepo = Mockito.mock(ItemRepository.class);
		return itemRepo;
	}

	@Bean
	public CustomTransformator getCustomTransformator(){
		return Mockito.mock(CustomTransformator.class);
	}
	
	@Bean
	public ValidationService getValidationService() throws ValidationServiceException, MalformedURLException{
		ValidationService mockService = Mockito.mock(ValidationService.class);
		File xmlFileToValidate = new File(this.getTmpFolderPath(),"mydata.dta");
		String[] xsdUrls = new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD };
		URL[] xsdFiles = new URL[xsdUrls.length];
		for (int i = 0; i < xsdUrls.length; i++) {
			xsdFiles[i] = new URL(xsdUrls[i]);
		}
		
		//return empty result
		Mockito.when(mockService.validate(xmlFileToValidate, xsdFiles)).thenReturn(new ValidationResult(xmlFileToValidate.getName()));
		
		return mockService;
	}
	
	@Bean
	public ServiceManager getServiceManager() throws ValidationServiceException, MalformedURLException{
		return new ServiceManagerImpl(this.getResultRepository(),this.getItemRepository()
				,this.getCustomTransformator(), this.getValidationService());
	}
	
	@Bean(name="tmpFolderPath")
	public String getTmpFolderPath(){
		return System.getProperty("java.io.tmpdir");
	}
	
	@Bean(name="dataFileName")
	public String getDataFile(){
		return "mydata.dta";
	}
}
