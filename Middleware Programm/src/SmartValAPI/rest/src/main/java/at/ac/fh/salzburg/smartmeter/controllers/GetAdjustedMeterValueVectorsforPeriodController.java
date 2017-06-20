package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.data.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;

import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;


/**
 * Created by maximilian on 01.06.2017.
 */
@Controller
public class GetAdjustedMeterValueVectorsforPeriodController extends CustomQueryControllerBase {
    @RequestMapping(value = "/query/adjustedmeterdatavectors", method = RequestMethod.GET)
    @ResponseBody

    public QueryResult<?> GetAdjustedMeterValueVectorsforPeriod(@RequestParam(value = "meterId1") String pmeterId1,
                                                                @RequestParam(value = "meterId2") String pmeterId2,
                                                                @RequestParam(value = "tspvon") String ptspvon,
                                                                @RequestParam(value = "tspbis") String ptspbis) {
        return databaseAccess.QueryDatabase(new GetAdjustedMeterValueVectorsforPeriodController.CustomMeterQuery(pmeterId1, pmeterId2, ptspvon, ptspbis));
    }

    private class CustomMeterQuery extends QueryBase<HashMap<Integer, List<MeterDataEntity>>> {

        int _meterId1 = 0;
        int _meterId2 = 0;
        String _tspvon = "";
        String _tspbis = "";

        private IDataSourceContext _dataSourceContext = null;

        public CustomMeterQuery(String pmeterId1, String pmeterId2, String ptspvon, String ptspbis) {
            _meterId1 = Integer.parseInt(pmeterId1);
            _meterId2 = Integer.parseInt(pmeterId2);
            _tspvon = ptspvon;
            _tspbis = ptspbis;

            //todo: Datasource context ausfüllen!
        }

        @Override
        public IDataSourceContext getDataSourceContext() {

            return _dataSourceContext;
        }

        @Override
        public String getQuery() {


            StringBuilder querystring = new StringBuilder("select data_id, meter_id, timestamp, count_total, count_register1, count_register2, count_register3, count_register4, power_p1, power_p2, power_p3, work_p1, work_p2, work_p3, frequency, voltage from meter_data where meter_id in (");
            querystring.append(Integer.toString(_meterId1));
            querystring.append(", ");
            querystring.append(Integer.toString(_meterId2));
            querystring.append(") ");
            querystring.append(" and timestamp between \"");
            querystring.append(_tspvon);
            querystring.append("\" and \"");
            querystring.append(_tspbis);
            querystring.append("\" ");
            querystring.append("order by meter_id, data_id;");

            return querystring.toString();
        }

        @Override
        public QueryResult<HashMap<Integer, List<MeterDataEntity>>> parseDatabaseResultSet(ResultSet resultSet) {

            My2vectorsQueryResult result = null;

            try {
                if (resultSet.first()) {
                    result = new My2vectorsQueryResult();
                    ResultsetParser resultsetParser = new ResultsetParser();

                    HashMap<Integer, List<MeterDataEntity>> aufbereiten = resultsetParser.aufbereiten(resultSet);

                    result.data = aufbereiten;
                } else {
                    result = new My2vectorsQueryResult(false, "No data", QueryStatusCode.Error);
                }
            } catch (SQLException e) {
                result = new My2vectorsQueryResult(false, e.getMessage(), QueryStatusCode.SqlError);

                e.printStackTrace();
            }
            return result;
        }
    }

    private class My2vectorsQueryResult extends QueryResult<HashMap<Integer, List<MeterDataEntity>>> {

        private HashMap<Integer, List<MeterDataEntity>> data = new HashMap<>();

        protected My2vectorsQueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode) {
            super(isSuccessful, errorMessage, queryStatusCode);
        }

        protected My2vectorsQueryResult() {
            super(true, null, QueryStatusCode.OK);
        }

        @Override
        public HashMap<Integer, List<MeterDataEntity>> getData() {
            return data;
        }
    }

    private long extractMilisfromTsp(String tspIn) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            if (tspIn.contains("'")) {
                tspIn.replace("'", "");
            }
            date = sdf.parse(tspIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    private class ResultsetParser {
        HashMap<Integer, List<MeterDataEntity>> aufbereiten(ResultSet resultSet) throws SQLException {
            int lastmeterId = -1;
            HashMap<Integer, List<MeterDataEntity>> ergebnis = new HashMap<>();
            List<MeterDataEntity> currentList = null;

            while (resultSet.next()) {
                MeterDataEntity c = new MeterDataEntity();
                c.setDataId(resultSet.getInt("data_id"));
                c.setMeterId(resultSet.getInt("meter_id"));
                c.setTimestamp(resultSet.getTimestamp("timestamp"));
                c.setCountTotal(resultSet.getDouble("count_total"));
                c.setCountRegister1(resultSet.getDouble("count_register1"));
                c.setCountRegister2(resultSet.getDouble("count_register2"));
                c.setCountRegister3(resultSet.getDouble("count_register3"));
                c.setCountRegister4(resultSet.getDouble("count_register4"));
                c.setPowerP1(resultSet.getDouble("power_p1"));
                c.setPowerP2(resultSet.getDouble("power_p2"));
                c.setPowerP3(resultSet.getDouble("power_p3"));
                c.setWorkP1(resultSet.getDouble("work_p1"));
                c.setWorkP2(resultSet.getDouble("work_p2"));
                c.setWorkP3(resultSet.getDouble("work_p3"));
                c.setFrequency(resultSet.getDouble("frequency"));
                c.setVoltage(resultSet.getDouble("voltage"));

                if (c.getMeterId() != lastmeterId) {
                    currentList = new ArrayList<>();
                    ergebnis.put(c.getMeterId(), currentList);
                    lastmeterId = c.getMeterId();
                }
                currentList.add(c);
            }
            // hier gehen wir synchronizen
            return ergebnis;
        }
    }
}
