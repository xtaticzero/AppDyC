/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.documento;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_MATDOCUMENTOS
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccMatDocumentosDTO implements Serializable {

    @SuppressWarnings("compatibility:-5614459818014013636")
    private static final long serialVersionUID = 1L;

    private String descripcionDoc;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idPlantilla;
    private Integer idUnidad;
    private String nombreDocumento;
    private String urlConfigurador;
    private String urlPlantilla;
    private DyccTipoPlantillaDTO dyccTipoPlantillaDTO;

    public DyccMatDocumentosDTO() {
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public void setDescripcionDoc(String descripcionDoc) {
        this.descripcionDoc = descripcionDoc;
    }

    public String getDescripcionDoc() {
        return descripcionDoc;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setUrlConfigurador(String urlConfigurador) {
        this.urlConfigurador = urlConfigurador;
    }

    public String getUrlConfigurador() {
        return urlConfigurador;
    }

    public void setUrlPlantilla(String urlPlantilla) {
        this.urlPlantilla = urlPlantilla;
    }

    public String getUrlPlantilla() {
        return urlPlantilla;
    }

    public void setDyccTipoPlantillaDTO(DyccTipoPlantillaDTO dyccTipoPlantillaDTO) {
        this.dyccTipoPlantillaDTO = dyccTipoPlantillaDTO;
    }

    public DyccTipoPlantillaDTO getDyccTipoPlantillaDTO() {
        return dyccTipoPlantillaDTO;
    }
}
