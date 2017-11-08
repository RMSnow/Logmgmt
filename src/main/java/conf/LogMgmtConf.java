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
    private String port;
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
    public String getPort() {
        return port;
    }

    @JsonProperty
    public void setPort(String port) {
        this.port = port;
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
