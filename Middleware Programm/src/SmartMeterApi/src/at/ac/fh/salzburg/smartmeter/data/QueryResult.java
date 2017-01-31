package at.ac.fh.salzburg.smartmeter.data;

public abstract class QueryResult<T> {

    protected boolean _isSuccessful;
    protected String _errorMessage;
    protected StatusCode _statusCode;


    /**
     * Constructor used for Everything ok
     */
    protected QueryResult(){
        _isSuccessful = true;
        _errorMessage = null;
        _statusCode = StatusCode.OK;
    }

    /**
     * Constructor to specify error details
     * @param isSuccessful
     * @param errorMessage
     * @param statusCode
     */
    protected QueryResult(boolean isSuccessful, String errorMessage, StatusCode statusCode){
        _isSuccessful = isSuccessful;
        _errorMessage = errorMessage;
        _statusCode = statusCode;
    }

    public boolean isSuccessful(){
        return _isSuccessful;
    }

    public String getErrorMessage(){
        return _errorMessage;
    }

    public StatusCode getStatusCode(){
        return _statusCode;
    }

    public abstract T GetData();
}
