package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/8.
 */
public class ErrorLog {

    private String ip;
    private String name;
    private String url;
    private String dateTime;
    private String data;

    @JsonProperty
    public String getIp() {
        return ip;
    }

    @JsonProperty
    public void setIp(String ip) {
        this.ip = ip;
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
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
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
    public String getData() {
        return data;
    }

    @JsonProperty
    public void setData(String data) {
        this.data = data;
    }


}
