package at.ac.fh.salzburg.smartmeter.data;

import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;

/**
 * Created by reimarklammer on 30.05.17.
 */
public class VoidQueryResult extends QueryResult<Void> {

    public VoidQueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode) {
        super(isSuccessful, errorMessage, queryStatusCode);
    }

    @Override
    public Void getData() {
        return null;
    }
}
