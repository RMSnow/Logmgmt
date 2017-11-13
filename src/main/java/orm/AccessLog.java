package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/7.
 */
public class AccessLog {
    private String host;
    private String name;
    private String timestamp;
    private String method;
    private String url;
    private Integer status;
    private String facility;


    @JsonProperty
    public String getMethod() {
        return method;
    }

    @JsonProperty
    public void setMethod(String method) {
        this.method = method;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
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
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
    public String getFacility() {
        return facility;
    }

    @JsonProperty
    public void setFacility(String facility) {
        this.facility = facility;
    }

}