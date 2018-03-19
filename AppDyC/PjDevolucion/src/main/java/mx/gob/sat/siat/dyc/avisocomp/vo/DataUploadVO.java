package mx.gob.sat.siat.dyc.avisocomp.vo;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


public class DataUploadVO implements Serializable {
    public DataUploadVO() {
        super();
    }

    private static final long serialVersionUID = 1L;

    private String folioAvisoNormal;
    private Integer idTipoDeclaracion;
    private Integer idConcepto;
    private Integer idPeriodo;
    private Integer idEjercicio;
    private Date fechaPresentacionDec;
    private String numOperacionDec;
    private BigDecimal importeComDeclarado;
    private Integer idTipoAviso;
    private String idTipoPeriodo;
    private Integer safIdPeriodo;
    private Integer safIdEjercicio;
    private BigDecimal saldoAplicar;
    private BigDecimal montoSaldoAFavor;
    private BigDecimal remanenteHistorico;
    private Date fechaCausacion;
    private BigDecimal remanenteAct;
    private Integer idOrigenSaldo;
    private Integer idTipoTramite;
    private BigDecimal importeSolicitado;
    private BigDecimal impActualizado;
    private BigDecimal impRemanente;
    private String diotNumOperacion;
    private Date diotFechaPresenta;
    private Integer safNumOperacionDec;
    private Integer tipoSaldo;
    private String espSubOrigen;
    private Integer presentoDiot;
    private Integer numControlRem;
    private Integer esRemanente;
    /**The  key  for the tables  when the datas are come back on the display*/
    private Integer folioServTemp;

    /** Table  dyct_declaratemp**/
    private Date fechaPresentacion;
    private String numOperacion;
    private BigDecimal saldoAFavor;
    private Date normalFechaPres;
    private Long normalNumOperacion;
    private BigDecimal normalImporteSaf;
    private Integer idTipoDeclaracionTemp;
    private BigDecimal acreditamiento;
    private BigDecimal devueltoCompensado;


    public void setFolioAvisoNormal(String folioAvisoNormal) {
        this.folioAvisoNormal = folioAvisoNormal;
    }

    public String getFolioAvisoNormal() {
        return folioAvisoNormal;
    }

    public void setIdTipoDeclaracion(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    public Integer getIdTipoDeclaracion() {
        return idTipoDeclaracion;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (fechaPresentacion != null) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

   

    public void setImporteComDeclarado(BigDecimal importeComDeclarado) {
        this.importeComDeclarado = importeComDeclarado;
    }

    public BigDecimal getImporteComDeclarado() {
        return importeComDeclarado;
    }

    public void setIdTipoAviso(Integer idTipoAviso) {
        this.idTipoAviso = idTipoAviso;
    }

    public Integer getIdTipoAviso() {
        return idTipoAviso;
    }

    public void setIdTipoPeriodo(String idTipoPeriodo) {
        this.idTipoPeriodo = idTipoPeriodo;
    }

    public String getIdTipoPeriodo() {
        return idTipoPeriodo;
    }

    public void setSafIdPeriodo(Integer safIdPeriodo) {
        this.safIdPeriodo = safIdPeriodo;
    }

    public Integer getSafIdPeriodo() {
        return safIdPeriodo;
    }

    public void setSafIdEjercicio(Integer safIdEjercicio) {
        this.safIdEjercicio = safIdEjercicio;
    }

    public Integer getSafIdEjercicio() {
        return safIdEjercicio;
    }

    public void setSaldoAplicar(BigDecimal saldoAplicar) {
        this.saldoAplicar = saldoAplicar;
    }

    public BigDecimal getSaldoAplicar() {
        return saldoAplicar;
    }

    public void setMontoSaldoAFavor(BigDecimal montoSaldoAFavor) {
        this.montoSaldoAFavor = montoSaldoAFavor;
    }

    public BigDecimal getMontoSaldoAFavor() {
        return montoSaldoAFavor;
    }

    public void setRemanenteHistorico(BigDecimal remanenteHistorico) {
        this.remanenteHistorico = remanenteHistorico;
    }

    public BigDecimal getRemanenteHistorico() {
        return remanenteHistorico;
    }

    public void setFechaCausacion(Date fechaCausacion) {
        if (null != fechaCausacion) {
            this.fechaCausacion = (Date)fechaCausacion.clone();
        } else {
            this.fechaCausacion = null;
        }
    }

    public Date getFechaCausacion() {
        if (fechaCausacion != null) {
            return (Date)fechaCausacion.clone();
        } else {
            return null;
        }
    }

    public void setRemanenteAct(BigDecimal remanenteAct) {
        this.remanenteAct = remanenteAct;
    }

    public BigDecimal getRemanenteAct() {
        return remanenteAct;
    }

    public void setIdOrigenSaldo(Integer idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    public Integer getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }


    public void setImpActualizado(BigDecimal impActualizado) {
        this.impActualizado = impActualizado;
    }

    public BigDecimal getImpActualizado() {
        return impActualizado;
    }

    public void setImpRemanente(BigDecimal impRemanente) {
        this.impRemanente = impRemanente;
    }

    public BigDecimal getImpRemanente() {
        return impRemanente;
    }

    public void setDiotNumOperacion(String diotNumOperacion) {
        this.diotNumOperacion = diotNumOperacion;
    }

    public String getDiotNumOperacion() {
        return diotNumOperacion;
    }

    public void setDiotFechaPresenta(Date diotFechaPresenta) {
        if (null != diotFechaPresenta) {
            this.diotFechaPresenta = (Date)diotFechaPresenta.clone();
        } else {
            this.diotFechaPresenta = null;
        }
    }

    public Date getDiotFechaPresenta() {
        if (diotFechaPresenta != null) {
            return (Date)diotFechaPresenta.clone();
        } else {
            return null;
        }
    }

    public void setSafNumOperacionDec(Integer safNumOperacionDec) {
        this.safNumOperacionDec = safNumOperacionDec;
    }

    public Integer getSafNumOperacionDec() {
        return safNumOperacionDec;
    }

    public void setTipoSaldo(Integer tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public Integer getTipoSaldo() {
        return tipoSaldo;
    }

    public void setEspSubOrigen(String espSubOrigen) {
        this.espSubOrigen = espSubOrigen;
    }

    public String getEspSubOrigen() {
        return espSubOrigen;
    }

    public void setPresentoDiot(Integer presentoDiot) {
        this.presentoDiot = presentoDiot;
    }

    public Integer getPresentoDiot() {
        return presentoDiot;
    }

    public void setNumControlRem(Integer numControlRem) {
        this.numControlRem = numControlRem;
    }

    public Integer getNumControlRem() {
        return numControlRem;
    }

    public void setEsRemanente(Integer esRemanente) {
        this.esRemanente = esRemanente;
    }

    public Integer getEsRemanente() {
        return esRemanente;
    }


    public void setFechaPresentacionDec(Date fechaPresentacionDec) {
        if (null != fechaPresentacionDec) {
            this.fechaPresentacionDec = (Date)fechaPresentacionDec.clone();
        } else {
            this.fechaPresentacionDec = null;
        }
    }

    public Date getFechaPresentacionDec() {
        if (fechaPresentacionDec != null) {
            return (Date)fechaPresentacionDec.clone();
        } else {
            return null;
        }
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setSaldoAFavor(BigDecimal saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public BigDecimal getSaldoAFavor() {
        return saldoAFavor;
    }

    public void setNormalFechaPres(Date normalFechaPres) {
        if (null != normalFechaPres) {
            this.normalFechaPres = (Date)normalFechaPres.clone();
        } else {
            this.normalFechaPres = null;
        }
    }

    public Date getNormalFechaPres() {
        if (null != normalFechaPres) {
            return (Date)normalFechaPres.clone();
        } else {
            return null;
        }
    }

    public void setNormalNumOperacion(Long normalNumOperacion) {
        this.normalNumOperacion = normalNumOperacion;
    }

    public Long getNormalNumOperacion() {
        return normalNumOperacion;
    }

    public void setNormalImporteSaf(BigDecimal normalImporteSaf) {
        this.normalImporteSaf = normalImporteSaf;
    }

    public BigDecimal getNormalImporteSaf() {
        return normalImporteSaf;
    }

    public void setFolioServTemp(Integer folioServTemp) {
        this.folioServTemp = folioServTemp;
    }

    public Integer getFolioServTemp() {
        return folioServTemp;
    }

    public void setIdTipoDeclaracionTemp(Integer idTipoDeclaracionTemp) {
        this.idTipoDeclaracionTemp = idTipoDeclaracionTemp;
    }

    public Integer getIdTipoDeclaracionTemp() {
        return idTipoDeclaracionTemp;
    }


    public void setAcreditamiento(BigDecimal acreditamiento) {
        this.acreditamiento = acreditamiento;
    }

    public BigDecimal getAcreditamiento() {
        return acreditamiento;
    }

    public void setDevueltoCompensado(BigDecimal devueltoCompensado) {
        this.devueltoCompensado = devueltoCompensado;
    }

    public BigDecimal getDevueltoCompensado() {
        return devueltoCompensado;
    }

    public void setNumOperacionDec(String numOperacionDec) {
        this.numOperacionDec = numOperacionDec;
    }

    public String getNumOperacionDec() {
        return numOperacionDec;
    }
}
