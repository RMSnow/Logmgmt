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
import orm.ErrorLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/8.
 */
public class ErrorLogDao {
    private static final String DB_NAME="logs";

    private static final String COLLECTION_NAME="error";


    private static final String KEY_ID="_id";
    private static final String KEY_IP="ip";
    private static final String KEY_NAME="name";
    private static final String KEY_URL="url";
    private static final String KEY_DATETIME="datetime";
    private static final String KEY_DATA="data";

    MongoCollection<Document> collection;

    public ErrorLogDao(){
        collection= MongoConnector.getCollection(DB_NAME,COLLECTION_NAME);
    }

    public String queryAll(){
        FindIterable<Document> it=collection.find();
        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public String queryByParam(String ip,String name,String fromDatetime,String toDatetime){
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
        FindIterable<Document> it= collection.find(Filters.and(conditions));

        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public void add(ErrorLog errorLog){
        Document d = new Document();
        if (errorLog.getData() != null) {
            d.append(KEY_DATA, errorLog.getData());
        }
        if (errorLog.getDateTime() != null) {
            d.append(KEY_DATETIME, DateUtil.parseDate(errorLog.getDateTime()));
        }
        if (errorLog.getIp() != null) {
            d.append(KEY_IP, errorLog.getIp());
        }
        if (errorLog.getUrl() != null) {
            d.append(KEY_URL, errorLog.getUrl());
        }
        if (errorLog.getName() != null) {
            d.append(KEY_NAME,errorLog.getName());
        }
        collection.insertOne(d);
    }

    public void delete(String id){
        collection.deleteOne(Filters.eq( KEY_ID, new ObjectId(id)));
    }

}
