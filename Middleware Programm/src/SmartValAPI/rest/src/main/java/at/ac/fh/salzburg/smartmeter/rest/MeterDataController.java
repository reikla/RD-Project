package at.ac.fh.salzburg.smartmeter.rest;
import at.ac.fh.salzburg.smartmeter.data.dao.MeterDataDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class MeterDataController {

    @Autowired
    private MeterDataDao meterdataDao;

    @RequestMapping("/admin/meterdata/{id}")
    @ResponseBody
    public MeterDataEntity readMeterData(@PathVariable int id) {
        return meterdataDao.findOne(id);
    }

    @RequestMapping(value = "/admin/meterdata", method = RequestMethod.PUT)
    @ResponseBody
    public MeterDataEntity createMeterData(@RequestBody MeterDataEntity meterdata) {
        return meterdataDao.save(meterdata);
    }

    @RequestMapping(value = "/admin/meterdata/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterDataEntity updateMeterData(@RequestBody MeterDataEntity meterdata) {
        return meterdataDao.save(meterdata);
    }

    @RequestMapping(value = "/admin/meterdata/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeterData(@PathVariable int id) {
        meterdataDao.delete(id);
        return true;
    }



}
