import at.ac.fh.salzburg.smartmeter.access.*;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;

import java.util.List;

/**
 * Created by reimarklammer on 14.03.17.
 */
public class MyApplication {
    public static void main(String[] args) {
        System.out.println("Hallo Welt");


        IPermissionManager permManager = (userContext, dataSourceContext) -> true;

        IDatabaseAccess dba = new SqlDatabaseAccess();

        DataAccess access = new DataAccess(permManager,dba);
        QueryResult<List<String>> queryResult = access.QueryDataSource(new QueryBase(null, null) {
            @Override
            public String getQuery() {
                return "SELECT * FROM `customer`";
            }
        });

        for (String lastName: queryResult.getData()) {
            System.out.println("LastName: " + lastName);
        }

        queryResult.getData();

    }
}
