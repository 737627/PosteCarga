package postecarga.resources;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource {

    @GET
    public Response getConfig() {
        Config config = ConfigProvider.getConfig();
        String serverUrl = config.getValue("stw.posteCargaServer.url", String.class);
        return Response.ok("{\"serverUrl\": \"" + serverUrl + "\"}").build();
    }
}

