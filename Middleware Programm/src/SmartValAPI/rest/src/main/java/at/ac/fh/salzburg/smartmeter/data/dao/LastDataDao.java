package at.ac.fh.salzburg.smartmeter.data.dao;

import at.ac.fh.salzburg.smartmeter.data.entities.LastDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by maximilian on 30.04.2017.
 */
@Transactional
@Repository
public interface LastDataDao extends CrudRepository<LastDataEntity, Integer>{
}
