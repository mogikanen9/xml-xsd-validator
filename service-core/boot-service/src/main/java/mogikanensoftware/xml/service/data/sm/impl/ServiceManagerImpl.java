package mogikanensoftware.xml.service.data.sm.impl;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

import mogikanensoftware.xml.service.data.dao.ItemRepository;
import mogikanensoftware.xml.service.data.dao.ResultRepository;
import mogikanensoftware.xml.service.data.entity.Item;
import mogikanensoftware.xml.service.data.entity.Result;
import mogikanensoftware.xml.service.data.sm.ServiceManager;
import mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import mogikanensoftware.xml.service.data.transform.CustomTransformator;

@Service
@Transactional
public class ServiceManagerImpl implements ServiceManager {

	private static final Logger logger = LogManager.getLogger(ServiceManagerImpl.class);
	
	private ResultRepository resultRepository;

	private ItemRepository itemRepository;

	private CustomTransformator customTransformator;

	@Autowired
	public ServiceManagerImpl(ResultRepository resultRepository, ItemRepository itemRepository,
			CustomTransformator customTransformator) {
		super();
		this.resultRepository = resultRepository;
		this.itemRepository = itemRepository;
		this.customTransformator = customTransformator;
	}

	@Override
	public Long logValidationResults(ValidationResult validationResult) throws ServiceManagerException {

		Result newResult = new Result(new Date(System.currentTimeMillis()), validationResult.getTargetName());
		newResult = resultRepository.save(newResult);

		List<Item> items = this.customTransformator.transform(validationResult.getValidationErrors(), newResult);
		items.addAll(this.customTransformator.transform(validationResult.getValidationWarnings(), newResult));

		itemRepository.save(items);

		return newResult.getId();
	}

	@Override
	public Iterable<Result> listResults() throws ServiceManagerException {
		return resultRepository.findAll();
	}

	@Override
	public Iterable<Item> listItems() throws ServiceManagerException {
		return itemRepository.findAll();
	}

	@Override
	public String getTmpFolderPath() throws ServiceManagerException {
		return System.getProperty("java.io.tmpdir");
	}

	@Override
	public String generateTmpFileName(String originalFileName) throws ServiceManagerException {
		String genFileName =  originalFileName + System.currentTimeMillis() + UUID.randomUUID().toString();
		if (logger.isDebugEnabled()) {
			logger.debug("tmp genFileName->" + genFileName);
		}
		return genFileName;
	}

}