package com.mogikanensoftware.xml.service.data.sm;

import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

public interface ServiceManager {

	Long logValidationResults(ValidationResult validationResult) throws ServiceManagerException; 
	Iterable<Result> listResults() throws ServiceManagerException; 
	Iterable<Item> listItems() throws ServiceManagerException;
	
	String getTmpFolderPath() throws ServiceManagerException;
	String generateTmpFileName(String fileName) throws ServiceManagerException;
	
	ValidationResult performValidation(ValidationParamInfo paramInfo) throws ServiceManagerException;
}
