package res;

import com.codahale.metrics.annotation.Timed;
import entity.MongoResult;
import entity.Result;
import entity.Status;
import mongodb.MongoService;
import org.hibernate.validator.constraints.NotEmpty;
import orm.ClientLog;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 应用层（客户端）日志
 */
@Path("/api/v1/clients")
@Produces(MediaType.APPLICATION_JSON)
public class ClientLogRes {
    //    id
//    timestamp
//
//    ip              String  [访问客户端ip]
//    userId          String  [操作用户id]
//    responseTime    long    [响应时间]
//    api             String  [调用api及其参数]
//    status          String  [返回状态码]
//    error           String  [错误]
//    msg             String  [消息]

    @POST
    @Timed
    public Result addClientLog(@NotEmpty @QueryParam("ip") String ip,
                               @NotEmpty @QueryParam("userId") String userId,
                               @NotEmpty @QueryParam("responseTime") String responseTime,
                               @NotEmpty @QueryParam("api") String api,
                               @NotEmpty @QueryParam("status") String status,
                               @NotEmpty @QueryParam("error") String error,
                               @NotEmpty @QueryParam("msg") String msg) {

        MongoResult result = MongoService.getClientLogCollection().add(
                new ClientLog(ip, userId, responseTime, api, status, error, msg));

        if (result != null) {
            return new Result("The log have been added.", Status.OK, result.getResults());
        } else {
            System.err.println("Errors in inserting the log.");
            return new Result("Errors in inserting the log.", Status.SERVER_ERROR, "");
        }
    }

    @GET
    @Timed
    public Result queryLogs(@QueryParam("fromTimestamp") String fromTimestamp,
                            @QueryParam("toTimestamp") String toTimestamp,
                            @QueryParam("ip") String ip,
                            @QueryParam("userId") String userId,
                            @QueryParam("api") String api,
                            @QueryParam("status") String status,
                            @QueryParam("limit") String limit) {

        MongoResult result = MongoService.getClientLogCollection().
                queryByParam(fromTimestamp, toTimestamp, ip, userId, api, status, limit);

        if (result != null) {
            if (result.getResultNum() == 0) {
                return new Result("None Logs", Status.NOT_FOUND, "");
            }

            if (limit != null) {
                return new Result(result.getResultNum() + " results.", Status.OK, result.getResults());
            } else {
                return new Result("Default quantity of results is " + result.getResultNum() + ". " +
                        "You can set it with the parameter \'limit\'.",
                        Status.OK, result.getResults());
            }
        } else {
            System.err.println("Errors in querying logs.");
            return new Result("Errors in querying logs.", Status.SERVER_ERROR, "");
        }
    }

}
