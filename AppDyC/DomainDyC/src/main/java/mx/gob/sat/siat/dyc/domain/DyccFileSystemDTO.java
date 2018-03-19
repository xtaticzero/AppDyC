package mx.gob.sat.siat.dyc.domain;

public class DyccFileSystemDTO {
    public DyccFileSystemDTO() {
        super();
    }
    
    private Integer idFileSystem;
    private String  puntoDeMontaje;
    private Double  espacioEnDisco;
    private Boolean activo;

    public void setIdFileSystem(Integer idFileSystem) {
        this.idFileSystem = idFileSystem;
    }

    public Integer getIdFileSystem() {
        return idFileSystem;
    }

    public void setPuntoDeMontaje(String puntoDeMontaje) {
        this.puntoDeMontaje = puntoDeMontaje;
    }

    public String getPuntoDeMontaje() {
        return puntoDeMontaje;
    }

    public void setEspacioEnDisco(Double espacioEnDisco) {
        this.espacioEnDisco = espacioEnDisco;
    }

    public Double getEspacioEnDisco() {
        return espacioEnDisco;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getActivo() {
        return activo;
    }
}
