package postecarga.dto;

public class NuevoPosteCargaDTO {
    private String idZonaDeCarga;
    private Double potenciaMaxima;
    private String estado;

    // Constructor
    public NuevoPosteCargaDTO() {
        this.estado = "libre";
    }

    public NuevoPosteCargaDTO(String idZonaDeCarga, Double potenciaMaxima, String estado) {
        this.idZonaDeCarga = idZonaDeCarga;
        this.potenciaMaxima = potenciaMaxima;
        this.estado = estado;
    }

    // Getters y Setters
    public String getIdZonaDeCarga() {
        return idZonaDeCarga;
    }

    public void setIdZonaDeCarga(String idZonaDeCarga) {
        this.idZonaDeCarga = idZonaDeCarga;
    }

    public Double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public void setPotenciaMaxima(Double potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
