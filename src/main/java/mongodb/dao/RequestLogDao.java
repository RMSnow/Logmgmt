package mongodb.dao;


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
import orm.RequestLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class RequestLogDao {
    public static final String KEY_ID = "_id";
    public static final String KEY_FACILITY = "facility";
    public static final String KEY_HOST = "host";
    public static final String KEY_SERVICE_NAME = "service_name";
    public static final String KEY_CLASS_NAME = "class_name";
    public static final String KEY_CLIENT_IP = "client_ip";
    public static final String KEY_DATETIME = "datetime";
    public static final String KEY_METHOD = "method";
    public static final String KEY_URL = "url";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CLIENT = "client";

    private static final String COLLECTION_NAME = "request";
    private MongoCollection<Document> collection;

    private static HashSet<String> METHOD_SET = new HashSet<>();
    private static HashSet<String> QUERY_METHOD_SET = new HashSet<>();

    public RequestLogDao() {
        collection = MongoConnector.getCollection(MongoConnector.DB_NAME, COLLECTION_NAME);

        METHOD_SET.add("DELETE");
        METHOD_SET.add("PATCH");
        METHOD_SET.add("PUT");
        METHOD_SET.add("POST");
        METHOD_SET.add("GET");
    }

    public MongoResult queryAll() {
        FindIterable<Document> it = collection.find();
        ArrayList<RequestLog> logs = new ArrayList<>();
        for (Document d : it) {
            logs.add(new RequestLog(d));
        }
        return new MongoResult(logs);
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
        if (fromId != null) {
            Document doc = queryById(fromId);
            if (doc != null) {
                fromTimestamp = null;
                conditions.add(Filters.gt(KEY_DATETIME, doc.getString(KEY_DATETIME)));
                //fromTimestamp = doc.getString(KEY_DATETIME);
            }
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
//            DELETE | PATCH | PUT | POST | GET
//            16     | 8     | 4   | 2    | 1

            char index = method.charAt(0);
            if (Character.isDigit(index)) {
                int cond = Integer.valueOf(method);
                if ((cond & 1) == 1) {
                    QUERY_METHOD_SET.add("GET");
                }
                if ((cond & 2) == 2) {
                    QUERY_METHOD_SET.add("POST");
                }
                if ((cond & 4) == 4) {
                    QUERY_METHOD_SET.add("PUT");
                }
                if ((cond & 8) == 8) {
                    QUERY_METHOD_SET.add("PATCH");
                }
                if ((cond & 16) == 16) {
                    QUERY_METHOD_SET.add("DELETE");
                }

                Iterator iterator = METHOD_SET.iterator();
                while (iterator.hasNext()) {
                    String element = (String) iterator.next();
                    if (!QUERY_METHOD_SET.contains(element)) {
                        conditions.add(Filters.ne(KEY_METHOD, element));
                    }
                }

                QUERY_METHOD_SET = new HashSet<>();

            } else {     //单值查询
                conditions.add(Filters.eq(KEY_METHOD, method));
            }
        }
        if (status != null) {
            conditions.add(Filters.eq(KEY_STATUS, Integer.valueOf(status)));
        }
        FindIterable<Document> it = null;
        if (limit != null) {
            //TODO: 自动查找最近的limit条记录
            it = collection.find(Filters.and(conditions))
                    .sort(Sorts.descending(KEY_DATETIME))
                    .limit(Integer.valueOf(limit));
        } else {
            //TODO: 不设置limit参数时，自动查找最近的20条记录
            it = collection.find(Filters.and(conditions))
                    .sort(Sorts.descending(KEY_DATETIME))
                    .limit(20);
        }
        ArrayList<RequestLog> logs = new ArrayList<>();
        for (Document d : it) {
            logs.add(new RequestLog(d));
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
            d.append(KEY_DATETIME, DateUtil.parseReqLogDateTime(requestLog.getDatetime()));
        }
        if (requestLog.getMethod() != null) {
            d.append(KEY_METHOD, requestLog.getMethod());
        }
        if (requestLog.getUrl() != null) {
            d.append(KEY_URL, requestLog.getUrl());
        }
        if (requestLog.getStatus() != null) {
            d.append(KEY_STATUS, requestLog.getStatus());
        }
        if (requestLog.getClient() != null) {
            d.append(KEY_CLIENT, requestLog.getClient());
        }
        collection.insertOne(d);
    }


    public MongoResult deleteByID(String id) {
        DeleteResult result = collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
        return new MongoResult(result.getDeletedCount(), result.wasAcknowledged());
    }

    public MongoResult deleteByParam(String serviceName,
                                     String host,
                                     String fromDateTime,
                                     String toDateTime,
                                     String method,
                                     String status,
                                     String facility) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        if (host != null) {
            conditions.add(Filters.eq(KEY_HOST, host));
        }
        if (fromDateTime != null) {
            conditions.add(Filters.gte(KEY_DATETIME, fromDateTime));
        }
        if (toDateTime != null) {
            conditions.add(Filters.lte(KEY_DATETIME, toDateTime));
        }
        if (method != null) {
            conditions.add(Filters.eq(KEY_METHOD, method));
        }
        if (status != null) {
            conditions.add(Filters.eq(KEY_STATUS, Integer.valueOf(status)));
        }
        if (facility != null) {
            conditions.add(Filters.eq(KEY_FACILITY, facility));
        }
        DeleteResult result = collection.deleteMany(Filters.and(conditions));
        return new MongoResult(result.getDeletedCount(), result.wasAcknowledged());
    }
}