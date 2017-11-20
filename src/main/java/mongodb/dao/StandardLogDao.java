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
import orm.StandardLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/8.
 */
public class StandardLogDao {
    private static final String DB_NAME = "logs";

    private static final String COLLECTION_NAME = "standard_log";


    private static final String KEY_ID = "_id";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_CLASS_NAME = "class_name";
    private static final String KEY_DATA = "data";

    MongoCollection<Document> collection;

    public StandardLogDao() {
        collection = MongoConnector.getCollection(DB_NAME, COLLECTION_NAME);
    }

    public String queryAll() {
        FindIterable<Document> it = collection.find();
        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public String queryByParam(Integer level, String name, String fromDatetime, String toDatetime) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (level != null) {
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDatetime));
        }
        if (fromDatetime != null) {
            fromDatetime = DateUtil.parseDate(fromDatetime);
            conditions.add(Filters.gte(KEY_TIMESTAMP, fromDatetime));
        }
        if (toDatetime != null) {
            toDatetime = DateUtil.parseDate(toDatetime);
            conditions.add(Filters.lte(KEY_TIMESTAMP, toDatetime));
        }
        FindIterable<Document> it = collection.find(Filters.and(conditions));

        return JsonUtil.parseFindIterableToJsonArray(it);
    }

    public void add(StandardLog standardLog) {
        Document d = new Document();
        if (standardLog.getData() != null) {
            d.append(KEY_DATA, standardLog.getData());
        }
        if (standardLog.getDatetime() != null) {
            d.append(KEY_TIMESTAMP, DateUtil.parseDate(standardLog.getDatetime()));
        }
        if (standardLog.getLevel() != null) {
            d.append(KEY_LEVEL, standardLog.getLevel());
        }
        if (standardLog.getClassName() != null) {
            d.append(KEY_CLASS_NAME, standardLog.getClassName());
        }
        collection.insertOne(d);
    }

    public void delete(String id) {
        collection.deleteOne(Filters.eq(KEY_ID, new ObjectId(id)));
    }

}