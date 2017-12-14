package mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.MongoResult;
import entity.RequestsRate;
import mongodb.DateUtil;
import mongodb.MongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import orm.Record;
import syslog.SyslogService;

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
        if (record.getSecondRequestsRate() != null) {
            RequestsRate[] rates = record.getSecondRequestsRate();
            Document arrayDoc = new Document();
            for (int i = 0; i < 12; i++) {
                if (rates[i] != null) {
                    arrayDoc.append(rates[i].getTimescale(), rates[i].getRate());
                }
            }

            if (arrayDoc.size() != 0) {
                d.append(KEY_SECOND_REQUESTS_RATE, arrayDoc);
            }
        }

        collection.insertOne(d);
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
                //TODO: 检查所有的属性
                Record record = serviceRecords.get(i);
                record.setTimestamp(DateUtil.getDateNow());

                add(record);
            }

            //TODO: 静态变量的清空
            SyslogService.initRecords();

            return new MongoResult(serviceRecords);
        } catch (Exception e) {
            System.err.println("Errors in inserting records.");
        }
        return null;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public MongoResult queryAll() {
        FindIterable<Document> it = collection.find();
        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return new MongoResult(records);
    }

    /**
     * 查询特定服务一天内的记录
     *
     * @param serviceName
     * @return
     */
//        日志分析接口：传服务名，返回分析结果，结果要包括
//
//        新建一个集合，（按小时存储以下），按天返回结果
//        # 该服务最常被调用的api
//        # 以及数量
//        # 服务错误数——Logging
//        # 不正常返回（非200）数——RequestLog
//        # 最近30天每天服务访问量
//
//        # 最近5分钟每秒请求数
    public ArrayList<Record> getDailyRecords(String serviceName) {
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        conditions.add(Filters.gte(KEY_TIMESTAMP,DateUtil.getYesterday()));

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
    public ArrayList getRecentRecords(String serviceName){
        List<Bson> conditions = new ArrayList<Bson>();
        if (serviceName != null) {
            conditions.add(Filters.eq(KEY_SERVICE_NAME, serviceName));
        }
        conditions.add(Filters.gte(KEY_TIMESTAMP,DateUtil.getRecentDays()));

        FindIterable<Document> it = collection.find(Filters.and(conditions));

        ArrayList<Record> records = new ArrayList<>();
        for (Document d : it) {
            records.add(new Record(d));
        }
        return records;
    }

}
