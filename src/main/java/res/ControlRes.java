package res;

import com.codahale.metrics.annotation.Timed;
import dock.DockService;
import entity.Result;

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
    @DELETE
    @Timed
    public Result repeal(){
        System.exit(0);
        return null;
    }
}
