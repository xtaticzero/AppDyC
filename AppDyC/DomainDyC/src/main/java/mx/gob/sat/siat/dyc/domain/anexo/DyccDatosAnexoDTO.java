package mx.gob.sat.siat.dyc.domain.anexo;

public class DyccDatosAnexoDTO {
    public DyccDatosAnexoDTO() {
        super();
    }
    
    private Integer idAnexo;
    private String  coordenadasX;
    private String  coordenadasY;
    private String  tiposDeDato;
    private Integer noRepeticiones;
    private Integer lontigud;
    private String  descripcion;

    public void setIdAnexo(Integer idAnexo) {
        this.idAnexo = idAnexo;
    }

    public Integer getIdAnexo() {
        return idAnexo;
    }

    public void setCoordenadasX(String coordenadasX) {
        this.coordenadasX = coordenadasX;
    }

    public String getCoordenadasX() {
        return coordenadasX;
    }

    public void setCoordenadasY(String coordenadasY) {
        this.coordenadasY = coordenadasY;
    }

    public String getCoordenadasY() {
        return coordenadasY;
    }

    public void setTiposDeDato(String tiposDeDato) {
        this.tiposDeDato = tiposDeDato;
    }

    public String getTiposDeDato() {
        return tiposDeDato;
    }

    public void setNoRepeticiones(Integer noRepeticiones) {
        this.noRepeticiones = noRepeticiones;
    }

    public Integer getNoRepeticiones() {
        return noRepeticiones;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setLontigud(Integer lontigud) {
        this.lontigud = lontigud;
    }

    public Integer getLontigud() {
        return lontigud;
    }
}
