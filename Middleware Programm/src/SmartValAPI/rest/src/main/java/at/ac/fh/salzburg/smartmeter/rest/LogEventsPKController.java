package at.ac.fh.salzburg.smartmeter.rest;
import at.ac.fh.salzburg.smartmeter.data.dao.LogEventsPKDao;
import at.ac.fh.salzburg.smartmeter.data.entities.LogEventsEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * Created by maximilian on 30.04.2017.
 */
public class LogEventsPKController {

    @Autowired
    private LogEventsPKDao logEventsPKDao;

    @RequestMapping("/admin/logeventspk/{id}")
    @ResponseBody
    public LogEventsEntityPK readLogEventsPK(@PathVariable int id) {
        return logEventsPKDao.findOne(id);
    }

    @RequestMapping(value = "/admin/logeventspk", method = RequestMethod.PUT)
    @ResponseBody
    public LogEventsEntityPK createEventsPK(@RequestBody LogEventsEntityPK logeventspk) {
        return logEventsPKDao.save(logeventspk);
    }

    @RequestMapping(value = "/admin/logeventspk/{id}", method = RequestMethod.POST)
    @ResponseBody
    public LogEventsEntityPK updateEventsPK(@RequestBody LogEventsEntityPK logeventspk) {
        return logEventsPKDao.save(logeventspk);
    }

    @RequestMapping(value = "/admin/logeventspk/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteEventsPK(@PathVariable int id) {
        logEventsPKDao.delete(id);
        return true;
    }


}
