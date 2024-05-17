package postecarga.resources;

import postecarga.dto.*;
import postecarga.ejb.PosteCargaBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import postecarga.PosteCarga;

@Path("/postecarga")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PosteCargaController {

    @Inject
    private PosteCargaBean posteCargaBean;

    @POST
    @Path("/connect")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response connectHose(ConexionDTO conexion) {
        if (conexion.getMatriculaVehiculo() == null || conexion.getIdPoste() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Matricula y ID del poste son requeridos\"}").build();
        }
        try {
            posteCargaBean.conectarManguera(conexion.getMatriculaVehiculo(), conexion.getIdPoste());
            return Response.ok().entity("{\"message\":\"Manguera conectada\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/disconnect")
    public Response disconnectHose(EstadoDTO estadoDTO) {
        if (estadoDTO.getIdPoste() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID del poste es requerido").build();
        }
        posteCargaBean.desconectarManguera(estadoDTO.getIdPoste());
        return Response.ok().entity("Manguera desconectada").build();
    }

    @POST
    @Path("/start")
    public Response startCharge(InicioCargaDTO inicioCarga) {
        if (inicioCarga.getIdPoste() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID del poste es requerido").build();
        }
        posteCargaBean.iniciarCarga(inicioCarga.getIdPoste(), inicioCarga.getConsignaCarga());
        return Response.ok().entity("Carga iniciada").build();
    }

    @POST
    @Path("/stop")
    public Response stopCharge(EstadoDTO estadoDTO) {
        if (estadoDTO.getIdPoste() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID del poste es requerido").build();
        }
        ResultadoCargaDTO resultado = posteCargaBean.detenerCarga(estadoDTO.getIdPoste());
        return Response.ok(resultado).build();
    }

    @GET
    @Path("/status")
    public Response getPosteCargaStatus(@QueryParam("idPoste") Long idPoste) {
        if (idPoste == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID del poste es requerido").build();
        }
        PosteCarga poste = posteCargaBean.obtenerPoste(idPoste);
        return Response.ok(poste).build();
    }

    @PUT
    @Path("/updateStatus")
    public Response updatePosteCargaStatus(EstadoDTO estadoDTO) {
        if (estadoDTO.getIdPoste() == null || estadoDTO.getNuevoEstado() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID del poste y el nuevo estado son requeridos").build();
        }
        posteCargaBean.actualizarEstado(estadoDTO.getIdPoste(), estadoDTO.getNuevoEstado());
        return Response.ok().entity("Estado del poste actualizado").build();
    }

    @POST
    @Path("/nuevoPoste")
    public Response crearNuevoPoste(NuevoPosteCargaDTO nuevoPosteCargaDTO) {
        if (nuevoPosteCargaDTO.getIdZonaDeCarga() == null || nuevoPosteCargaDTO.getPotenciaMaxima() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Zona de carga y potencia máxima son requeridos").build();
        }
        PosteCarga poste = posteCargaBean.crearNuevoPoste(nuevoPosteCargaDTO);
        return Response.ok(poste).build();
    }

    @GET
    @Path("/listaPostes")
    public Response obtenerListaPostes() {
        List<PosteCarga> postes = posteCargaBean.obtenerTodosLosPostes();
        return Response.ok(postes).build();
    }
}
