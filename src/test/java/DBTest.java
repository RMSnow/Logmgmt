import orm.AccessLog;

/**
 * Created by snow on 11/11/2017.
 */
public class DBTest {
    public static void main(String[] args) {
        String name="courseservice";
        String url="GET /application/api/v2/class? HTTP/1.1";
        String dateTime="04/Nov/2017:11:43:54";
        int status=200;
        String ip="123.207.73.150";
        String client="Apache-HttpClient/4.5.2 (Java/1.8.0_151)";
        AccessLog accessLog =new AccessLog();
        accessLog.setHost(ip);
        accessLog.setName(name);
        accessLog.setUrl(url);
        accessLog.setStatus(status);
        accessLog.setClient(client);
        accessLog.setDatetime(dateTime);

    }
}

