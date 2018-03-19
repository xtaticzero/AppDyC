package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.util.Date;

/**import org.primefaces.model.UploadedFile;*/


public class DyctAnexoDTO implements Serializable {
    @SuppressWarnings("compatibility:-6059223507415569822")
    private static final long serialVersionUID = -7969048759833975624L;

    private int idAnexo;
    private String nombre;
    private String url;
    private String numControl;
    private int idTipoTramite;
    private Date fechaRegistro;

    private String estadoDesc;
    private String nombreAnexo;
    private String urlPlantillaAnexo;
    /**private transient UploadedFile file;*/

    public DyctAnexoDTO() {
        super();
    }


    public void setIdAnexo(int idAnexo) {
        this.idAnexo = idAnexo;
    }

    public int getIdAnexo() {
        return idAnexo;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }


    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getEstadoDesc() {
        return estadoDesc;
    }

    public void setNombreAnexo(String nombreAnexo) {
        this.nombreAnexo = nombreAnexo;
    }

    public String getNombreAnexo() {
        return nombreAnexo;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setUrlPlantillaAnexo(String urlPlantillaAnexo) {
        this.urlPlantillaAnexo = urlPlantillaAnexo;
    }

    public String getUrlPlantillaAnexo() {
        return urlPlantillaAnexo;
    }

    /**public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }*/

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }
}
