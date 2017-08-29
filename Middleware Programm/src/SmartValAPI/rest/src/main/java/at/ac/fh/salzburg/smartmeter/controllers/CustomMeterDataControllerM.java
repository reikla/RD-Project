package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.data.QueryResult;


import at.ac.fh.salzburg.smartmeter.data.mao.MyMongoMeterDataQuery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maximilian on 12.08.2017.
 */

@Controller
public class MyCustomMeterDataController extends CustomQueryControllerBase {
    @RequestMapping("/query/myMeterDataquery")
    @ResponseBody
    public QueryResult<?> GetMeterDatas(@RequestParam(value = "meterId", required = false) String pmeterId,
                                        @RequestParam(value = "tspvon", required = false) String ptspvon,
                                        @RequestParam(value = "tspbis", required = false) String ptspbis,
                                        @RequestParam(value = "maxSamplefreq", required = false) String maxSamplefreq) {
        // Todo: ermittle ob MySQL oder Mongo.
        /*
        return databaseAccess.QueryDatabase(new MyMongoMeterDataQuery(pmeterId, ptspvon, ptspbis, maxSamplefreq));
        */
        return databaseAccess.QueryDatabase(new MyMongoMeterDataQuery());

    }

}

