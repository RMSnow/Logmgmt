package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import entity.Status;
import mongodb.dao.LogDao;
import org.hibernate.validator.constraints.NotEmpty;
import orm.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 普通日志资源
 */
@Path("/api/v1/logs")
@Produces(MediaType.APPLICATION_JSON)
public class LogsRes {
    private LogDao logDao;

    public LogsRes() {
        logDao = new LogDao();
    }

    @POST
    @Timed
    //创建一条新日志：需提供服务名称与日志内容
    public Result createLog(@NotEmpty @QueryParam("name") String name,
                            @NotEmpty @QueryParam("content") String content) {

        //-----------解析Log的内容（content）是不是放在DAO层做比较好？-----------
        Log log = new Log();
        logDao.add(log);
        //-----------------------------------------------------------------


//        {
//                "_id" : ObjectID("[特定值]"),
//                "name" : "courseservice",
//                "ip" : "123.207.73.150",
//                "dateTime" : "04/Nov/2017:11:43:54",
//                "url" : "GET /application/api/v2/class? HTTP/1.1",
//                "status" : 200,
//                "client" : "Apache-HttpClient/4.5.2 (Java/1.8.0_151)"
//        }

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

        String data = logDao.queryByParam(name, ip, fromDateTime, toDateTime, client);
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
        //logDao.delete();
        //-------------------------------------------------------------


        //删除成功时：
        return new Result("", Status.NO_CONTENT, "");
    }
}


/*
（1）定义LogsRes、ErrorsRes中的所有方法 ok
（2）调试DAO层
（3）实现一个，并用工具／插件调试
（4）实现其他的
 */