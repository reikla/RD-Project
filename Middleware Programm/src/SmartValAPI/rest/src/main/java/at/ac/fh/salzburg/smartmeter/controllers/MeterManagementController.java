package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.data.dao.MeterManagementDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterManagementEntity;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterManagementEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class MeterManagementController {

    @Autowired
    private MeterManagementDao meterManagementDao;

    @RequestMapping("/admin/metermanangement/{id}")
    @ResponseBody
    public MeterManagementEntity readMeterManagement(@PathVariable int id) {
        return meterManagementDao.findByIdMeter(id);
    }

    @RequestMapping(value = "/admin/metermanangement", method = RequestMethod.PUT)
    @ResponseBody
    public MeterManagementEntity createMeterManagement(@RequestBody MeterManagementEntity metermanagement) {
        return meterManagementDao.save(metermanagement);
    }

    @RequestMapping(value = "/admin/metermanangement/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterManagementEntity updateMeterManagement(@RequestBody MeterManagementEntity metermanagement) {
        return meterManagementDao.save(metermanagement);
    }

    @RequestMapping(value = "/admin/metermanangement/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeterManagement(@PathVariable int id) {
        meterManagementDao.deleteByIdMeter(id);
        return true;
    }


}
