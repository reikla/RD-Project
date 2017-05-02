package at.ac.fh.salzburg.smartmeter.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import at.ac.fh.salzburg.smartmeter.data.dao.MeterProtocolDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterProtocolEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maximilian on 25.04.2017.
 */
@Controller
public class MeterProtocolController {
    @Autowired
    private MeterProtocolDao meterProtocolDao;

    @RequestMapping("/admin/meterprotocol/{id}")
    @ResponseBody
    public MeterProtocolEntity readMeterProtocol(@PathVariable int id) {
        return meterProtocolDao.findOne(id);
    }

    @RequestMapping(value = "/admin/meterprotocol", method = RequestMethod.PUT)
    @ResponseBody
    public MeterProtocolEntity createProtocol(@RequestBody MeterProtocolEntity meterprotocol) {
        return meterProtocolDao.save(meterprotocol);
    }

    @RequestMapping(value = "/admin/meterprotocol/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterProtocolEntity updateMeterProtocol(@RequestBody MeterProtocolEntity meterprotocol) {
        return meterProtocolDao.save(meterprotocol);
    }

    @RequestMapping(value = "/admin/meterprotocol/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteCustomer(@PathVariable int id) {
        meterProtocolDao.delete(id);
        return true;
    }

}
