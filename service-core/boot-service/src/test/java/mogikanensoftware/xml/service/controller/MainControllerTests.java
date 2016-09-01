package mogikanensoftware.xml.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;

import mogikanensoftware.xml.service.data.entity.Result;
import mogikanensoftware.xml.service.data.sm.ServiceManager;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ValidationService validationService;

	@MockBean
	private ServiceManager serviceManager;

	private long currentTime;
	
	protected Result createNewSimpleResult(){
		Result result = new Result();
		result.setId(100L);
		result.setFileName("myfile.txt");
		
		result.setDateTime(new java.sql.Date(currentTime));
		return result;
	}
	
	@Before
	public void setUp() throws Exception {
		
		currentTime = System.currentTimeMillis();
		
		BDDMockito.given(this.serviceManager.listResults()).willReturn(Arrays.asList(createNewSimpleResult()));
	}

	@Test
	public void testDefaultValidate() throws Exception{
		//TODO build Multipart File post request
//		this.mvc.perform(post("/validate").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(content().string("Honda Civic"));
	}

	@Test
	public void testValidate() throws Exception{
		// TODO
	}
	
	@Test
	public void testListResults() throws Exception{
		List<Result> expectedResult = Arrays.asList(createNewSimpleResult());
		String expectedResultAsString = (new ObjectMapper()).writeValueAsString(expectedResult);
		this.mvc.perform(get("/listResults").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string(expectedResultAsString));
	}
	
	@Test
	public void testListItems(){
		// TODO
	}

}
