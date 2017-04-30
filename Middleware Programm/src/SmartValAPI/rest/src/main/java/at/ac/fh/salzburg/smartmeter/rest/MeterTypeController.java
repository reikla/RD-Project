package at.ac.fh.salzburg.smartmeter.rest;

import at.ac.fh.salzburg.smartmeter.data.dao.MeterTypeDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterTypeEntity;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by maximilian on 25.04.2017.
 */
@Controller
public class MeterTypeController {

    @Autowired
    private MeterTypeDao meterTypeDao;

    @RequestMapping("/admin/metertype/{id}")
    @ResponseBody
    public MeterTypeEntity readMeterTye(@PathVariable int id) {
        return meterTypeDao.findOne(id);
    }

    @RequestMapping(value = "/admin/metertype", method = RequestMethod.PUT)
    @ResponseBody
    public MeterTypeEntity createMeterType(@RequestBody MeterTypeEntity meterType) {
        return meterTypeDao.save(meterType);
    }

    @RequestMapping(value = "/admin/metertype/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterTypeEntity updateMeterType(@RequestBody MeterTypeEntity meterType) {
        return meterTypeDao.save(meterType);
    }

    @RequestMapping(value = "/admin/metertype/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeterType(@PathVariable int id) {
        meterTypeDao.delete(id);
        return true;
    }
}

