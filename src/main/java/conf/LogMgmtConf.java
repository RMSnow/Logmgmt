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
    private String port1;
    @NotEmpty
    private String port2;
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
    public String getPort1() {
        return port1;
    }

    @JsonProperty
    public void setPort1(String port1) {
        this.port1 = port1;
    }

    @JsonProperty
    public String getPort2() {
        return port2;
    }

    @JsonProperty
    public void setPort2(String port2) {
        this.port2 = port2;
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
