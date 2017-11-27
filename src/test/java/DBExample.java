/**
 * Created by WYJ on 2017/11/7.
 */
import mongodb.MongoService;
import orm.LoggingLog;
import orm.RequestLog;

import java.io.IOException;


public class DBExample {
    public static void main(String[] args) throws IOException{
//        addAccessLog();
//        queryAccessLog();
//        deleteAccessLog();
//
//        addStandardLog();
        queryStandardLog();
//        deleteStandardLog();
//        String s="17/十一月/2017:06:28:39";
//        System.out.println(DateUtil.parseReqLogDateTime(s));

//        String timestamp=DateUtil.getDateNow();
//        System.out.println(timestamp);


    }
    private static void addAccessLog(){
        //新增
        //<134>Nov 17 14:28:39 DESKTOP-9EODV8A couseservice[12624]: [dw-17] http.request 0:0:0:0:0:0:0:1 - - [17/十一月/2017:06:28:39 +0000] "GET /application/kk HTTP/1.1" 404 43 "-" "PostmanRuntime/6.4.1" 231

        String name="courseservice";
        String url="/application/api/v2/class? HTTP/1.1";
        String method="GET";
        String dateTime="04/Nov/2017:11:43:54";
        int status=200;
        String ip="123.207.73.150";
        String facility="Apache-HttpClient/4.5.2 (Java/1.8.0_151)";
        RequestLog requestLog =new RequestLog();
        requestLog.setHost(ip);
        requestLog.setServiceName(name);
        requestLog.setMethod(method);
        requestLog.setUrl(url);
        requestLog.setStatus(status);
        requestLog.setClient(facility);
        requestLog.setDatetime(dateTime);
        MongoService.getRequestLogCollection().add(requestLog);
    }
    private static void queryAccessLog(){
        //        查询
        System.out.println(MongoService.getRequestLogCollection().queryByParam(
                null,
                null,
                "24-11-2017 23:11:40",
                null,
                null,
                null,
                null
        ).toString());
//        System.out.println(MongoService.getRequestLogCollection().queryAll());
    }
    private static void deleteAccessLog(){
        //删除
        MongoService.getRequestLogCollection().delete("5a07bed8ae678f0c9bd91339");
    }
    private static void addStandardLog(){
        String className="io.dropwizard.server.SimpleServerFactory";
        String dateTime="04/Nov/2017:11:43:54";
        int level=6;
        String data="Registering admin handler with root path prefix: /admin";
        LoggingLog loggingLog =new LoggingLog();
        loggingLog.setLevel(level);
        loggingLog.setMessage(data);
        loggingLog.setClassName(className);
        loggingLog.setTimestamp(dateTime);
        MongoService.getLoggingLogCollection().add(loggingLog);
    }
    private static void queryStandardLog(){
        System.out.println(MongoService.getLoggingLogCollection().queryByParam(
                null,
                null,
                null,
                "28-11-2017",
                null,
                "0",
                null
        ).toString());
    }
    private static void deleteStandardLog(){

        MongoService.getLoggingLogCollection().delete("5a0722d2a9eba32a28da1d70");
    }
}
