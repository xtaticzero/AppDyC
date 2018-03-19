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


/**
 * DTO de la tabla DYCC_MATRIZANEXOS
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccMatrizAnexosDTO implements Serializable {

    @SuppressWarnings("compatibility:-9159333935850504144")
    private static final long serialVersionUID = 1L;

    private Integer idAnexo;
    private String nombreAnexo;
    private String descripcionAnexo;
    private Date fechaInicio;
    private Date fechaFin;
    private String url;
    private DyccAnexoTramiteDTO anexoTramite;

    public DyccMatrizAnexosDTO() {
    }

    public DyccMatrizAnexosDTO(String descripcionAnexo, Date fechaFin, Date fechaInicio, Integer idAnexo,
                               String nombreAnexo, String url) {
        this.descripcionAnexo = descripcionAnexo;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idAnexo = idAnexo;
        this.nombreAnexo = nombreAnexo;
        this.url = url;
    }

    public void setIdAnexo(Integer idAnexo) {
        this.idAnexo = idAnexo;
    }

    public Integer getIdAnexo() {
        return idAnexo;
    }

    public void setNombreAnexo(String nombreAnexo) {
        this.nombreAnexo = nombreAnexo;
    }

    public String getNombreAnexo() {
        return nombreAnexo;
    }

    public void setDescripcionAnexo(String descripcionAnexo) {
        this.descripcionAnexo = descripcionAnexo;
    }

    public String getDescripcionAnexo() {
        return descripcionAnexo;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
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

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setAnexoTramite(DyccAnexoTramiteDTO anexoTramite) {
        this.anexoTramite = anexoTramite;
    }

    public DyccAnexoTramiteDTO getAnexoTramite() {
        return anexoTramite;
    }
}
