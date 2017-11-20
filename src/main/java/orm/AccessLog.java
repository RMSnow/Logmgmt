package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/7.
 */
public class AccessLog {
    private String host;
    private String name;
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

}