package res;

import com.codahale.metrics.annotation.Timed;
import entity.MongoResult;
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
                                @QueryParam("fromId") String fromId,
                                @QueryParam("host") String host,
                                @QueryParam("fromDateTime") String fromDateTime,
                                @QueryParam("toDateTime") String toDateTime,
                                @QueryParam("method") String method,
                                @QueryParam("status") String status,
                                @QueryParam("limit") String limit) {

        MongoResult result = MongoService.getRequestLogCollection().queryByParam(serviceName,
                fromId, host, fromDateTime, toDateTime, method, status, limit);

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
    public Result deleteRequests(@NotEmpty @QueryParam("serviceName") String serviceName,
                                 @QueryParam("host") String host,
                                 @QueryParam("fromDateTime") String fromDateTime,
                                 @QueryParam("toDateTime") String toDateTime,
                                 @QueryParam("method") String method,
                                 @QueryParam("status") String status) {

        MongoResult result = MongoService.getRequestLogCollection().deleteByParam(serviceName,
                host, fromDateTime, toDateTime, method, status, null);

        if (result.getResultNum() == 0) {
            return new Result("NOT FOUND", Status.NOT_FOUND, "");
        }

        return new Result(result.getResultNum() + " results has been deleted.", Status.OK, "");
    }
}