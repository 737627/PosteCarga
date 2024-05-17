package postecarga.dto;

public class ResultadoCargaDTO {
    private Float kWhCargados; 
    private double costo;      
    private String estadoFinal; 

    // Getters and Setters
    public Float getKWhCargados() {
        return kWhCargados;
    }

    public void setKWhCargados(Float kWhCargados) {
        this.kWhCargados = kWhCargados;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
}
