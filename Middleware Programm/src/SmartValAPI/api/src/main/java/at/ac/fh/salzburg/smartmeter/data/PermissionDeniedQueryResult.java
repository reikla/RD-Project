package at.ac.fh.salzburg.smartmeter.data;

import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;

/**
 * Created by reimarklammer on 20.06.17.
 */
public class PermissionDeniedQueryResult extends QueryResult<Void> {

    public PermissionDeniedQueryResult(){
        super(false,"Permission Denied", QueryStatusCode.PermissionDenied);
    }

    @Override
    public Void getData() {
        return null;
    }
}
