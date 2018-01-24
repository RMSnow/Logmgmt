package mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import mongodb.MongoResult;
import orm.record.RequestsOfScale;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import orm.record.Record;
import syslog.SyslogEvent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * 日志分析记录
 */
public class RecordDao {
    public static final String KEY_ID = "_id";
    public static final String KEY_SERVICE_NAME = "service_name";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_API_REQUEST_TABLE = "api_request_table";
    public static final String KEY_LOGGING_ERRORS = "logging_errors";
    public static final String KEY_REQUEST_EXCEPTIONS = "request_exception";
    public static final String KEY_HOUR_REQUESTS = "hour_requests";
    public static final String KEY_SECOND_REQUESTS_RATE = "second_requests_rate";

    private static final String COLLECTION_NAME = "record";
    private MongoCollection<Document> collection;

    public RecordDao() {
        collection = MongoConnector.getCollection(MongoConnector.DB_NAME, COLLECTION_NAME);
    }

    /**
     * 添加一条记录
     *
     * @param record
     */
    public void add(Record record) {
        try{
            Document d = new Document();
            if (record.getServiceName() != null) {
                d.append(KEY_SERVICE_NAME, record.getServiceName());
            }
            if (record.getTimestamp() != null) {
                d.append(KEY_TIMESTAMP, record.getTimestamp());
            }
            if (record.getApiRequestTable() != null) {
                Hashtable<String, Integer> table = record.getApiRequestTable();
                Document hashDoc = new Document();
                Iterator it = table.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    hashDoc.append(key, table.get(key));
                }

                if (hashDoc.size() != 0) {
                    d.append(KEY_API_REQUEST_TABLE, hashDoc);
                }
            }
            if (record.getLoggingErrors() != null) {
                d.append(KEY_LOGGING_ERRORS, record.getLoggingErrors());
            }
            if (record.getRequestExceptions() != null) {
                d.append(KEY_REQUEST_EXCEPTIONS, record.getRequestExceptions());
            }
            if (record.getHourRequests() != null) {
                d.append(KEY_HOUR_REQUESTS, record.getHourRequests());
            }
            if (record.getSecondRequestsOfScale() != null) {
                RequestsOfScale[] rates = record.getSecondRequestsOfScale();
                Document arrayDoc = new Document();
                for (int i = 0; i < 12; i++) {
                    if (rates[i] != null) {
                        //TODO: 目前存的是5分钟内的访问次数，不是秒频率
                        arrayDoc.append(rates[i].getTimescale(), rates[i].getRequests());
                    }
                }

                if (arrayDoc.size() != 0) {
                    d.append(KEY_SECOND_REQUESTS_RATE, arrayDoc);
                }
            }

            collection.insertOne(d);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 存入生成的记录，并设置静态变量的清空
     *
     * @param serviceRecords
     * @return
     */
    public MongoResult addAll(ArrayList<Record> serviceRecords) {
        try {
            for (int i = 0; i < serviceRecords.size(); i++) {
                Record record = serviceRecords.get(i);
                record.setTimestamp(DateUtil.getDateNow());

                add(record);
            }

            //TODO: 静态变量的清空
            //SyslogService.initRecords();
            SyslogEvent.initRecords();

            return new MongoResult(serviceRecords);
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
    public MongoResult queryAll(String limit) {
        try {
            FindIterable<Document> it;
            if (limit != null) {
                it = collection.find().sort(Sorts.descending(KEY_TIMESTAMP)).limit(Integer.valueOf(limit));
            }else {
                it = collection.find().sort(Sorts.descending(KEY_TIMESTAMP)).limit(20);
            }

            ArrayList<Record> records = new ArrayList<>();
            for (Document d : it) {
                records.add(new Record(d));
            }
            return new MongoResult(records);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询昨天开始的记录
     *
     * @param serviceName
     * @return
     */
    public ArrayList<Record> getDailyRecords(String serviceName) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        conditions.add(Filters.gte(KEY_TIMESTAMP, DateUtil.getYesterday()));

        FindIterable<Document> it = collection.find(Filters.and(conditions));

        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return records;
    }

    /**
     * 查询特定某一天的记录
     *
     * @param serviceName
     * @param amount
     * @return
     */
    public ArrayList<Record> getDailyRecords(String serviceName, int amount) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        conditions.add(Filters.gte(KEY_TIMESTAMP, DateUtil.getTheDay(amount)));
        conditions.add(Filters.lte(KEY_TIMESTAMP, DateUtil.getTheDay(amount + 1)));

        FindIterable<Document> it = collection.find(Filters.and(conditions));

        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return records;
    }

    /**
     * recent days
     */
    public ArrayList getRecentRecords(String serviceName) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        conditions.add(Filters.gte(KEY_TIMESTAMP, DateUtil.getRecentDays()));

        FindIterable<Document> it = collection.find(Filters.and(conditions));

        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return records;
    }

}
