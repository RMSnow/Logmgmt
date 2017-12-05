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
    public Result queryDailyRecords(@NotEmpty @QueryParam("serviceName") String serviceName) {

        //TODO: res & docs

        return null;
    }
}
