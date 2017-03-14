package at.ac.fh.salzburg.old;

import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;

/**
 * Class that encapsulates paginated data
 * @param <T> The datatype that is queried
 */
public final class PaginatedData<T> extends QueryResult {

    private T _data;
    private int _pageNum;
    private int _numOfEntries;

    /**
     * The constructor for no error
     * @param data
     * @param numOfEntries
     * @param pageNum
     */
    private PaginatedData(T data, int numOfEntries, int pageNum){
        super(); // we use the everything OK constructor of BaseClass
        _data = data;
        _numOfEntries = numOfEntries;
        _pageNum = pageNum;
    }

    private PaginatedData(String errorMessage, QueryStatusCode queryStatusCode){
        super(false, errorMessage, queryStatusCode);
        _data = null;
        _pageNum = -1;
        _numOfEntries = -1;
    }

    public static <T> PaginatedData CreateResult(T data, int pageNum, int numOfEntries){
        return new PaginatedData(data,numOfEntries,pageNum);
    }

    public static PaginatedData CreateErrorResult(String errorMessage, QueryStatusCode queryStatusCode){
        return new PaginatedData(errorMessage, queryStatusCode);
    }


    public int getNumberOfEntries(){
        return _numOfEntries;
    }
    public int getPageNum(){
        return _pageNum;
    }

    @Override
    public T GetData() {
        return _data;
    }
}
