package mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import mx.gob.sat.siat.dyc.trabajo.service.impl.ExtractorInfoAnalisisDesServiceImpl;

import org.apache.log4j.Logger;


@ManagedBean(name = "mbExtracInfSol")
@SessionScoped
public class ExtractorInfoSolicitudMB
{
    private static final Logger LOG = Logger.getLogger(ExtractorInfoSolicitudMB.class.getName());
    
    private String numControl;
    
    private String infoDycpServicio;
    private String infoDycpAvisoComp;
    private String infoDycpCompensacion;
    private String infoDyctResolComp;
    private String infoDyctDocumento;
    private String infoDycpSolicitud;
    private String infoDyctResolucion;
    private String infoDyctDeclaracion;
    private String infoDyctFacultades;
    private String infoDyctReqInfo;
    private String infoDyctExpediente;
    private String infoDyctArchivo;
    private String infoDyctContribuyente;
    private String infoDyctOtraInfoReq;
    private String infoDyctAnexoReq;

    private Integer idSaldoIcep;
    private String infoDycpSaldoIcep;
    private String infoDyctMovDevolucion;
    private String infoDycpCompensacionOrigen;
    private String infoDycpCompensacionDestino;
    private String infoDyctDeclaraIcep;
    private String infoDyctMovsaldo;
    private String infoDycpSolicitudSaldos;
    private String infoDyctCompHistOrigen;
    private String infoDyctCompHistoricaDestino;
    
    @ManagedProperty(value = "#{bdAnalisisDesarrollo}")
    private ExtractorInfoAnalisisDesServiceImpl delegate;
    
    public void extraerInfo()
    {
        LOG.debug("numControl ->" + numControl);
        Map<String, String> infoSolicitud = delegate.extraerInfoSolicitud(numControl);
        infoDycpServicio = infoSolicitud.get("infoDycpServicio");
        infoDycpAvisoComp = infoSolicitud.get("infoDycpAvisoComp");
        infoDycpCompensacion = infoSolicitud.get("infoDycpCompensacion");
        infoDyctResolComp = infoSolicitud.get("infoDyctResolComp");
        infoDyctDocumento = infoSolicitud.get("infoDyctDocumento");
        infoDycpSolicitud = infoSolicitud.get("infoDycpSolicitud");
        setInfoDyctResolucion(infoSolicitud.get("infoDyctResolucion"));
        setInfoDyctDeclaracion(infoSolicitud.get("infoDyctDeclaracion"));
        infoDyctFacultades = infoSolicitud.get("infoDyctFacultades");
        infoDyctReqInfo = infoSolicitud.get("infoDyctReqInfo");
        infoDyctExpediente = infoSolicitud.get("infoDyctExpediente");
        infoDyctArchivo = infoSolicitud.get("infoDyctArchivo");
        infoDyctContribuyente = infoSolicitud.get("infoDyctContribuyente");
        infoDyctOtraInfoReq = infoSolicitud.get("infoDyctOtraInfoReq");
        infoDyctAnexoReq = infoSolicitud.get("infoDyctAnexoReq");
    }
    
    public void extraerInfoSaldos()
    {
        LOG.debug("idSaldoIcep ->" + idSaldoIcep);
        Map<String, String> infoSaldos = delegate.extraerInfoSaldos(idSaldoIcep);
        infoDycpSaldoIcep = infoSaldos.get("infoDyctSaldoIcep");
        infoDyctMovDevolucion = infoSaldos.get("infoDyctMovDevolucion");
        infoDycpCompensacionOrigen = infoSaldos.get("infoDycpCompensacionOrigen");
        infoDycpCompensacionDestino = infoSaldos.get("infoDycpCompensacionDestino");
        infoDyctDeclaraIcep = infoSaldos.get("infoDyctDeclaraIcep");
        infoDyctMovsaldo = infoSaldos.get("infoDyctMovsaldo");
        setInfoDycpSolicitudSaldos(infoSaldos.get("infoDycpSolicitudSaldos"));
        infoDyctCompHistOrigen = infoSaldos.get("infoDyctCompHistOrigen");
        infoDyctCompHistoricaDestino = infoSaldos.get("infoDyctCompHistDest");
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getInfoDycpSolicitud() {
        return infoDycpSolicitud;
    }

    public void setInfoDycpSolicitud(String infoDycpSolicitud) {
        this.infoDycpSolicitud = infoDycpSolicitud;
    }

    public String getInfoDycpServicio() {
        return infoDycpServicio;
    }

    public void setInfoDycpServicio(String infoDycpServicio) {
        this.infoDycpServicio = infoDycpServicio;
    }

    public ExtractorInfoAnalisisDesServiceImpl getDelegate() {
        return delegate;
    }

    public void setDelegate(ExtractorInfoAnalisisDesServiceImpl delegate) {
        this.delegate = delegate;
    }

    public String getInfoDyctResolComp() {
        return infoDyctResolComp;
    }

    public void setInfoDyctResolComp(String infoDyctResolComp) {
        this.infoDyctResolComp = infoDyctResolComp;
    }

    public String getInfoDyctDocumento() {
        return infoDyctDocumento;
    }

    public void setInfoDyctDocumento(String infoDyctDocumento) {
        this.infoDyctDocumento = infoDyctDocumento;
    }

    public String getInfoDycpCompensacion() {
        return infoDycpCompensacion;
    }

    public void setInfoDycpCompensacion(String infoDycpCompensacion) {
        this.infoDycpCompensacion = infoDycpCompensacion;
    }

    public String getInfoDyctResolucion() {
        return infoDyctResolucion;
    }

    public void setInfoDyctResolucion(String infoDyctResolucion) {
        this.infoDyctResolucion = infoDyctResolucion;
    }

    public String getInfoDyctDeclaracion() {
        return infoDyctDeclaracion;
    }

    public void setInfoDyctDeclaracion(String infoDyctDeclaracion) {
        this.infoDyctDeclaracion = infoDyctDeclaracion;
    }

    public String getInfoDyctFacultades() {
        return infoDyctFacultades;
    }

    public void setInfoDyctFacultades(String infoDyctFacultades) {
        this.infoDyctFacultades = infoDyctFacultades;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public String getInfoDycpSaldoIcep() {
        return infoDycpSaldoIcep;
    }

    public void setInfoDycpSaldoIcep(String infoDycpSaldoIcep) {
        this.infoDycpSaldoIcep = infoDycpSaldoIcep;
    }

    public String getInfoDyctMovDevolucion() {
        return infoDyctMovDevolucion;
    }

    public void setInfoDyctMovDevolucion(String infoDyctMovDevolucion) {
        this.infoDyctMovDevolucion = infoDyctMovDevolucion;
    }

    public String getInfoDycpCompensacionOrigen() {
        return infoDycpCompensacionOrigen;
    }

    public void setInfoDycpCompensacionOrigen(String infoDycpCompensacionOrigen) {
        this.infoDycpCompensacionOrigen = infoDycpCompensacionOrigen;
    }

    public String getInfoDycpCompensacionDestino() {
        return infoDycpCompensacionDestino;
    }

    public void setInfoDycpCompensacionDestino(String infoDycpCompensacionDestino) {
        this.infoDycpCompensacionDestino = infoDycpCompensacionDestino;
    }

    public String getInfoDyctDeclaraIcep() {
        return infoDyctDeclaraIcep;
    }

    public void setInfoDyctDeclaraIcep(String infoDyctDeclaraIcep) {
        this.infoDyctDeclaraIcep = infoDyctDeclaraIcep;
    }

    public String getInfoDyctMovsaldo() {
        return infoDyctMovsaldo;
    }

    public void setInfoDyctMovsaldo(String infoDyctMovsaldo) {
        this.infoDyctMovsaldo = infoDyctMovsaldo;
    }

    public String getInfoDycpSolicitudSaldos() {
        return infoDycpSolicitudSaldos;
    }

    public void setInfoDycpSolicitudSaldos(String infoDycpSolicitudSaldos) {
        this.infoDycpSolicitudSaldos = infoDycpSolicitudSaldos;
    }

    public String getInfoDyctCompHistOrigen() {
        return infoDyctCompHistOrigen;
    }

    public void setInfoDyctCompHistOrigen(String infoDyctCompHistOrigen) {
        this.infoDyctCompHistOrigen = infoDyctCompHistOrigen;
    }

    public String getInfoDyctCompHistoricaDestino() {
        return infoDyctCompHistoricaDestino;
    }

    public void setInfoDyctCompHistoricaDestino(String infoDyctCompHistoricaDestino) {
        this.infoDyctCompHistoricaDestino = infoDyctCompHistoricaDestino;
    }

    public String getInfoDyctReqInfo() {
        return infoDyctReqInfo;
    }

    public void setInfoDyctReqInfo(String infoDyctReqInfo) {
        this.infoDyctReqInfo = infoDyctReqInfo;
    }

    public String getInfoDyctExpediente() {
        return infoDyctExpediente;
    }

    public void setInfoDyctExpediente(String infoDyctExpediente) {
        this.infoDyctExpediente = infoDyctExpediente;
    }

    public String getInfoDyctArchivo() {
        return infoDyctArchivo;
    }

    public void setInfoDyctArchivo(String infoDyctArchivo) {
        this.infoDyctArchivo = infoDyctArchivo;
    }

    public String getInfoDyctContribuyente() {
        return infoDyctContribuyente;
    }

    public void setInfoDyctContribuyente(String infoDyctContribuyente) {
        this.infoDyctContribuyente = infoDyctContribuyente;
    }

    public String getInfoDyctOtraInfoReq() {
        return infoDyctOtraInfoReq;
    }

    public void setInfoDyctOtraInfoReq(String infoDyctOtraInfoReq) {
        this.infoDyctOtraInfoReq = infoDyctOtraInfoReq;
    }

    public String getInfoDyctAnexoReq() {
        return infoDyctAnexoReq;
    }

    public void setInfoDyctAnexoReq(String infoDyctAnexoReq) {
        this.infoDyctAnexoReq = infoDyctAnexoReq;
    }

    public String getInfoDycpAvisoComp() {
        return infoDycpAvisoComp;
    }

    public void setInfoDycpAvisoComp(String infoDycpAvisoComp) {
        this.infoDycpAvisoComp = infoDycpAvisoComp;
    }
}
