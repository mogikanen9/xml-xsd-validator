package mogikanensoftware.xml.service.data.sm;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;

public interface ServiceManager {

	Long logValidationResults(ValidationResult validationResult) throws ServiceManagerException; 
	Iterable<Result> listResults() throws ServiceManagerException; 
	Iterable<Item> listItems() throws ServiceManagerException;
	
	String getTmpFolderPath() throws ServiceManagerException;
	String generateTmpFileName(String filName) throws ServiceManagerException;
}
