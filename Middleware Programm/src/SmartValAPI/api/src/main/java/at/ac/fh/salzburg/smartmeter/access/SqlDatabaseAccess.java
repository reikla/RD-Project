package at.ac.fh.salzburg.smartmeter.access;

import at.ac.fh.salzburg.smartmeter.Constants;
import at.ac.fh.salzburg.smartmeter.data.data.PermissionDeniedQueryResult;
import at.ac.fh.salzburg.smartmeter.data.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.data.VoidQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * Created by reimarklammer on 28.03.17.
 */

@Component
public class SqlDatabaseAccess implements IDatabaseAccess {

    @Autowired
    private IPermissionManager permissionManager;

    @Autowired
    IUserContextFactory userContextFactory;

    @Override
    public QueryResult<?> QueryDatabase(QueryBase query) {
        Connection connection = null;
        QueryResult<?> result = null;


        IUserContext usercontext = userContextFactory.getUserContext();

        if (query.getDataSourceContexts() != null) {
            for (IDataSourceContext dataSourcecontext : query.getDataSourceContexts()) {
                if (!permissionManager.IsAllowedToAccess(usercontext, dataSourcecontext)) {
                    return new PermissionDeniedQueryResult();
                }
            }
        }

        try {
            connection = DriverManager.getConnection(
                    Constants.METER_DATA_CONNECTION_STRING,
                    Constants.METER_DATA_CONNECTION_USERNAME,
                    Constants.METER_DATA_CONNECTION_PASSWORD);
            Statement statement = connection.createStatement();
            if (statement.execute(query.getQuery())) {
                ResultSet resultSet = statement.getResultSet();
                result = query.parseDatabaseResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = new VoidQueryResult(false, "The SQL query went wrong.", QueryStatusCode.SqlError);
        } finally {
            if (connection != null)
                try {
                    connection.close();
                    connection = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }
}
