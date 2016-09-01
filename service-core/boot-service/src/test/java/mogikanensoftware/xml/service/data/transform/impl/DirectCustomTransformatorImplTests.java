package mogikanensoftware.xml.service.data.transform.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;
import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoType;

import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;

public class DirectCustomTransformatorImplTests{

	private DirectCustomTransformatorImpl transformator;

	@Before
	public void before() {
		transformator = new DirectCustomTransformatorImpl();
	}

	@After
	public void after() {
		transformator = null;
	}

	@Test
	public void testTransformNulls() {
		List<Item> listOfItems = null;
		
		//both null
		try {
			listOfItems = transformator.transform(null, null);
			Assert.fail("NullPointerExceptioj expected to be thrown when bith args are null");
		} catch (NullPointerException ex) {
			Assert.assertNull(listOfItems);
		}
		
		//second is null
		@SuppressWarnings("unchecked")
		Set<ValidationInfoBean> viBeans = (Set<ValidationInfoBean>)Mockito.mock(Set.class);
		try {
			listOfItems = transformator.transform(viBeans, null);
			Assert.fail("NullPointerExceptioj expected to be thrown");
		} catch (NullPointerException ex) {
			Assert.assertNull(listOfItems);
		}
		
		//first one is null
		Result result = Mockito.mock(Result.class);
		try {
			listOfItems = transformator.transform(null, result);
			Assert.fail("NullPointerExceptioj expected to be thrown");
		} catch (NullPointerException ex) {
			Assert.assertNull(listOfItems);
		}
		
		viBeans = new HashSet<>();
		ValidationInfoBean vBeanError = Mockito.mock(ValidationInfoBean.class);
		Mockito.when(vBeanError.getInfoType()).thenReturn(ValidationInfoType.error);
		ValidationInfoBean vBeanWarning = Mockito.mock(ValidationInfoBean.class);
		Mockito.when(vBeanWarning.getInfoType()).thenReturn(ValidationInfoType.error);
		viBeans.add(vBeanError);
		viBeans.add(vBeanWarning);
		
		listOfItems = transformator.transform(viBeans, result);
		Assert.assertNotNull(listOfItems);
		Assert.assertTrue(listOfItems.size()==2);
	}

	
	@Test
	public void testGetTransformationFunction(){
		
		Function<ValidationInfoBean, Item> mapToItem = transformator.getTransformationFunction(null);
		Assert.assertNotNull(mapToItem);
		
		mapToItem = transformator.getTransformationFunction(Mockito.mock(Result.class));
		Assert.assertNotNull(mapToItem);
		
		Item rsItem = null;
		try{
			rsItem = mapToItem.apply(null);
			Assert.fail("NullPointerExceptioj expected to be thrown");
		}catch(NullPointerException ex){
			Assert.assertNull(rsItem);
		}
		
		ValidationInfoBean bean = Mockito.mock(ValidationInfoBean.class);
		Mockito.when(bean.getElementName()).thenReturn("Element1");
		Mockito.when(bean.getErrorType()).thenReturn("My Error Type");
		Mockito.when(bean.getMessage()).thenReturn("My Custom Message");
		Mockito.when(bean.getInfoType()).thenReturn(ValidationInfoType.error);
		
		
		rsItem = mapToItem.apply(bean);
		Assert.assertNotNull(rsItem);
		Assert.assertEquals(rsItem.getItemType(),bean.getInfoType().toString());
		Assert.assertEquals(rsItem.getMessage(), bean.getMessage());
		Assert.assertEquals(rsItem.getTargetName(), bean.getElementName());
		Assert.assertEquals(rsItem.getTargetType(), bean.getErrorType());
	}
}
