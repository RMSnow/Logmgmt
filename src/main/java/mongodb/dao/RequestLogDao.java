package mongodb.dao;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.MongoQueryResult;
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

    private MongoCollection<Document> collection;

    public RequestLogDao() {
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
                                         String host,
                                         String fromTimestamp,
                                         String toTimestamp,
                                         String method,
                                         String status,
                                         String limit) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        if (host != null) {
            conditions.add(Filters.eq(KEY_HOST, host));
        }
        if (fromTimestamp != null) {
            conditions.add(Filters.gte(KEY_DATETIME, fromTimestamp));
        }
        if (toTimestamp != null) {
            conditions.add(Filters.lte(KEY_DATETIME, toTimestamp));
        }
        if (method != null) {
            conditions.add(Filters.eq(KEY_METHOD, method));
        }
        if (status != null) {
            conditions.add(Filters.eq(KEY_STATUS, status));
        }
        FindIterable<Document> it = collection.find(Filters.and(conditions));
        if (limit != null) {
            it.limit(Integer.valueOf(limit));
        }
        return JsonUtil.parseFindIterableToQueryResult(it);
    }


    public void add(RequestLog requestLog) {
        Document d = new Document();
        if (requestLog.getFacility() != null) {
            d.append(KEY_FACILITY, requestLog.getFacility());
        }
        if (requestLog.getHost() != null) {
            d.append(KEY_HOST, requestLog.getHost());
        }
        if (requestLog.getClientIP() != null) {
            d.append(KEY_CLIENT_IP, requestLog.getClientIP());
        }
        if (requestLog.getClassName() != null) {
            d.append(KEY_CLASS_NAME, requestLog.getClassName());
        }
        if (requestLog.getServiceName() != null) {
            d.append(KEY_SERVICE_NAME, requestLog.getServiceName());
        }
        if (requestLog.getDatetime() != null) {
            d.append(KEY_DATETIME,DateUtil.parseReqLogDateTime(requestLog.getDatetime()));
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