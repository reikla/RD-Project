package at.ac.fh.salzburg.smartmeter.access;


/**
 * this interface is intended to specify what data source should be accessed.
 * this can be in every possible granularity.
 * e.g all Salzburg AG data, all Austrian data, data from fridge a in household b in town c
 */
public interface IDataSourceContext {

    String MeterID();
}
