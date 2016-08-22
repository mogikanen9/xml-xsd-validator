package mogikanensoftware.xml.service.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

@RestController
public class MainController {

	private static final Logger logger = LogManager.getLogger(MainController.class); 		
	
	@Autowired
	private ValidationService validationService;
	
		
	@RequestMapping(value="/validate",method = RequestMethod.POST)
	public ValidationResult validate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate){
		try {
			String folderPath = System.getProperty("java.io.tmpdir");			
			logger.info(String.format("folderPath -> %s", folderPath));
			
			String fileName = xmlFileToValidate.getOriginalFilename()+System.currentTimeMillis()+UUID.randomUUID().toString();
			
			Files.copy(xmlFileToValidate.getInputStream(), Paths.get(folderPath, fileName));
			
			File  file = new File(folderPath,fileName);
			return validationService.validate(file, new File[]{
					new File("C:/XMLforSpec4.1A/report_manager.xsd"),
					new File("C:/XMLforSpec4.1A/report_manager_dt.xsd")
			});
		} catch (IOException | ValidationServiceException e) {
			logger.error(e.getMessage(),e);
		}
		return new ValidationResult();
	}
}
