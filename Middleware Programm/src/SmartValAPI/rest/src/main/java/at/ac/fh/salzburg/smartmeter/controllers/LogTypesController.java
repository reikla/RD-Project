package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.data.dao.LogTypesDao;
import at.ac.fh.salzburg.smartmeter.data.entities.LogTypesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * Created by maximilian on 30.04.2017.
 */
@Controller
public class LogTypesController {

    @Autowired
    private LogTypesDao logTypesDao;

    @RequestMapping("/admin/logtypes/{id}")
    @ResponseBody
    public LogTypesEntity readLogTypes(@PathVariable int id) {
        return logTypesDao.findOne(id);
    }

    @RequestMapping(value = "/admin/logtypes", method = RequestMethod.PUT)
    @ResponseBody
    public LogTypesEntity createLogTypes(@RequestBody LogTypesEntity logtypes) {
        return logTypesDao.save(logtypes);
    }

    @RequestMapping(value = "/admin/logtypes/{id}", method = RequestMethod.POST)
    @ResponseBody
    public LogTypesEntity updateLogTypes(@RequestBody LogTypesEntity logtypes) {
        return logTypesDao.save(logtypes);
    }

    @RequestMapping(value = "/admin/logtypes/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteLogTypes(@PathVariable int id) {
        logTypesDao.delete(id);
        return true;
    }

}
