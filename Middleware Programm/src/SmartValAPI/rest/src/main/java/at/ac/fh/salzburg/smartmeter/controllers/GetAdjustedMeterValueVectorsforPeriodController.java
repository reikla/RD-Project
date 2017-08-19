package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;
import at.ac.fh.salzburg.smartmeter.parser.ResultsetParser;
import at.ac.fh.salzburg.smartmeter.parser.MeterDataMetaData;
import at.ac.fh.salzburg.smartmeter.parser.AdjustedMeterValueVectorsforPeriod;

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
import java.util.*;


/**
 * Created by maximilian on 01.06.2017.
 */
@Controller
public class GetAdjustedMeterValueVectorsforPeriodController extends CustomQueryControllerBase {

    @RequestMapping(value = "/query/adjustedmeterdatavectors", method = RequestMethod.GET)
    @ResponseBody
    public QueryResult<?> GetAdjustedMeterValueVectorsforPeriod(@RequestParam(value = "meterIds") String[] pmeterIds,
                                                                @RequestParam(value = "tspvon") String ptspvon,
                                                                @RequestParam(value = "tspbis") String ptspbis,
                                                                @RequestParam(value = "maxSamplefreq", required = false) String maxSamplefreq) {
        return databaseAccess.QueryDatabase(new GetAdjustedMeterValueVectorsforPeriodController.CustomMeterQuery(pmeterIds, ptspvon, ptspbis, maxSamplefreq));
    }

    /**
     * This class handles the custom Query
     */
    private class CustomMeterQuery extends QueryBase<AdjustedMeterValueVectorsforPeriod> {

        List<Integer> _meterIds = new ArrayList<>();
        int _maxSamplefreq = 0;
        String _tspvon = "";
        String _tspbis = "";

        private IDataSourceContext _dataSourceContext[] = null;

        /**
         * extracts the meterIds passed by the get request and fills the array for accessing,
         * checks the timestamps and eventually a maximum sample frequence to be returned
         *
         * @param pmeterIds
         * @param ptspvon
         * @param ptspbis
         * @param maxSamplefreq
         */
        public CustomMeterQuery(String[] pmeterIds, String ptspvon, String ptspbis, String maxSamplefreq) {
            for (String item : pmeterIds) {
                try {
                    _meterIds.add(Integer.parseInt(item));
                } catch (NumberFormatException e) {
                }
            }
            ;

            // übergebene Samplefrequenz übernehmen
            try {
                _maxSamplefreq = Integer.parseInt(maxSamplefreq);
            } catch (NumberFormatException e) {
                _maxSamplefreq = 0;
            }
            ;
            _tspvon = ptspvon;
            _tspbis = ptspbis;

            //todo: ist der IDataSourceContext richtig ausgefüllt?
            _dataSourceContext = getDataSourceContexts();
        }

        @Override
        public IDataSourceContext[] getDataSourceContexts() {

            return new IDataSourceContext[0];

        }


        @Override
        /**
         * generates the query string from template and variables
         */
        public String getQuery() {


            boolean first = true;
            StringBuilder querystring = new StringBuilder("select data_id, meter_id, timestamp, count_total, count_register1, count_register2, count_register3, count_register4, power_p1, power_p2, power_p3, work_p1, work_p2, work_p3, frequency, voltage from meter_data where meter_id in (");
            for (int item : _meterIds) {
                if (first != true)
                    querystring.append(", ");
                else first = false;
                querystring.append(Integer.toString(item));
            }
            querystring.append(") ");
            querystring.append(" and timestamp between \"");
            querystring.append(_tspvon);
            querystring.append("\" and \"");
            querystring.append(_tspbis);
            querystring.append("\" ");
            querystring.append("order by meter_id, data_id;");
            System.out.println("Query: " + querystring.toString());

            return querystring.toString();
        }

        @Override
        public QueryResult<AdjustedMeterValueVectorsforPeriod> parseDatabaseResultSet(ResultSet resultSet) {

            My2vectorsQueryResult result = null;

            try {
                if (resultSet.first()) {
                    result = new My2vectorsQueryResult();
                    ResultsetParser resultsetParser = new ResultsetParser();
                    MeterDataMetaData meterDataMetaData = new MeterDataMetaData();
                    resultsetParser.setlargestSampleDistance(_maxSamplefreq);

                    HashMap<Integer, List<MeterDataEntity>> aufbereiten = resultsetParser.aufbereiten(resultSet, meterDataMetaData);

                    result.data.meterData = aufbereiten;
                    result.data.metaData = meterDataMetaData;

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

    private class My2vectorsQueryResult extends QueryResult<AdjustedMeterValueVectorsforPeriod> {
        private MeterDataMetaData metaData = new MeterDataMetaData();
        private AdjustedMeterValueVectorsforPeriod data = new AdjustedMeterValueVectorsforPeriod();

        protected My2vectorsQueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode) {
            super(isSuccessful, errorMessage, queryStatusCode);
        }

        protected My2vectorsQueryResult() {
            super(true, null, QueryStatusCode.OK);
        }

        @Override
        public AdjustedMeterValueVectorsforPeriod getData() {
            return data;
        }

        public void setData(AdjustedMeterValueVectorsforPeriod p) {
            data = p;
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

}

