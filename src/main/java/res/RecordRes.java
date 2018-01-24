package res;

import com.codahale.metrics.annotation.Timed;
import orm.record.DailyRecord;
import mongodb.MongoResult;
import entity.Result;
import entity.Status;
import mongodb.MongoService;
import org.hibernate.validator.constraints.NotEmpty;
import orm.record.Record;
import syslog.SyslogEvent;

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
     * 每小时需要被定时服务请求一次，以将记录存入数据库（需线程安全）
     */
    public Result addRecords() {
        MongoResult result = MongoService.getRecordCollection()
                .addAll(SyslogEvent.getServiceRecords());

        if (result != null) {
            if (result.getResultNum() == 0) {
                return new Result("None Records", Status.NOT_FOUND, "");
            }

            return new Result(result.getResultNum() + " records have been added.", Status.OK, result.getResults());
        } else {
            System.err.println("Errors in inserting records.");
            return new Result("Errors in inserting records.", Status.SERVER_ERROR, "");
        }
    }

    @Path("/rates")
    @POST
    @Timed
    /**
     * 每5分钟需要被定时服务请求一次，以计算近5分钟的每秒访问次数（需线程安全）
     */
    public Result calculateRequestsRate() {
        int recordSize = SyslogEvent.addSecondRequestsRate();
        return new Result("Successfully calculate rates for " + recordSize + " services.", Status.OK, "");
    }

    @Path("/analysis")
    @GET
    @Timed
    public Result queryDailyRecords(@NotEmpty @QueryParam("serviceName") String serviceName) {
        ArrayList<Record> records = MongoService.getRecordCollection().getDailyRecords(serviceName);
        if (records != null) {
            if (records.size() == 0) {
                return new Result("None Records", Status.NOT_FOUND, "");
            }

            DailyRecord dailyRecord = new DailyRecord(serviceName, records);
            return new Result("The daily analysis of " + serviceName, Status.OK, dailyRecord.getResultTable());
        } else {
            return new Result("Errors of Server.", Status.SERVER_ERROR, "");
        }
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
    public Result queryAllRecords(@QueryParam("limit") String limit) {
        MongoResult result = MongoService.getRecordCollection().queryAll(limit);
        if (result != null) {
            if (result.getResultNum() == 0) {
                return new Result("None Records", Status.NOT_FOUND, "");
            }

            if (limit != null) {
                return new Result(result.getResultNum() + " results.", Status.OK, result.getResults());
            } else {
                return new Result("Default quantity of results is " + result.getResultNum() + ". " +
                        "You can set it with the parameter \'limit\'.",
                        Status.OK, result.getResults());
            }
        } else {
            return new Result("Errors of Server.", Status.SERVER_ERROR, "");
        }
    }

}
