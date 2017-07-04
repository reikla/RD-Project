package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;
import at.ac.fh.salzburg.smartmeter.data.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.entities.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reimarklammer on 30.05.17.
 */
@Controller
public class MyFancyQueryController extends CustomQueryControllerBase {


    @RequestMapping("/query/myfancycustomerquery")
    @ResponseBody
    public QueryResult<?> GetCustomers(){
        return databaseAccess.QueryDatabase(new MyFancyCustomerQuery());
    }


    private class MyFancyCustomerQuery extends QueryBase<List<CustomerEntity>>{

        public MyFancyCustomerQuery() {
        }

        @Override
        public IDataSourceContext[] getDataSourceContexts() {
            return null;
        }

        @Override
        public String getQuery() {
            return "select * from customer";
        }

        @Override
        public QueryResult<List<CustomerEntity>> parseDatabaseResultSet(ResultSet resultSet) {
            MyFancyCustomerQueryResult result = new MyFancyCustomerQueryResult();

            try {
                while(resultSet.next()){
                    CustomerEntity c = new CustomerEntity();
                    c.setFirstname(resultSet.getString("firstname"));
                    c.setLastname(resultSet.getString("lastname"));
                    result.AddCustomer(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result;

        }
    }

    private class MyFancyCustomerQueryResult extends QueryResult<List<CustomerEntity>>{

        protected MyFancyCustomerQueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode) {
            super(isSuccessful, errorMessage, queryStatusCode);
        }

        protected MyFancyCustomerQueryResult() {
            super(true,null,QueryStatusCode.OK);
        }

        private List<CustomerEntity> data = new ArrayList<>();

        @Override
        public List<CustomerEntity> getData() {
            return data;
        }

        public void AddCustomer(CustomerEntity c){
            data.add(c);
        }
    }


}
