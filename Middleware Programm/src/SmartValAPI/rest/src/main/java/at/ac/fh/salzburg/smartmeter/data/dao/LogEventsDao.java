package at.ac.fh.salzburg.smartmeter.data.dao;

import at.ac.fh.salzburg.smartmeter.data.entities.LogEventsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by reimarklammer on 25.04.17.
 */
@Transactional
@Repository
public interface LogEventsDao extends CrudRepository<LogEventsEntity,Integer > {

}
