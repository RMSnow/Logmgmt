package conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;

/**
 * 配置信息
 */
public class LogMgmtConf extends Configuration {
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
    private String mongodbUserName;
    @NotEmpty
    private String mongodbPassword;
    @NotEmpty
    private String url;
    @NotEmpty
    private String pswd;
    @NotEmpty
    private String registryIp;
    @NotEmpty
    private String registryPort;
    @NotEmpty
    private String discoverPort;
    @NotEmpty
    private ArrayList<String> recipients = new ArrayList<>();

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
    public String getMongodbUserName() {
        return mongodbUserName;
    }

    @JsonProperty
    public void setMongodbUserName(String mongodbUserName) {
        this.mongodbUserName = mongodbUserName;
    }

    @JsonProperty
    public String getMongodbPassword() {
        return mongodbPassword;
    }

    @JsonProperty
    public void setMongodbPassword(String mongodbPassword) {
        this.mongodbPassword = mongodbPassword;
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
    public String getPswd() {
        return pswd;
    }

    @JsonProperty
    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    @JsonProperty
    public String getRegistryIp() {
        return registryIp;
    }

    @JsonProperty
    public void setRegistryIp(String registryIp) {
        this.registryIp = registryIp;
    }

    @JsonProperty
    public String getRegistryPort() {
        return registryPort;
    }

    @JsonProperty
    public void setRegistryPort(String registryPort) {
        this.registryPort = registryPort;
    }

    @JsonProperty
    public String getDiscoverPort() {
        return discoverPort;
    }

    @JsonProperty
    public void setDiscoverPort(String discoverPort) {
        this.discoverPort = discoverPort;
    }

    @JsonProperty
    public ArrayList<String> getRecipients() {
        return recipients;
    }

    @JsonProperty
    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }
}
