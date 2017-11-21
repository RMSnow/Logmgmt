package mongodb.dao;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import mongodb.JsonUtil;
import org.bson.types.ObjectId;
import orm.AccessLog;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/7.
 */
public class AccessLogDao {

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

    public AccessLogDao() {
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


    public void add(AccessLog accessLog) {
        Document d = new Document();
        if (accessLog.getClient() != null) {
            d.append(KEY_CLIENT, accessLog.getClient());
        }
        if (accessLog.getDatetime() != null) {
            d.append(KEY_DATETIME, DateUtil.parseDate(accessLog.getDatetime()));
        }
        if (accessLog.getHost() != null) {
            d.append(KEY_HOST, accessLog.getHost());
        }
        if (accessLog.getName() != null) {
            d.append(KEY_NAME, accessLog.getName());
        }
        if (accessLog.getMethod() != null) {
            d.append(KEY_METHOD, accessLog.getMethod());
        }
        if (accessLog.getStatus() != 0) {
            d.append(KEY_STATUS, accessLog.getStatus());
        }
        if (accessLog.getUrl() != null) {
            d.append(KEY_URL, accessLog.getUrl());
        }
        collection.insertOne(d);
    }


    public void delete(String id) {
        collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
    }
}