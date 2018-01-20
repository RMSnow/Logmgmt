package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.MongoResult;
import entity.Result;
import entity.Status;
import mongodb.MongoConnector;
import mongodb.MongoService;
import res.RecordRes;
import syslog.Server;
import syslog.SyslogEvent;
import syslog.SyslogService;

import static entity.ConfInfo.serviceName;
import static java.lang.Thread.sleep;


/**
 * Created by snow on 11/11/2017.
 */
public class serviceTest {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        MongoConnector.host = "localhost";
        MongoConnector.port = 27017;
        MongoConnector.userName = "wyj";
        MongoConnector.password = "123456";
        MongoConnector.init();

        new Server();

        add();
        //queryRecent();
    }

    public static void add() throws InterruptedException, JsonProcessingException {
        //jackson
        ObjectMapper mapper = new ObjectMapper();
        String json;

        MongoResult result;

        for (int j = 0; j < 2; j++) {
            //第二次不访问，检测是否清空静态变量
            for (int i = 0; i < 2; i++) {
                //访问courseservice
                System.out.println("10秒内访问courseservice...");
                sleep(5000);
                System.out.println("10秒结束...");

                //计算rates
                SyslogEvent.addSecondRequestsRate();
            }

            //增加：将record存入数据库
            result = MongoService.getRecordCollection()
                    .addAll(SyslogEvent.getServiceRecords());

            if (result.getResultNum() == 0) {
                json = mapper.writeValueAsString(
                        new Result("None Records", Status.NOT_FOUND, ""));
                System.out.println(json);
            }

            json = mapper.writeValueAsString(
                    new Result(result.getResultNum() + " records have been added.", Status.OK, result.getResults())
            );
            System.out.println(json);
        }

        //查询
        result = MongoService.getRecordCollection().queryAll(null);

        if (result.getResultNum() == 0) {
            json = mapper.writeValueAsString(
                    new Result("None Records", Status.NOT_FOUND, "")
            );
            System.out.println(json);
        }

        json = mapper.writeValueAsString(
                new Result(result.getResultNum() + " results.", Status.OK, result.getResults())
        );
        System.out.println(json);
    }

    public static void queryRecent() throws JsonProcessingException {
        //jackson
        ObjectMapper mapper = new ObjectMapper();
        String json;

        Result result = new RecordRes().queryDailyRecords("test");
        json = mapper.writeValueAsString(result);
        System.out.println(json);
    }

}