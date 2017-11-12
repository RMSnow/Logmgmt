/**
 * Created by WYJ on 2017/11/7.
 */

import mongodb.MongoService;
import orm.ErrorLog;
import orm.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBExample {
    public static void main(String[] args) {
//        addLog();
//        queryLog();
//        deleteLog();

//        addErrorLog();
//        queryErrorLog();
//        deleteErrorLog();
    }
    private static void addLog(){
        //新增
        String data="test data";
        String name="courseservice";
        String url="GET /application/api/v2/class? HTTP/1.1";
        String dateTime="04/Nov/2017:11:43:54";
        int status=200;
        String ip="123.207.73.150";
        String client="Apache-HttpClient/4.5.2 (Java/1.8.0_151)";
        Log log=new Log();
        log.setData(data);
        log.setIp(ip);
        log.setName(name);
        log.setUrl(url);
        log.setStatus(status);
        log.setClient(client);
        log.setDateTime(dateTime);
        MongoService.getLogCollection().add(log);
    }
    private static void queryLog(){
        //        查询
        System.out.println(MongoService.getLogCollection().queryByParam(
                null,
                null,
                "04/Nov/2017:11:43:54",
                null,
                null
        ));
    }
    private static void deleteLog(){
        //删除
//        MongoService.getLogCollection().delete("5a0187ffda72a61efc254fa2");
        MongoService.getLogCollection().delete("5a07bed8ae678f0c9bd91339");
    }
    private static void addErrorLog(){
        String name="courseservice";
        String url="GET /application/api/v2/class? HTTP/1.1";
        String dateTime="04/Nov/2017:11:43:54";
        String ip="123.207.73.150";
        String data="Exception in thread main java.lang.ArithmeticException: / by zero ";
        ErrorLog errorLog=new ErrorLog();
        errorLog.setData(data);
        errorLog.setIp(ip);
        errorLog.setName(name);
        errorLog.setUrl(url);
        errorLog.setDateTime(dateTime);
        MongoService.getErrorLogCollection().add(errorLog);
    }
    private static void queryErrorLog(){
        System.out.println(MongoService.getErrorLogCollection().queryByParam(
                null,
                null,
                "04/Nov/2017:10:43:54",
                null
        ));
    }
    private static void deleteErrorLog(){

        MongoService.getErrorLogCollection().delete("5a0722d2a9eba32a28da1d70");
    }
}
