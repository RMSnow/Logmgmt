package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by snow on 05/12/2017.
 */
@Path("/api/v1/records")
@Produces(MediaType.APPLICATION_JSON)
public class RecordRes {
    @GET
    @Timed
    public Result queryDailyRecords(@NotEmpty @QueryParam("serviceName") String serviceName,
                                    @QueryParam("fromDatetime") String fromDateTime,
                                    @QueryParam("toDatetime") String toDateTime) {

        //TODO: res & docs

//        日志分析接口：传服务名，返回分析结果，结果要包括
//
//        新建一个集合，（按小时存储以下），按天返回结果
//        # 该服务最常被调用的api
//        # 以及数量
//        # 服务错误数——Logging
//        # 不正常返回（非200）数——RequestLog
//        # 最近30天每天服务访问量
//
//        # 最近5分钟每秒请求数

        return null;
    }
}
