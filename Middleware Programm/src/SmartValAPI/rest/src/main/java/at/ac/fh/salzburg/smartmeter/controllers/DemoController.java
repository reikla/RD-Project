package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.VoidQueryResult;
import at.ac.fh.salzburg.smartmeter.data.entities.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController extends CustomQueryControllerBase {

    @RequestMapping("/query/demoquery/{lastName}")
    public QueryResult<?> getCustomer(@PathVariable String lastName){
        return databaseAccess.QueryDatabase(new DemoQuery(lastName));
    }

    private class DemoQuery extends QueryBase<List<CustomerEntity>> {

        private String _lastName;

        public DemoQuery(String lastName) {
            super();
            _lastName = lastName;
        }

        @Override
        public IDataSourceContext[] getDataSourceContexts() {
            return null;
        }

        @Override
        public String getQuery() {
            return "select * from customer where lastname = '" + _lastName+"'";
        }

        @Override
        public QueryResult parseDatabaseResultSet(ResultSet resultSet) {
            List<CustomerEntity> returnList = new ArrayList<>();
            try{
                while(resultSet.next()){
                    CustomerEntity e = new CustomerEntity();
                    e.setLastname(resultSet.getString("lastname"));
                    e.setFirstname(resultSet.getString("firstname"));
                    e.setCustomerId(resultSet.getInt("customer_id"));
                    returnList.add(e);
                }
            }catch(SQLException e){
                e.printStackTrace();
                return new VoidQueryResult(false,e.getMessage(),QueryStatusCode.SqlError);
            }
            return new CustomerListQueryResult(returnList);
        }
    }

    private class CustomerListQueryResult extends QueryResult<List<CustomerEntity>>{

        private List<CustomerEntity> _data;

        public CustomerListQueryResult(List<CustomerEntity> data){
            super();
            _data = data;
        }

        public CustomerListQueryResult(boolean isSuccessful, String errorMessage,
                                       QueryStatusCode queryStatusCode, List<CustomerEntity> data){
            super(isSuccessful,errorMessage,queryStatusCode);
            _data = data;
        }

        @Override
        public List<CustomerEntity> getData() {
            return _data;
        }
    }
}
