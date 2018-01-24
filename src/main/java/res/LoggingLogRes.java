package res;

import com.codahale.metrics.annotation.Timed;
import mongodb.MongoResult;
import entity.Result;
import entity.Status;
import mongodb.MongoService;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 运行日志资源（主要为错误信息）
 */
@Path("/api/v1/loggings")
@Produces(MediaType.APPLICATION_JSON)
public class LoggingLogRes {

    @GET
    @Timed
    public Result queryLoggings(@NotEmpty @QueryParam("serviceName") String serviceName,
                                @QueryParam("fromId") String fromId,
                                @QueryParam("level") String level,
                                @QueryParam("host") String host,
                                @QueryParam("fromTimeStamp") String fromTimeStamp,
                                @QueryParam("toTimeStamp") String toTimeStamp,
                                @QueryParam("errDetails") String queryOrNot,
                                @QueryParam("limit") String limit) {

        MongoResult result = MongoService.getLoggingLogCollection().queryByParam(serviceName,
                fromId, level, host, fromTimeStamp, toTimeStamp, queryOrNot, limit);

        if (result.getResultNum() == 0) {
            return new Result("NOT FOUND", Status.NOT_FOUND, "");
        }

        if (limit != null) {
            return new Result(result.getResultNum() + " results.", Status.OK, result.getResults());
        } else {
            return new Result("Default quantity of results is " + result.getResultNum() + ". " +
                    "You can set it with the parameter \'limit\'.",
                    Status.OK, result.getResults());
        }
    }

    @DELETE
    @Timed
    public Result deleteLoggings(@NotEmpty @QueryParam("serviceName") String serviceName,
                                 @QueryParam("level") String level,
                                 @QueryParam("host") String host,
                                 @QueryParam("fromTimeStamp") String fromTimeStamp,
                                 @QueryParam("toTimeStamp") String toTimeStamp) {

        //把delete的返回值也改为MongoQueryResult

        MongoResult result = MongoService.getLoggingLogCollection().deleteByParam(serviceName,
                level, host, fromTimeStamp, toTimeStamp);

        if (result.getResultNum() == 0) {
            return new Result("NOT FOUND", Status.NOT_FOUND, "");
        }

        return new Result(result.getResultNum() + " results has been deleted.", Status.OK, "");
    }
}
