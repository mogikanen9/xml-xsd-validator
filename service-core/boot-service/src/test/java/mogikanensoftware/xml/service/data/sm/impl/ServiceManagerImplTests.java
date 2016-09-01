package mogikanensoftware.xml.service.data.sm.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

import mogikanensoftware.xml.service.TestAppConfig;
import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;
import mogikanensoftware.xml.service.data.sm.ServiceManager;
import mogikanensoftware.xml.service.data.sm.ServiceManagerException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestAppConfig.class)
public class ServiceManagerImplTests {

	@Autowired
	private ServiceManager serviceManager;
	
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

}
