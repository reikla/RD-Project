package at.ac.fh.salzburg.smartmeter.data.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class StringListQueryResult extends QueryResult<List<String>> {

    private final List<String> queryResult;

    public StringListQueryResult(){
        super();
        queryResult = new ArrayList<>();
    }


    @Override
    public List<String> getData() {
        return queryResult;
    }
}
