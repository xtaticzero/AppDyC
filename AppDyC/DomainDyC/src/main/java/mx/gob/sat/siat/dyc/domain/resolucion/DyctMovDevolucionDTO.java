/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;


/**
 * DTO para el catalogo DYCT_MOVDEVOLUCION
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctMovDevolucionDTO implements Serializable 
{
    @SuppressWarnings("compatibility:7395614384177268676")
    private static final long serialVersionUID = 1L;
    
    private BigDecimal actualizacion;
    private Date fechaResolucion;
    private BigDecimal importeAutorizado;
    private BigDecimal importeSolDev;
    private BigDecimal importeNegado;
    private BigDecimal importeNetoDev;
    private BigDecimal intereses;
    private BigDecimal retIntereses;
    private BigDecimal impCompensado;
    private String numControl;
    private DyccTipoResolDTO idTipoResol;
    private Integer idMovDevolucion;
    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private Date fechaFin;

    public DyctMovDevolucionDTO() {
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (fechaResolucion != null) {
            this.fechaResolucion = (Date)fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
        }
    }

    public Date getFechaResolucion() {
        if (fechaResolucion != null) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
        }
    }

    public void setImporteAutorizado(BigDecimal importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public BigDecimal getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteNegado(BigDecimal importeNegado) {
        this.importeNegado = importeNegado;
    }

    public BigDecimal getImporteNegado() {
        return importeNegado;
    }

    public void setActualizacion(BigDecimal actualizacion) {
        this.actualizacion = actualizacion;
    }

    public BigDecimal getActualizacion() {
        return actualizacion;
    }


    public void setIntereses(BigDecimal intereses) {
        this.intereses = intereses;
    }

    public BigDecimal getIntereses() {
        return intereses;
    }

    public void setIdTipoResol(DyccTipoResolDTO idTipoResol) {
        this.idTipoResol = idTipoResol;
    }

    public DyccTipoResolDTO getIdTipoResol() {
        return idTipoResol;
    }

    public void setIdMovDevolucion(Integer idMovDevolucion) {
        this.idMovDevolucion = idMovDevolucion;
    }

    public Integer getIdMovDevolucion() {
        return idMovDevolucion;
    }
    
    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }
    
    public void setImporteSolDev(BigDecimal importeSolDev) {
        this.importeSolDev = importeSolDev;
    }

    public BigDecimal getImporteSolDev() {
        return importeSolDev;
    }

    public void setImporteNetoDev(BigDecimal importeNetoDev) {
        this.importeNetoDev = importeNetoDev;
    }

    public BigDecimal getImporteNetoDev() {
        return importeNetoDev;
    }
    
    public void setRetIntereses(BigDecimal retIntereses) {
        this.retIntereses = retIntereses;
    }

    public BigDecimal getRetIntereses() {
        return retIntereses;
    }

    public void setImpCompensado(BigDecimal impCompensado) {
        this.impCompensado = impCompensado;
    }

    public BigDecimal getImpCompensado() {
        return impCompensado;
    }
    
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }
}
