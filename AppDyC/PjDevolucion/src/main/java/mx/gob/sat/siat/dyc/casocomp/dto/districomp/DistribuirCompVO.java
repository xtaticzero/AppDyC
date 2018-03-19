package mx.gob.sat.siat.dyc.casocomp.dto.districomp;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;


/**
 * Clase para caso de uso Distribuicion de Compensaciones a Administraciones
 * @author Luis Alberto Dominguez Palomino LADP
 * @version 1.0 11-Abril-2014
 */
public class DistribuirCompVO implements Serializable {

    @SuppressWarnings("compatibility:-298244632634838879")
    private static final long serialVersionUID = 1L;
    
    private String numControl;
    private int estadoAviso;
    private BigDecimal importSaldoOrig;
    private BigDecimal importCompensado;
    private Integer tipoDeclaracion;
    private String inconsistencia;
    private int unidadAvisoComp;
    private int servicio;
    private int concepto;
    private int ejercicio;
    private int periodo;
    private String rfcContribunyente;
    private Date fechaPresentaDec;
    private Double monto;
    private int idSaldoIcepDestino;
    private int idSaldoIcepOrigen;
    private Boolean isRemanente;
    private DycpCompensacionDTO dycpCompensacionDTO;
    
    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setEstadoAviso(int estadoAviso) {
        this.estadoAviso = estadoAviso;
    }

    public int getEstadoAviso() {
        return estadoAviso;
    }

    public void setImportSaldoOrig(BigDecimal importSaldoOrig) {
        this.importSaldoOrig = importSaldoOrig;
    }

    public BigDecimal getImportSaldoOrig() {
        return importSaldoOrig;
    }

    public void setImportCompensado(BigDecimal importCompensado) {
        this.importCompensado = importCompensado;
    }

    public BigDecimal getImportCompensado() {
        return importCompensado;
    }

    public void setTipoDeclaracion(Integer tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Integer getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setInconsistencia(String inconsistencia) {
        this.inconsistencia = inconsistencia;
    }

    public String getInconsistencia() {
        return inconsistencia;
    }

    public void setUnidadAvisoComp(int unidadAvisoComp) {
        this.unidadAvisoComp = unidadAvisoComp;
    }

    public int getUnidadAvisoComp() {
        return unidadAvisoComp;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public int getServicio() {
        return servicio;
    }

    public void setConcepto(int concepto) {
        this.concepto = concepto;
    }

    public int getConcepto() {
        return concepto;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setRfcContribunyente(String rfcContribunyente) {
        this.rfcContribunyente = rfcContribunyente;
    }

    public String getRfcContribunyente() {
        return rfcContribunyente;
    }

    public void setFechaPresentaDec(Date fechaPresentaDec) {
        if (fechaPresentaDec != null) {
            this.fechaPresentaDec = (Date)fechaPresentaDec.clone();
        } else {
            this.fechaPresentaDec = null;
        }
    }

    public Date getFechaPresentaDec() {
        if (fechaPresentaDec != null) {
            return (Date)fechaPresentaDec.clone();
        } else {
            return null;
        }
    }
    
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setIdSaldoIcepDestino(int idSaldoIcepDestino) {
        this.idSaldoIcepDestino = idSaldoIcepDestino;
    }

    public int getIdSaldoIcepDestino() {
        return idSaldoIcepDestino;
    }

    public void setIdSaldoIcepOrigen(int idSaldoIcepOrigen) {
        this.idSaldoIcepOrigen = idSaldoIcepOrigen;
    }

    public int getIdSaldoIcepOrigen() {
        return idSaldoIcepOrigen;
    }

    public void setIsRemanente(Boolean isRemanente) {
        this.isRemanente = isRemanente;
    }

    public Boolean getIsRemanente() {
        return isRemanente;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }
}
