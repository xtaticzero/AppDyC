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


/**
 * DTO de la tabla DYCT_DETALLEICEP
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctDetalleIcepDTO implements Serializable {

    @SuppressWarnings("compatibility:-4599475536465388250")
    private static final long serialVersionUID = 1L;

    private Date fechaMovimiento;
    private Integer idDetalleIcep;
    private BigDecimal importeMovimiento;
    private DyccMovIcepDTO dyccMovIcepDTO;
    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private String numControl;

    public DyctDetalleIcepDTO() {
    }

    public DyctDetalleIcepDTO(Date fechaMovimiento, Integer idDetalleIcep, DyccMovIcepDTO dyccMovIcepDTO,
                              DyctSaldoIcepDTO dyctSaldoIcepDTO, BigDecimal importeMovimiento) {
        this.fechaMovimiento = fechaMovimiento != null ? (Date)fechaMovimiento.clone() : null;
        this.idDetalleIcep = idDetalleIcep;
        this.dyccMovIcepDTO = dyccMovIcepDTO;
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
        this.importeMovimiento = importeMovimiento;
    }

    public Date getFechaMovimiento() {
        if (null != fechaMovimiento) {
            return (Date)fechaMovimiento.clone();
        } else {
            return null;
        }
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        if (null != fechaMovimiento) {
            this.fechaMovimiento = (Date)fechaMovimiento.clone();
        } else {
            this.fechaMovimiento = null;
        }
    }

    public void setIdDetalleIcep(Integer idDetalleIcep) {
        this.idDetalleIcep = idDetalleIcep;
    }

    public Integer getIdDetalleIcep() {
        return idDetalleIcep;
    }

    public void setImporteMovimiento(BigDecimal importeMovimiento) {
        this.importeMovimiento = importeMovimiento;
    }

    public BigDecimal getImporteMovimiento() {
        return importeMovimiento;
    }

    public void setDyccMovIcepDTO(DyccMovIcepDTO dyccMovIcepDTO) {
        this.dyccMovIcepDTO = dyccMovIcepDTO;
    }

    public DyccMovIcepDTO getDyccMovIcepDTO() {
        return dyccMovIcepDTO;
    }

    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }
}
