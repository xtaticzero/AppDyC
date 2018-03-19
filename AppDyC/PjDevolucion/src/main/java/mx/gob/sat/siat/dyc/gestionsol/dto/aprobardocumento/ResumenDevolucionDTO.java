package mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO para la aprobacion del documento
 * @author Ericka Janeth Ibarra Ponce
 * @since 13/01/2014
 */
public class ResumenDevolucionDTO {
    public ResumenDevolucionDTO() {
        super();
    }

    private String numControl;
    private String rfcContribuyente;
    private String tipoPersona;
    private String nombre;
    private String razonSocial;
    private String tipoRequerimiento;
    private String tipoResolucion;
    private BigDecimal importeSolicitado;
    private BigDecimal importeAutorizado;
    private BigDecimal importeCompensado;
    private BigDecimal importeNeto;
    private BigDecimal importeActualizado;

    private BigDecimal saldoNegado;
    private String descTipoTramite;
    private int periodoCompensacion;
    private BigDecimal importeCompensacion;
    private String descOrigenSaldo;
    private Date fechaInicioOrigen;
    private BigDecimal montoSaldoFavor;
    private BigDecimal saldoAplicar;
    private int idTipoServicio;
    private int idEstadoDoc;
    private int idEstadoSol;
    private Integer idTipoResol;
    private String descTipoTramiteICEP;
    private String accion;
    private String concepto;
    private String periodo;
    private int ejercicio;
    private String impuesto;
    private String idTipoTramite;
    private int resolucionAutomatica;
    private String cepOrigen;
    private String cepDestino;
    
    private String numEmpleadoDict;
    private String nombreDict;

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setTipoResolucion(String tipoResolucion) {
        this.tipoResolucion = tipoResolucion;
    }

    public String getTipoResolucion() {
        return tipoResolucion;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImporteAutorizado(BigDecimal importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public BigDecimal getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteActualizado(BigDecimal importeActualizado) {
        this.importeActualizado = importeActualizado;
    }

    public BigDecimal getImporteActualizado() {
        return importeActualizado;
    }


    public void setDescTipoTramite(String descTipoTramite) {
        this.descTipoTramite = descTipoTramite;
    }

    public String getDescTipoTramite() {
        return descTipoTramite;
    }

    public void setPeriodoCompensacion(int periodoCompensacion) {
        this.periodoCompensacion = periodoCompensacion;
    }

    public int getPeriodoCompensacion() {
        return periodoCompensacion;
    }

    public void setImporteCompensacion(BigDecimal importeCompensacion) {
        this.importeCompensacion = importeCompensacion;
    }

    public BigDecimal getImporteCompensacion() {
        return importeCompensacion;
    }

    public void setDescOrigenSaldo(String descOrigenSaldo) {
        this.descOrigenSaldo = descOrigenSaldo;
    }

    public String getDescOrigenSaldo() {
        return descOrigenSaldo;
    }

    public void setFechaInicioOrigen(Date fechaInicioOrigen) {
        if (fechaInicioOrigen != null) {
            this.fechaInicioOrigen = (Date)fechaInicioOrigen.clone();
        } else {
            this.fechaInicioOrigen = null;
        }
    }

    public Date getFechaInicioOrigen() {
        if (fechaInicioOrigen != null) {
            return (Date)fechaInicioOrigen.clone();
        } else {
            return null;
        }
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdEstadoDoc(int idEstadoDoc) {
        this.idEstadoDoc = idEstadoDoc;
    }

    public int getIdEstadoDoc() {
        return idEstadoDoc;
    }

    public void setIdEstadoSol(int idEstadoSol) {
        this.idEstadoSol = idEstadoSol;
    }

    public int getIdEstadoSol() {
        return idEstadoSol;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setSaldoAplicar(BigDecimal saldoAplicar) {
        this.saldoAplicar = saldoAplicar;
    }

    public BigDecimal getSaldoAplicar() {
        return saldoAplicar;
    }

    public void setMontoSaldoFavor(BigDecimal montoSaldoFavor) {
        this.montoSaldoFavor = montoSaldoFavor;
    }

    public BigDecimal getMontoSaldoFavor() {
        return montoSaldoFavor;
    }

    public void setDescTipoTramiteICEP(String descTipoTramiteICEP) {
        this.descTipoTramiteICEP = descTipoTramiteICEP;
    }

    public String getDescTipoTramiteICEP() {
        return descTipoTramiteICEP;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAccion() {
        return accion;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setIdTipoTramite(String idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public String getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setResolucionAutomatica(int resolucionAutomatica) {
        this.resolucionAutomatica = resolucionAutomatica;
    }

    public int getResolucionAutomatica() {
        return resolucionAutomatica;
    }

    public void setSaldoNegado(BigDecimal saldoNegado) {
        this.saldoNegado = saldoNegado;
    }

    public BigDecimal getSaldoNegado() {
        return saldoNegado;
    }

    public void setCepOrigen(String cepOrigen) {
        this.cepOrigen = cepOrigen;
    }

    public String getCepOrigen() {
        return cepOrigen;
    }

    public void setCepDestino(String cepDestino) {
        this.cepDestino = cepDestino;
    }

    public String getCepDestino() {
        return cepDestino;
    }

    public void setIdTipoResol(Integer idTipoResol) {
        this.idTipoResol = idTipoResol;
    }

    public Integer getIdTipoResol() {
        return idTipoResol;
    }
    
    public void setNumEmpleadoDict ( String numEmpleadoDict ){
        this.numEmpleadoDict = numEmpleadoDict;
    }
    public void setNombreDict ( String nombreDict ){
            this.nombreDict = nombreDict;
    }

    public String getNumEmpleadoDict (){
            return numEmpleadoDict;
    }
    public String getNombreDict (){
            return nombreDict;
    }
}
