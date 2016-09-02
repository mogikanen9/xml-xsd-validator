package com.mogikanensoftware.xml.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogikanensoftware.xml.service.controller.MainController;
import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManager;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTests {

	private static final String MYFILE_XML = "myfile.xml";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ValidationService validationService;

	@MockBean
	private ServiceManager serviceManager;

	private long currentTime;

	protected Result createNewSimpleResult(long id) {
		Result result = new Result();
		result.setId(id);
		result.setFileName("myfile.txt");

		result.setDateTime(new java.sql.Date(currentTime));
		return result;
	}

	protected Item createNewSimpleItem(Result result, long id) {
		Item item = new Item();
		item.setDateTime(new java.sql.Date(currentTime));
		item.setId(id);
		item.setItemType("error");
		item.setMessage("Item message");
		item.setResult(result);
		item.setTargetName("targetName");
		item.setTargetType("element");
		return item;
	}

	@Before
	public void setUp() throws Exception {

		currentTime = System.currentTimeMillis();

		BDDMockito.given(this.serviceManager.listItems())
				.willReturn(Arrays.asList(createNewSimpleItem(createNewSimpleResult(100L), 200)));
		BDDMockito.given(this.serviceManager.listResults()).willReturn(Arrays.asList(createNewSimpleResult(100L)));

		BDDMockito.given(serviceManager.generateTmpFileName(MYFILE_XML)).willReturn(MYFILE_XML + currentTime);

		BDDMockito.given(serviceManager.getTmpFolderPath()).willReturn(System.getProperty("java.io.tmpdir"));
	}

	@Test
	public void testListResults() throws Exception {

		List<Result> expectedResult = Arrays.asList(createNewSimpleResult(100L));
		String expectedResultAsString = (new ObjectMapper()).writeValueAsString(expectedResult);
		this.mvc.perform(get("/listResults").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(expectedResultAsString));
	}

	@Test(expected = org.springframework.web.util.NestedServletException.class)
	public void testListResultsException() throws Exception {

		final String errMsg = "Invalid data";
		BDDMockito.given(this.serviceManager.listResults()).willThrow(new ServiceManagerException(errMsg));
		try {
			this.mvc.perform(get("/listResults").accept(MediaType.APPLICATION_JSON)).andExpect(status().is(500));
		} catch (Exception sme) {
			Assert.assertTrue(sme.getMessage().contains(errMsg));
			Assert.assertTrue(sme.getCause() instanceof ServiceManagerException);
			throw sme;
		}
	}

	@Test
	public void testListItems() throws Exception {
		List<Item> expectedItems = (Arrays.asList(createNewSimpleItem(createNewSimpleResult(100L), 200)));
		String expectedItemsAsString = (new ObjectMapper()).writeValueAsString(expectedItems);
		this.mvc.perform(get("/listItems").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(expectedItemsAsString));
	}

	@Test(expected = org.springframework.web.util.NestedServletException.class)
	public void testListItensException() throws Exception {

		final String errMsg = "Bad data";
		BDDMockito.given(this.serviceManager.listItems()).willThrow(new ServiceManagerException(errMsg));
		try {
			this.mvc.perform(get("/listItems").accept(MediaType.APPLICATION_JSON)).andExpect(status().is(500));
		} catch (Exception sme) {
			Assert.assertTrue(sme.getMessage().contains(errMsg));
			Assert.assertTrue(sme.getCause() instanceof ServiceManagerException);
			throw sme;
		}
	}

	@Test
	public void testDefaultValidate() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("xmlFileToValidate", MYFILE_XML, "text/plain",
				"some xml".getBytes());

		mvc.perform(MockMvcRequestBuilders.fileUpload("/defaultValidate").file(firstFile)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is(200));

	}

	@Test
	public void testValidate() throws Exception {
		// TODO
	}

	@Test
	public void testDeafultValidateAsync() throws Exception {
		// TODO
	}

	@Test
	public void testValidateAsync() throws Exception {
		// TODO
	}
}
