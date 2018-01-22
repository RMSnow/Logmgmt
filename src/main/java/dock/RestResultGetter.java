package dock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import entity.ConfInfo;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

//import com.wordnik.swagger.annotations.Api;
//import orm.Apis;

//import static service.gateway.MessageBuilder.newMessage;

public class RestResultGetter {

    public static Apis ownApi;

    public static Apis registryApi;

    public static Apis discoverApi;

    public static HashMap<String, Apis> apis;

    private static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        apis = new HashMap<>();
        ownApi = new Apis();
        registryApi = new Apis();
        discoverApi = new Apis();

        //TODO: 初始化配置信息
        ownApi.setIp(ConfInfo.ip);
        ownApi.setName(ConfInfo.serviceName);
        ownApi.setPort(Integer.parseInt(ConfInfo.logmgmtPort));
        ownApi.setPswd(ConfInfo.pswd);
        registryApi.setIp(ConfInfo.registryIp);
        registryApi.setPort(Integer.parseInt(ConfInfo.registryPort));
        discoverApi.setIp(ConfInfo.registryIp);
        discoverApi.setPort(Integer.parseInt(ConfInfo.discoverPort));

    }

    private MessageDetail msg;

    private int method;

    private RestResultGetter() {
    }

    public static RestResultGetter newResult(MessageDetail msg) {
        RestResultGetter getter = new RestResultGetter();
        getter.msg = msg;
        return getter;
    }

    public RestResultGetter get() {
        this.method = Methods.GET;
        return this;
    }

    public RestResultGetter post() {
        this.method = Methods.POST;
        return this;
    }

    public RestResultGetter patch() {
        this.method = Methods.PATCH;
        return this;
    }

    public RestResultGetter delete() {
        this.method = Methods.DELETE;
        return this;
    }

    public RestResultGetter put() {
        this.method = Methods.PUT;
        return this;
    }

    // 服务注册 list 撤销
    public JSONObject registry() {
        return request(registryApi);
    }

    // 服务发现1 2 以及不可用报告
    public JSONObject discover() {
        return request(discoverApi);
    }

    // 使用服务发现方式1访问其他服务api
    public JSONObject request1() {
        String url = getUrl(discoverApi, "/application/api/v2/apis?name=" + msg.getName() + "&auth=" + ownApi.getToken());
        JSONObject discover = restTemplate.getForObject(url, JSONObject.class);
        Apis api = null;
        if (!Integer.valueOf(200).equals(discover.getInteger("status"))) {
            return discover;
        } else
            api = discover.getObject("msg", Apis.class);
        return request(api);
    }

    // 使用本地缓存的api表访问其他服务api
    public JSONObject request2() {
        Apis api = apis.get(msg.getName());
        if (api != null) {
            return request(api);
        }
        return null;
    }

    // 根据api提供的ip和port以及msg提供的信息访问
    private JSONObject request(Apis api) {
        JSONObject ret = null;
        switch (method) {
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
                restTemplate.delete(getUrl(api, msg.toString()));
                break;
            case Methods.PUT:
                restTemplate.put(getUrl(api, msg.toString()), null);
                break;
        }
        return ret;
    }

    // 得到最终访问url
    private String getUrl(Apis api, String apiUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(api.getIp())
                .append(":")
                .append(api.getPort())
                .append(apiUrl);
        return sb.toString();
    }

    // 注册API
    public static void registerApi() {
        try {
            MessageDetail msg = MessageBuilder.newMessage().setApiUrl("/application/api/v2/apis")
                    .setParam("ip", ownApi.getIp())
                    .setParam("port", ownApi.getPort())
                    .setParam("name", ownApi.getName())
                    .setParam("pswd", ownApi.getPswd())
                    .build();
            JSONObject json = RestResultGetter.newResult(msg).post().registry();
            System.err.println(json.get("status"));
            System.err.println("ok");
            Apis newApi = json.getObject("data", Apis.class);
            if (newApi != null) {
                ownApi.setId(newApi.getId());
                ownApi.setSecret(newApi.getSecret());
                ownApi.setToken(newApi.getToken());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 使用服务发现2订阅服务
    public static JSONObject listApis() {
        try {
            MessageDetail msg = MessageBuilder.newMessage().setApiUrl("/application/api/v2/apis")
                    .setParam("auth", ownApi.getToken())
                    .build();
            JSONObject json = RestResultGetter.newResult(msg).get().discover();
            if (json != null && json.getInteger("status") == 200) {
                apis.clear();
                JSONArray data = json.getJSONArray("msg");
                for (int i = 0; i < data.size(); i++) {
                    Apis newApi = data.getObject(i, Apis.class);
                    apis.put(newApi.getName(), newApi);
                }
            }
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
