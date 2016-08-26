package mogikanensoftware.xml.service.data.transform;

import java.util.List;
import java.util.Set;

import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;

import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;

public interface CustomTransformator {

	List<Item> transform (Set<ValidationInfoBean> viBeans, Result result);
}
