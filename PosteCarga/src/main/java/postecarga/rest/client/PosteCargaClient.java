package postecarga.rest.client;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class PosteCargaClient {

    private Config config = ConfigProvider.getConfig();
    private WebTarget webTarget;
    private Client client;

    public PosteCargaClient() {
        // El valor POR DEFECTO de BASE_URI se obtiene del fichero "src/main/resources/META-INF/microprofile-config.properties"
        String BASE_URI = config.getValue("stw.posteCargaServer.url", String.class);
        System.out.println("PosteCargaClient BASE_URI: " + BASE_URI);
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("postecarga");
    }

    public void conectarManguera(Object requestEntity) throws ClientErrorException {
        webTarget.path("connect").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void desconectarManguera(Long idPoste) throws ClientErrorException {
        webTarget.path("disconnect").queryParam("idPoste", idPoste)
                .request().post(null);
    }

    public void iniciarCarga(Object requestEntity) throws ClientErrorException {
        webTarget.path("start").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T detenerCarga(Class<T> responseType, Long idPoste) throws ClientErrorException {
        WebTarget resource = webTarget.path("stop").queryParam("idPoste", idPoste);
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).post(null, responseType);
    }

    public <T> T obtenerPoste(Class<T> responseType, Long idPoste) throws ClientErrorException {
        WebTarget resource = webTarget.path("status").queryParam("idPoste", idPoste);
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void actualizarEstado(Object requestEntity) throws ClientErrorException {
        webTarget.path("updateStatus").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void close() {
        client.close();
    }
}
