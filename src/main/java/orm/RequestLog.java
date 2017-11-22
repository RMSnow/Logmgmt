package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/7.
 */
public class RequestLog {

    private String host;
    private String serviceName;
    private String className;
    private Integer facility;
    private String clientIP;
    private String datetime;
    private String method;
    private String url;
    private Integer status;
    private String client;


    @JsonProperty
    public String getMethod() {
        return method;
    }

    @JsonProperty
    public void setMethod(String method) {
        this.method = method;
    }


    @JsonProperty
    public String getHost() {
        return host;
    }

    @JsonProperty
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty
    public String getDatetime() {
        return datetime;
    }

    @JsonProperty
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty
    public int getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty
    public String getClient() {
        return client;
    }

    @JsonProperty
    public void setClient(String client) {
        this.client = client;
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
    public String getClassName() {
        return className;
    }

    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty
    public Integer getFacility() {
        return facility;
    }

    @JsonProperty
    public void setFacility(Integer facility) {
        this.facility = facility;
    }

    @JsonProperty
    public String getClientIP() {
        return clientIP;
    }

    @JsonProperty
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    @JsonProperty
    public void setStatus(Integer status) {
        this.status = status;
    }
}