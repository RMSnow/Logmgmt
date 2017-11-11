package mongodb.dao;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import mongodb.JsonUtil;
import org.bson.types.ObjectId;
import orm.Log;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/7.
 */
public class LogDao {

    private static final String DB_NAME = "logs";

    private static final String COLLECTION_NAME = "log";

    private static final String KEY_ID="_id";
    private static final String KEY_IP="ip";
    private static final String KEY_NAME="name";
    private static final String KEY_DATETIME="datetime";
    private static final String KEY_URL="url";
    private static final String KEY_STATUS="status";
    private static final String KEY_CLIENT="client";
    private static final String KEY_DATA="data";

    MongoCollection<Document> collection;

    public LogDao() {
        collection = MongoConnector.getCollection(DB_NAME, COLLECTION_NAME);
    }
    public String queryAll(){
        FindIterable<Document> it=collection.find();
        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public String queryByParam(String name,String ip,String fromDatetime,String toDatetime,String client){
        List<Bson> conditions=new ArrayList<Bson>();
        if (name != null) {
            conditions.add(Filters.eq(KEY_NAME,name));
        }
        if (ip != null) {
            conditions.add(Filters.eq(KEY_IP,ip));
        }
        if (fromDatetime != null) {
            fromDatetime=DateUtil.parseDate(fromDatetime);
            conditions.add(Filters.gte(KEY_DATETIME,fromDatetime));
        }
        if (toDatetime != null) {
            toDatetime=DateUtil.parseDate(toDatetime);
            conditions.add(Filters.lte(KEY_DATETIME,toDatetime));
        }
        if (client != null) {
            conditions.add(Filters.eq(KEY_CLIENT,client));
        }
        FindIterable<Document> it= collection.find(Filters.and(conditions));

        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public void add(Log log) {
        Document d = new Document();
        if (log.getClient() != null) {
            d.append(KEY_CLIENT, log.getClient());
        }
        if (log.getData() != null) {
            d.append(KEY_DATA, log.getData());
        }
        if (log.getDateTime() != null) {
            d.append(KEY_DATETIME, DateUtil.parseDate(log.getDateTime()));
        }
        if (log.getIp() != null) {
            d.append(KEY_IP, log.getIp());
        }
        if (log.getStatus() != 0) {
            d.append(KEY_STATUS, log.getStatus());
        }
        if (log.getUrl() != null) {
            d.append(KEY_URL, log.getUrl());
        }
        collection.insertOne(d);
    }


    public void delete(String id) {
        collection.deleteOne(Filters.eq( KEY_ID, new ObjectId(id)));
    }
}