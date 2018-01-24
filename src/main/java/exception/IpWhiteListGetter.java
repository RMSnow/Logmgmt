package exception;

import com.alibaba.fastjson.JSONObject;
import dock.Apis;
import dock.RestResultGetter;
import entity.ConfInfo;

import java.util.HashSet;

/**
 * 定时从注册中心获取IP白名单
 */
public class IpWhiteListGetter {
    public static HashSet<String> ipWhiteList = new HashSet<>();

    //test
    public static void main(String[] args){
        ConfInfo.ip = "119.29.228.21";
        ConfInfo.serviceName = "logmgmt";
        ConfInfo.logmgmtPort = "9999";
        ConfInfo.pswd = "d50e43d9fe9a418692eb5db78217af7b";
        ConfInfo.registryIp = "123.207.73.150";
        ConfInfo.registryPort = "8000";
        ConfInfo.discoverPort = "8001";

        JSONObject jsonObject = RestResultGetter.newResult(null).get().ipRequest();
        System.out.println(jsonObject);
    }

    //TODO: 10分钟更新一次白名单
}

