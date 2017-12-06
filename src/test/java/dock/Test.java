package dock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import static dock.MessageBuilder.newMessage;
//import static service.gateway.MessageBuilder.newMessage;

public class Test {

    public static void main(String[] args) {
        MessageDetail msg = newMessage().setApiUrl("/application/api/v2/tokens")
                .setParam("phoneNumber", "18664388610")
                .setParam("password", "fin")
                .setServiceName(Services.KEY_VERIFY)
                .build();
        JSONObject json = RestResultGetter.newResult(msg).get().start();
        System.out.println(json.toJSONString());
    }
}
