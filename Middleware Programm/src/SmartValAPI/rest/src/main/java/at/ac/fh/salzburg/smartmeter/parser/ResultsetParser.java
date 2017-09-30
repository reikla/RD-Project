package at.ac.fh.salzburg.smartmeter.parser;

import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by maximilian on 07.06.2017.
 */
public class ResultsetParser {

    private int _largestSampleDistance = 0;

    public HashMap<Integer, List<MeterDataEntity>> aufbereiten(ResultSet resultSet, MeterDataMetaData meterDataMetaData) throws SQLException {
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
                ergebnis.put(c.getMeterId(), currentList); /**/
                lastmeterId = c.getMeterId();
            }
            currentList.add(c);
        } while (resultSet.next());


        /// mehr als einer?
        /// angleichen der Vektoren - synchronisieren

        HashMap<Integer, List<MeterDataEntity>> hm = new HashMap<>();
        Set<Integer> keys = ergebnis.keySet();

        // größten kleinsten und kleinsten größten Timestamp raussuchen
        Timestamp startTimestamp = new Timestamp(0);
        Timestamp endTimestamp = new Timestamp(Long.MAX_VALUE);
        for (Integer key : keys) {
            startTimestamp = new Timestamp(Math.max(startTimestamp.getTime(), getStartTsp(ergebnis.get(key)).getTime()));
            endTimestamp = new Timestamp(Math.min(endTimestamp.getTime(), getEndTsp(ergebnis.get(key)).getTime()));
        }

        // alle Listen auf diese Timestamp Begrenzung zuschneiden
        for (Integer key : keys) {
            List<MeterDataEntity> temp = getonlyTspsbetween(ergebnis.get(key), startTimestamp, endTimestamp);
            ergebnis.replace(key, getonlyTspsbetween(ergebnis.get(key), startTimestamp, endTimestamp));
        }

        // sample Abstand: größten ermitteln
        int largestSampleDistance = _largestSampleDistance;
        for (Integer key : keys) {
            List<MeterDataEntity> x = ergebnis.get(key);
            largestSampleDistance = Math.max(largestSampleDistance, getAvgSampleRate(x));
        }

        /// Metadaten merken
        meterDataMetaData.minTimestamp = startTimestamp;
        meterDataMetaData.maxTimestmap = endTimestamp;
        meterDataMetaData.samplingRateMillis = largestSampleDistance;

        if (largestSampleDistance > 0)
            // umschreiben auf längesten SampleAbstand
            for (Integer key : keys) {
                List<MeterDataEntity> x = ergebnis.get(key);
                List<MeterDataEntity> y = rewriteSamplerate(x, largestSampleDistance);
                hm.put(key, y);
            }
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

    private Integer getAvgSampleRate(List<MeterDataEntity> meterDataEntityList) {

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

        long startTimestamp = 0;

        if (meterDataEntityList.size() > 0)
            startTimestamp = meterDataEntityList.get(0).getTimestamp().getTime();

        cal.setTimeInMillis(meterDataEntityList.get(0).getTimestamp().getTime());
        cal.add(Calendar.MILLISECOND, samplerate);
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

                    cal.add(Calendar.MILLISECOND, samplerate);
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

    public void setlargestSampleDistance(int distance) {
        _largestSampleDistance = distance;
    }
}

