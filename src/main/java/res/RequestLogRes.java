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
 * 访问日志资源
 */
@Path("/api/v1/requests")
@Produces(MediaType.APPLICATION_JSON)
public class RequestLogRes {
    @GET
    @Timed
    public Result queryRequests(@NotEmpty @QueryParam("serviceName") String serviceName,
                            @QueryParam("host") String host,
                            @QueryParam("fromDateTime") String fromDateTime,
                            @QueryParam("toDateTime") String toDateTime,
                            @QueryParam("method") String method,
                            @QueryParam("status") String status) {

//        String data = MongoService.getRequestLogCollection().queryByParam(serviceName,
//                host,fromDateTime,toDateTime,method,status,null);
        MongoQueryResult data = MongoService.getRequestLogCollection().queryByParam(serviceName,
        host,fromDateTime,toDateTime,method,status,null);
        if (data == null) {
            return new Result("NOT FOUND", Status.NOT_FOUND, "");
        }

        /*
            注：(1)可添加一个查询到的日志数量sum
                (2)标注一下参数fromTimeStamp和toTimeStamp的格式，之后我把写到api文档上
         */

        return new Result("[SUM] outcome", Status.OK, data);

    }

    @DELETE
    @Timed
    public Result deleteRequests(@NotEmpty @QueryParam("serviceName") String serviceName,
                                 @QueryParam("host") String host,
                                 @QueryParam("fromDateTime") String fromDateTime,
                                 @QueryParam("toDateTime") String toDateTime,
                                 @QueryParam("method") String method,
                                 @QueryParam("status") String status) {

        /*
            缺少获得ID的方法
         */

        return new Result("", Status.OK, "");
    }
}


//mongodb
//mongod --config /usr/local/etc/mongod.conf