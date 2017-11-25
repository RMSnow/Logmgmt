package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
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
                            @QueryParam("errDetails") String queryOrNot){
        /*
            注：(1)参数queryOrNot的值为0或1，当值为0时，则不显示errDetails的信息；
               当值为1时，显示errDetails的信息。其默认值为0。
               (2)标注一下参数fromTimeStamp和toTimeStamp的格式，之后我把写到api文档上
               (3)可添加一个查询到的日志数量sum
         */

        return null;
    }

    @DELETE
    @Timed
    public Result deleteLoggings(@NotEmpty @QueryParam("serviceName") String serviceName,
                                 @QueryParam("level") String level,
                                 @QueryParam("host") String host,
                                 @QueryParam("fromTimeStamp") String fromTimeStamp,
                                 @QueryParam("toTimeStamp") String toTimeStamp){
        /*
            注：缺少获得ID的方法
         */

        return null;
    }
}
