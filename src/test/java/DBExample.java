/**
 * Created by WYJ on 2017/11/7.
 */

import mongodb.MongoService;
import orm.Log;

public class DBExample {
    public static void main(String[] args) {
        //新增
        String data="test data";
        String ip=null;
        Log log=new Log();
        log.setData(data);
        log.setIp(ip);
        MongoService.getLogCollection().add(log);

        //查询
        System.out.println(MongoService.getLogCollection().queryAll());

        //删除，还没做好
        MongoService.getLogCollection().delete("5a016854da72a61864f72d90");
    }
}
