package at.ac.fh.salzburg.smartmeter.rest;
import at.ac.fh.salzburg.smartmeter.data.dao.LastEventsDao;
import at.ac.fh.salzburg.smartmeter.data.entities.LastEventsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class LastEventsController {
    @Autowired
    private LastEventsDao lastEventsDao;

    @RequestMapping("/admin/lastevents/{id}")
    @ResponseBody
    public LastEventsEntity readLastEvents(@PathVariable int id) {
        return lastEventsDao.findOne(id);
    }

    @RequestMapping(value = "/admin/lastevents", method = RequestMethod.PUT)
    @ResponseBody
    public LastEventsEntity createLastEvents(@RequestBody LastEventsEntity lastevent) {
        return lastEventsDao.save(lastevent);
    }

    @RequestMapping(value = "/admin/lastevents/{id}", method = RequestMethod.POST)
    @ResponseBody
    public LastEventsEntity updateLastEvents(@RequestBody LastEventsEntity lastevent) {
        return lastEventsDao.save(lastevent);
    }

    @RequestMapping(value = "/admin/lastevents/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteLastEvents(@PathVariable int id) {
        lastEventsDao.delete(id);
        return true;
    }
}