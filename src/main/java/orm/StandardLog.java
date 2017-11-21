package orm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by WYJ on 2017/11/8.
 */
public class StandardLog {

    private String name;
    private Integer level;
    private String datetime;
    private String className;
    private String message;

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
    public String getDatetime() {
        return datetime;
    }

    @JsonProperty
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }
}