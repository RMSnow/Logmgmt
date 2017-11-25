package mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import mongodb.JsonUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import orm.LoggingLog;

import java.util.ArrayList;
import java.util.List;

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
    private static final String KEY_NAME = "name";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ERR_DETAILS = "err_details";

    MongoCollection<Document> collection;

    public LoggingLogDao() {
        collection = MongoConnector.getCollection(DB_NAME, COLLECTION_NAME);
    }

    public String queryAll() {
        FindIterable<Document> it = collection.find();
        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public String queryByParam(Integer level, String name, String fromDatetime, String toDatetime) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (name != null) {
            conditions.add(Filters.eq(KEY_NAME,name));
        }
        if (level != null) {
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDatetime));
        }
        if (fromDatetime != null) {
            fromDatetime = DateUtil.parseReqLogDateTime(fromDatetime);
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDatetime));
        }
        if (toDatetime != null) {
            toDatetime = DateUtil.parseReqLogDateTime(toDatetime);
            conditions.add(Filters.lte(KEY_TIMESTAMP, toDatetime));
        }
        FindIterable<Document> it = collection.find(Filters.and(conditions));

        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public void add(LoggingLog loggingLog) {
        Document d = new Document();
        if (loggingLog.getFacility() != null) {
            d.append(KEY_FACILITY,loggingLog.getFacility());
        }
        if (loggingLog.getLevel() != null) {
            d.append(KEY_LEVEL, loggingLog.getLevel());
        }
        if (loggingLog.getTimestamp() != null) {
            d.append(KEY_TIMESTAMP, DateUtil.parseTimeStamp(loggingLog.getTimestamp()));
        }
        if (loggingLog.getHost() != null) {
            d.append(KEY_HOST,loggingLog.getHost());
        }
        if (loggingLog.getServiceName() != null) {
            d.append(KEY_SERVICE_NAME,loggingLog.getServiceName());
        }
//        if (loggingLog.getClassName() != null) {
//            d.append(KEY_NAME, loggingLog.getClassName());
//        }
        if (loggingLog.getMessage() != null) {
            d.append(KEY_MESSAGE, loggingLog.getMessage());
        }
        if (loggingLog.getClassName() != null) {
            d.append(KEY_CLASS_NAME, loggingLog.getClassName());
        }
        if (loggingLog.getErrDetails() != null) {
            d.append(KEY_ERR_DETAILS,loggingLog.getErrDetails());
        }
        collection.insertOne(d);
    }

    public void delete(String id) {
        collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
    }

}