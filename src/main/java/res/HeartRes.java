package res;

import dock.RestResultGetter;
import entity.Result;
import entity.Status;
import health.HeartbeatTest;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by snow on 02/12/2017.
 */
@Path("/api/v2")
@Produces(MediaType.APPLICATION_JSON)
public class HeartRes {
//    public static volatile boolean OK = false;
//
//    @GET
//    public Result heartBeat(@NotEmpty @QueryParam("code") String code) {
//        OK = true;
//        return new Result(code, Status.OK, "");
//    }

    public static ArrayList<String> tokens = new ArrayList<>();
    @POST
    @Path("/notify")
    public Result notifyChange(){
        RestResultGetter.listApis();
        return null;
    }


    @GET
    @Path("/heart")
    public Result heartBeat(@NotEmpty @QueryParam("code")String code){
        HeartbeatTest.OK = true;
        System.err.println("ok");
        return new Result(code, Status.OK , "");
    }

    @GET
    @Path("/valid")
    public Result valid(){
        return new Result("", Status.OK, tokens);
    }
}
