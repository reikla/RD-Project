package at.ac.fh.salzburg.smartmeter.access;


import at.ac.fh.salzburg.smartmeter.data.QueryResult;

import java.sql.ResultSet;

/**
 * A Baseclass to query the database
 */
public abstract class QueryBase<T> {


    /**
     *
     * @return the dataSourceContext of the query
     */
    public abstract IDataSourceContext[] getDataSourceContexts();

    /**
     * Implement this method for the query.
     * It will get
     * @return
     */
    public abstract String getQuery();

    public abstract QueryResult<T> parseDatabaseResultSet(ResultSet resultSet);
}
