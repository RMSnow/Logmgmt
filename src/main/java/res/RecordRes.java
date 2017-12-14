package res;

import com.codahale.metrics.annotation.Timed;
import entity.DailyRecord;
import entity.MongoResult;
import entity.Result;
import entity.Status;
import mongodb.MongoService;
import org.hibernate.validator.constraints.NotEmpty;
import orm.Record;
import syslog.SyslogService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * 日志分析记录资源
 */
@Path("/api/v1/records")
@Produces(MediaType.APPLICATION_JSON)
public class RecordRes {
    @POST
    @Timed
    /**
     * 每小时需要被定时服务请求一次，以将记录存入数据库
     */
    public Result addRecords() {
        MongoResult result = MongoService.getRecordCollection()
                .addAll(SyslogService.getServiceRecords());

        if (result.getResultNum() == 0) {
            return new Result("None Records", Status.NOT_FOUND, "");
        }

        return new Result(result.getResultNum() + " records have been added.", Status.OK, result.getResults());
    }

    @Path("/rates")
    @POST
    @Timed
    /**
     * 每5分钟需要被定时服务请求一次，以计算近5分钟的每秒访问次数
     */
    public Result calculateRequestsRate() {
        int recordSize = SyslogService.addSecondRequestsRate();
        return new Result("Successfully calculate rates for " + recordSize + " services.", Status.OK, "");
    }

    @Path("/analysis")
    @GET
    @Timed
    public Result queryDailyRecords(@NotEmpty @QueryParam("serviceName") String serviceName) {

        //TODO: res & docs

//        日志分析接口：传服务名，返回分析结果，结果要包括
//
//        新建一个集合，（按小时存储以下），按天返回结果
//        # 该服务最常被调用的api
//        # 以及数量
//        # 服务错误数——Logging
//        # 不正常返回（非200）数——RequestLog
//        # 最近30天每天服务访问量
//
//        # 最近5分钟每秒请求数

        ArrayList<Record> records = MongoService.getRecordCollection().getDailyRecords(serviceName);
        if (records.size() == 0) {
            return new Result("None Records", Status.NOT_FOUND, "");
        }

        DailyRecord dailyRecord = new DailyRecord(serviceName, records);
        //最近30天每天服务访问量
        dailyRecord.setRecentDaysRequests(MongoService.getRecordCollection().
                getRecentRecords(serviceName));
        return new Result("The recent analysis of " + serviceName, Status.OK, dailyRecord.getResultTable());
    }

//    @Path("/analysis")
//    @GET
//    @Timed
//    public Result queryDailyRecords(@NotEmpty @QueryParam("serviceName") String serviceName,
//                                    @QueryParam("fromDatetime") String fromDateTime,
//                                    @QueryParam("toDatetime") String toDateTime) {
//
//        return null;
//    }

    @GET
    @Timed
    public Result queryAllRecords() {
        MongoResult result = MongoService.getRecordCollection().queryAll();

        if (result.getResultNum() == 0) {
            return new Result("None Records", Status.NOT_FOUND, "");
        }

        return new Result(result.getResultNum() + " results.", Status.OK, result.getResults());
    }

}
