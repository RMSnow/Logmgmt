package res;

import com.codahale.metrics.annotation.Timed;
import entity.MongoQueryResult;
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
                                @QueryParam("level") String level,
                                @QueryParam("host") String host,
                                @QueryParam("fromTimeStamp") String fromTimeStamp,
                                @QueryParam("toTimeStamp") String toTimeStamp,
                                @QueryParam("errDetails") String queryOrNot,
                                @QueryParam("limit") String limit) {
        /*
            注：(1)参数queryOrNot的值为0或1，当值为0时，则不显示errDetails的信息；
               当值为1时，显示errDetails的信息。其默认值为0。
               (2)标注一下参数fromTimeStamp和toTimeStamp的格式，之后我把写到api文档上
               (3)可添加一个查询到的日志数量sum
         */

        //queryOrNot

        MongoQueryResult result = MongoService.getLoggingLogCollection().queryByParam(serviceName,
                level, host, fromTimeStamp, toTimeStamp, queryOrNot, limit);

        if (result.getResultNum() == 0) {
            return new Result("NOT FOUND", Status.NOT_FOUND, "");
        }

        return new Result(result.getResultNum() + " results.", Status.OK, result.getJsonResult());
    }

    @DELETE
    @Timed
    public Result deleteLoggings(@NotEmpty @QueryParam("serviceName") String serviceName,
                                 @QueryParam("level") String level,
                                 @QueryParam("host") String host,
                                 @QueryParam("fromTimeStamp") String fromTimeStamp,
                                 @QueryParam("toTimeStamp") String toTimeStamp) {

        //把delete的返回值也改为MongoQueryResult

        MongoService.getLoggingLogCollection().deleteByParam(serviceName,
                level, host, fromTimeStamp, toTimeStamp);

        return new Result("", Status.OK, "");
    }
}
