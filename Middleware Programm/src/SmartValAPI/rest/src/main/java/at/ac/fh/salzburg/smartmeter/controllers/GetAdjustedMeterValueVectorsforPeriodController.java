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
import java.sql.Timestamp;
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

            do {
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
            } while (resultSet.next());


            /// hier angleichen der Vektoren - synchronisieren

            HashMap<Integer, List<MeterDataEntity>> hm = new HashMap<>();
            Set<Integer> keys = ergebnis.keySet();

            // größten kleinsten und kleinsten größten Timestamp raussuchen
            Timestamp startTimestamp = new Timestamp(0);
            Timestamp endTimestamp = new Timestamp(Long.MAX_VALUE);
            for (Integer key : keys) {
                startTimestamp = new Timestamp(Math.max(startTimestamp.getTime(), getStartTsp(ergebnis.get(key)).getTime()));
                endTimestamp = new Timestamp(Math.min(endTimestamp.getTime(), getEndTsp(ergebnis.get(key)).getTime()));
            }


            // alle Listen auf diese Timestamp Begrenzeung zuschneiden
            for (Integer key : keys) {
                List<MeterDataEntity> temp = getonlyTspsbetween(ergebnis.get(key), startTimestamp, endTimestamp);
                ergebnis.replace(key, getonlyTspsbetween(ergebnis.get(key), startTimestamp, endTimestamp));
            }

            // samplesize kleinste ermitteln

            for (Integer key : keys) {
                System.out.println("meter_id: " + key);
                List<MeterDataEntity> x = ergebnis.get(key);
                System.out.println("Messdatenwerte" + x.size());
                System.out.println("Samplerate: " + getSampleRate(x));

                List<MeterDataEntity> y = rewriteSamplerate(x, 8);
                System.out.println("rewritten: " + y.size());
                hm.put(key, y);
            }
            //return ergebnis;
            return hm;
        }

        private Timestamp getStartTsp(List<MeterDataEntity> meterDataEntityList) {

            Timestamp minTimestamp = new Timestamp(Long.MAX_VALUE);
            for (MeterDataEntity item : meterDataEntityList) {
                if (item.getTimestamp().getTime() < minTimestamp.getTime()) {
                    minTimestamp = item.getTimestamp();
                }
            }
            return minTimestamp;
        }

        private Timestamp getEndTsp(List<MeterDataEntity> meterDataEntityList) {

            Timestamp maxTimestamp = new Timestamp(0);
            for (MeterDataEntity item : meterDataEntityList) {
                if (item.getTimestamp().getTime() > maxTimestamp.getTime()) {
                    maxTimestamp = item.getTimestamp();
                }
            }
            return maxTimestamp;
        }

        private List<MeterDataEntity> getonlyTspsbetween(List<MeterDataEntity> meterDataEntityList, Timestamp minimumTsp, Timestamp maxiTsp) {
            List<MeterDataEntity> ergebnis = new ArrayList<>();

            for (MeterDataEntity item : meterDataEntityList) {
                long u = minimumTsp.getTime();
                long x = item.getTimestamp().getTime();
                long o = maxiTsp.getTime();

                if (item.getTimestamp().getTime() >= minimumTsp.getTime() & item.getTimestamp().getTime() <= maxiTsp.getTime())
                    ergebnis.add(item);
            }
            return ergebnis;
        }

        private List<MeterDataEntity> messwerteinterpolieren(List<MeterDataEntity> meterDataEntityList, int sampleRate) {
            return new ArrayList<MeterDataEntity>();
        }


        private Integer getSampleRate(List<MeterDataEntity> meterDataEntityList) {

            // nicht für leere Listen
            if (meterDataEntityList.isEmpty())
                return 0;
            // zumindest zwei Werte müssen enthalten sein
            if (meterDataEntityList.size() < 2)
                return 0;

            long firstTimestamp = meterDataEntityList.get(0).getTimestamp().getTime();
            long lastTimestamp = meterDataEntityList.get(meterDataEntityList.size() - 1).getTimestamp().getTime();

            double temp = (lastTimestamp - firstTimestamp) / meterDataEntityList.size();
            int sR = (int) Math.round(temp);
            return sR;
        }

        private List<MeterDataEntity> rewriteSamplerate(List<MeterDataEntity> meterDataEntityList, int samplerate) {
            ArrayList<MeterDataEntity> reformatted = new ArrayList<>();
            int curIdx = -1;

            Calendar cal = Calendar.getInstance();

            long startTimestamp = meterDataEntityList.get(0).getTimestamp().getTime();

            cal.setTimeInMillis(meterDataEntityList.get(0).getTimestamp().getTime());
            cal.add(Calendar.SECOND, samplerate);
            Timestamp ende = new Timestamp(cal.getTime().getTime());
            long endTimestamp = ende.getTime();
            boolean firstValFlag = true;
            int collectedElems = 0;

            MeterDataEntity curElement = null;

            for (MeterDataEntity item : meterDataEntityList) {
                // Überlauf -> dann abschliessen und anhängen
                if (item.getTimestamp().getTime() > endTimestamp) {
                    if (curElement != null) {
                        // betroffenen Felder durch die Anzahl gesammelten durchdividieren
                        curElement.setCountTotal(curElement.getCountTotal() / collectedElems);
                        curElement.setCountRegister1(curElement.getCountRegister1() / collectedElems);
                        curElement.setCountRegister2(curElement.getCountRegister2() / collectedElems);
                        curElement.setCountRegister3(curElement.getCountRegister3() / collectedElems);
                        curElement.setCountRegister4(curElement.getCountRegister4() / collectedElems);
                        curElement.setPowerP1(curElement.getPowerP1() / collectedElems);
                        curElement.setPowerP2(curElement.getPowerP2() / collectedElems);
                        curElement.setPowerP3(curElement.getPowerP3() / collectedElems);
                        curElement.setWorkP1(curElement.getWorkP1() / collectedElems);
                        curElement.setWorkP2(curElement.getWorkP2() / collectedElems);
                        curElement.setWorkP3(curElement.getWorkP3() / collectedElems);
                        curElement.setFrequency(curElement.getFrequency() / collectedElems);
                        curElement.setVoltage(curElement.getVoltage() / collectedElems);

                        reformatted.add(curElement);
                        firstValFlag = true;
                        collectedElems = 0;
                        startTimestamp = endTimestamp;
                        ///endTimestamp = endTimestamp + samplerate;

                        cal.add(Calendar.SECOND, samplerate);
                        ende = new Timestamp(cal.getTime().getTime());
                        endTimestamp = ende.getTime();

                    }
                }
                // beim ersten Mal übernehmen
                if (firstValFlag == true) {
                    curElement = new MeterDataEntity();
                    curElement.setMeterId(item.getMeterId());
                    curElement.setDataId(item.getDataId());
                    curElement.setTimestamp(item.getTimestamp());
                    curElement.setCountTotal(item.getCountTotal());
                    curElement.setCountRegister1(item.getCountRegister1());
                    curElement.setCountRegister2(item.getCountRegister2());
                    curElement.setCountRegister3(item.getCountRegister3());
                    curElement.setCountRegister4(item.getCountRegister4());
                    curElement.setPowerP1(item.getPowerP1());
                    curElement.setPowerP2(item.getPowerP2());
                    curElement.setPowerP3(item.getPowerP3());
                    curElement.setWorkP1(item.getWorkP1());
                    curElement.setWorkP2(item.getWorkP2());
                    curElement.setWorkP3(item.getWorkP3());
                    curElement.setFrequency(item.getFrequency());
                    curElement.setVoltage(item.getVoltage());
                    firstValFlag = false;
                    collectedElems = 1;
                } else {
                    // sonst aufsummieren
                    curElement.setCountTotal(curElement.getCountTotal() + item.getCountTotal());
                    curElement.setCountRegister1(curElement.getCountRegister1() + item.getCountRegister1());
                    curElement.setCountRegister2(curElement.getCountRegister2() + item.getCountRegister2());
                    curElement.setCountRegister3(curElement.getCountRegister3() + item.getCountRegister3());
                    curElement.setCountRegister4(curElement.getCountRegister4() + item.getCountRegister4());
                    curElement.setPowerP1(curElement.getPowerP1() + item.getPowerP1());
                    curElement.setPowerP2(curElement.getPowerP2() + item.getPowerP2());
                    curElement.setPowerP3(curElement.getPowerP3() + item.getPowerP3());
                    curElement.setWorkP1(curElement.getWorkP1() + item.getWorkP1());
                    curElement.setWorkP2(curElement.getWorkP2() + item.getWorkP2());
                    curElement.setWorkP3(curElement.getWorkP3() + item.getWorkP3());
                    curElement.setFrequency(curElement.getFrequency() + item.getFrequency());
                    curElement.setVoltage(curElement.getVoltage() + item.getVoltage());
                    firstValFlag = false;
                    collectedElems += 1;
                }
            }
            return reformatted;
        }
    }
}

