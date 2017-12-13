package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import mongodb.DateUtil;
import orm.Record;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * 一天的日志分析记录
 */
public class DailyRecord extends Record {
    @JsonProperty
    private int dailyRequests = 0;

    @JsonProperty
    private Hashtable<String, Object> resultTable = new Hashtable<>();

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

    public DailyRecord(String serviceName, ArrayList<Record> records) {
        super(serviceName);

        //timestamp
        this.setTimestamp(DateUtil.getDateNow());

        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);

            //apiRequestTable
            Hashtable<String, Integer> table = record.getApiRequestTable();
            Iterator iterator = table.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (apiRequestTable.containsKey(key)) {
                    apiRequestTable.put(key, apiRequestTable.get(key) + table.get(key));
                } else {
                    apiRequestTable.put(key, table.get(key));
                }
            }

            //loggingErrors
            this.setLoggingErrors(loggingErrors + record.getLoggingErrors());

            //requestExceptions
            this.setRequestExceptions(requestExceptions + record.getRequestExceptions());

            //dailyRequests
            this.setDailyRequests(dailyRequests + record.getHourRequests());

            //最近一小时的秒访问率
            if (i == records.size() - 1) {
                this.setSecondRequestsRate(record.getSecondRequestsRate());
            }
        }

        setResultTable();
    }

    public int getDailyRequests() {
        return dailyRequests;
    }

    public void setDailyRequests(int dailyRequests) {
        this.dailyRequests = dailyRequests;
    }

    public Hashtable<String, Object> getResultTable() {
        return resultTable;
    }

    public void setResultTable(Hashtable<String, Object> resultTable) {
        this.resultTable = resultTable;
    }

    public void setResultTable() {
        resultTable.put("serviceName", serviceName);
        resultTable.put("timestamp", timestamp);
        resultTable.put("apiRequestTable", apiRequestTable);
        resultTable.put("loggingErrors", loggingErrors);
        resultTable.put("requestExceptions", requestExceptions);
        resultTable.put("dailyRequests", dailyRequests);
        resultTable.put("latestRequestRates", secondRequestsRate);
    }
}
