package at.ac.fh.salzburg.smartmeter.access;


import at.ac.fh.salzburg.smartmeter.data.data.QueryResult;

import java.sql.ResultSet;

/**
 * A Baseclass to query the database
 */
public abstract class QueryBase<T> {
    private IUserContext _userContext;
    private IDataSourceContext _dataSourceContext;


    public QueryBase(IUserContext userContext, IDataSourceContext dataSourceContext){
        _userContext = userContext;
        _dataSourceContext = dataSourceContext;
    }

    /**
     *
     * @return the user context of the query
     */
    public IUserContext getUserContext(){
        return _userContext;
    }

    /**
     *
     * @return the dataSourceContext of the query
     */
    public IDataSourceContext getDataSourceContext(){
        return _dataSourceContext;
    }

    /**
     * Implement this method for the query.
     * It will get
     * @return
     */
    public abstract String getQuery();

    public abstract QueryResult<T> parseDatabaseResultSet(ResultSet resultSet);
}
