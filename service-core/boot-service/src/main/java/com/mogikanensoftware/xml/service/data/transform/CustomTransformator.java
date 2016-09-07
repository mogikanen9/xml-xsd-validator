package com.mogikanensoftware.xml.service.data.transform;

import java.util.List;
import java.util.Set;

import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.utils.core.bean.ValidationInfoBean;

public interface CustomTransformator {

	List<Item> transform (Set<ValidationInfoBean> viBeans, Result result);
}
