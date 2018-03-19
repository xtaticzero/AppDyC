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
import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;

/**
 * DTO de la tabla DYCP_COMPENSACION
 *
 * @author Alfredo Ramirez
 * @since 01/04/2014
 */
public class DycpCompensacionDTO extends DycpServicioDTO implements Serializable {

    @SuppressWarnings("compatibility:4724161284887647465")
    private static final long serialVersionUID = 1L;

    private BigDecimal importeCompensado;
    private Date fechaPresentaDec;
    private String numOperacionDec;
    private DyccTipoDeclaraDTO dyccTipoDeclaraDTO;
    private DyccEstadoCompDTO dyccEstadoCompDTO;
    private DyccAprobadorDTO dyccAprobadorDTO;
    private DycpAvisoCompDTO dycpAvisoCompDTO;
    private DyctSaldoIcepDTO dyctSaldoIcepDestinoDTO;
    private BigDecimal remanenteHistorico;
    private BigDecimal remanenteAct;
    private DyctSaldoIcepDTO dyctSaldoIcepOrigenDTO;
    private DycpServicioDTO dycpServicioDTO;
    private DycpServicioDTO dycpServicioAnteriorDTO;
    private DyctResolCompDTO dyctResolCompDTO;
    private List<DyctOrigenAvisoDTO> dyctOrigenAvisoList;
    private Date fechaInicioTramite;
    private Date fechaPresentacion;

    public DycpCompensacionDTO() {
    }

    public void setFechaPresentaDec(Date fechaPresentaDec) {
        if (null != fechaPresentaDec) {
            this.fechaPresentaDec = (Date) fechaPresentaDec.clone();
        } else {
            this.fechaPresentaDec = null;
        }
    }

    public Date getFechaPresentaDec() {
        if (null != fechaPresentaDec) {
            return (Date) fechaPresentaDec.clone();
        } else {
            return null;
        }
    }

    public void setNumOperacionDec(String numOperacionDec) {
        this.numOperacionDec = numOperacionDec;
    }

    public String getNumOperacionDec() {
        return numOperacionDec;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setDyccEstadoCompDTO(DyccEstadoCompDTO dyccEstadoCompDTO) {
        this.dyccEstadoCompDTO = dyccEstadoCompDTO;
    }

    public DyccEstadoCompDTO getDyccEstadoCompDTO() {
        return dyccEstadoCompDTO;
    }

    public void setDyccTipoDeclaraDTO(DyccTipoDeclaraDTO dyccTipoDeclaraDTO) {
        this.dyccTipoDeclaraDTO = dyccTipoDeclaraDTO;
    }

    public DyccTipoDeclaraDTO getDyccTipoDeclaraDTO() {
        return dyccTipoDeclaraDTO;
    }

    public void setDyccAprobadorDTO(DyccAprobadorDTO dyccAprobadorDTO) {
        this.dyccAprobadorDTO = dyccAprobadorDTO;
    }

    public DyccAprobadorDTO getDyccAprobadorDTO() {
        return dyccAprobadorDTO;
    }

    public void setDyctResolCompDTO(DyctResolCompDTO dyctResolCompDTO) {
        this.dyctResolCompDTO = dyctResolCompDTO;
    }

    public DyctResolCompDTO getDyctResolCompDTO() {
        return dyctResolCompDTO;
    }

    public void setDycpAvisoCompDTO(DycpAvisoCompDTO dycpAvisoCompDTO) {
        this.dycpAvisoCompDTO = dycpAvisoCompDTO;
    }

    public DycpAvisoCompDTO getDycpAvisoCompDTO() {
        return dycpAvisoCompDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDestinoDTO() {
        return dyctSaldoIcepDestinoDTO;
    }

    public void setDyctSaldoIcepDestinoDTO(DyctSaldoIcepDTO dyctSaldoIcepDestinoDTO) {
        this.dyctSaldoIcepDestinoDTO = dyctSaldoIcepDestinoDTO;
    }

    public void setRemanenteHistorico(BigDecimal remanenteHistorico) {
        this.remanenteHistorico = remanenteHistorico;
    }

    public BigDecimal getRemanenteHistorico() {
        return remanenteHistorico;
    }

    public void setRemanenteAct(BigDecimal remanenteAct) {
        this.remanenteAct = remanenteAct;
    }

    public BigDecimal getRemanenteAct() {
        return remanenteAct;
    }

    public void setDyctOrigenAvisoList(List<DyctOrigenAvisoDTO> dyctOrigenAvisoList) {
        this.dyctOrigenAvisoList = dyctOrigenAvisoList;
    }

    public List<DyctOrigenAvisoDTO> getDyctOrigenAvisoList() {
        return dyctOrigenAvisoList;
    }

    public void setDyctSaldoIcepOrigenDTO(DyctSaldoIcepDTO dyctSaldoIcepOrigenDTO) {
        this.dyctSaldoIcepOrigenDTO = dyctSaldoIcepOrigenDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepOrigenDTO() {
        return dyctSaldoIcepOrigenDTO;
    }

    public void setFechaInicioTramite(Date fechaInicioTramite) {
        if (null != fechaInicioTramite) {
            this.fechaInicioTramite = (Date) fechaInicioTramite.clone();
        } else {
            this.fechaInicioTramite = null;
        }
    }

    public Date getFechaInicioTramite() {
        if (null != fechaInicioTramite) {
            return (Date) fechaInicioTramite.clone();
        } else {
            return null;
        }
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date) fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date) fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public DycpServicioDTO getDycpServicioAnteriorDTO() {
        return dycpServicioAnteriorDTO;
    }

    public void setDycpServicioAnteriorDTO(DycpServicioDTO dycpServicioAnteriorDTO) {
        this.dycpServicioAnteriorDTO = dycpServicioAnteriorDTO;
    }
}
