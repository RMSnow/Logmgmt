import mongodb.MongoService;
import orm.Log;

/**
 * Created by snow on 11/11/2017.
 */
public class DBTest {
    public static void main(String[] args) {
        //新增
        String data = "test data";
        String ip = null;
        Log log = new Log();
        log.setData(data);
        log.setIp(ip);
        MongoService.getLogCollection().add(log);

        //查询
        System.out.println(MongoService.getLogCollection().queryByParam(
                null,
                "127.0.0.1",
                null,
                null,
                null
        ));

//        //删除，还没做好
//        MongoService.getLogCollection().delete("5a016854da72a61864f72d90");
    }
}
