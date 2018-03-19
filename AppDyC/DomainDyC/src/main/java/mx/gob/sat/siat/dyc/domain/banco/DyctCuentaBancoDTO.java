/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.banco;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;


/**
 * DTO de la tabla DYCT_CUENTABANCO
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctCuentaBancoDTO implements Serializable {

    @SuppressWarnings("compatibility:4614792154887002100")
    private static final long serialVersionUID = 1L;

    private String clabe;
    private Date fechaFin;
    private DycpSolicitudDTO dycpSolicitudDTO;
    private DyccInstCreditoDTO dyccInstCreditoDTO;
    private Date fechaRegistro;
    private Date fechaUltimaMod;
    private Integer cuentaValida;
    private DyctArchivoDTO dyctArchivoDTO;

    public DyctCuentaBancoDTO() {
    }

    public DyctCuentaBancoDTO(String clabe, Date fechaFin, DyccInstCreditoDTO dyccInstCreditoDTO,
                              DycpSolicitudDTO dycpSolicitudDTO, Date fechaRegistro, Date fechaUltimaMod,
                              Integer cuentaValida) {
        this.clabe = clabe;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.dyccInstCreditoDTO = dyccInstCreditoDTO;
        this.dycpSolicitudDTO = dycpSolicitudDTO;
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.fechaUltimaMod = fechaUltimaMod != null ? (Date)fechaUltimaMod.clone() : null;
        this.cuentaValida = cuentaValida;
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

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getClabe() {
        return clabe;
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

    public void setDyccInstCreditoDTO(DyccInstCreditoDTO dyccInstCreditoDTO) {
        this.dyccInstCreditoDTO = dyccInstCreditoDTO;
    }

    public DyccInstCreditoDTO getDyccInstCreditoDTO() {
        return dyccInstCreditoDTO;
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

    public void setFechaUltimaMod(Date fechaUltimaMod) {
        if (null != fechaUltimaMod) {
            this.fechaUltimaMod = (Date)fechaUltimaMod.clone();
        } else {
            this.fechaUltimaMod = null;
        }
    }

    public Date getFechaUltimaMod() {
        if (null != fechaUltimaMod) {
            return (Date)fechaUltimaMod.clone();
        } else {
            return null;
        }
    }

    public void setCuentaValida(Integer cuentaValida) {
        this.cuentaValida = cuentaValida;
    }

    public Integer getCuentaValida() {
        return cuentaValida;
    }

    public void setDyctArchivoDTO(DyctArchivoDTO dyctArchivoDTO) {
        this.dyctArchivoDTO = dyctArchivoDTO;
    }

    public DyctArchivoDTO getDyctArchivoDTO() {
        return dyctArchivoDTO;
    }
}
