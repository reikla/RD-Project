package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.entities.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;

@Controller
public class DemoController extends CustomQueryControllerBase {

    @RequestMapping("/query/demoquery/{firstName}")
    public QueryResult<?> getCustomer(@PathVariable String firstName){
        return databaseAccess.QueryDatabase(new DemoQuery(firstName));
    }

    private class DemoQuery extends QueryBase<CustomerEntity> {

        private String _firstName;

        public DemoQuery(String firstName) {
            super();
            _firstName = firstName;
        }

        @Override
        public IDataSourceContext[] getDataSourceContexts() {
            return new IDataSourceContext[0];
        }

        @Override
        public String getQuery() {
            return null;
        }

        @Override
        public QueryResult parseDatabaseResultSet(ResultSet resultSet) {
            return null;
        }
    }
}
