import mongodb.MongoService;
import orm.Log;

/**
 * Created by snow on 11/11/2017.
 */
public class DBTest {
    public static void main(String[] args) {
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

        System.out.println(log.getData());
    }
}


/*
开发流程：

（1）【周一】设计、完善api
（2）【周二】postman插件，测试syslog服务，先搞清楚现在它的流程（TCP／UDP协议等等）
（3）【周二、周三】logmgmt服务里面，开启syslog服务（可以用app的构造函数来实现）
（4）【周三、周四】增：通过syslog的端口port2
    先写syslog服务
（5）【周五、周六】查、删：通过logmgmt的端口port1
    再写本来的服务

 */