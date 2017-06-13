package at.ac.fh.salzburg.smartmeter.rest;
import at.ac.fh.salzburg.smartmeter.data.dao.MeterDataDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class MeterDataController {

    @Autowired
    private MeterDataDao meterdataDao;

    @RequestMapping("/admin/meterdata/{id}")
    @ResponseBody
    public List<MeterDataEntity> readMeterData(@PathVariable int id) {

        return meterdataDao.findAllByMeterId(id);
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
        //return meterdataDao.deleteByMeterId(id);
        return true;
    }



}
