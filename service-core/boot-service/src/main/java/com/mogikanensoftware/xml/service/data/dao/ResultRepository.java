package com.mogikanensoftware.xml.service.data.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mogikanensoftware.xml.service.data.entity.Result;

@Repository
public interface ResultRepository extends CrudRepository<Result,Long>{

}
