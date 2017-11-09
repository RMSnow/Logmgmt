package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * 错误信息资源
 */
@Path("/api/v1/logs/errors")
@Produces(MediaType.APPLICATION_JSON)
public class ErrorsRes {
    public ErrorsRes(){

    }

    @POST
    @Timed
    //创建一条新日志：需提供服务名称与日志内容
    public Result createLog(@NotEmpty @QueryParam("name") String name,
                            @NotEmpty @QueryParam("content") String content){
        return null;
    }
}
