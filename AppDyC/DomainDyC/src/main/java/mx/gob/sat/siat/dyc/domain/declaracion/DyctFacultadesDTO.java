package mx.gob.sat.siat.dyc.domain.declaracion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;


/**
 * DTO de la tabla DYCT_FACULTADES
 * @author  Alfredo Ramirez
 * @editor Adrian Aguilar
 * @since   01/04/2014
 */
public class DyctFacultadesDTO implements Serializable {

    @SuppressWarnings("compatibility:-4717856408383834082")
    private static final long serialVersionUID = 1L;
    
    private DycpServicioDTO dycpServicioDTO;
    private DyccAprobadorDTO dyccAprobadorDTO;
    private Date fechaRegistro;
    private String folio;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idFacultades;


    public DyctFacultadesDTO() {
        super();
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setDyccAprobadorDTO(DyccAprobadorDTO dyccAprobadorDTO) {
        this.dyccAprobadorDTO = dyccAprobadorDTO;
    }

    public DyccAprobadorDTO getDyccAprobadorDTO() {
        return dyccAprobadorDTO;
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

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
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

    public void setIdFacultades(Integer idFacultades) {
        this.idFacultades = idFacultades;
    }

    public Integer getIdFacultades() {
        return idFacultades;
    }
}
