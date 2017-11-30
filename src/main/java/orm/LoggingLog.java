package orm;

import com.fasterxml.jackson.annotation.JsonProperty;
import mongodb.dao.LoggingLogDao;
import org.bson.Document;

/**
 * Created by WYJ on 2017/11/8.
 */
public class LoggingLog {

    private String id;
    private String facility;
    private String timestamp;
    private Integer level;
    private String host;
    private String serviceName;
    private String className;
    private String message;
    private String errDetails;

    public LoggingLog() {
    }

    public LoggingLog(Document d) {
        setId(d.getObjectId(LoggingLogDao.KEY_ID).toString());
        setFacility(d.getString(LoggingLogDao.KEY_FACILITY));
        setLevel(d.getInteger(LoggingLogDao.KEY_LEVEL));
        setHost(d.getString(LoggingLogDao.KEY_HOST));
        setServiceName(d.getString(LoggingLogDao.KEY_SERVICE_NAME));
        setTimestamp(d.getString(LoggingLogDao.KEY_TIMESTAMP));
        setClassName(d.getString(LoggingLogDao.KEY_CLASS_NAME));
        setMessage(d.getString(LoggingLogDao.KEY_MESSAGE));
        setErrDetails(d.getString(LoggingLogDao.KEY_ERR_DETAILS));
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
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty
    public void setServiceName(String name) {
        this.serviceName = name;
    }

    @JsonProperty
    public String getFacility() {
        return facility;
    }

    @JsonProperty
    public void setFacility(String facility) {
        this.facility = facility;
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
    public String getHost() {
        return host;
    }

    @JsonProperty
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty
    public String getErrDetails() {
        return errDetails;
    }

    @JsonProperty
    public void setErrDetails(String errDetails) {
        this.errDetails = errDetails;
    }
}