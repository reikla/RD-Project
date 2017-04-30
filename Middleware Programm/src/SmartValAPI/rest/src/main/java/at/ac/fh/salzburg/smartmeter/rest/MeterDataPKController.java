package at.ac.fh.salzburg.smartmeter.rest;

/**
 * Created by maximilian on 30.04.2017.
 */
import at.ac.fh.salzburg.smartmeter.data.dao.MeterDataPKDao;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


public class MeterDataPKController {

    @Autowired
    private MeterDataPKDao meterDataPKDao;

    @RequestMapping("/admin/meterdatapk/{id}")
    @ResponseBody
    public MeterDataEntityPK readMeterDataPK(@PathVariable int id) {
        return meterDataPKDao.findOne(id);
    }

    @RequestMapping(value = "/admin/meterdatapk", method = RequestMethod.PUT)
    @ResponseBody
    public MeterDataEntityPK createMeterDataPK(@RequestBody MeterDataEntityPK meterDataPK) {
        return meterDataPKDao.save(meterDataPK);
    }

    @RequestMapping(value = "/admin/meterdatapk/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MeterDataEntityPK updateMeterDataPK(@RequestBody MeterDataEntityPK meterDataPK) {
        return meterDataPKDao.save(meterDataPK);
    }

    @RequestMapping(value = "/admin/meterdatapk/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeterDataPK(@PathVariable int id) {
        meterDataPKDao.delete(id);
        return true;
    }

}
