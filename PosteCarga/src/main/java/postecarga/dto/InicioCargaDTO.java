package postecarga.dto;

public class InicioCargaDTO {
    private Long idPoste;    
    private Float consignaCarga;  

    // Getters and Setters
    public Long getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(Long idPoste) {
        this.idPoste = idPoste;
    }

    public Float getConsignaCarga() {
        return consignaCarga;
    }

    public void setConsignaCarga(Float consignaCarga) {
        this.consignaCarga = consignaCarga;
    }
}
