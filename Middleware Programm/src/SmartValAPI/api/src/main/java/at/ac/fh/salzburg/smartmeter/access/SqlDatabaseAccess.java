package at.ac.fh.salzburg.smartmeter.access;

import at.ac.fh.salzburg.smartmeter.Constants;
import at.ac.fh.salzburg.smartmeter.data.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.data.StringListQueryResult;

import java.sql.*;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class SqlDatabaseAccess implements IDatabaseAccess {
    @Override
    public QueryResult QueryDatabase(QueryBase query) {
        Connection connection = null;
        StringListQueryResult result = new StringListQueryResult();

        try{
            connection = DriverManager.getConnection(
                    Constants.METER_DATA_CONNECTION_STRING,
                    "root",null);
            Statement statement = connection.createStatement();
            if(statement.execute(query.getQuery())){
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    result.getData().add(resultSet.getString("lastname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection != null)
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
