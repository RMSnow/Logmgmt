package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/8.
 */
public class StandardLog {

    private Integer level;
    private String timestamp;
    private String className;
    private String data;

    @JsonProperty
    public Integer getLevel() {
        return level;
    }

    @JsonProperty
    public void setLevel(Integer level) {
        this.level = level;
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
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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