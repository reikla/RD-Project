package at.ac.fh.salzburg.smartmeter.rest;

/**
 * Created by maximilian on 30.04.2017.
 */

import at.ac.fh.salzburg.smartmeter.data.dao.MeterManagementPKDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterManagementEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


public class MeterManagementPKController {


    @Autowired
    private MeterManagementPKDao metermenangementPKDao;

    @RequestMapping("/admin/metermanangementpk/{id}")
    @ResponseBody
    public MeterManagementEntityPK readMeterManagementPK(@PathVariable int id) {
        return metermenangementPKDao.findOne(id);
    }

    @RequestMapping(value = "/admin/metermanangementpk", method = RequestMethod.PUT)
    @ResponseBody
    public MeterManagementEntityPK createMeterManagementPK(@RequestBody MeterManagementEntityPK metermanagementPK) {
        return metermenangementPKDao.save(metermanagementPK);
    }

    @RequestMapping(value = "/admin/metermanangementpk/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterManagementEntityPK updateMeterManagementPK(@RequestBody MeterManagementEntityPK metermanagementPK) {
        return metermenangementPKDao.save(metermanagementPK);
    }

    @RequestMapping(value = "/admin/metermanangementpk/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeterManagementPK(@PathVariable int id) {
        metermenangementPKDao.delete(id);
        return true;
    }

}
