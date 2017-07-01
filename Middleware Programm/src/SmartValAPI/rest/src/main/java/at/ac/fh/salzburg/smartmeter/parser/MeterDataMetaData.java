package at.ac.fh.salzburg.smartmeter.parser;

import java.sql.Timestamp;

/**
 * Created by maximilian on 01.07.2017.
 */
public class MeterDataMetaData {
    public int meter_id = 0;
    public Timestamp minTimestamp = null;
    public Timestamp maxTimestmap = null;
    public double samplingRate = 0;

}
