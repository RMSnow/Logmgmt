package health;

import com.alibaba.fastjson.JSON;
import dock.DockService;
import dock.RestResultGetter;
import entity.Result;
import org.springframework.stereotype.Service;
import res.HeartRes;

import java.util.concurrent.TimeUnit;

/**
 * Created by snow on 01/12/2017.
 */
@Service
public class HeartbeatTest implements Runnable {

    public static volatile boolean OK = false;

    @Override
    public void run() {
        while (true) {
            try {
                //TODO: 心跳包5秒发送一次
//                for (int i = 0; i < 3; i++) {
//
//                }

                //15秒检测一次是否断线
//                if (HeartRes.OK) {
//                    HeartRes.OK = false;
//                } else {
//                    Result result = DockService.registerApi();
//                    System.out.println(JSON.toJSONString(result));
//                }

                if(OK){
                    OK = false;
                }else{
                    RestResultGetter.registerApi();
                    //？？？
                    if(HeartRes.tokens.size() < 16)
                        HeartRes.tokens.add(RestResultGetter.ownApi.getToken());
                    RestResultGetter.listApis();
                }

                TimeUnit.MILLISECONDS.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
