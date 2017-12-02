package util;

import com.alibaba.fastjson.JSON;
import entity.Result;
import res.HeartRes;

import java.util.concurrent.TimeUnit;

/**
 * Created by snow on 01/12/2017.
 */
public class HeartbeatTest implements Runnable {
    @Override
    public void run() {
        try{
            while (true){
                if(HeartRes.OK){
                    HeartRes.OK = false;
                }else{
                    Result result = new HTTPTool().registerApi();
                    System.out.println(JSON.toJSONString(result));
                }
                TimeUnit.MILLISECONDS.sleep(180000);//每隔三分钟检测一次heart
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
