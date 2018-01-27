package exception;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dock.RestResultGetter;
import entity.ConfInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * 定时从注册中心获取IP白名单
 */
public class IpWhiteListGetter implements Runnable {
    public static HashSet<String> ipWhiteList = new HashSet<>();

    //10分钟更新一次白名单
    @Override
    public void run() {
        while (true) {
            try {
                initIpWhiteList();

                JSONArray msgArray = RestResultGetter.newResult(null).get().ipRequest().getJSONArray("data");
                if (msgArray != null){
                    for (int i = 0; i < msgArray.size(); i++) {
                        String ip = (String) ((JSONObject)msgArray.get(i)).get("ip");
                        ipWhiteList.add(ip);
                    }
                }

                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //初始化白名单
    public static synchronized void initIpWhiteList(){
        ipWhiteList = new HashSet<>();
    }

    //IP分析比对
    public static synchronized boolean isSecure(String clientIp){
//        Iterator iterator = ipWhiteList.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        if (ipWhiteList.contains(clientIp))
            return true;
        else
            return false;
    }
}

