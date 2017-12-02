package res;

import com.codahale.metrics.annotation.Timed;
import entity.Result;
import util.HTTPTool;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * api资源
 */
@Path("/api/v1/control")
@Produces(MediaType.APPLICATION_JSON)
public class ControlRes {
    @POST
    @Timed
    public Result register(){
        //注册API
        Result result = new HTTPTool().registerApi();
        return result;
    }

    @DELETE
    @Timed
    public Result repeal(){
        System.exit(0);
        return null;
    }
}
