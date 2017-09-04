package at.ac.fh.salzburg.smartmeter.data.mao;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.QueryBase;
import at.ac.fh.salzburg.smartmeter.access.QueryStatusCode;
import at.ac.fh.salzburg.smartmeter.data.QueryResult;
import at.ac.fh.salzburg.smartmeter.data.entities.MeterDataEntity;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyMongoMeterDataQuery extends QueryBase<List<MeterDataEntity>> {

    private MongoClient mongoClient_;
    private MongoDatabase mongoDatabase_;
    private MongoCollection meterDataCollection_;

    private String mongoserver = "localhost";  // Todo: auslagern!
    private int mongoportport = 27017;
    private String database = "SmartValAPI";
    private String meterCollection = "meter_data";

    private int pmeterId_ = 0;
    private long ptspvon_ = 0;
    private long ptspbis_ = 0;
    private int maxSamplefreq_ = 0;



    public MyMongoMeterDataQuery(String pmeterId, String ptspvon, String ptspbis, String maxSamplefreq) {

        // Parameters
        pmeterId_ = Integer.parseInt(pmeterId);
        ptspvon_ = extractMilisfromTsp(ptspvon);
        ptspbis_ = extractMilisfromTsp(ptspbis);
        maxSamplefreq_ = Integer.parseInt(maxSamplefreq);


        try {
            // connect Database
            mongoClient_ = new MongoClient(mongoserver, mongoportport);
            mongoDatabase_ = mongoClient_.getDatabase(database);
            meterDataCollection_ = mongoDatabase_.getCollection(meterCollection);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    public IDataSourceContext[] getDataSourceContexts() {
        return null;
    }

    @Override
    public String getQuery() {
        return "SELECT CURRENT_TIMESTAMP";
    }

    @Override
    public QueryResult<List<MeterDataEntity>> parseDatabaseResultSet(ResultSet resultSet) {
        MyCustomMeterDataQueryResult result = new MyCustomMeterDataQueryResult();

        FindIterable<Document> findIterable = meterDataCollection_.find(Filters.and(
                                                                            Filters.eq("meterId", pmeterId_),
                                                                            Filters.and(
                                                                                    Filters.gte("timestamp", ptspvon_),
                                                                                    Filters.lte("timestamp", ptspbis_))));
        if (findIterable != null) {
            for (Document current : findIterable) {
                MeterDataEntity c = new MeterDataEntity();

                c.setMeterId(current.getInteger("meterId"));
                c.setDataId(current.getInteger("dataId"));
                c.setTimestamp(new Timestamp(current.getLong("timestamp")));
                c.setCountTotal(current.getDouble("countTotal"));
                c.setCountRegister1(current.getDouble("countRegister1"));
                c.setCountRegister2(current.getDouble("countRegister2"));
                c.setCountRegister3(current.getDouble("countRegister3"));
                c.setCountRegister4(current.getDouble("countRegister4"));
                c.setPowerP1(current.getDouble("powerP1"));
                c.setPowerP2(current.getDouble("powerP2"));
                c.setPowerP3(current.getDouble("powerP3"));
                c.setWorkP1(current.getDouble("workP1"));
                c.setWorkP2(current.getDouble("workP2"));
                c.setWorkP3(current.getDouble("workP3"));
                c.setFrequency(current.getDouble("frequency"));
                c.setVoltage(current.getDouble("voltage"));

                result.AddMeterData(c);
            }
        }
        return result;
    }

    private class MyCustomMeterDataQueryResult extends QueryResult<List<MeterDataEntity>> {

        private List<MeterDataEntity> data = new ArrayList<>();

        protected MyCustomMeterDataQueryResult(boolean isSuccessful, String errorMessage, QueryStatusCode queryStatusCode) {
            super(isSuccessful, errorMessage, queryStatusCode);
        }

        protected MyCustomMeterDataQueryResult() {
            super(true, null, QueryStatusCode.OK);
        }

        @Override
        public List<MeterDataEntity> getData() {
            return data;
        }

        public void AddMeterData(MeterDataEntity c) {
            data.add(c);
        }
    }

    private long extractMilisfromTsp(String tspIn) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            if (tspIn.contains("'")) {
                tspIn.replace("'", "");
            }
            date = sdf.parse(tspIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

}
