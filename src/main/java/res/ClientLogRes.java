package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 应用层（客户端）日志
 */
@Path("/api/v1/clients")
@Produces(MediaType.APPLICATION_JSON)
public class ClientLogRes {
    //    id
//    timestamp
//
//    ip              String  [访问客户端ip]
//    userId          String  [操作用户id]
//    responseTime    long    [响应时间]
//    api             String  [调用api及其参数]
//    status          String  [返回状态码]
//    error           String  [错误]
//    msg             String  [消息]

    @POST
    @Timed
    public Result addClientLog(@NotEmpty @QueryParam("ip") String ip,
                               @NotEmpty @QueryParam("userId") String userId,
                               @NotEmpty @QueryParam("responseTime") String responseTime,
                               @NotEmpty @QueryParam("api") String api,
                               @NotEmpty @QueryParam("status") String status,
                               @NotEmpty @QueryParam("error") String error,
                               @NotEmpty @QueryParam("msg") String msg){
        return null;
    }

    @GET
    @Timed
    public Result queryLogs(@QueryParam("fromTimestamp") String fromTimestamp,
                            @QueryParam("toTimestamp") String toTimestamp,
                            @QueryParam("ip") String ip,
                            @QueryParam("userId") String userId,
                            @QueryParam("api") String api,
                            @QueryParam("status") String status,
                            @QueryParam("limit") String limit){
        return null;
    }

}
