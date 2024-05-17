package postecarga.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import postecarga.PosteCarga;
import postecarga.dto.NuevoPosteCargaDTO;
import postecarga.dto.ResultadoCargaDTO;
import postecarga.repository.PosteCargaRepository;

@Stateless
public class PosteCargaBean {

    @Inject
    private PosteCargaRepository repository;

    public void conectarManguera(String matricula, Long idPoste) {
        if (matricula == null || idPoste == null) {
            throw new IllegalArgumentException("Matricula y idPoste no deben ser nulos");
        }

        PosteCarga poste = repository.findById(idPoste);
        if (poste == null) {
            throw new IllegalStateException("No se encontró un poste con el ID: " + idPoste);
        }

        if ("libre".equalsIgnoreCase(poste.getEstado()) || "desconectado".equalsIgnoreCase(poste.getEstado())) {
            poste.setEstado("ocupado");
            poste.setMatriculaVehiculo(matricula);
            repository.save(poste);
        } else {
            throw new IllegalStateException("El poste no está disponible para conexión");
        }
    }

    public void desconectarManguera(Long idPoste) {
        PosteCarga poste = repository.findById(idPoste);
        if (poste == null || !"ocupado".equalsIgnoreCase(poste.getEstado())) {
            throw new IllegalStateException("El poste no está actualmente ocupado o no existe");
        }

        poste.setEstado("desconectado");
        poste.setMatriculaVehiculo(null);
        repository.save(poste);
    }

    public void iniciarCarga(Long idPoste, double consigna) {
        PosteCarga poste = repository.findById(idPoste);
        if (poste != null && "ocupado".equalsIgnoreCase(poste.getEstado())) {
            poste.setEstado("cargando");
            repository.save(poste);
        }
    }

    public ResultadoCargaDTO detenerCarga(Long idPoste) {
        PosteCarga poste = repository.findById(idPoste);
        if (poste == null) {
            throw new IllegalStateException("No se encontró un poste con el ID: " + idPoste);
        }

        if ("cargando".equalsIgnoreCase(poste.getEstado())) {
            poste.setEstado("libre");
            ResultadoCargaDTO resultado = new ResultadoCargaDTO();
            resultado.setKWhCargados(poste.getPotenciaEntregada());
            resultado.setCosto(poste.getPotenciaEntregada() * 0.20); // Ejemplo de cálculo del costo
            resultado.setEstadoFinal("Completado");

            repository.save(poste);
            return resultado;
        } else {
            throw new IllegalStateException("El poste no está actualmente cargando");
        }
    }

    public PosteCarga obtenerPoste(Long idPoste) {
        return repository.findById(idPoste);
    }

    public void actualizarEstado(Long idPoste, String nuevoEstado) {
        PosteCarga poste = repository.findById(idPoste);
        if (poste != null) {
            poste.setEstado(nuevoEstado);
            repository.save(poste);
        }
    }

    public PosteCarga crearNuevoPoste(NuevoPosteCargaDTO nuevoPosteCargaDTO) {
        PosteCarga nuevoPoste = new PosteCarga();
        nuevoPoste.setIdZonaDeCarga(nuevoPosteCargaDTO.getIdZonaDeCarga());
        nuevoPoste.setPotenciaMaxima(nuevoPosteCargaDTO.getPotenciaMaxima());
        nuevoPoste.setEstado("libre"); // Aseguramos que el estado sea siempre "libre" al crear
        repository.save(nuevoPoste);
        return nuevoPoste;
    }

    public List<PosteCarga> obtenerTodosLosPostes() {
        return repository.findAll();
    }
}
