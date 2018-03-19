/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCT_PAPELTRABAJO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyctPapelTrabajoDTO implements Serializable {

    @SuppressWarnings("compatibility:-4655039293128022105")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaBaja;
    private Date fechaRegistro;
    private Integer idPapelTrabajo;
    private String nombreArchivo;
    private String url;
    private DyctExpedienteDTO dyctExpedienteDTO;

    public DyctPapelTrabajoDTO() {
    }

    public DyctPapelTrabajoDTO(String descripcion, Date fechaBaja, Date fechaRegistro, Integer idPapelTrabajo,
                               String nombreArchivo, DyctExpedienteDTO dyctExpedienteDTO, String url) {
        this.descripcion = descripcion;
        this.fechaBaja = fechaBaja != null ? (Date)fechaBaja.clone() : null;
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.idPapelTrabajo = idPapelTrabajo;
        this.nombreArchivo = nombreArchivo;
        this.dyctExpedienteDTO = dyctExpedienteDTO;
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

    public void setFechaBaja(Date fechaBaja) {
        if (null != fechaBaja) {
            this.fechaBaja = (Date)fechaBaja.clone();
        } else {
            this.fechaBaja = null;
        }
    }

    public Date getFechaBaja() {
        if (null != fechaBaja) {
            return (Date)fechaBaja.clone();
        } else {
            return null;
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdPapelTrabajo(Integer idPapelTrabajo) {
        this.idPapelTrabajo = idPapelTrabajo;
    }

    public Integer getIdPapelTrabajo() {
        return idPapelTrabajo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDyctExpedienteDTO(DyctExpedienteDTO dyctExpedienteDTO) {
        this.dyctExpedienteDTO = dyctExpedienteDTO;
    }

    public DyctExpedienteDTO getDyctExpedienteDTO() {
        return dyctExpedienteDTO;
    }
}
