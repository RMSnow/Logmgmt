package dock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
//import com.wordnik.swagger.annotations.Api;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import orm.Apis;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

//import static service.gateway.MessageBuilder.newMessage;

public class RestResultGetter {

    private static RestTemplate restTemplate;

    static{
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().set(6, new FastJsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private String registryUrl = "http://123.207.73.150:8001/application/api/v1/discover/";

    private MessageDetail msg;

    private int method;

    private RestResultGetter(){
    }

    public static RestResultGetter newResult(MessageDetail msg) {
        RestResultGetter getter = new RestResultGetter();
        getter.msg = msg;
        return getter;
    }

    public RestResultGetter get(){
        this.method = Methods.GET;
        return this;
    }

    public RestResultGetter post(){
        this.method = Methods.POST;
        return this;
    }

    public RestResultGetter patch(){
        this.method = Methods.PATCH;
        return this;
    }

    public RestResultGetter delete(){
        this.method = Methods.DELETE;
        return this;
    }

    public RestResultGetter put(){
        this.method = Methods.PUT;
        return this;
    }

    public JSONObject start(){
        String url = registryUrl + msg.getName();
        JSONObject discover = restTemplate.getForObject(url, JSONObject.class);
        Apis api = null;
        if(!Integer.valueOf(200).equals(discover.getInteger("status"))){
            return discover;
        }else
            api = discover.getObject("msg",Apis.class);
        JSONObject ret = null;
        switch (method){
            case Methods.GET:
                ret = restTemplate.getForObject(getUrl(api, msg.toString()), JSONObject.class);
                break;
            case Methods.POST:
                ret = restTemplate.postForObject(getUrl(api, msg.toString()), msg.getEntity(), JSONObject.class);
                break;
            case Methods.PATCH:
                ret = restTemplate.exchange(getUrl(api, msg.toString()), HttpMethod.PATCH, null, JSONObject.class).getBody();
                break;
            case Methods.DELETE:
                ret = restTemplate.exchange(getUrl(api, msg.toString()), HttpMethod.DELETE, null, JSONObject.class).getBody();
                break;
            case Methods.PUT:
                ret = restTemplate.exchange(getUrl(api, msg.toString()), HttpMethod.PUT, null, JSONObject.class).getBody();
                break;
        }
        return ret;
    }

    private String getUrl(Apis api,String apiUrl){
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(api.getIp())
                .append(":")
                .append(api.getPort())
                .append(apiUrl);
        return sb.toString();
    }
}
