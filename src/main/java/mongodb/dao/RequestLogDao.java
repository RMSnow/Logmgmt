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
//<134>Nov 17 14:28:39 DESKTOP-9EODV8A couseservice[12624]: [dw-17] http.request 0:0:0:0:0:0:0:1 - - [17/十一月/2017:06:28:39 +0000] "GET /application/kk HTTP/1.1" 404 43 "-" "PostmanRuntime/6.4.1" 231

    private static final String DB_NAME = "logs";

    private static final String COLLECTION_NAME = "request";

    private static final String KEY_ID = "_id";
    private static final String KEY_FACILITY = "facility";
    private static final String KEY_HOST = "host";
    private static final String KEY_SERVICE_NAME = "service_name";
    private static final String KEY_CLASS_NAME = "class_name";
    private static final String KEY_CLIENT_IP = "client_ip";
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
            conditions.add(Filters.eq(KEY_SERVICE_NAME, name));
        }
        if (host != null) {
            conditions.add(Filters.eq(KEY_HOST, host));
        }
        if (method != null) {
            conditions.add(Filters.eq(KEY_METHOD, method));
        }
        if (fromTimestamp != null) {
            fromTimestamp = DateUtil.parseReqLogDateTime(fromTimestamp);
            conditions.add(Filters.gte(KEY_DATETIME, fromTimestamp));
        }
        if (toTimestamp != null) {
            toTimestamp = DateUtil.parseReqLogDateTime(toTimestamp);
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
        if (requestLog.getDatetime() != null) {
            d.append(KEY_FACILITY, requestLog.getFacility());
        }
        if (requestLog.getHost() != null) {
            d.append(KEY_HOST, requestLog.getHost());
        }
        if (requestLog.getDatetime() != null) {
            d.append(KEY_CLIENT_IP, requestLog.getClientIP());
        }
        if (requestLog.getDatetime() != null) {
            d.append(KEY_CLASS_NAME, requestLog.getClassName());
        }
        if (requestLog.getServiceName() != null) {
            d.append(KEY_SERVICE_NAME, requestLog.getServiceName());
        }
        if (requestLog.getDatetime() != null) {
            d.append(KEY_DATETIME, requestLog.getDatetime());
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
        if (requestLog.getClient() != null) {
            d.append(KEY_CLIENT, requestLog.getClient());
        }
        collection.insertOne(d);
    }


    public void delete(String id) {
        collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
    }
}