package at.ac.fh.salzburg.smartmeter.data.dao;

import at.ac.fh.salzburg.smartmeter.data.entities.MeterManufactorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by maximilian on 30.04.2017.
 */
@Transactional
@Repository
public interface MeterManufactorDao extends CrudRepository<MeterManufactorEntity, Integer> {
}
