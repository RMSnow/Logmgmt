package mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.MongoResult;
import mongodb.MongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import orm.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snow on 05/12/2017.
 */
public class RecordDao {
    //TODO: Dao

    public static final String KEY_ID = "_id";
    public static final String KEY_SERVICE_NAME = "service_name";
    public static final String KEY_MOST_URI = "most_api";
    public static final String KEY_MOST_REQUESTS = "most_api_sum";
    public static final String KEY_LOGGING_ERRORS = "logging_errors";
    public static final String KEY_REQUEST_EXCEPTIONS = "request_exception";
    public static final String KEY_HOUR_REQUESTS = "hour_requests";
    public static final String KEY_SECOND_REQUESTS_RATE = "second_requests_rate";

    private static final String COLLECTION_NAME = "record";
    private MongoCollection<Document> collection;

    public RecordDao() {
        //?????????????如果没有怎么办?????????????
        collection = MongoConnector.getCollection(MongoConnector.DB_NAME, COLLECTION_NAME);
    }

    public void add(Record record) {
        Document d = new Document();
        if (record.getServiceName() != null) {
            d.append(KEY_SERVICE_NAME, record.getServiceName());
        }
        if (record.getMostURI() != null) {
            d.append(KEY_MOST_URI, record.getMostURI());
        }
        if (record.getMostRequests() != null) {
            d.append(KEY_MOST_REQUESTS, record.getMostRequests());
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
            d.append(KEY_SECOND_REQUESTS_RATE, record.getSecondRequestsRate());
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
