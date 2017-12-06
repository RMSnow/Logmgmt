import entity.MongoResult;
import mongodb.MongoConnector;
import mongodb.MongoService;
import orm.Record;

/**
 * Created by snow on 11/11/2017.
 */
public class DBTest {
    public static void main(String[] args) {
//        String name = "courseservice";
//        String url = "GET /application/api/v2/class? HTTP/1.1";
//        String dateTime = "04/Nov/2017:11:43:54";
//        int status = 200;
//        String ip = "123.207.73.150";
//        String client = "Apache-HttpClient/4.5.2 (Java/1.8.0_151)";
//        RequestLog requestLog = new RequestLog();
//        requestLog.setHost(ip);
//        requestLog.setServiceName(name);
//        requestLog.setUrl(url);
//        requestLog.setStatus(status);
//        requestLog.setClient(client);
//        requestLog.setDatetime(dateTime);

//        private String id;
//        private String serviceName;
//
//        private String mostURI;
//        private Integer mostRequests;
//
//        private Integer loggingErrors;
//        private Integer requestExceptions;
//        private Integer hourRequests;
//        private Double hourRequestsRate;

        MongoConnector.init();

        for (int i = 0; i < 5; i++) {
            Record record = new Record();
            record.setServiceName("recordTest");
            record.setMostURI("GET /application/api/v2/class");
            record.setMostRequests(i * 100);
            record.setLoggingErrors(i * 5);
            record.setRequestExceptions(i * 10);
            record.setHourRequests(i * 1000);
            record.setSecondRequestsRate((double) (record.getHourRequests() / 12));
            MongoService.getRecordCollection().add(record);
            System.out.println("successfully insert Record " + i);
        }

        System.out.println("----------");
        System.out.println("All Record...");
        MongoService.getRecordCollection().queryAll();
        System.out.println("----------");
        System.out.println("Query By Param \"Test\"...");
        MongoService.getRecordCollection().queryByParam("Test");
        System.out.println("----------");
        System.out.println("Query By Param \"recordTest\"...");
        MongoResult result = MongoService.getRecordCollection().queryByParam("recordTest");
        System.out.println(result.getResultNum() + " results");

    }
}

