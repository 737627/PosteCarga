package postecarga.dto;

public class PosteCargaDTO {
    private Long id;
    private String idZonaDeCarga;

    public PosteCargaDTO() {}

    public PosteCargaDTO(Long id, String idZonaDeCarga) {
        this.id = id;
        this.idZonaDeCarga = idZonaDeCarga;
    }

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
}
