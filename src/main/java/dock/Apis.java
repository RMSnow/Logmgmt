package dock;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fin on 2017/7/20.
 */
public class Apis {

    @JsonProperty
    private String name;

    @JsonProperty
    private int port;

    @JsonProperty
    private String ip;

    @JsonProperty
    private String status;

    @JsonProperty
    private int ping;

    @JsonProperty
    private String token;

    @JsonProperty
    private String id;

    @JsonProperty
    private String secret;

    @JsonProperty
    private String pswd;

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getPswd() {
        return pswd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Apis apis = (Apis) o;

        if (port != apis.port)
            return false;
        return ip != null ? ip.equals(apis.ip) : apis.ip == null;
    }

    @Override
    public int hashCode() {
        int result = port;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        return result;
    }

}
