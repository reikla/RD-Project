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

    @RequestMapping("/admin/customer/{id}")
    @ResponseBody
    public CustomerEntity readCustomer(@PathVariable int id) {
        return customerDao.findOne(id);
    }

    @RequestMapping(value = "/admin/customer", method = RequestMethod.PUT)
    @ResponseBody
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customer) {
        return customerDao.save(customer);
    }

    @RequestMapping(value = "/admin/customer/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CustomerEntity updateCustomer(@RequestBody CustomerEntity customer) {
        return customerDao.save(customer);
    }

    @RequestMapping(value = "/admin/customer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteCustomer(@PathVariable int id) {
        customerDao.delete(id);
        return true;
    }
}
