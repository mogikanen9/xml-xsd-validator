package mogikanensoftware.xml.service.data.transform.impl;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;

import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;
import mogikanensoftware.xml.service.data.transform.CustomTransformator;

@Component
public class DirectCustomTransformatorImpl implements CustomTransformator {

	@Override
	public List<Item> transform (Set<ValidationInfoBean> viBeans, final Result result) {
		
		Function<ValidationInfoBean, Item> mapToItem = new Function<ValidationInfoBean, Item>() {
		    public Item apply(ValidationInfoBean viBean) {
		    	Item item = new Item();
				item.setDateTime(new Date(System.currentTimeMillis()));
				item.setItemType(viBean.getInfoType().toString());
				item.setMessage(viBean.getMessage());
				item.setTargetName(viBean.getElementName());
				item.setTargetType(viBean.getErrorType());
				item.setResult(result);
				return item;
		    }
		};
		
		return viBeans.stream().map(mapToItem).collect(Collectors.toList());
		
	}

}
