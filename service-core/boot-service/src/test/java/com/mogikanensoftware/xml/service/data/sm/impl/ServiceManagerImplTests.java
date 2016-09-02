package com.mogikanensoftware.xml.service.data.sm.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManager;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestAppConfig.class)
public class ServiceManagerImplTests {

	
	@Autowired
	private ServiceManager serviceManager;
	
	@Autowired
	@Qualifier("dataFileName")
	private String dataFileName;
	
	@Test
	public void testLogValidationResults() throws ServiceManagerException {
		ValidationResult validationResult = Mockito.mock(ValidationResult.class);
		Long resultId = serviceManager.logValidationResults(validationResult);
		Assert.assertNotNull(resultId);
		Assert.assertTrue(resultId>0);
	}

	@Test
	public void testListResults() {
		try {
			Iterable<Result> results = serviceManager.listResults();
		} catch (ServiceManagerException e) {
			Assert.fail(e.getMessage());
		}
		
	}

	@Test
	public void testListItems() throws ServiceManagerException {
		try {
		Iterable<Item> items = serviceManager.listItems();
		} catch (ServiceManagerException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetTmpFolderPath() throws ServiceManagerException{
		String tmpFolderpath = serviceManager.getTmpFolderPath();
		Assert.assertEquals(tmpFolderpath, System.getProperty("java.io.tmpdir"));
	}

	@Test
	public void testGenerateTmpFileName() throws ServiceManagerException{
		String tmpFileName = serviceManager.generateTmpFileName(dataFileName);
		Assert.assertTrue(tmpFileName.startsWith(dataFileName));
		Assert.assertNotEquals(tmpFileName, dataFileName);
	
		String tmpFileName2 = serviceManager.generateTmpFileName(dataFileName);
		Assert.assertNotEquals(tmpFileName, tmpFileName2);
	}
	
	@Test
	public void testPerformValidation() throws ServiceManagerException{
		String[] xsdUrls = new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD };
		String folderPath = serviceManager.getTmpFolderPath();
		String fileName = dataFileName;
		ValidationResult rs = serviceManager.performValidation(xsdUrls, folderPath, fileName);
		Assert.assertNotNull(rs);
		Assert.assertEquals(rs.getTargetName(),dataFileName);
	}
	
	@Test(expected=ServiceManagerException.class)
	public void testPerformValidationBadXsdUrl() throws ServiceManagerException{
		String[] xsdUrls = new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD, "bad.xsd.url?<<" };
		String folderPath = serviceManager.getTmpFolderPath();
		String fileName = dataFileName;
		serviceManager.performValidation(xsdUrls, folderPath, fileName);
	}
	
	@Test(expected=ServiceManagerException.class)
	public void testPerformValidationBadFileName() throws ServiceManagerException{
		serviceManager.performValidation(new String[] { "http://neskaju.nikomu/gde.xsd" }, 
				serviceManager.getTmpFolderPath(), "fileDoesNotExists.txt");
	}
}
