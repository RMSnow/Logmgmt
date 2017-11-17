/**
 * Created by WYJ on 2017/11/7.
 */

import mongodb.MongoService;
import orm.StandardLog;
import orm.AccessLog;
import syslog.AccessSyslogEvent;

public class DBExample {
    public static void main(String[] args) {
//        addAccessLog();
//        queryAccessLog();
//        deleteAccessLog();
//
//        addStandardLog();
//        queryStandardLog();
//        deleteStandardLog();


    }
    private static void addAccessLog(){
        //新增
        String name="courseservice";
        String url="/application/api/v2/class? HTTP/1.1";
        String method="GET";
        String dateTime="04/Nov/2017:11:43:54";
        int status=200;
        String ip="123.207.73.150";
        String facility="Apache-HttpClient/4.5.2 (Java/1.8.0_151)";
        AccessLog accessLog =new AccessLog();
        accessLog.setHost(ip);
        accessLog.setName(name);
        accessLog.setMethod(method);
        accessLog.setUrl(url);
        accessLog.setStatus(status);
        accessLog.setFacility(facility);
        accessLog.setTimestamp(dateTime);
        MongoService.getAccessLogCollection().add(accessLog);
    }
    private static void queryAccessLog(){
        //        查询
        System.out.println(MongoService.getAccessLogCollection().queryByParam(
                "courseservice",
                "123.207.73.150",
                null,
                "Apache-HttpClient/4.5.2 (Java/1.8.0_151)",
                null,
                null
        ));
    }
    private static void deleteAccessLog(){
        //删除
//        MongoService.getAccessLogCollection().delete("5a0187ffda72a61efc254fa2");
        MongoService.getAccessLogCollection().delete("5a07bed8ae678f0c9bd91339");
    }
    private static void addStandardLog(){
        String className="io.dropwizard.server.SimpleServerFactory";
        String dateTime="04/Nov/2017:11:43:54";
        int level=6;
        String data="Registering admin handler with root path prefix: /admin";
        StandardLog standardLog =new StandardLog();
        standardLog.setLevel(level);
        standardLog.setData(data);
        standardLog.setClassName(className);
        standardLog.setTimestamp(dateTime);
        MongoService.getStandardLogCollection().add(standardLog);
    }
    private static void queryStandardLog(){
        System.out.println(MongoService.getStandardLogCollection().queryByParam(
                null,
                null,
                "04/Nov/2017:11:43:54",
                null
        ));
    }
    private static void deleteStandardLog(){

        MongoService.getStandardLogCollection().delete("5a0722d2a9eba32a28da1d70");
    }
}
