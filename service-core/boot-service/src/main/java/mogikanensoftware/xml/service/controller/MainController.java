package mogikanensoftware.xml.service.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
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
import com.mogikanensoftware.xml.utils.core.service.Constants;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

import mogikanensoftware.xml.service.data.dao.ItemRepository;
import mogikanensoftware.xml.service.data.dao.ResultRepository;
import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;
import mogikanensoftware.xml.service.data.sm.ServiceManager;
import mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import mogikanensoftware.xml.service.data.transform.CustomTransformator;


@RestController
public class MainController {

	private static final Logger logger = LogManager.getLogger(MainController.class);

	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private ServiceManager serviceManager;

	
	
	@RequestMapping(value = "/defaultValidate", method = RequestMethod.POST)
	public ValidationResult defaultValidate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate)
			throws Exception {
		return this.validate(xmlFileToValidate,
				new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD });
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ValidationResult validate(@RequestParam("xmlFileToValidate") MultipartFile xmlFileToValidate,
			@RequestParam(name = "xsdUrls[]") String[] xsdUrls) throws Exception {
		try {
			String folderPath = System.getProperty("java.io.tmpdir");
			logger.info(String.format("folderPath -> %s", folderPath));

			logger.info("xsdUrls->");
			Arrays.stream(xsdUrls).forEach(logger::info);
						

			String fileName = xmlFileToValidate.getOriginalFilename() + System.currentTimeMillis()
					+ UUID.randomUUID().toString();

			Files.copy(xmlFileToValidate.getInputStream(), Paths.get(folderPath, fileName));

			File file = new File(folderPath, fileName);

			URL[] xsds = new URL[xsdUrls.length];
			for (int i = 0; i < xsdUrls.length; i++) {
				xsds[i] = new URL(xsdUrls[i]);
			}

			ValidationResult validationResult = validationService.validate(file, xsds);

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("ValidationResult -> %s", validationResult.toString()));
			}

			Long resultId = serviceManager.logValidationResults(validationResult);
			logger.info(String.format("result saved with id  ->%d", resultId));			
			
			
			logger.info("items were saved");
			return validationResult;
			
		} catch (IOException | ValidationServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "/listResults", method = RequestMethod.GET)
	public Iterable<Result> listResults() throws Exception{
		try {
			return serviceManager.listResults();
		} catch (ServiceManagerException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/listItems", method = RequestMethod.GET)
	public Iterable<Item> listItems() throws Exception{
		try {
			return serviceManager.listItems();
		} catch (ServiceManagerException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}		
	}
}