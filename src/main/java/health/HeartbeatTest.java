package health;

import com.alibaba.fastjson.JSON;
import dock.DockService;
import entity.Result;
import res.HeartRes;

import java.util.concurrent.TimeUnit;

/**
 * Created by snow on 01/12/2017.
 */
public class HeartbeatTest implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                if (HeartRes.OK) {
                    HeartRes.OK = false;
                } else {
                    Result result = DockService.registerApi();
                    System.out.println(JSON.toJSONString(result));
                }
                TimeUnit.MILLISECONDS.sleep(180000);//每隔三分钟检测一次heart
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
