/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.domain.contribuyente.ContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO;
import mx.gob.sat.siat.dyc.domain.icep.ObtieneIcepDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.AdicionarSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.enums.AceptarPropuestaEnum;
import mx.gob.sat.siat.dyc.util.constante.enums.CreditoFiscalEnum;
import mx.gob.sat.siat.dyc.util.constante.enums.EstadoDevISREnum;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoDeclaracionEnum;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoFirmaEnum;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;

/**
 *
 * @author root
 */
public class DatosTramiteISRDetalleVO implements Serializable {

    private static final long serialVersionUID = -1169045806293706607L;

    private String mensajeRespuestaConsulta;
    private String estadoConsulta;
    //estado del trámite de devolución
    private int idEstadoProceso;
    private long folioDeclaracion;
    //Nuevos Parametro
    private ContribuyenteDTO contribuyente;
    private DeclaracionDTO declaracion;
    //Tipos de Declaracion
    private DeterminacionISRDTO declaracionSAT;
    private DeterminacionISRDTO declaracionContribuyente;
    private DeterminacionISRDTO resultadoProceso;

    private int tipoDeclaracion;
    private Date fechaPresentacion;
    private String fechaPresentacionCadena;
    private int ejercicio;
    private int idPeriodo;
    private String descripcionPeriodo;
    private String cuetaClabe;
    private double montoISRAFavor;
    private String nombreBanco;

    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;

    private String folioMATDYC;
    private short tipoTramite;
    private short concepto;
    private short impuesto;
    private double saldoAPagar;
    private double saldoAPagarCalcular;
    private Date fechaHoraEstado;
    private String fechaHoraEstadoCadena;
    private String identificadorResultado;
    private String descripcionResultado;
    private Date fechaPago;
    private String fechaPagoCadena;
    private String motivoRechazoPago;
    private String idEstadoProcesoDetalle;
    private String tipoDeTramiteDes;
    private String folioMATDYCNumeroControl;
    private String conceptoDes;
    private TramiteDTO tramite;
    private String saldoAPagarCadena;
    private ObtieneIcepDTO icep;
    private AdicionarSolicitudDTO flagsSolicitud;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private List<RechazoTramiteVO> rechazosTramite;
    private List<InconsistenciaTramiteVO> inconsistenciasTramite;

    //CC automaticas
    private Date fechaEstado;
    private RetroalimentacionPagoVO retroalimentacionPago;
    private List<RetencionInconsistenciaVO> retencionInconsistencias;
    private List<DeduccionInconsistenciaVO> deduccionInconsistencias;
    private int idFirma;
    private short idTipoProceso;
    private short aceptoPropuestaRecibida;
    private short creditoFiscal;
    private String estatusConsulta;
    private long isrAFavorDelEjercicio;
    private long saldoCalculadoXSistema;
    private long idTipoResolucion;

    private long isrAcargoDelEjercicio;

    private TipoFirmaEnum tipoFirmaEnum;
    private TipoDeclaracionEnum tipoDeclaracionEnum;
    private AceptarPropuestaEnum aceptarPropuestaEnum;
    private CreditoFiscalEnum creditoFiscalEnum;

    public DatosTramiteISRDetalleVO() {
        fechaHoraEstadoCadena = ConstantesDyC.EMPTY_STRING;
        fechaPagoCadena = ConstantesDyC.EMPTY_STRING;
        idEstadoProcesoDetalle = ConstantesDyC.EMPTY_STRING;
        folioMATDYCNumeroControl = ConstantesDyC.EMPTY_STRING;
        saldoAPagarCadena = ConstantesDyC.EMPTY_STRING;
        fechaPresentacionCadena = ConstantesDyC.EMPTY_STRING;
        flagsSolicitud = new AdicionarSolicitudDTO();
        flagsSolicitud.setCompetencia(ConstantesTipoRol.AGAFF);
        flagsSolicitud.setShowEdoCta(Boolean.TRUE);
    }

    public double getMontoISRAFavor() {
        return montoISRAFavor;
    }

    public void setMontoISRAFavor(double montoISRAFavor) {
        this.montoISRAFavor = montoISRAFavor;
    }

    public long getFolioDeclaracion() {
        return folioDeclaracion;
    }

    public void setFolioDeclaracion(long folioDeclaracion) {
        this.folioDeclaracion = folioDeclaracion;
    }

    public int getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setTipoDeclaracion(int tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Date getFechaPresentacion() {

        return (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion != null ? new Date(fechaPresentacion.getTime()) : null;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getDescripcionPeriodo() {
        return descripcionPeriodo;
    }

    public void setDescripcionPeriodo(String descripcionPeriodo) {
        this.descripcionPeriodo = descripcionPeriodo;
    }

    public String getFolioMATDYC() {
        return folioMATDYC;
    }

    public void setFolioMATDYC(String folioMATDYC) {
        this.folioMATDYC = folioMATDYC;
    }

    public short getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(short tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public short getConcepto() {
        return concepto;
    }

    public void setConcepto(short concepto) {
        this.concepto = concepto;
    }

    public short getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(short impuesto) {
        this.impuesto = impuesto;
    }

    public double getSaldoAPagar() {

        if (validaTramiteAuto(idEstadoProceso)) {
            return new Double("0.0");
        }

        if (validaTramiteCancelar(idEstadoProceso)) {
            return new Double("0.0");
        }

        if (idEstadoProceso == EstadoDevISREnum.PROCESO_PAGO.getId() || idEstadoProceso == EstadoDevISREnum.PAGADO.getId() || idEstadoProceso == EstadoDevISREnum.NO_PAGADO.getId()) {
            return saldoAPagar;
        }

        return new Double("0.0");
    }

    public double getSaldoAPagarNR() {
        return saldoAPagar;
    }

    private boolean validaTramiteAuto(int estadoProceso) {

        boolean flgEstadoProceso = estadoProceso == EstadoDevISREnum.PROCESO.getId();
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.REVISION_POR_USUARIO.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.AUTORIZADA_POR_PROCESO.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.PROCEDENTE.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.AUTORIZADA_POR_AUTORIDAD.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.AUTORIZADA_POR_USUARIO.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.REPROCESO.getId());

        return flgEstadoProceso;
    }

    private boolean validaTramiteCancelar(int estadoProceso) {

        boolean flgEstadoProceso = estadoProceso == EstadoDevISREnum.PREAUTORIZADO.getId();
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.RECHAZADO_POR_USUARIO.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.RECHAZADO_POR_PROCESO.getId());
        flgEstadoProceso = flgEstadoProceso || (estadoProceso == EstadoDevISREnum.RECHAZADO_POR_CONTROL_SALDO.getId());

        return flgEstadoProceso;
    }

    public void setSaldoAPagar(double saldoAPagar) {
        this.saldoAPagar = saldoAPagar;
        this.saldoAPagarCalcular = saldoAPagar;
    }

    public Date getFechaHoraEstado() {
        return (fechaHoraEstado != null) ? (Date) fechaHoraEstado.clone() : null;
    }

    public String getFechaHoraEstadoCadena() {

        if (validaTramiteAuto(idEstadoProceso)) {
            return fechaHoraEstadoCadena;
        }

        if (fechaHoraEstado != null) {
            return format.format(fechaHoraEstado);
        }

        return fechaHoraEstadoCadena;
    }

    public void setFechaHoraEstado(Date fechaHoraEstado) {
        this.fechaHoraEstado = fechaHoraEstado != null ? new Date(fechaHoraEstado.getTime()) : null;
    }

    public String getIdentificadorResultado() {
        return identificadorResultado;
    }

    public void setIdentificadorResultado(String identificadorResultado) {
        this.identificadorResultado = identificadorResultado;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }

    public void setDescripcionResultado(String descripcionResultado) {
        this.descripcionResultado = descripcionResultado;
    }

    public String getCuetaClabe() {
        return cuetaClabe;
    }

    public void setCuetaClabe(String cuetaClabe) {
        this.cuetaClabe = cuetaClabe;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public Date getFechaPago() {
        return (fechaPago != null) ? (Date) fechaPago.clone() : null;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago != null ? new Date(fechaPago.getTime()) : null;
    }

    public String getMotivoRechazoPago() {

        return motivoRechazoPago == null ? "" : motivoRechazoPago;
    }

    public void setMotivoRechazoPago(String motivoRechazoPago) {
        this.motivoRechazoPago = motivoRechazoPago;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public List<RechazoTramiteVO> getRechazosTramite() {
        return rechazosTramite;
    }

    public void setRechazosTramite(List<RechazoTramiteVO> rechazosTramite) {
        this.rechazosTramite = rechazosTramite;
    }

    public List<InconsistenciaTramiteVO> getInconsistenciasTramite() {
        return inconsistenciasTramite;
    }

    public void setInconsistenciasTramite(List<InconsistenciaTramiteVO> inconsistenciasTramite) {
        this.inconsistenciasTramite = inconsistenciasTramite;
    }

    public int getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(int idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public String getMensajeRespuestaConsulta() {
        return mensajeRespuestaConsulta;
    }

    public void setMensajeRespuestaConsulta(String mensajeRespuestaConsulta) {
        this.mensajeRespuestaConsulta = mensajeRespuestaConsulta;
    }

    public String getEstadoConsulta() {
        return estadoConsulta;
    }

    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }

    public String getTipoDeTramiteDes() {

        return tipoDeTramiteDes;
    }

    public void setTipoDeTramiteDes(String tipoDeTramiteDes) {
        this.tipoDeTramiteDes = tipoDeTramiteDes;
    }

    public String getConceptoDes() {
        return conceptoDes;
    }

    public void setConceptoDes(String conceptoDes) {
        this.conceptoDes = conceptoDes;
    }

    public String getFechaPresentacionCadena() {

        if (fechaPresentacion != null) {
            return format.format(fechaPresentacion);
        }

        return fechaPresentacionCadena;
    }

    public void setFechaPresentacionCadena(String fechaPresentacionCadena) {
        this.fechaPresentacionCadena = fechaPresentacionCadena;
    }

    public void setFechaHoraEstadoCadena(String fechaHoraEstadoCadena) {
        this.fechaHoraEstadoCadena = fechaHoraEstadoCadena;
    }

    public String getFechaPagoCadena() {

        if (validaTramiteCancelar(idEstadoProceso)) {
            return "";
        }

        if (idEstadoProceso == EstadoDevISREnum.PROCESO_PAGO.getId() || idEstadoProceso == EstadoDevISREnum.PAGADO.getId() || idEstadoProceso == EstadoDevISREnum.NO_PAGADO.getId()) {
            if (fechaPago != null) {
                return format.format(fechaPago);
            } else {
                return "";
            }
        }

        if (validaTramiteAuto(idEstadoProceso)) {
            return "";
        }

        return fechaPagoCadena;
    }

    public void setFechaPagoCadena(String fechaPagoCadena) {

        this.fechaPagoCadena = fechaPagoCadena;
    }

    public TramiteDTO getTramite() {
        return tramite;
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    public String getIdEstadoProcesoDetalle() {

        if (idEstadoProceso == EstadoDevISREnum.PAGADO.getId()) {
            return "Depositado";
        }

        if (idEstadoProceso == EstadoDevISREnum.PROCESO_PAGO.getId()) {
            return EstadoDevISREnum.PROCESO_PAGO.getDescripcion();
        }

        if (idEstadoProceso == EstadoDevISREnum.NO_PAGADO.getId()) {
            return EstadoDevISREnum.NO_PAGADO.getDescripcion();
        }

        if (validaTramiteCancelar(idEstadoProceso)) {
            return idEstadoProcesoDetalle;
        }

        if (validaTramiteAuto(idEstadoProceso)) {
            return idEstadoProcesoDetalle;
        }

        return idEstadoProcesoDetalle;
    }

    public void setIdEstadoProcesoDetalle(String idEstadoProcesoDetalle) {
        this.idEstadoProcesoDetalle = idEstadoProcesoDetalle;
    }

    public String getFolioMATDYCNumeroControl() {

        if (idEstadoProceso == EstadoDevISREnum.PAGADO.getId() || idEstadoProceso == EstadoDevISREnum.PROCESO_PAGO.getId() || idEstadoProceso == EstadoDevISREnum.NO_PAGADO.getId()) {
            return folioMATDYC;
        }

        if (validaTramiteCancelar(idEstadoProceso)) {
            return folioMATDYCNumeroControl;
        }

        if (validaTramiteAuto(idEstadoProceso)) {
            return folioMATDYCNumeroControl;
        }

        return folioMATDYCNumeroControl;
    }

    public void setFolioMATDYCNumeroControl(String folioMATDYCNumeroControl) {
        this.folioMATDYCNumeroControl = folioMATDYCNumeroControl;
    }

    public String getSaldoAPagarCadena() {

        if (validaTramiteAuto(idEstadoProceso)) {
            return ConstantesDyC.EMPTY_STRING;
        }

        if (validaTramiteCancelar(idEstadoProceso)) {
            return ConstantesDyC.EMPTY_STRING;
        }

        if (idEstadoProceso == EstadoDevISREnum.PROCESO_PAGO.getId() || idEstadoProceso == EstadoDevISREnum.PAGADO.getId() || idEstadoProceso == EstadoDevISREnum.NO_PAGADO.getId()) {
            return "$ " + saldoAPagar + "0";
        }

        return saldoAPagarCadena;
    }

    public void setSaldoAPagarCadena(String saldoAPagarCadena) {
        this.saldoAPagarCadena = saldoAPagarCadena;
    }

    public ObtieneIcepDTO getIcep() {
        return icep;
    }

    public void setIcep(ObtieneIcepDTO icep) {
        this.icep = icep;
    }

    public AdicionarSolicitudDTO getFlagsSolicitud() {
        return flagsSolicitud;
    }

    public void setFlagsSolicitud(AdicionarSolicitudDTO flagsSolicitud) {
        this.flagsSolicitud = flagsSolicitud;
    }

    public double getSaldoAPagarCalcular() {
        return saldoAPagarCalcular;
    }

    public void setSaldoAPagarCalcular(double saldoAPagarCalcular) {
        this.saldoAPagarCalcular = saldoAPagarCalcular;
    }

    public DeterminacionISRDTO getDeclaracionSAT() {
        return declaracionSAT;
    }

    public void setDeclaracionSAT(DeterminacionISRDTO declaracionSAT) {
        this.declaracionSAT = declaracionSAT;
    }

    public DeterminacionISRDTO getDeclaracionContribuyente() {
        return declaracionContribuyente;
    }

    public void setDeclaracionContribuyente(DeterminacionISRDTO declaracionContribuyente) {
        this.declaracionContribuyente = declaracionContribuyente;
    }

    public DeterminacionISRDTO getResultadoProceso() {
        return resultadoProceso;
    }

    public void setResultadoProceso(DeterminacionISRDTO resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

    public ContribuyenteDTO getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(ContribuyenteDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public DeclaracionDTO getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(DeclaracionDTO declaracion) {
        this.declaracion = declaracion;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public Date getFechaEstado() {
        if (fechaEstado != null) {
            return (Date) fechaEstado.clone();
        } else {
            return null;
        }
    }

    public void setFechaEstado(Date fechaEstado) {
        if (fechaEstado != null) {
            this.fechaEstado = (Date) fechaEstado.clone();
        } else {
            this.fechaEstado = null;
        }
    }

    public RetroalimentacionPagoVO getRetroalimentacionPago() {
        return retroalimentacionPago;
    }

    public void setRetroalimentacionPago(RetroalimentacionPagoVO retroalimentacionPago) {
        this.retroalimentacionPago = retroalimentacionPago;
    }

    public List<RetencionInconsistenciaVO> getRetencionInconsistencias() {
        return retencionInconsistencias;
    }

    public void setRetencionInconsistencias(List<RetencionInconsistenciaVO> retencionInconsistencias) {
        this.retencionInconsistencias = retencionInconsistencias;
    }

    public List<DeduccionInconsistenciaVO> getDeduccionInconsistencias() {
        return deduccionInconsistencias;
    }

    public void setDeduccionInconsistencias(List<DeduccionInconsistenciaVO> deduccionInconsistencias) {
        this.deduccionInconsistencias = deduccionInconsistencias;
    }

    public int getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(int idFirma) {
        this.idFirma = idFirma;
    }

    public short getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(short idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public short getAceptoPropuestaRecibida() {
        return aceptoPropuestaRecibida;
    }

    public void setAceptoPropuestaRecibida(short aceptoPropuestaRecibida) {
        this.aceptoPropuestaRecibida = aceptoPropuestaRecibida;
    }

    public short getCreditoFiscal() {
        return creditoFiscal;
    }

    public void setCreditoFiscal(short creditoFiscal) {
        this.creditoFiscal = creditoFiscal;
    }

    public String getEstatusConsulta() {
        return estatusConsulta;
    }

    public void setEstatusConsulta(String estatusConsulta) {
        this.estatusConsulta = estatusConsulta;
    }

    public long getIsrAFavorDelEjercicio() {
        return isrAFavorDelEjercicio;
    }

    public void setIsrAFavorDelEjercicio(long isrAFavorDelEjercicio) {
        this.isrAFavorDelEjercicio = isrAFavorDelEjercicio;
    }

    public long getSaldoCalculadoXSistema() {
        return saldoCalculadoXSistema;
    }

    public void setSaldoCalculadoXSistema(long saldoCalculadoXSistema) {
        this.saldoCalculadoXSistema = saldoCalculadoXSistema;
    }

    public long getIdTipoResolucion() {
        return idTipoResolucion;
    }

    public void setIdTipoResolucion(long idTipoResolucion) {
        this.idTipoResolucion = idTipoResolucion;
    }

    public long getIsrAcargoDelEjercicio() {
        return isrAcargoDelEjercicio;
    }

    public void setIsrAcargoDelEjercicio(long isrAcargoDelEjercicio) {
        this.isrAcargoDelEjercicio = isrAcargoDelEjercicio;
    }

    public TipoFirmaEnum getTipoFirmaEnum() {
        return tipoFirmaEnum;
    }

    public void setTipoFirmaEnum(TipoFirmaEnum tipoFirmaEnum) {
        this.tipoFirmaEnum = tipoFirmaEnum;
    }

    public TipoDeclaracionEnum getTipoDeclaracionEnum() {
        return tipoDeclaracionEnum;
    }

    public void setTipoDeclaracionEnum(TipoDeclaracionEnum tipoDeclaracionEnum) {
        this.tipoDeclaracionEnum = tipoDeclaracionEnum;
    }

    public AceptarPropuestaEnum getAceptarPropuestaEnum() {
        return aceptarPropuestaEnum;
    }

    public void setAceptarPropuestaEnum(AceptarPropuestaEnum aceptarPropuestaEnum) {
        this.aceptarPropuestaEnum = aceptarPropuestaEnum;
    }

    public CreditoFiscalEnum getCreditoFiscalEnum() {
        return creditoFiscalEnum;
    }

    public void setCreditoFiscalEnum(CreditoFiscalEnum creditoFiscalEnum) {
        this.creditoFiscalEnum = creditoFiscalEnum;
    }

    @Override
    public String toString() {
        
        return "DatosTramiteISRDetalleVO{" + "mensajeRespuestaConsulta=" + mensajeRespuestaConsulta + ", estadoConsulta=" + estadoConsulta + ", idEstadoProceso=" + idEstadoProceso + ", folioDeclaracion=" + folioDeclaracion + ", contribuyente=" + contribuyente + ", declaracion=" + declaracion + ", declaracionSAT=" + declaracionSAT + ", declaracionContribuyente=" + declaracionContribuyente + ", resultadoProceso=" + resultadoProceso + ", tipoDeclaracion=" + tipoDeclaracion + ", fechaPresentacion=" + fechaPresentacion + ", fechaPresentacionCadena=" + fechaPresentacionCadena + ", ejercicio=" + ejercicio + ", idPeriodo=" + idPeriodo + ", descripcionPeriodo=" + descripcionPeriodo + ", cuetaClabe=" + cuetaClabe + ", montoISRAFavor=" + montoISRAFavor + ", nombreBanco=" + nombreBanco + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", nombres=" + nombres + ", folioMATDYC=" + folioMATDYC + ", tipoTramite=" + tipoTramite + ", concepto=" + concepto + ", impuesto=" + impuesto + ", saldoAPagar=" + saldoAPagar + ", saldoAPagarCalcular=" + saldoAPagarCalcular + ", fechaHoraEstado=" + fechaHoraEstado + ", fechaHoraEstadoCadena=" + fechaHoraEstadoCadena + ", identificadorResultado=" + identificadorResultado + ", descripcionResultado=" + descripcionResultado + ", fechaPago=" + fechaPago + ", fechaPagoCadena=" + fechaPagoCadena + ", motivoRechazoPago=" + motivoRechazoPago + ", idEstadoProcesoDetalle=" + idEstadoProcesoDetalle + ", tipoDeTramiteDes=" + tipoDeTramiteDes + ", folioMATDYCNumeroControl=" + folioMATDYCNumeroControl + ", conceptoDes=" + conceptoDes + ", tramite=" + tramite + ", saldoAPagarCadena=" + saldoAPagarCadena + ", icep=" + icep + ", flagsSolicitud=" + flagsSolicitud + ", format=" + format + ", rechazosTramite=" + rechazosTramite + ", inconsistenciasTramite=" + inconsistenciasTramite + ", fechaEstado=" + fechaEstado + ", retroalimentacionPago=" + retroalimentacionPago + ", retencionInconsistencias=" + retencionInconsistencias + ", deduccionInconsistencias=" + deduccionInconsistencias + ", idFirma=" + idFirma + ", idTipoProceso=" + idTipoProceso + ", aceptoPropuestaRecibida=" + aceptoPropuestaRecibida + ", creditoFiscal=" + creditoFiscal + ", estatusConsulta=" + estatusConsulta + ", isrAFavorDelEjercicio=" + isrAFavorDelEjercicio + ", saldoCalculadoXSistema=" + saldoCalculadoXSistema + ", idTipoResolucion=" + idTipoResolucion + ", isrAcargoDelEjercicio=" + isrAcargoDelEjercicio + ", tipoFirmaEnum=" + tipoFirmaEnum + ", tipoDeclaracionEnum=" + tipoDeclaracionEnum + ", aceptarPropuestaEnum=" + aceptarPropuestaEnum + ", creditoFiscalEnum=" + creditoFiscalEnum + '}';
    }

}
