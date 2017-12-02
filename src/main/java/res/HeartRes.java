package res;

import entity.Result;
import entity.Status;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by snow on 02/12/2017.
 */
@Path("/api/v1/heart")
@Produces(MediaType.APPLICATION_JSON)
public class HeartRes {
    public static volatile boolean OK = false;

    @GET
    public Result heartBeat(@NotEmpty @QueryParam("code") String code) {
        OK = true;
        return new Result(code, Status.OK, "");
    }
}
