package mongodb.dao;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import mongodb.JsonUtil;
import org.bson.types.ObjectId;
import orm.RequestLog;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/7.
 */
public class RequestLogDao {

    private static final String DB_NAME = "logs";

    private static final String COLLECTION_NAME = "access_log";

    private static final String KEY_ID = "_id";
    private static final String KEY_HOST = "host";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATETIME = "datetime";
    private static final String KEY_METHOD = "method";
    private static final String KEY_URL = "url";
    private static final String KEY_STATUS = "status";
    private static final String KEY_CLIENT = "client";

    MongoCollection<Document> collection;

    public RequestLogDao() {
        collection = MongoConnector.getCollection(DB_NAME, COLLECTION_NAME);
    }

    public String queryAll() {
        FindIterable<Document> it = collection.find();
        return JsonUtil.parseFindIterableToJsonArray(it);
    }


    public String queryByParam(String name, String host, String method, String facility, String fromTimestamp, String toTimestamp) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (name != null) {
            conditions.add(Filters.eq(KEY_NAME, name));
        }
        if (host != null) {
            conditions.add(Filters.eq(KEY_HOST, host));
        }
        if (method != null) {
            conditions.add(Filters.eq(KEY_METHOD, method));
        }
        if (fromTimestamp != null) {
            fromTimestamp = DateUtil.parseDate(fromTimestamp);
            conditions.add(Filters.gte(KEY_DATETIME, fromTimestamp));
        }
        if (toTimestamp != null) {
            toTimestamp = DateUtil.parseDate(toTimestamp);
            conditions.add(Filters.lte(KEY_DATETIME, toTimestamp));
        }
        if (facility != null) {
            conditions.add(Filters.eq(KEY_CLIENT, facility));
        }
        FindIterable<Document> it = collection.find(Filters.and(conditions));
        return JsonUtil.parseFindIterableToJsonArray(it);
    }


    public void add(RequestLog requestLog) {
        Document d = new Document();
        if (requestLog.getClient() != null) {
            d.append(KEY_CLIENT, requestLog.getClient());
        }
        if (requestLog.getDatetime() != null) {
            d.append(KEY_DATETIME, DateUtil.parseDate(requestLog.getDatetime()));
        }
        if (requestLog.getHost() != null) {
            d.append(KEY_HOST, requestLog.getHost());
        }
        if (requestLog.getServiceName() != null) {
            d.append(KEY_NAME, requestLog.getServiceName());
        }
        if (requestLog.getMethod() != null) {
            d.append(KEY_METHOD, requestLog.getMethod());
        }
        if (requestLog.getUrl() != null) {
            d.append(KEY_URL, requestLog.getUrl());
        }
        if (requestLog.getStatus() != 0) {
            d.append(KEY_STATUS, requestLog.getStatus());
        }
        collection.insertOne(d);
    }


    public void delete(String id) {
        collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
    }
}