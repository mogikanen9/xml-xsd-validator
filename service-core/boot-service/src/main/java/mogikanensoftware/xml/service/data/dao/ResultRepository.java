package mogikanensoftware.xml.service.data.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mogikanensoftware.xml.service.data.entity.Result;

@Repository
public interface ResultRepository extends CrudRepository<Result,Long>{

}
