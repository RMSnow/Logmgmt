package mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.MongoResult;
import entity.RequestsRate;
import mongodb.MongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import orm.Record;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by snow on 05/12/2017.
 */
public class RecordDao {
//    _id                 [自动生成]
//    serviceName         [String]
//    timestamp           [是否可以自动生成？根据计划任务]
//
//    apiRequestTable     [HashTable<String, Integer>]
//    loggingErrors       [Integer]
//    requestExceptions   [Integer]
//    hourRequests        [Integer]
//    secondRequestsRate  [ObjectArray]

    public static final String KEY_ID = "_id";
    public static final String KEY_SERVICE_NAME = "service_name";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_API_REQUEST_TABLE = "api_request_table";
    public static final String KEY_LOGGING_ERRORS = "logging_errors";
    public static final String KEY_REQUEST_EXCEPTIONS = "request_exception";
    public static final String KEY_HOUR_REQUESTS = "hour_requests";
    public static final String KEY_SECOND_REQUESTS_RATE = "second_requests_rate";

    private static final String COLLECTION_NAME = "record";
    private MongoCollection<Document> collection;

    public RecordDao() {
        collection = MongoConnector.getCollection(MongoConnector.DB_NAME, COLLECTION_NAME);
    }

    public void add(Record record) {
        Document d = new Document();
        if (record.getServiceName() != null) {
            d.append(KEY_SERVICE_NAME, record.getServiceName());
        }
        if (record.getTimestamp() != null) {
            d.append(KEY_TIMESTAMP, record.getTimestamp());
        }
        if (record.getApiRequestTable() != null) {
            Hashtable<String, Integer> table = record.getApiRequestTable();
            Document hashDoc = new Document();
            Iterator it = table.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                hashDoc.append(key, table.get(key));
            }

            if (hashDoc.size() != 0){
                d.append(KEY_API_REQUEST_TABLE, hashDoc);
            }
        }
        if (record.getLoggingErrors() != null) {
            d.append(KEY_LOGGING_ERRORS, record.getLoggingErrors());
        }
        if (record.getRequestExceptions() != null) {
            d.append(KEY_REQUEST_EXCEPTIONS, record.getRequestExceptions());
        }
        if (record.getHourRequests() != null) {
            d.append(KEY_HOUR_REQUESTS, record.getHourRequests());
        }
        if (record.getSecondRequestsRate() != null) {
            RequestsRate[] rates = record.getSecondRequestsRate();
            Document arrayDoc = new Document();
            for (int i = 0; i < 12; i++) {
                if (rates[i] != null){
                    arrayDoc.append(rates[i].getTimescale(), rates[i].getRate());
                }
            }

            if (arrayDoc.size() != 0){
                d.append(KEY_SECOND_REQUESTS_RATE, arrayDoc);
            }
        }

        collection.insertOne(d);
    }

    public MongoResult queryAll() {
        FindIterable<Document> it = collection.find();
        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return new MongoResult(records);
    }

    public MongoResult queryByParam(String serviceName) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }

        FindIterable<Document> it = collection.find(Filters.and(conditions));

        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return new MongoResult(records);
    }
}
