package com.mogikanensoftware.xml.service.data.sm.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogikanensoftware.xml.service.data.dao.ItemRepository;
import com.mogikanensoftware.xml.service.data.dao.ResultRepository;
import com.mogikanensoftware.xml.service.data.entity.Item;
import com.mogikanensoftware.xml.service.data.entity.Result;
import com.mogikanensoftware.xml.service.data.sm.ServiceManager;
import com.mogikanensoftware.xml.service.data.sm.ServiceManagerException;
import com.mogikanensoftware.xml.service.data.sm.ValidationParamInfo;
import com.mogikanensoftware.xml.service.data.transform.CustomTransformator;
import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;
import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

@Service
public class ServiceManagerImpl implements ServiceManager {

	private static final Logger logger = LogManager.getLogger(ServiceManagerImpl.class);

	private ResultRepository resultRepository;

	private ItemRepository itemRepository;

	private CustomTransformator customTransformator;

	private ValidationService validationService;

	@Autowired
	public ServiceManagerImpl(ResultRepository resultRepository, ItemRepository itemRepository,
			CustomTransformator customTransformator, ValidationService validationService) {
		super();
		this.resultRepository = resultRepository;
		this.itemRepository = itemRepository;
		this.customTransformator = customTransformator;
		this.validationService = validationService;
	}

	@Override
	@Transactional
	public Long logValidationResults(ValidationResult validationResult) throws ServiceManagerException {

		Result newResult = new Result(new Date(System.currentTimeMillis()), validationResult.getTargetName());
		newResult = resultRepository.save(newResult);

		List<Item> items = this.customTransformator.transform(validationResult.getValidationErrors(), newResult);
		items.addAll(this.customTransformator.transform(validationResult.getValidationWarnings(), newResult));

		itemRepository.save(items);

		return newResult.getId();
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Result> listResults() throws ServiceManagerException {
		return resultRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Item> listItems() throws ServiceManagerException {
		return itemRepository.findAll();
	}

	@Override
	public String getTmpFolderPath() throws ServiceManagerException {
		return System.getProperty("java.io.tmpdir");
	}

	@Override
	public String generateTmpFileName(String originalFileName) throws ServiceManagerException {
		String genFileName = originalFileName + System.currentTimeMillis() + UUID.randomUUID().toString();
		if (logger.isDebugEnabled()) {
			logger.debug("tmp genFileName->" + genFileName);
		}
		return genFileName;
	}

	@Override
	@Transactional
	public ValidationResult performValidation(ValidationParamInfo paramInfo)
			throws ServiceManagerException {
		try {
			logger.info("xsdUrls->");
			Arrays.stream(paramInfo.getXsdUrls()).forEach(logger::info);

			File file = new File(paramInfo.getFolderPath(), paramInfo.getFileName());

			URL[] xsds = new URL[paramInfo.getXsdUrls().length];
			for (int i = 0; i < paramInfo.getXsdUrls().length; i++) {
				xsds[i] = new URL(paramInfo.getXsdUrls()[i]);
			}

			ValidationResult validationResult = validationService.validate(file, xsds);

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("ValidationResult -> %s",
						validationResult != null ? validationResult.toString() : "null"));
			}

			Long resultId = this.logValidationResults(validationResult);
			logger.info(String.format("result saved with id  ->%d", resultId));

			logger.info("items were saved");
			return validationResult;
		} catch (IOException | ValidationServiceException | NullPointerException ex) {
			logger.error(ex.getMessage());
			throw new ServiceManagerException(ex.getMessage(), ex);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Item> listItems(Long resultId) throws ServiceManagerException {
		return itemRepository.findByResultId(resultId);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Item> listItems(String fileName) throws ServiceManagerException {
		return itemRepository.findByResultFileName(fileName);
	}

}
