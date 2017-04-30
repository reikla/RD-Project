package at.ac.fh.salzburg.smartmeter.data.dao;

/**
 * Created by maximilian on 30.04.2017.
 */

import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntityPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface MeterDataPKDao extends CrudRepository<MeterDataEntityPK, Integer> {
}