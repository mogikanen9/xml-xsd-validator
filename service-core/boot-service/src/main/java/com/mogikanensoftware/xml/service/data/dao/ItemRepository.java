package com.mogikanensoftware.xml.service.data.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mogikanensoftware.xml.service.data.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long>{

	List<Item> findByResultId(Long resultId);
	List<Item> findByResultFileName(String fileName);
}
