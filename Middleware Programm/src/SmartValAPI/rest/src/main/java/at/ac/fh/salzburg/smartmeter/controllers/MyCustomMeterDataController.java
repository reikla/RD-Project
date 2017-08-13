package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maximilian on 12.08.2017.
 */

@Controller
public class MyCustomMeterDataController extends CustomQueryControllerBase {

        @RequestMapping("/query/myMeterDataquery")
        @ResponseBody
        public QueryResult<?> GetMeterDatas(){
            return databaseAccess.QueryDatabase(new MyCustomMeterDataQuery());
        }


        private class MyCustomMeterDataQuery extends QueryBase<List<MeterDataEntity>> {

            public MyCustomMeterDataQuery() {
            }

            @Override
            public IDataSourceContext[] getDataSourceContexts() {
                return null;
            }

            @Override
            public String getQuery() {
                return "select * from meter_data where meter_id = 30";
            }

            @Override
            public QueryResult<List<MeterDataEntity>> parseDatabaseResultSet(ResultSet resultSet) {
                MyCustomMeterDataQueryResult result = new MyCustomMeterDataController.MyCustomMeterDataQueryResult();

                try {
                    while(resultSet.next()){
                        MeterDataEntity c = new MeterDataEntity();
                        //c.setDataId(resultSet.getInt("data_id"));
                        //c.setMeterId(resultSet.getInt("meter_id"));
                        c.setTimestamp(resultSet.getTimestamp("timestamp"));
                        /*c.setCountTotal(resultSet.getDouble("count_total"));
                        c.setCountRegister1(resultSet.getDouble("count_register1"));
                        c.setCountRegister2(resultSet.getDouble("count_register2"));
                        c.setCountRegister3(resultSet.getDouble("count_register3"));
                        c.setCountRegister4(resultSet.getDouble("count_register4"));
                        */c.setPowerP1(resultSet.getDouble("power_p1"));
                        /*c.setPowerP2(resultSet.getDouble("power_p2"));
                        c.setPowerP3(resultSet.getDouble("power_p3"));
                        c.setWorkP1(resultSet.getDouble("work_p1"));
                        c.setWorkP2(resultSet.getDouble("work_p2"));
                        c.setWorkP3(resultSet.getDouble("work_p3"));
                        c.setFrequency(resultSet.getDouble("frequency"));
                        c.setVoltage(resultSet.getDouble("voltage"));
                        */
                        result.AddMeterData(c);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return result;

            }
        }

        private class MyCustomMeterDataQueryResult extends QueryResult<List<MeterDataEntity>>{

            private List<MeterDataEntity> data = new ArrayList<>();

            protected MyCustomMeterDataQueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode) {
                super(isSuccessful, errorMessage, queryStatusCode);
            }

            protected MyCustomMeterDataQueryResult() {
                super(true,null,QueryStatusCode.OK);
            }

            @Override
            public List<MeterDataEntity> getData() {
                return data;
            }

            public void AddMeterData(MeterDataEntity c){
                data.add(c);
            }
        }

    }
