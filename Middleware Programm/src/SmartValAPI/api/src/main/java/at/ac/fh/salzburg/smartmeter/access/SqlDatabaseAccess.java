package at.ac.fh.salzburg.smartmeter.access;

import at.ac.fh.salzburg.smartmeter.data.PermissionDeniedQueryResult;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.VoidQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ResourceBundle;

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


        IUserContext userContext = userContextFactory.getUserContext();

        if (query.getDataSourceContexts() != null) {
            for (IDataSourceContext dataSourceContext : query.getDataSourceContexts()) {
                if (!permissionManager.IsAllowedToAccess(userContext, dataSourceContext)) {
                    return new PermissionDeniedQueryResult();
                }
            }
        }

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            connection = DriverManager.getConnection(
                    bundle.getString("spring.datasource.url"),
                    bundle.getString("spring.datasource.username"),
                    bundle.getString("spring.datasource.password"));
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
