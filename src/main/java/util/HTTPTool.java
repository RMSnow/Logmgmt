package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.ConfInfo;
import entity.Result;
import entity.Status;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by snow on 01/12/2017.
 */
public class HTTPTool {
    public static String auth = "kong";
    public static String name = ConfInfo.serviceName;
    public static String url = ConfInfo.url;
    public static String ip = ConfInfo.ip;
    public static String port = ConfInfo.logmgmtPort;

    CloseableHttpClient httpClient;
    HttpContext context;

    public HTTPTool(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        context = new BasicHttpContext();
        //网关ip和port
    }

    //注册API
    public Result registerApi(){
        Result result = new Result("network error", Status.ERR_NETWORK,"");

        try {
            CloseableHttpResponse response = httpClient.execute(new HttpPost(url+":8000/application/api/v1/apis?ip="+ip+"&port="+port+"&name="+name), context);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JSONObject content = JSON.parseObject(EntityUtils.toString(entity));
                result.setMsg(content.get("msg"));
                result.setStatus((int)content.get("status"));
                if(result.getStatus() == Status.OK){
                    auth = (String)content.get("auth");
                }
            }
            response.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    //请求API
    public Result requestApi(String name, String requestUri, int method){
        byte[] bytes= Base64.getUrlEncoder().encode(requestUri.getBytes(StandardCharsets.UTF_8));
        requestUri = new String(bytes, StandardCharsets.UTF_8);

        Result result = new Result("network error", Status.ERR_NETWORK,"");
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(url+":8001/application/api/v1/dispatcher?name="+name+"&requestUri="+requestUri+"&method="+method), context);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JSONObject content = JSON.parseObject(EntityUtils.toString(entity));
                if(content!=null) {
                    result.setMsg(content.get("msg"));
                    result.setStatus((int) content.get("status"));
                    result.setData(content.get("data"));
                }
            }
            response.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
