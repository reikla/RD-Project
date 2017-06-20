package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.data.dao.MeterManufactorDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterManufactorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class MeterManufactorController {
    @Autowired
    private MeterManufactorDao meterManufactorDao;

    @RequestMapping("/admin/metermanufactor/{id}")
    @ResponseBody
    public MeterManufactorEntity readMeterManufactor(@PathVariable int id) {
        return meterManufactorDao.findOne(id);
    }

    @RequestMapping(value = "/admin/metermanufactor", method = RequestMethod.PUT)
    @ResponseBody
    public MeterManufactorEntity createMeterManufactor(@RequestBody MeterManufactorEntity metermanuFactor) {
        return meterManufactorDao.save(metermanuFactor);
    }

    @RequestMapping(value = "/admin/metermanufactor/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterManufactorEntity updateMeterManufactor(@RequestBody MeterManufactorEntity metermanuFactor) {
        return meterManufactorDao.save(metermanuFactor);
    }

    @RequestMapping(value = "/admin/metermanufactor/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeterManufactor(@PathVariable int id) {
        meterManufactorDao.delete(id);
        return true;
    }


}
