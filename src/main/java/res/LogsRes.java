package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;

import org.hibernate.validator.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 普通日志资源
 */
@Path("/api/v1/logs")
@Produces(MediaType.APPLICATION_JSON)
public class LogsRes {

    public LogsRes(){

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

/*
（1）定义LogsRes、ErrorsRes中的所有方法 ok
（2）调试DAO层
（3）实现一个，并用工具／插件调试
（4）实现其他的
 */