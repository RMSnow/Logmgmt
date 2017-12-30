package orm;

import com.fasterxml.jackson.annotation.JsonProperty;
import mongodb.dao.ClientLogDao;
import org.bson.Document;

/**
 * 应用层（客户端）日志
 */
public class ClientLog {
//    id
//    timestamp
//
//    ip              String  [访问客户端ip]
//    userId          String  [操作用户id]
//    responseTime    long    [响应时间]
//    api             String  [调用api及其参数]
//    status          String  [返回状态码]
//    error           String  [错误]
//    msg             String  [消息]

    @JsonProperty
    private String id;
    @JsonProperty
    private String timestamp;
    @JsonProperty
    private String ip;
    @JsonProperty
    private String userId;
    @JsonProperty
    private Long responseTime;
    @JsonProperty
    private String api;
    @JsonProperty
    private String status;
    @JsonProperty
    private String error;
    @JsonProperty
    private String msg;

    public ClientLog(Document d) {
        setId(d.getObjectId(ClientLogDao.KEY_ID).toString());
        setTimestamp(d.getString(ClientLogDao.KEY_TIMESTAMP));
        setIp(d.getString(ClientLogDao.KEY_IP));
        setUserId(d.getString(ClientLogDao.KEY_USER_ID));
        setResponseTime(d.getLong(ClientLogDao.KEY_RESPONSE_TIME));
        setApi(d.getString(ClientLogDao.KEY_API));
        setStatus(d.getString(ClientLogDao.KEY_STATUS));
        setError(d.getString(ClientLogDao.KEY_ERROR));
        setMsg(d.getString(ClientLogDao.KEY_MSG));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
