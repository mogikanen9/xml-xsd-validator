package mogikanensoftware.xml.service;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mogikanensoftware.xml.service.data.dao.ItemRepository;
import mogikanensoftware.xml.service.data.dao.ResultRepository;
import mogikanensoftware.xml.service.data.entity.Result;
import mogikanensoftware.xml.service.data.sm.ServiceManager;
import mogikanensoftware.xml.service.data.sm.impl.ServiceManagerImpl;
import mogikanensoftware.xml.service.data.transform.CustomTransformator;

@Configuration
public class TestAppConfig {

	@Bean
	public ResultRepository getResultRepository(){
		ResultRepository rs = Mockito.mock(ResultRepository.class);
		
		//custom mock 4 save
		Result savedResult = new Result();
		savedResult.setId(System.currentTimeMillis());
		Mockito.when(rs.save((Result)Matchers.any())).thenReturn(savedResult);
		
		return rs;
	}
	
	@Bean
	public ItemRepository getItemRepository(){
		return Mockito.mock(ItemRepository.class);
	}

	@Bean
	public CustomTransformator getCustomTransformator(){
		return Mockito.mock(CustomTransformator.class);
	}
	
	@Bean
	public ServiceManager getServiceManager(){
		return new ServiceManagerImpl(this.getResultRepository(),this.getItemRepository(),this.getCustomTransformator());
	}
}
