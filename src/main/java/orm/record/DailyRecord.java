package orm.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import mongodb.DateUtil;
import mongodb.MongoService;

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
    private int[] recentDaysRequests = new int[30];

    @JsonProperty
    private RequestsOfScale[] recentDaysRequestsTable = new RequestsOfScale[30];

    @JsonProperty
    private Hashtable<String, Object> resultTable = new Hashtable<>();

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
                this.setSecondRequestsOfScale(record.getSecondRequestsOfScale());
            }
        }

        //最近三十天的日访问次数
        for (int i = 0; i < 30; i++) {
            recentDaysRequests[i] = 0;

            int amount = i - 30;
            ArrayList<Record> theDayRecords = MongoService.getRecordCollection().
                    getDailyRecords(serviceName, amount);

            for (int j = 0; j < theDayRecords.size(); j++) {
                recentDaysRequests[i] += theDayRecords.get(j).getHourRequests();
            }

            recentDaysRequestsTable[i] = new RequestsOfScale(DateUtil.getTheDay(amount + 1), recentDaysRequests[i]);
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
        resultTable.put("latestRequestRates", secondRequestsOfScale);

        resultTable.put("recentDaysRequestsTable", recentDaysRequestsTable);
    }
}
