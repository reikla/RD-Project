package at.ac.fh.salzburg.smartmeter.data.dao;

import at.ac.fh.salzburg.smartmeter.data.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by reimarklammer on 18.04.17.
 */
@Transactional
@Repository
public interface CustomerDao extends CrudRepository<CustomerEntity, Long> {
    CustomerEntity findByCustomerId(int customerId);
    List<CustomerEntity> findAllByCustomerIdGreaterThan(int customerId);
}
