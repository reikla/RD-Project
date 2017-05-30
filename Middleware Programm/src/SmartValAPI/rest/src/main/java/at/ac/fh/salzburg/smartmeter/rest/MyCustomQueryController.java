package at.ac.fh.salzburg.smartmeter.rest;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.data.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.data.StringListQueryResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by reimarklammer on 23.05.17.
 */
@Controller
public class MyCustomQueryController extends CustomQueryControllerBase {

    @RequestMapping("/query/myCustomQuery")
    @ResponseBody
    public QueryResult<?> GetMyCustomData(){
        return databaseAccess.QueryDatabase(new MyCustomQuery(null,null));
    }


    private class MyCustomQuery extends QueryBase<List<String>> {

        public MyCustomQuery(IUserContext userContext, IDataSourceContext dataSourceContext) {
            super(userContext, dataSourceContext);
        }

        @Override
        public String getQuery() {
            return "select data_id from meter_data";
        }

        @Override
        public QueryResult<List<String>> parseDatabaseResultSet(ResultSet resultSet) {
            StringListQueryResult result = new StringListQueryResult();
            try {
                while (resultSet.next()) {
                    result.getData().add(resultSet.getString("data_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }
    }
}
