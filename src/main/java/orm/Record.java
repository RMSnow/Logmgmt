package orm;

import com.fasterxml.jackson.annotation.JsonProperty;
import mongodb.dao.RecordDao;
import org.bson.Document;

/**
 * Created by snow on 05/12/2017.
 */
public class Record {
//    新建一个集合，存储以下，按小时
//    # 该服务最常被调用的api以及数量
//    # 服务错误数——Logging
//    # 不正常返回（非200）数——RequestLog
//    # 最近30天每天服务访问量
//
//    # 最近5分钟每秒请求数

    //TODO: orm
    private String id;
    private String serviceName;

    private String mostURI;
    private Integer mostRequests;

    private Integer loggingErrors;
    private Integer requestExceptions;
    private Integer hourRequests;
    private Double secondRequestsRate;

    public Record(){

    }

    public Record(Document d) {
        setId(d.getObjectId(RecordDao.KEY_ID).toString());
        setServiceName(d.getString(RecordDao.KEY_SERVICE_NAME));
        setMostURI(d.getString(RecordDao.KEY_MOST_URI));
        setMostRequests(d.getInteger(RecordDao.KEY_MOST_REQUESTS));
        setLoggingErrors(d.getInteger(RecordDao.KEY_LOGGING_ERRORS));
        setRequestExceptions(d.getInteger(RecordDao.KEY_REQUEST_EXCEPTIONS));
        setHourRequests(d.getInteger(RecordDao.KEY_HOUR_REQUESTS));
        setSecondRequestsRate(d.getDouble(RecordDao.KEY_SECOND_REQUESTS_RATE));
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JsonProperty
    public String getMostURI() {
        return mostURI;
    }

    @JsonProperty
    public void setMostURI(String mostURI) {
        this.mostURI = mostURI;
    }

    @JsonProperty
    public Integer getMostRequests() {
        return mostRequests;
    }

    @JsonProperty
    public void setMostRequests(Integer mostRequests) {
        this.mostRequests = mostRequests;
    }

    @JsonProperty
    public Integer getLoggingErrors() {
        return loggingErrors;
    }

    @JsonProperty
    public void setLoggingErrors(Integer loggingErrors) {
        this.loggingErrors = loggingErrors;
    }

    @JsonProperty
    public Integer getRequestExceptions() {
        return requestExceptions;
    }

    @JsonProperty
    public void setRequestExceptions(Integer requestExceptions) {
        this.requestExceptions = requestExceptions;
    }

    @JsonProperty
    public Integer getHourRequests() {
        return hourRequests;
    }

    @JsonProperty
    public void setHourRequests(Integer hourRequests) {
        this.hourRequests = hourRequests;
    }

    @JsonProperty
    public Double getSecondRequestsRate() {
        return secondRequestsRate;
    }

    @JsonProperty
    public void setSecondRequestsRate(Double secondRequestsRate) {
        this.secondRequestsRate = secondRequestsRate;
    }
}
