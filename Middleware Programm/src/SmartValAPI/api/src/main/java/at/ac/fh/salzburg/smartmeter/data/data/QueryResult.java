package at.ac.fh.salzburg.smartmeter.data.data;

import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;

public abstract class QueryResult<T> {

    protected boolean _isSuccessful;
    protected String _errorMessage;
    protected QueryStatusCode _statusCode;


    /**
     * Constructor used for Everything ok
     */
    protected QueryResult(){
        _isSuccessful = true;
        _errorMessage = null;
        _statusCode = QueryStatusCode.OK;
    }

    /**
     * Constructor to specify error details
     * @param isSuccessful
     * @param errorMessage
     * @param queryStatusCode
     */
    protected QueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode){
        _isSuccessful = isSuccessful;
        _errorMessage = errorMessage;
        _statusCode = queryStatusCode;
    }

    public boolean isSuccessful(){
        return _isSuccessful;
    }

    public String getErrorMessage(){
        return _errorMessage;
    }

    public QueryStatusCode getStatusCode(){
        return _statusCode;
    }

    public abstract T getData();
}
