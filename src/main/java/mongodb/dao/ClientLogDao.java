package mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.MongoResult;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import orm.ClientLog;

import java.util.ArrayList;
import java.util.List;


/**
 * 应用层（客户端）日志
 */
public class ClientLogDao {
//    id
//    timestamp
//
//    ip              String  [访问客户端ip]
//    userId          String  [操作用户id]
//    responseTime    long    [响应时间]
//    api             String  [调用api及其参数]
//    status          String  [返回状态码]
//    error           String  [错误]
//    msg             String  [消息]

    public static final String KEY_ID = "_id";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_IP = "ip";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_RESPONSE_TIME = "responseTime";
    public static final String KEY_API = "api";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ERROR = "error";
    public static final String KEY_MSG = "msg";

    private static final String COLLECTION_NAME = "client";
    private MongoCollection<Document> collection;

    public ClientLogDao() {
        collection = MongoConnector.getCollection(MongoConnector.DB_NAME, COLLECTION_NAME);
    }

    /**
     * 添加一条记录
     *
     * @param clientLog
     */
    public MongoResult add(ClientLog clientLog) {
        try {
            Document d = new Document();
            if (clientLog.getTimestamp() != null) {
                d.append(KEY_TIMESTAMP, clientLog.getTimestamp());
            }
            if (clientLog.getIp() != null) {
                d.append(KEY_IP, clientLog.getIp());
            }
            if (clientLog.getUserId() != null) {
                d.append(KEY_USER_ID, clientLog.getUserId());
            }
            if (clientLog.getResponseTime() != null) {
                d.append(KEY_RESPONSE_TIME, clientLog.getResponseTime());
            }
            if (clientLog.getApi() != null) {
                d.append(KEY_API, clientLog.getApi());
            }
            if (clientLog.getStatus() != null) {
                d.append(KEY_STATUS, clientLog.getStatus());
            }
            if (clientLog.getError() != null) {
                d.append(KEY_ERROR, clientLog.getError());
            }
            if (clientLog.getMsg() != null) {
                d.append(KEY_MSG, clientLog.getMsg());
            }

            collection.insertOne(d);

//            ArrayList<ClientLog> logs = new ArrayList<>();
//            logs.add(clientLog);
//            return new MongoResult(logs);

            return queryByParam(clientLog.getTimestamp(), clientLog.getTimestamp(),
                    null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public MongoResult queryAll() {
        try {
            FindIterable<Document> it = collection.find();
            ArrayList<ClientLog> logs = new ArrayList<>();
            for (Document d : it) {
                logs.add(new ClientLog(d));
            }
            return new MongoResult(logs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    参数列表
//    # fromTimestamp
//    # toTimestamp
//
//    # ip
//    # userId
//    # api
//    # status
//
//    # limit

    /**
     * 参数查询
     *
     * @param fromTimestamp
     * @param toTimestamp
     * @param ip
     * @param userId
     * @param api
     * @param status
     * @param limit
     * @return
     */
    public MongoResult queryByParam(String fromTimestamp,
                                    String toTimestamp,
                                    String ip,
                                    String userId,
                                    String api,
                                    String status,
                                    String limit) {
        try {
            List<Bson> conditions = new ArrayList<Bson>();
            if (fromTimestamp != null) {
                conditions.add(Filters.gte(KEY_TIMESTAMP, fromTimestamp));
            }
            if (toTimestamp != null) {
                conditions.add(Filters.lte(KEY_TIMESTAMP, toTimestamp));
            }else {
                conditions.add(Filters.lte(KEY_TIMESTAMP, DateUtil.getDateNow()));
            }
            if (ip != null) {
                conditions.add(Filters.eq(KEY_IP, ip));
            }
            if (userId != null) {
                conditions.add(Filters.eq(KEY_USER_ID, userId));
            }
            if (api != null) {
                conditions.add(Filters.eq(KEY_API, api));
            }
            if (status != null) {
                conditions.add(Filters.eq(KEY_STATUS, status));
            }

            FindIterable<Document> it = null;
            if (limit != null) {
                it = collection.find(Filters.and(conditions)).limit(Integer.valueOf(limit));
            } else {
                it = collection.find(Filters.and(conditions));
            }

            ArrayList<ClientLog> logs = new ArrayList<>();
            for (Document d : it) {
                logs.add(new ClientLog(d));
            }
            return new MongoResult(logs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
