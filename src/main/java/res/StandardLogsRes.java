package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 错误信息资源
 */
@Path("/api/v1/logs/errors")
@Produces(MediaType.APPLICATION_JSON)
public class StandardLogsRes {
    public StandardLogsRes(){

    }

    @POST
    @Timed
    //创建一条新日志：需提供服务名称与日志内容
    public Result createLog(@NotEmpty @QueryParam("name") String name,
                            @NotEmpty @QueryParam("content") String content){
        return null;
    }

    @GET
    @Timed
    //查询日志：根据限制条件查询日志列表
    public Result queryLogs(@NotEmpty @QueryParam("name") String name,
                            @QueryParam("ip") String ip,
                            @QueryParam("dateTime") String datetime,
                            @QueryParam("client") String client){
        return null;
    }

    @DELETE
    @Timed
    //删除日志：根据限制条件删除日志
    public Result deleteLogs(@NotEmpty @QueryParam("name") String name,
                             @QueryParam("ip") String ip,
                             @QueryParam("dateTime") String datetime,
                             @QueryParam("client") String client){
        return null;
    }
}
