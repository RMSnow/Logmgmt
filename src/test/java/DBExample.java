/**
 * Created by WYJ on 2017/11/7.
 */

import mongodb.MongoService;
import orm.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBExample {
    public static void main(String[] args) {
        //新增
//        String data="test data";
//        String ip=null;
//        Log log=new Log();
//        log.setData(data);
//        log.setIp(ip);
//        MongoService.getLogCollection().add(log);

        //查询
//        System.out.println(MongoService.getLogCollection().queryByParam(
//                null,
//                "127.0.0.1",
//                null,
//                null,
//                null
//        ));

        //删除，还没做好
//        MongoService.getLogCollection().delete("5a016854da72a61864f72d90");
        try {

            String originDate="04/Nov/2017:11:43:54";
            SimpleDateFormat originFormat=new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss", Locale.ENGLISH);
            SimpleDateFormat newFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Date d=originFormat.parse(originDate);
            String newDate=newFormat.format(d);
            System.out.println(newDate);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
