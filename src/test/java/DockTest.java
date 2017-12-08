import com.alibaba.fastjson.JSONObject;
import dock.*;

import static dock.MessageBuilder.newMessage;
//import static service.gateway.MessageBuilder.newMessage;

public class DockTest {

    public static void main(String[] args) {
        MessageDetail msg = newMessage().setApiUrl("/application/api/v2/tokens")
                .setParam("phoneNumber", "18664388610")
                .setParam("password", "fin")
                .setServiceName(Services.KEY_VERIFY)
                .build();
        JSONObject json = dock.RestResultGetter.newResult(msg).get().start();
        System.out.println(json.toJSONString());
    }
}
