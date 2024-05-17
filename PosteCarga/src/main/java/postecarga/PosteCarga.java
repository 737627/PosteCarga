package postecarga;

import jakarta.persistence.*;

@Entity
@Table(name = "poste_carga")
public class PosteCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_zona_de_carga")
    private String idZonaDeCarga;

    @Column(name = "potencia_maxima")
    private double potenciaMaxima;

    @Column(name = "estado")
    private String estado; 

    @Column(name = "potencia_entregada")
    private float potenciaEntregada;

    @Column(name = "id_carga")
    private Integer idCarga;

    @Column(name = "energia_entregada")
    private double energiaEntregada;

    @Column(name = "instante_inicio_carga")
    private long instanteInicioCarga;

    @Column(name = "matricula_vehiculo")
    private String matriculaVehiculo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdZonaDeCarga() {
        return idZonaDeCarga;
    }

    public void setIdZonaDeCarga(String idZonaDeCarga) {
        this.idZonaDeCarga = idZonaDeCarga;
    }

    public double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public void setPotenciaMaxima(double potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getPotenciaEntregada() {
        return potenciaEntregada;
    }

    public void setPotenciaEntregada(float potenciaEntregada) {
        this.potenciaEntregada = potenciaEntregada;
    }

    public Integer getIdCarga() {
        return idCarga;
    }

    public void setIdCarga(Integer idCarga) {
        this.idCarga = idCarga;
    }

    public double getEnergiaEntregada() {
        return energiaEntregada;
    }

    public void setEnergiaEntregada(double energiaEntregada) {
        this.energiaEntregada = energiaEntregada;
    }

    public long getInstanteInicioCarga() {
        return instanteInicioCarga;
    }

    public void setInstanteInicioCarga(long instanteInicioCarga) {
        this.instanteInicioCarga = instanteInicioCarga;
    }

    public String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }
}
