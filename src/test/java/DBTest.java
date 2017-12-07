import entity.ConfInfo;
import entity.MongoResult;
import entity.RequestsRate;
import mongodb.MongoConnector;
import mongodb.MongoService;
import orm.Record;

import java.util.Date;

/**
 * Created by snow on 11/11/2017.
 */
public class DBTest {
    public static void main(String[] args) {
//        @JsonProperty
//        private String id;
//        @JsonProperty
//        private String serviceName;
//        @JsonProperty
//        private String timestamp;
//        @JsonProperty
//        private Hashtable<String, Integer> apiRequestTable = new Hashtable<>();
//        @JsonProperty
//        private Integer loggingErrors;
//        @JsonProperty
//        private Integer requestExceptions;
//        @JsonProperty
//        private Integer hourRequests;
//        @JsonProperty
//        private RequestsRate[] secondRequestsRate = new RequestsRate[12];

//        mongodbPort: 27017
//        mongodbHost: localhost
//        mongodbUserName: wyj
//        mongodbPassword: 123456

        MongoConnector.host = "localhost";
        MongoConnector.port = 27017;
        MongoConnector.userName = "wyj";
        MongoConnector.password = "123456";
        MongoConnector.init();

        for (int i = 0; i < 5; i++) {
            Record record = new Record();
            record.setServiceName("recordTest");
            record.setTimestamp(new Date().toString());

            record.putApiRequestTable("test_" + i + "_1", i);
            record.putApiRequestTable("test_" + i + "_2", i);
            record.putApiRequestTable("test_" + i + "_3", i);

            record.setLoggingErrors(i * 5);
            record.setRequestExceptions(i * 10);
            record.setHourRequests(i * 1000);

            for (int j = 0; j < 12; j++) {
                record.setSecondRequestsRate(j, new RequestsRate(new Date().toString(),
                        record.getHourRequests() / 12));
            }

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

