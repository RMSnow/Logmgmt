package mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.MongoQueryResult;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import mongodb.JsonUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import orm.LoggingLog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by WYJ on 2017/11/8.
 */
public class LoggingLogDao {
    private static final String DB_NAME = "logs";

    private static final String COLLECTION_NAME = "logging";


    private static final String KEY_ID = "_id";
    private static final String KEY_FACILITY = "facility";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_HOST = "host";
    private static final String KEY_SERVICE_NAME = "service_name";
    private static final String KEY_CLASS_NAME = "class_name";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ERR_DETAILS = "err_details";

    MongoCollection<Document> collection;

    public LoggingLogDao() {
        collection = MongoConnector.getCollection(DB_NAME, COLLECTION_NAME);
    }

    public MongoQueryResult queryAll() {
        FindIterable<Document> it = collection.find();
        return JsonUtil.parseFindIterableToQueryResult(it);
    }

    /*
    *query datetime format:
    *   dd-MM-yyyy hh:mm:ss
    *example:
    *   24-11-2017 23:11:40
    *another format:
    *   24-11-2017
    *equals:
    *   24-11-2017 00:00:00
     */
    public MongoQueryResult queryByParam(String serviceName,
                                         String level,
                                         String host,
                                         String fromDatetime,
                                         String toDatetime,
                                         String queryDetails,
                                         String limit) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        if (level != null) {
            conditions.add(Filters.eq(KEY_LEVEL, Integer.valueOf(level)));
        }
        if (host != null) {
            conditions.add(Filters.eq(KEY_HOST, host));
        }
        if (fromDatetime != null) {
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDatetime));
        }
        if (toDatetime != null) {
            conditions.add(Filters.lte(KEY_TIMESTAMP, toDatetime));
        }
        FindIterable<Document> it = collection.find(Filters.and(conditions));
        if (queryDetails.equals("0")) {
            it = it.projection(
                    new BasicDBObject(KEY_ERR_DETAILS, 0)
            );
        }
        if (limit != null) {
            it.limit(Integer.valueOf(limit));
        }

        return JsonUtil.parseFindIterableToQueryResult(it);
    }

    public void add(LoggingLog loggingLog) {
        Document d = new Document();
        if (loggingLog.getFacility() != null) {
            d.append(KEY_FACILITY, loggingLog.getFacility());
        }
        if (loggingLog.getLevel() != null) {
            d.append(KEY_LEVEL, loggingLog.getLevel());
        }
        if (loggingLog.getTimestamp() != null) {
            d.append(KEY_TIMESTAMP, DateUtil.getDateNow());
        }
        if (loggingLog.getHost() != null) {
            d.append(KEY_HOST, loggingLog.getHost());
        }
        if (loggingLog.getServiceName() != null) {
            d.append(KEY_SERVICE_NAME, loggingLog.getServiceName());
        }
        if (loggingLog.getMessage() != null) {
            d.append(KEY_MESSAGE, loggingLog.getMessage());
        }
        if (loggingLog.getClassName() != null) {
            d.append(KEY_CLASS_NAME, loggingLog.getClassName());
        }
        if (loggingLog.getErrDetails() != null) {
            d.append(KEY_ERR_DETAILS, loggingLog.getErrDetails());
        }
        collection.insertOne(d);
    }

    public void deleteByID(String id) {
        collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
    }

    public void deleteByParam(String serviceName,
                              String level,
                              String fromDateTime,
                              String toDateTime) {

        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        if (level != null) {
            conditions.add(Filters.eq(KEY_LEVEL, level));
        }
        if (fromDateTime != null) {
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDateTime));
        }
        if (toDateTime != null) {
            conditions.add(Filters.lte(KEY_TIMESTAMP, toDateTime));
        }
        collection.deleteMany(Filters.and(conditions));
    }

}