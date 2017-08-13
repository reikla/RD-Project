package at.ac.fh.salzburg.smartmeter.access;

import at.ac.fh.salzburg.smartmeter.data.QueryResult;

public interface IDatabaseAccess {
    QueryResult QueryDatabase(QueryBase query);
}
