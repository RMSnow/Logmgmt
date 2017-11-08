package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;

import javax.validation.constraints.NotEmpty;
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
    public String test(@QueryParam("name") String name){
        return "hello" + name;
    }
}
