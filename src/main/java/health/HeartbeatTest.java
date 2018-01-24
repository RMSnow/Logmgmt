package health;

import com.alibaba.fastjson.JSON;
import dock.RestResultGetter;
import entity.Result;
import org.springframework.stereotype.Service;
import res.HeartRes;

import java.util.concurrent.TimeUnit;

import static dock.RestResultGetter.ownApi;

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
                //TODO：本地测试使用token
//                ownApi.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
//                        "eyJwb3J0Ijo5OTk5LCJpcCI6IjExOS4yOS4yMjguMjEiLCJpc3MiOiJhcGktZ2F0ZXdheSJ9." +
//                        "866FC2FD514D1EA18FB62E10FDBE9AE4FA31A5AB757ECA0B2D19CB3EB25F4591");

                //每隔15秒检测一次服务是否连接
                if(OK){
                    OK = false;
                }else{
                    RestResultGetter.registerApi();
                    //？？？
                    if(HeartRes.tokens.size() < 16)
                        HeartRes.tokens.add(ownApi.getToken());
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
