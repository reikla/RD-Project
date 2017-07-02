package at.ac.fh.salzburg.smartmeter.parser;

import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by maximilian on 01.07.2017.
 */
public class AdjustedMeterValueVectorsforPeriod {
    public MeterDataMetaData metaData = null;
    public HashMap<Integer, List<MeterDataEntity>> meterData = null;
}
