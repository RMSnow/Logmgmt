package mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import mongodb.MongoResult;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import orm.LoggingLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class LoggingLogDao {
    public static final String KEY_ID = "_id";
    public static final String KEY_FACILITY = "facility";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_HOST = "host";
    public static final String KEY_SERVICE_NAME = "service_name";
    public static final String KEY_CLASS_NAME = "class_name";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_ERR_DETAILS = "err_details";
    private static final String COLLECTION_NAME = "logging";
    MongoCollection<Document> collection;

    private static HashSet<Integer> LEVEL_SET = new HashSet<>();
    private static HashSet<Integer> QUERY_LEVEL_SET = new HashSet<>();

    public LoggingLogDao() {
        collection = MongoConnector.getCollection(MongoConnector.DB_NAME, COLLECTION_NAME);

        for (int i = 0; i < 8; i++) {
            LEVEL_SET.add(i);
        }
    }

    public MongoResult queryAll() {
        FindIterable<Document> it = collection.find();
        ArrayList<LoggingLog> logs = new ArrayList<>();
        for (Document d : it) {
            logs.add(new LoggingLog(d));
        }
        return new MongoResult(logs);
//        return JsonUtil.parseFindIterableToQueryResult(it);
    }

    /*
    *query datetime format:
    *   yyyy-MM-dd hh:mm:ss
    *example:
    *   2017-11-24 23:11:40
    *another format:
    *   2017-11-24
    *equals:
    *   2017-11-24 00:00:00
     */
    public MongoResult queryByParam(String serviceName,
                                    String fromId,
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
        if (fromId != null) {
            Document doc = queryById(fromId);
            if (doc != null) {
                conditions.add(Filters.gt(KEY_TIMESTAMP, doc.getString(KEY_TIMESTAMP)));
                fromDatetime = null;
                //fromDatetime = doc.getString(KEY_TIMESTAMP);
            }
        }
        if (level != null) {

//            0       Emergency: system is unusable
//            1       Alert: action must be taken immediately
//            2       Critical: critical conditions
//            3       Error: error conditions
//            4       Warning: warning conditions
//            5       Notice: normal but significant condition
//            6       Informational: informational messages
//            7       Debug: debug-level messages

//            Emergency | Alert | Critical | Error | Warning | Notice | Informational | Debug
//            128       | 64    | 32       | 16    | 8       | 4      | 2             | 1

            int cond = Integer.valueOf(level);
            if ((cond & 1) == 1) {
                QUERY_LEVEL_SET.add(7);
            }
            if ((cond & 2) == 2) {
                QUERY_LEVEL_SET.add(6);
            }
            if ((cond & 4) == 4) {
                QUERY_LEVEL_SET.add(5);
            }
            if ((cond & 8) == 8) {
                QUERY_LEVEL_SET.add(4);
            }
            if ((cond & 16) == 16) {
                QUERY_LEVEL_SET.add(3);
            }
            if ((cond & 32) == 32) {
                QUERY_LEVEL_SET.add(2);
            }
            if ((cond & 64) == 64) {
                QUERY_LEVEL_SET.add(1);
            }
            if ((cond & 128) == 128) {
                QUERY_LEVEL_SET.add(0);
            }

            Iterator iterator = LEVEL_SET.iterator();
            while (iterator.hasNext()) {
                int element = (Integer) iterator.next();
                if (!QUERY_LEVEL_SET.contains(element)) {
                    conditions.add(Filters.ne(KEY_LEVEL, element));
                }
            }

            QUERY_LEVEL_SET = new HashSet<>();
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
        FindIterable<Document> it = null;
        if (limit != null) {
            it = collection.find(Filters.and(conditions))
                    .sort(Sorts.descending(KEY_TIMESTAMP))
                    .limit(Integer.valueOf(limit));
        } else {
            it = collection.find(Filters.and(conditions))
                    .sort(Sorts.descending(KEY_TIMESTAMP)).limit(20);
        }
        if (queryDetails != null) {
            if (queryDetails.equals("0")) {
                it = it.projection(
                        new BasicDBObject(KEY_ERR_DETAILS, 0)
                );
            }
        } else {
            it = it.projection(
                    new BasicDBObject(KEY_ERR_DETAILS, 0)
            );
        }
        ArrayList<LoggingLog> logs = new ArrayList<>();
        for (Document d : it) {
            logs.add(new LoggingLog(d));
        }
        return new MongoResult(logs);
//        return JsonUtil.parseFindIterableToQueryResult(it);
    }

    public Document queryById(String id) {
        if (id != null) {
            List<Bson> conditions = new ArrayList<Bson>();
            conditions.add(Filters.eq(KEY_ID, new ObjectId(id)));

            FindIterable<Document> it = collection.find(Filters.and(conditions));

            for (Document d : it) {
                return d;
            }
        }
        return null;
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

    public MongoResult deleteByID(String id) {
        DeleteResult result = collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
        return new MongoResult(result.getDeletedCount(), result.wasAcknowledged());
    }

    public MongoResult deleteByParam(String serviceName,
                                     String level,
                                     String host,
                                     String fromDateTime,
                                     String toDateTime) {

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
        if (fromDateTime != null) {
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDateTime));
        }
        if (toDateTime != null) {
            conditions.add(Filters.lte(KEY_TIMESTAMP, toDateTime));
        }

        DeleteResult result = collection.deleteMany(Filters.and(conditions));
        return new MongoResult(result.getDeletedCount(), result.wasAcknowledged());
    }

}