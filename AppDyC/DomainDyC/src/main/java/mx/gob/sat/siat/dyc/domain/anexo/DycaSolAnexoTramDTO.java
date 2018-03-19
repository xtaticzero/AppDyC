/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.anexo;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;


/**
 * DTO para el tabla DYCA_SOLANEXOTRAM
 * @author  Alfredo Ramirez
 * Actualizado por Luis ALberto Dominguez Palomino LADP
 * @since   31/03/2014
 */
public class DycaSolAnexoTramDTO implements Serializable {


    @SuppressWarnings("compatibility:-829675584459966855")
    private static final long serialVersionUID = 1L;
    
    private Date fechaRegistro;
    private String nombreArchivo;
    private String numControl;
    private String url;
    private Integer procesado;
    private DyccAnexoTramiteDTO dyccAnexoTramiteDTO;
    private DycpSolicitudDTO dycpSolicitudDTO;

    public DycaSolAnexoTramDTO() {
    }

    public DycaSolAnexoTramDTO(Date fechaRegistro, DyccAnexoTramiteDTO dyccAnexoTramiteDTO, String nombreArchivo,
                               DycpSolicitudDTO dycpSolicitudDTO, String url) {
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.dyccAnexoTramiteDTO = dyccAnexoTramiteDTO;
        this.nombreArchivo = nombreArchivo;
        this.dycpSolicitudDTO = dycpSolicitudDTO;
        this.url = url;
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

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    
    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
    }

    public Integer getProcesado() {
        return procesado;
    }
    
    public void setDyccAnexoTramiteDTO(DyccAnexoTramiteDTO dyccAnexoTramiteDTO) {
        this.dyccAnexoTramiteDTO = dyccAnexoTramiteDTO;
    }

    public DyccAnexoTramiteDTO getDyccAnexoTramiteDTO() {
        return dyccAnexoTramiteDTO;
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

}
