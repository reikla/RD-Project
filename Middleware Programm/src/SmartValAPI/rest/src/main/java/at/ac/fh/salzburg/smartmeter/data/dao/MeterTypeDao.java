package at.ac.fh.salzburg.smartmeter.data.dao;

import at.ac.fh.salzburg.smartmeter.data.entities.MeterTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by maximilian on 25.04.2017.
 */
@Transactional
@Repository
public interface MeterTypeDao extends CrudRepository<MeterTypeEntity, Long> {
        MeterTypeEntity findByMeterTypeId(int meterTypeId);
}
