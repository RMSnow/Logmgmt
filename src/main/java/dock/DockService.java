package dock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dock.MessageBuilder;
import dock.MessageDetail;
import dock.RestResultGetter;
import dock.Services;
import entity.ConfInfo;
import entity.Result;
import entity.Status;

/**
 * 服务对接
 */
public class DockService {
    public static String name = ConfInfo.serviceName;
    public static String url = ConfInfo.url;
    public static String ip = ConfInfo.ip;
    public static String port = ConfInfo.logmgmtPort;

    //注册API
    public static Result registerApi() {
        MessageDetail msg = MessageBuilder.newMessage().setApiUrl("/application/api/v1/apis")
                .setParam("ip", ip)
                .setParam("port", port)
                .setParam("name", name)
                .setServiceName(Services.API_REGISTRY)
                .build();
        JSONObject json = RestResultGetter.newResult(msg).post().start();
        Result result = new Result("", Status.ERR_NETWORK, "");
        if (json != null) {
            result.setMsg(json.get("msg"));
            result.setStatus((int) json.get("status"));
            result.setData(json.get("data"));
        }
        System.out.println(JSON.toJSONString(result));
        return result;
    }
}
