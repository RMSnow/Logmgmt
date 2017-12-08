package orm;

import com.fasterxml.jackson.annotation.JsonProperty;
import entity.RequestsRate;
import mongodb.dao.RecordDao;
import org.bson.Document;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by snow on 05/12/2017.
 */
public class Record {
//    _id                 [自动生成]
//    serviceName         [String]
//    timestamp           [是否可以自动生成？根据计划任务]
//
//    apiRequestTable     [HashTable<String, Integer>]
//    loggingErrors       [Integer]
//    requestExceptions   [Integer]
//    hourRequests        [Integer]
//    secondRequestsRate  [ObjectArray]

    @JsonProperty
    private String id;
    @JsonProperty
    private String serviceName;
    @JsonProperty
    private String timestamp;
    @JsonProperty
    private Hashtable<String, Integer> apiRequestTable = new Hashtable<>();
    @JsonProperty
    private Integer loggingErrors;
    @JsonProperty
    private Integer requestExceptions;
    @JsonProperty
    private Integer hourRequests;
    @JsonProperty
    private RequestsRate[] secondRequestsRate = new RequestsRate[12];

    public Record(String serviceName) {
        this.serviceName = serviceName;
    }

    public Record(Document d) {
        setId(d.getObjectId(RecordDao.KEY_ID).toString());
        setServiceName(d.getString(RecordDao.KEY_SERVICE_NAME));
        setTimestamp(d.getString(RecordDao.KEY_TIMESTAMP));

        //apiRequestTable
        Document hashDoc = (Document) d.get(RecordDao.KEY_API_REQUEST_TABLE);
        Iterator hashIt = hashDoc.keySet().iterator();
        while (hashIt.hasNext()) {
            String key = (String) hashIt.next();
            putApiRequestTable(key, hashDoc.getInteger(key));
            //System.out.println("key = " + key + ", value = " + hashDoc.getInteger(key));
        }

        setLoggingErrors(d.getInteger(RecordDao.KEY_LOGGING_ERRORS));
        setRequestExceptions(d.getInteger(RecordDao.KEY_REQUEST_EXCEPTIONS));
        setHourRequests(d.getInteger(RecordDao.KEY_HOUR_REQUESTS));

        //secondRequestsRate
        Document arrayDoc = (Document) d.get(RecordDao.KEY_SECOND_REQUESTS_RATE);
        Iterator arrayIt = arrayDoc.keySet().iterator();
        int index = 0;
        while (arrayIt.hasNext()) {
            String key = (String) arrayIt.next();
            RequestsRate rate = new RequestsRate(key, arrayDoc.getDouble(key));
            //System.out.println(rate);
            setSecondRequestsRate(index++, rate);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Hashtable<String, Integer> getApiRequestTable() {
        return apiRequestTable;
    }

    public void setApiRequestTable(Hashtable<String, Integer> apiRequestTable) {
        this.apiRequestTable = apiRequestTable;
    }

    public Integer getLoggingErrors() {
        return loggingErrors;
    }

    public void setLoggingErrors(Integer loggingErrors) {
        this.loggingErrors = loggingErrors;
    }

    public Integer getRequestExceptions() {
        return requestExceptions;
    }

    public void setRequestExceptions(Integer requestExceptions) {
        this.requestExceptions = requestExceptions;
    }

    public Integer getHourRequests() {
        return hourRequests;
    }

    public void setHourRequests(Integer hourRequests) {
        this.hourRequests = hourRequests;
    }

    public RequestsRate[] getSecondRequestsRate() {
        return secondRequestsRate;
    }

    public void setSecondRequestsRate(RequestsRate[] secondRequestsRate) {
        this.secondRequestsRate = secondRequestsRate;
    }

    public void putApiRequestTable(String key, Integer value) {
        apiRequestTable.put(key, value);
    }

    public void setSecondRequestsRate(int index, RequestsRate requestsRate) {
        secondRequestsRate[index] = requestsRate;
    }
}
