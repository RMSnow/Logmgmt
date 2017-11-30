package conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 配置信息
 */
public class LogMgmtConf extends Configuration{
    @NotEmpty
    private String name;
    @NotEmpty
    private String ip;
    @NotEmpty
    private String logmgmtPort;
    @NotEmpty
    private String syslogPort;
    @NotEmpty
    private String mongodbPort;
    @NotEmpty
    private String mongodbHost;
    @NotEmpty
    private String url;

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
    public String getLogmgmtPort() {
        return logmgmtPort;
    }

    @JsonProperty
    public void setLogmgmtPort(String logmgmtPort) {
        this.logmgmtPort = logmgmtPort;
    }

    @JsonProperty
    public String getSyslogPort() {
        return syslogPort;
    }

    @JsonProperty
    public void setSyslogPort(String syslogPort) {
        this.syslogPort = syslogPort;
    }

    @JsonProperty
    public String getMongodbPort() {
        return mongodbPort;
    }

    @JsonProperty
    public void setMongodbPort(String mongodbPort) {
        this.mongodbPort = mongodbPort;
    }

    @JsonProperty
    public String getMongodbHost() {
        return mongodbHost;
    }

    @JsonProperty
    public void setMongodbHost(String mongodbHost) {
        this.mongodbHost = mongodbHost;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
    }
}
