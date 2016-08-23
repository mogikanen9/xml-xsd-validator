package mogikanensoftware.xml.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mogikanensoftware.xml.utils.core.service.ParsingService;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.impl.BasicValidationServiceImpl;
import com.mogikanensoftware.xml.utils.core.service.impl.SAXErrorsParsingServiceImpl;

@Configuration
public class ApplicationConfig {

	@Bean
	public ValidationService getValidationService(){
	 return new BasicValidationServiceImpl(getParsingService());	
	}
	
	@Bean
	public ParsingService getParsingService(){
		return new SAXErrorsParsingServiceImpl();
	}
	
}
