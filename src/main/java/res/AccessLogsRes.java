package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import entity.Status;
import mongodb.dao.AccessLogDao;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 普通日志资源
 */
@Path("/api/v1/logs")
@Produces(MediaType.APPLICATION_JSON)
public class AccessLogsRes {
    private AccessLogDao accessLogDao;

    public AccessLogsRes() {
        accessLogDao = new AccessLogDao();
    }

    @POST
    @Timed
    //创建一条新日志：需提供服务名称与日志内容
    public Result createLog(@NotEmpty @QueryParam("name") String name,
                            @NotEmpty @QueryParam("content") String content) {

        //AccessLog accessLog = ContentParser.getAttrFromContent(name, content);

        /*
        (1)处理后的
        <134>Nov 15 16:40:39 snow.local couseservice[1039]: [main] org.eclipse.jetty.server.handler.ContextHandler Started i.d.j.MutableServletContextHandler@d5ce97f{/application,null,AVAILABLE}

        (2)原本的
        INFO  [2017-11-04 11:43:42,343] org.eclipse.jetty.util.log: Logging initialized @1835ms
        INFO  [2017-11-04 11:43:42,474] io.dropwizard.server.ServerFactory: Starting couseservice
        INFO  [2017-11-04 11:43:42,481] io.dropwizard.server.SimpleServerFactory: Registering jersey handler with root path prefix: /application
        INFO  [2017-11-04 11:43:42,495] io.dropwizard.server.SimpleServerFactory: Registering admin handler with root path prefix: /admin
        INFO  [2017-11-04 11:43:42,556] org.eclipse.jetty.setuid.SetUIDListener: Opened couseservice@6e521c1e{HTTP/1.1}{0.0.0.0:7000}
         */

        //accessLogDao.add(accessLog);

        //创建成功时：
        return new Result("CREATED", Status.CREATED, "");
    }

    @GET
    @Timed
    //查询日志：根据限制条件查询日志列表
    public Result queryLogs(@NotEmpty @QueryParam("name") String name,
                            @QueryParam("ip") String ip,
                            @QueryParam("fromDateTime") String fromDateTime,
                            @QueryParam("toDateTime") String toDateTime,
                            @QueryParam("client") String client) {

        String data = accessLogDao.queryByParam(name, ip,null, client, fromDateTime, toDateTime);
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

        //----------------------------待完善----------------------------
        //accessLogDao.delete();
        //-------------------------------------------------------------


        //删除成功时：
        return new Result("", Status.NO_CONTENT, "");
    }
}


/*
（1）定义LogsRes、ErrorsRes中的所有方法 ok
（2）调试DAO层 ok
（3）实现一个，并用工具／插件调试
（4）实现其他的
 */


//mongodb
//mongod --config /usr/local/etc/mongod.conf