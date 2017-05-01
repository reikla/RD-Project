//package at.ac.fh.salzburg.smartmeter.rest;
//
//import at.ac.fh.salzburg.smartmeter.data.dao.LastDataDao;
//import at.ac.fh.salzburg.smartmeter.data.entities.LastDataEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * Created by maximilian on 30.04.2017.
// */
//@Controller
//public class LastDataController {
//
//    @Autowired
//    private LastDataDao lastDataDao;
//
//    @RequestMapping("/admin/lastdata/{id}")
//    @ResponseBody
//    public LastDataEntity readLastData(@PathVariable int id) {
//        return lastDataDao.findOne(id);
//    }
//
//    @RequestMapping(value = "/admin/lastdata", method = RequestMethod.PUT)
//    @ResponseBody
//    public LastDataEntity createLastData(@RequestBody LastDataEntity lastData) {
//        return lastDataDao.save(lastData);
//    }
//
//    @RequestMapping(value = "/admin/lastdata/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public LastDataEntity updateLastData(@RequestBody LastDataEntity lastData) {
//        return lastDataDao.save(lastData);
//    }
//
//    @RequestMapping(value = "/admin/lastdata/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public boolean deleteLastData(@PathVariable int id) {
//        lastDataDao.delete(id);
//        return true;
//    }
//
//}
