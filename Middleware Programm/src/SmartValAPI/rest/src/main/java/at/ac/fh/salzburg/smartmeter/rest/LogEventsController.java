package at.ac.fh.salzburg.smartmeter.rest;
import at.ac.fh.salzburg.smartmeter.data.dao.LogEventsDao;
import at.ac.fh.salzburg.smartmeter.data.entities.LogEventsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class LogEventsController {

    @Autowired
    private LogEventsDao logEventsDao;

    @RequestMapping("/admin/logevents/{id}")
    @ResponseBody
    public LogEventsEntity readLogEvents(@PathVariable int id) {
        return logEventsDao.findOne(id);
    }

    @RequestMapping(value = "/admin/logevents", method = RequestMethod.PUT)
    @ResponseBody
    public LogEventsEntity createLogEvents(@RequestBody LogEventsEntity logevents) {
        return logEventsDao.save(logevents);
    }

    @RequestMapping(value = "/admin/logevents/{id}", method = RequestMethod.POST)
    @ResponseBody
    public LogEventsEntity updateLogEvents(@RequestBody LogEventsEntity logevents) {
        return logEventsDao.save(logevents);
    }

    @RequestMapping(value = "/admin/logevents/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteLogEvents(@PathVariable int id) {
        logEventsDao.delete(id);
        return true;
    }

}
