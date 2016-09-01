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
		ResultRepository resultRepo = Mockito.mock(ResultRepository.class);
		
		//custom mock 4 save
		Result savedResult = new Result();
		savedResult.setId(System.currentTimeMillis());
		Mockito.when(resultRepo.save((Result)Matchers.any())).thenReturn(savedResult);
		
		return resultRepo;
	}
	
	@Bean
	public ItemRepository getItemRepository(){
		ItemRepository itemRepo = Mockito.mock(ItemRepository.class);
		return itemRepo;
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
