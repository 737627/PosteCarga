package postecarga.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("jakartaee10")
public class JakartaEE10Resource {

   
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response
                .ok("Ping successful: Jakarta EE backend is running.")
                .build();
    }



    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkStatus() {
        
        String status = "{\"status\":\"active\"}";
        return Response
                .ok(status)
                .header("Content-Type", "application/json")
                .build();
    }
}
