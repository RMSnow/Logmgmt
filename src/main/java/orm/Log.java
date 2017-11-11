package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/7.
 */
public class Log {
    //新增name属性（日志所属的服务名称）
    private String name;

    private String ip;
    private String dateTime;
    private String url;
    private int status;
    private String client;
    private String data;

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getIp() {
        return ip;
    }

    @JsonProperty
    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonProperty
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
    public String getData() {
        return data;
    }

    @JsonProperty
    public void setData(String data) {
        this.data = data;
    }


}
