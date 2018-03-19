package mx.gob.sat.siat.dyc.domain.archivo;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;


public class DyctArchivoTempDTO implements Serializable{

    @SuppressWarnings("compatibility:-6355107183678382016")
    private static final long serialVersionUID = 1L;
    
    private Integer idArchivoTemp;
    private DyctServicioTempDTO dyctServicioTempDTO;
    private String url;
    private String nombreArchivo;
    private Integer tipoArchivo; 
    private String descripcion;
    
    public DyctArchivoTempDTO() {
        super();
    }

    public void setIdArchivoTemp(Integer idArchivoTemp) {
        this.idArchivoTemp = idArchivoTemp;
    }

    public Integer getIdArchivoTemp() {
        return idArchivoTemp;
    }

    public void setDyctServicioTempDTO(DyctServicioTempDTO dyctServicioTempDTO) {
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public DyctServicioTempDTO getDyctServicioTempDTO() {
        return dyctServicioTempDTO;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }
    
    public void setTipoArchivo(Integer tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public Integer getTipoArchivo() {
        return tipoArchivo;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
