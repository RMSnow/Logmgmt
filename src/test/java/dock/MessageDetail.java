package dock;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

/**
 * Created by fin on 2017/7/26.
 */
public class MessageDetail implements Serializable {

    private String token;

    private String url;

    private String name;

    private String entity;

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getEntity() {

        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    private Map<String,String> param;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        if (!param.isEmpty()) {
            for (Map.Entry<String, String> entrys : param.entrySet()) {
                sb.append(entrys.getKey()).append("=").append(entrys.getValue()).append("&");
            }
        }
        if (token != null) {
            sb.append("auth=").append(token);
        }
        return sb.toString();
    }


    public int size() {
        return param.size();
    }


    public boolean isEmpty() {
        return param.isEmpty();
    }



    public String get(Object key) {
        return param.get(key);
    }


    public String put(String key, String value) {
        return param.put(key, value);
    }

    public String remove(Object key) {
        return param.remove(key);
    }


    public void putAll(Map<? extends String, ? extends String> m) {
        param.putAll(m);
    }


    public void clear() {
        param.clear();
    }


    public Set<String> keySet() {
        return param.keySet();
    }


    public Collection<String> values() {
        return param.values();
    }


    public Set<Map.Entry<String, String>> entrySet() {
        return param.entrySet();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
