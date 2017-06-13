package at.ac.fh.salzburg.smartmeter.data.dao;

import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntityPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by maximilian on 30.04.2017.
 */
@Transactional
@Repository
public interface MeterDataDao extends CrudRepository<MeterDataEntity, MeterDataEntityPK>{
    public List<MeterDataEntity> findAllByMeterId(int meterId);
    //boolean deleteByMeterId(int meterId);
}
