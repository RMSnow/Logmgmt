import orm.record.RequestsOfScale;
import mongodb.MongoConnector;
import mongodb.MongoService;
import orm.record.Record;

import java.util.Date;

/**
 * Created by snow on 11/11/2017.
 */
public class DBTest {
    public static void main(String[] args) {
        MongoConnector.host = "localhost";
        MongoConnector.port = 27017;
        MongoConnector.userName = "wyj";
        MongoConnector.password = "123456";
        MongoConnector.init();


    }

    public void basicDB(){
        for (int i = 0; i < 5; i++) {
            Record record = new Record("recordTest");
            record.setTimestamp(new Date().toString());

            record.putApiRequestTable("test_" + i + "_1", i);
            record.putApiRequestTable("test_" + i + "_2", i);
            record.putApiRequestTable("test_" + i + "_3", i);

            record.setLoggingErrors(i * 5);
            record.setRequestExceptions(i * 10);
            record.setHourRequests(i * 1000);

            for (int j = 0; j < 12; j++) {
                RequestsOfScale rate = new RequestsOfScale(new Date().toString() + "_" + i + "_" + j,
                        record.getHourRequests() / 12);
                //System.out.println(rate);
                record.setSecondRequestsRate(j, rate);
            }

            MongoService.getRecordCollection().add(record);

            System.out.println("successfully insert Record " + i);
        }

        System.out.println("----------");
        System.out.println("All Record...");
        MongoService.getRecordCollection().queryAll(null);
        System.out.println("----------");
        System.out.println("Query By Param \"DockTest\"...");
//        MongoService.getRecordCollection().queryByParam("DockTest");
        System.out.println("----------");
        System.out.println("Query By Param \"recordTest\"...");
//        MongoResult result = MongoService.getRecordCollection().queryByParam("recordTest");
//        System.out.println(result.getResultNum() + " results");
    }
}

