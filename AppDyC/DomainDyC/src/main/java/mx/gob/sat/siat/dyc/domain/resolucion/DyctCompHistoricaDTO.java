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
 * DTO de la tabla DYCT_COMPHISTORICA.
 * @author  Alfredo Ramirez
 * @author Luis Alberto Dominguez Palomino
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Agosto 19, 2015

 */
public class DyctCompHistoricaDTO implements Serializable {

    @SuppressWarnings("compatibility:-8564511613622728559")
    private static final long serialVersionUID = 1L;

    private String numControl;
    private Date fechaDeclaraDest;
    private BigDecimal importeCompensado;
    private BigDecimal importeLiquidado;
    private Date fechaResolucion;
    private DyccTipoResolDTO dyccTipoResolDTO;
    private Integer idMovCompensacion;
    private DyctSaldoIcepDTO dyctSaldoIcepOrigenDTO;
    private DyctSaldoIcepDTO dyctSaldoIcepDestinoDTO;
    private String numDocDeterminante;
    private String notas;
    private Date fechaFin;

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Date getFechaDeclaraDest() {
        if (fechaDeclaraDest != null) {
            return (Date)fechaDeclaraDest.clone();
        } else {
            return null;
    }
    }

    public void setFechaDeclaraDest(Date fechaDeclaraDest) {
        if (fechaDeclaraDest != null) {
            this.fechaDeclaraDest = (Date)fechaDeclaraDest.clone();
        } else {
            this.fechaDeclaraDest = null;
    }
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public BigDecimal getImporteLiquidado() {
        return importeLiquidado;
    }

    public void setImporteLiquidado(BigDecimal importeLiquidado) {
        this.importeLiquidado = importeLiquidado;
    }

    public Date getFechaResolucion() {
        if (fechaResolucion != null) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
    }
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (fechaResolucion != null) {
            this.fechaResolucion = (Date)fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
    }
    }

    public DyccTipoResolDTO getDyccTipoResolDTO() {
        return dyccTipoResolDTO;
    }

    public void setDyccTipoResolDTO(DyccTipoResolDTO dyccTipoResolDTO) {
        this.dyccTipoResolDTO = dyccTipoResolDTO;
    }

    public Integer getIdMovCompensacion() {
        return idMovCompensacion;
    }

    public void setIdMovCompensacion(Integer idMovCompensacion) {
        this.idMovCompensacion = idMovCompensacion;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepOrigenDTO() {
        return dyctSaldoIcepOrigenDTO;
    }

    public void setDyctSaldoIcepOrigenDTO(DyctSaldoIcepDTO dyctSaldoIcepOrigenDTO) {
        this.dyctSaldoIcepOrigenDTO = dyctSaldoIcepOrigenDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDestinoDTO() {
        return dyctSaldoIcepDestinoDTO;
    }

    public void setDyctSaldoIcepDestinoDTO(DyctSaldoIcepDTO dyctSaldoIcepDestinoDTO) {
        this.dyctSaldoIcepDestinoDTO = dyctSaldoIcepDestinoDTO;
    }

    public String getNumDocDeterminante() {
        return numDocDeterminante;
    }

    public void setNumDocDeterminante(String numDocDeterminante) {
        this.numDocDeterminante = numDocDeterminante;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
    }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
    }
    }
}
