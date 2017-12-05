package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by snow on 05/12/2017.
 */
public class Record {
//    新建一个集合，存储以下，按天
//    # 该服务最常被调用的api以及数量
//    # 服务错误数——Logging
//    # 不正常返回（非200）数——RequestLog
//    # 最近30天每天服务访问量
//
//    # 最近5分钟每秒请求数

    //TODO: orm

    private String mostURI;
    private int requests;
    private int loggingErrors;
    private int requestExceptions;
    private int dailyRequests;
    private double hourRequestsRate;

    @JsonProperty
    public String getMostURI() {
        return mostURI;
    }

    @JsonProperty
    public void setMostURI(String mostURI) {
        this.mostURI = mostURI;
    }

    @JsonProperty
    public int getRequests() {
        return requests;
    }

    @JsonProperty
    public void setRequests(int requests) {
        this.requests = requests;
    }

    @JsonProperty
    public int getLoggingErrors() {
        return loggingErrors;
    }

    @JsonProperty
    public void setLoggingErrors(int loggingErrors) {
        this.loggingErrors = loggingErrors;
    }

    @JsonProperty
    public int getRequestExceptions() {
        return requestExceptions;
    }

    @JsonProperty
    public void setRequestExceptions(int requestExceptions) {
        this.requestExceptions = requestExceptions;
    }

    @JsonProperty
    public int getDailyRequests() {
        return dailyRequests;
    }

    @JsonProperty
    public void setDailyRequests(int dailyRequests) {
        this.dailyRequests = dailyRequests;
    }

    @JsonProperty
    public double getHourRequestsRate() {
        return hourRequestsRate;
    }

    @JsonProperty
    public void setHourRequestsRate(double hourRequestsRate) {
        this.hourRequestsRate = hourRequestsRate;
    }
}
