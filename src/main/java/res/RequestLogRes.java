package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import entity.Status;
import mongodb.dao.RequestLogDao;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 访问日志资源
 */
@Path("/api/v1/logs")
@Produces(MediaType.APPLICATION_JSON)
public class RequestLogRes {
    private RequestLogDao requestLogDao;

    public RequestLogRes() {
        requestLogDao = new RequestLogDao();
    }

    @GET
    @Timed
    //查询日志：根据限制条件查询日志列表
    public Result queryLogs(@NotEmpty @QueryParam("name") String name,
                            @QueryParam("ip") String ip,
                            @QueryParam("fromDateTime") String fromDateTime,
                            @QueryParam("toDateTime") String toDateTime,
                            @QueryParam("client") String client) {

        String data = requestLogDao.queryByParam(name, ip,null, client, fromDateTime, toDateTime);
        if (data == null) {
            return new Result("NOT FOUND", Status.NOT_FOUND, "");
        }

        //----------后期：添加查询到的日志数量（参见courserservice中的Page），data的返回类型----------
        return new Result("", Status.OK, data);
    }

    @DELETE
    @Timed
    //删除日志：根据限制条件删除日志
    public Result deleteLogs(@NotEmpty @QueryParam("name") String name,
                             @QueryParam("ip") String ip,
                             @QueryParam("dateTime") String datetime,
                             @QueryParam("client") String client) {

        //删除成功时：
        return new Result("", Status.NO_CONTENT, "");
    }
}


//mongodb
//mongod --config /usr/local/etc/mongod.conf