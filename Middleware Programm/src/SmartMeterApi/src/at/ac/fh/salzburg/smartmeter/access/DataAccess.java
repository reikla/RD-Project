package at.ac.fh.salzburg.smartmeter.access;

import at.ac.fh.salzburg.smartmeter.data.QueryResult;

/**
 * The central class to query the Database
 */
public class DataAccess {

    private final ILdapPermissionManager _permissionManager;
    private final IDatabaseAccess _databaseAccess;


    public DataAccess(ILdapPermissionManager permissionManager, IDatabaseAccess databaseAccess){
        _permissionManager = permissionManager;
        _databaseAccess = databaseAccess;
    }

    /**
     * Query the Database
     * @param query the query to execute
     * @return a QueryResult with either Data or a ErrorMessage.
     */
    public QueryResult QueryDataSource(QueryBase query){
        if(!_permissionManager.IsAllowedToAccess(
                query.getUserContext(),
                query.getDataSourceContext())){
            return  createNotAllowedResult();
        }
        return _databaseAccess.QueryDatabase(query);
    }

    private QueryResult createNotAllowedResult(){
        return new QueryResult(false,
                "Not allowed to access datasource",
                QueryStatusCode.Error) {
            @Override
            public Object GetData() {
                return null;
            }
        };
    }
}