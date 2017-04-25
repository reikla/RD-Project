package at.ac.fh.salzburg.smartmeter.rest;

import at.ac.fh.salzburg.smartmeter.data.dao.CustomerDao;
import at.ac.fh.salzburg.smartmeter.data.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by reimarklammer on 04.04.17.
 */
@Controller
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @RequestMapping("/admin/customer")
    @ResponseBody
    public CustomerEntity findByCustomerId( int id){
        return customerDao.findByCustomerId(id);
    }

    @RequestMapping(value = "/admin/customer", method = RequestMethod.PUT)
    @ResponseBody
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customer){
        return customerDao.save(customer);
    }

}
