/*
 *  Todos los Derechos Reservados 2013 SAT.
 *  Servicio de Administracion Tributaria (SAT).
 *
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.IOException;
import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.casocomp.service.DictaminarCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridEstadoDocBean;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridIcepsOrigenSaldoBean;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.consultarexpediente.DyCConsultarExpedienteMB;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;


@ManagedBean(name = "pDictCCMB")
@ViewScoped
public class PDictaminarCasoCompensacionMB {
    private static final Logger LOG = Logger.getLogger(PDictaminarCasoCompensacionMB.class);

    @ManagedProperty("#{serviceDictaminarCompensacion}")
    private DictaminarCompensacionService service;

    @ManagedProperty(value = "#{dyCConsultarExpedienteMB}")
    private DyCConsultarExpedienteMB dyCConsultarExpedienteMB;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    @ManagedProperty(value = "#{aprobarResolucionComp}")
    private PAprobarResolucionMB aprobarResolucionComp;

    @ManagedProperty(value = "#{emitirCartaContMB}")
    private EmitirCartaContribuyenteMB emitirCartaContMB;
    
    @ManagedProperty("#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    private String tipoTramite;
    private String estadoResolucion;
    private String estadoCompensacion;
    private String fechaAprobacionResolucion;

    private BigDecimal importeCompensado;
    private String impuesto;
    private String concepto;
    private String ejercicio;
    private String periodo;
    private String tipoDeclaracion;
    private String fechaPresentacion;
    private String folioAviso;
    private boolean verOpciones;
    private Integer claveResol;
    private String nruler;
    
    public static final String AC ="AC";
    public static final String ROL = "SAT_DYC_DICT";
    public static final String ESTADO_COM ="estadoCompensacion";
    public static final String REQUERIDO = "Requerido";
    


    private List<ItemComboBean> opciones;

    private FilaGridIcepsOrigenSaldoBean icepOrigenSaldo;

    private List<FilaGridEstadoDocBean> documentos;

    private String nota;

    private Integer idAccionSelec;
    private Integer idSelectLiquida;
    private boolean pnlLiquida;

    public PDictaminarCasoCompensacionMB() {
        LOG.debug("constructor PDictaminarCasoCompensacionMB");
        pnlLiquida = Boolean.TRUE;
    }

    @PostConstruct
    public void cargar() {
        LOG.debug("INICIO cargar");
        HashMap<String, Object> infoInicial =
            (HashMap<String, Object>)service.obtenerInfoIniDictaminarCC(mbSession.getNumControl(), mbSession.getRol(),
                                                                        mbSession.getTipoUnidadAdmva());
        this.tipoTramite = (String)infoInicial.get("tipoTramite");
        this.estadoResolucion = (String)infoInicial.get("estadoResolucion");
        this.estadoCompensacion = (String)infoInicial.get("estadoCompensacion");
        this.fechaAprobacionResolucion = (String)infoInicial.get("fechaAprobacionResolucion");
        this.importeCompensado = (BigDecimal)infoInicial.get("importeCompensado");
        this.impuesto = (String)infoInicial.get("impuesto");
        this.concepto = (String)infoInicial.get("concepto");
        this.ejercicio = (String)infoInicial.get("ejercicio");
        this.periodo = (String)infoInicial.get("periodo");
        this.tipoDeclaracion = (String)infoInicial.get("tipoDeclaracion");
        this.fechaPresentacion = (String)infoInicial.get("fechaPresentacion");
        folioAviso = (String)infoInicial.get("folioAviso");
        this.icepOrigenSaldo = (FilaGridIcepsOrigenSaldoBean)infoInicial.get("icepOrigenSaldo");
        this.documentos = (List<FilaGridEstadoDocBean>)infoInicial.get("documentos");
        opciones = (List<ItemComboBean>)infoInicial.get("accionesPermitidas");

        if (null != opciones) {
            this.verOpciones =
                    opciones.size() != ConstantesDyCNumerico.VALOR_0 && mbSession.getParametroRegresar() != ConstantesDyCNumerico.VALOR_7 ?
                    Boolean.TRUE : Boolean.FALSE;
        } else {
            this.verOpciones = Boolean.FALSE;
        }

        if (mbSession.getParametroRegresar() == ConstantesDyCNumerico.VALOR_7) {
            mbSession.setNRule(ConstantesDyC.CADENA_BANDEJA_COMPENSACIONES);
        }

        nruler = mbSession.getNRule();
        mbSession.setAccButton('C');
        boolean condicion = null != mbSession.getRol() && infoInicial.get("estadoCompensacion") != null && mbSession.getRol().getNombre().equals(ROL);
        condicion = condicion && infoInicial.get(ESTADO_COM).toString().trim().equals(REQUERIDO) && mbSession.getNumControl().contains(AC);
       if(condicion){       
                ItemComboBean item = new ItemComboBean(ConstantesDyCNumerico.VALOR_10, ConstantesAdministrarSolicitud.COMBO_MENSAJE10);
                opciones.add(item);
       } 
    }

    public String regresarPantallaAnterior() {

        LOG.debug("################" + mbSession.getParametroRegresar());

        switch (mbSession.getParametroRegresar()) {
        case ConstantesDyCNumerico.VALOR_7:
            return "dicCasoComp1";

        case ConstantesDyCNumerico.VALOR_8:
            mbSession.setParametroRegreso(1);
            return "dicCasoComp2";

        case ConstantesDyCNumerico.VALOR_9:
            mbSession.setParametroRegreso(2);
            return "dicCasoComp3";

        default:
            return "";
        }

    }

    public void consultarExpediente() {
        String numControl = mbSession.getNumControl();
        String rfcContribuyente = mbSession.getRfcContribuyente();
        Integer claveAdm = mbSession.getClaveAdm();
        dyCConsultarExpedienteMB.setNumControl(numControl);
        dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_1);
        dyCConsultarExpedienteMB.setRfcContribuyente(rfcContribuyente);
        dyCConsultarExpedienteMB.setClaveAdm(claveAdm);
        dyCConsultarExpedienteMB.setFolioAviso(getFolioAviso());
        dyCConsultarExpedienteMB.setRemanenteHistorico(icepOrigenSaldo.getRemanenteHistorico());
        dyCConsultarExpedienteMB.init();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../gestionsol/consultarExpediente.jsf");
        } catch (IOException ioe) {
            LOG.info(ioe);
        }
    }

    public String manejarAceptarPDictaminarCC() {
        LOG.debug("manejarAceptarPDictaminarCC idAccionSelec ->" + idAccionSelec);
        mbSession.setSalida(null);
        switch (idAccionSelec) {
        case ConstantesDyCNumerico.VALOR_0:
            JSFUtils.messageGlobal("Seleccione una opción valida", FacesMessage.SEVERITY_ERROR);
            break;
        case ConstantesDyCNumerico.VALOR_2:
            mbSession.setSalida("emitirRequerimiento");
            break;
        case ConstantesDyCNumerico.VALOR_3:
            mbSession.setSalida("emitirReqConfOpProv");
            break;
        case ConstantesDyCNumerico.VALOR_4:
        case ConstantesDyCNumerico.VALOR_5:
            if (ConstantesDyCNumerico.VALOR_4 == idAccionSelec) {
                mbSession.setTipoDocumento(ConstantesACC.TIPODOC_REQ_PRESC_CONTTE);
            } else if (ConstantesDyCNumerico.VALOR_5 == idAccionSelec) {
                mbSession.setTipoDocumento(ConstantesACC.TIPODOC_CARTA_INVITACION);
            }
            emitirCartaContMB.generarCarta();
            RequestContext.getCurrentInstance().execute("dlgEnviarAprob.show()");
            if (!mbSession.getMensaje().equals("Error al generar la carta")) {
                RequestContext.getCurrentInstance().execute("descarga()");
                FacesContext.getCurrentInstance().addMessage("msgAResol",
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, mbSession.getMensaje(),
                                                                              null));
            }
            mbSession.setMensaje(null);
            break;
        case ConstantesDyCNumerico.VALOR_6:
            mbSession.setSalida("cargarDescPapelesTrabajo");
            break;
        case ConstantesDyCNumerico.VALOR_11:
            mbSession.setSalida("dictaminar");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgTes.show();"); 
            break;
        default:
            manejarAceptarPDictaminarCC(idAccionSelec);
            break;
        }
        LOG.debug("mbSession.getSalida() ->" + mbSession.getSalida());
        return mbSession.getSalida();
    }

    private void manejarAceptarPDictaminarCC(Integer opcion) {
        switch (opcion) {
        case ConstantesDyCNumerico.VALOR_8:
            pnlLiquida = Boolean.TRUE;
            RequestContext.getCurrentInstance().execute("dlgEmitirResolucion.show()");
            break;
        case ConstantesDyCNumerico.VALOR_9:
        case ConstantesDyCNumerico.VALOR_10:
            if (ConstantesDyCNumerico.VALOR_9 == idAccionSelec) {
                aprobarResolucionComp.setClaveResolucion(ConstantesDyCNumerico.VALOR_1);
                aprobarResolucionComp.setDlgTitle("Aprobar resolución");
            } else if (ConstantesDyCNumerico.VALOR_10 == idAccionSelec) {
                aprobarResolucionComp.setClaveResolucion(ConstantesDyCNumerico.VALOR_2);
                aprobarResolucionComp.setDlgTitle("No aprobar resolución");
            }
            aprobarResolucionComp.setMostrarDlgSuperiores(Boolean.FALSE);
            RequestContext.getCurrentInstance().execute("dlgAResolucion.show()");
            break;
        default:
            mbSession.setSalida("bandejaCompensaciones");
            break;
        }
    }

    public String selectLiquidacion() {
        mbSession.setSalida(null);
        switch (idSelectLiquida) {
        case ConstantesDyCNumerico.VALOR_0:
            FacesContext.getCurrentInstance().addMessage("msgLiquida",
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione una opción valida",
                                                                          null));
            break;
        case ConstantesDyCNumerico.VALOR_1:
            pnlLiquida = false;
            mbSession.setIdNumAprob(ConstantesDyCNumerico.VALOR_0);
            break;
        case ConstantesDyCNumerico.VALOR_2:
            mbSession.setSalida("emitirLiquidacion");
            mbSession.setTipoLiquidacion('C');
            mbSession.setMensaje(null);
            break;
        case ConstantesDyCNumerico.VALOR_3:
            mbSession.setSalida("emitirLiquidacion");
            mbSession.setTipoLiquidacion('S');
            mbSession.setMensaje(null);
            break;
        default:
            mbSession.setSalida("bandejaCompensaciones");
            break;
        }
        return mbSession.getSalida();
    }
    
     public void cambioDeEstadoEnProceso() 
    {
        try 
        {
            int idEstadoCom = ConstantesDyCNumerico.VALOR_3;
            int idEstadoReq = ConstantesDyCNumerico.VALOR_5;
            
            dycpSolicitudService.actualizarIdEstadoSolDic(idEstadoCom, mbSession.getNumControl(), idEstadoReq);
           
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgCloseD.show();"); 
        } catch (SIATException ex) {
            LOG.error("cambioDeEstadoEnProceso ->" + ex.getMessage());
        }
    }
    
      public void cambioDeEstadoEnProcesoCerrar() throws IOException 
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/resources/pages/casoComp/bandejaCasosCompensacion.jsf?facesRedirect=true");
     
    }
     
     

    public void insertarNota() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("numControlComp", mbSession.getNumControl());
        params.put("texto", this.getNota());
        params.put("nomCompUsuario", mbSession.getNombreEmpleado());
        try {
            getService().insertarNota(params);
            this.setNota(null);
            JSFUtils.messageGlobalDetail(FacesMessage.SEVERITY_INFO, "La nota ha sido registrada en el expediente",
                                         null);
        } catch (Exception e) {
            JSFUtils.messageGlobalDetail(FacesMessage.SEVERITY_ERROR, "ERROR :" + e.getMessage(), null);
        }

    }

    public void pintarMensaje() {
        FacesContext.getCurrentInstance().addMessage(null,
                                                     new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje escrito en PDictaminarCasoCompensacionMB",
                                                                      "pintarMensaje()"));
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getEstadoResolucion() {
        return estadoResolucion;
    }

    public void setEstadoResolucion(String estadoResolucion) {
        this.estadoResolucion = estadoResolucion;
    }

    public String getEstadoCompensacion() {
        return estadoCompensacion;
    }

    public void setEstadoCompensacion(String estadoCompensacion) {
        this.estadoCompensacion = estadoCompensacion;
    }

    public String getFechaAprobacionResolucion() {
        return fechaAprobacionResolucion;
    }

    public void setFechaAprobacionResolucion(String fechaAprobacionResolucion) {
        this.fechaAprobacionResolucion = fechaAprobacionResolucion;
    }

    public List<FilaGridEstadoDocBean> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<FilaGridEstadoDocBean> documentos) {
        this.documentos = documentos;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getIdAccionSelec() {
        return idAccionSelec;
    }

    public void setIdAccionSelec(Integer idAccionSelec) {
        this.idAccionSelec = idAccionSelec;
    }

    public List<ItemComboBean> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<ItemComboBean> opciones) {
        this.opciones = opciones;
    }

    public DictaminarCompensacionService getService() {
        return service;
    }

    public void setService(DictaminarCompensacionService service) {
        this.service = service;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public FilaGridIcepsOrigenSaldoBean getIcepOrigenSaldo() {
        return icepOrigenSaldo;
    }

    public void setIcepOrigenSaldo(FilaGridIcepsOrigenSaldoBean icepOrigenSaldo) {
        this.icepOrigenSaldo = icepOrigenSaldo;
    }

    public String getFolioAviso() {
        return folioAviso;
    }

    public void setFolioAviso(String folioAviso) {
        this.folioAviso = folioAviso;
    }

    public void setVerOpciones(boolean verOpciones) {
        this.verOpciones = verOpciones;
    }

    public boolean isVerOpciones() {
        return verOpciones;
    }

    public void setIdSelectLiquida(Integer idSelectLiquida) {
        this.idSelectLiquida = idSelectLiquida;
    }

    public Integer getIdSelectLiquida() {
        return idSelectLiquida;
    }

    public void setPnlLiquida(boolean pnlLiquida) {
        this.pnlLiquida = pnlLiquida;
    }

    public boolean isPnlLiquida() {
        return pnlLiquida;
    }

    public void setClaveResol(Integer claveResol) {
        this.claveResol = claveResol;
    }

    public Integer getClaveResol() {
        return claveResol;
    }

    public void setAprobarResolucionComp(PAprobarResolucionMB aprobarResolucionComp) {
        this.aprobarResolucionComp = aprobarResolucionComp;
    }

    public PAprobarResolucionMB getAprobarResolucionComp() {
        return aprobarResolucionComp;
    }

    public void setEmitirCartaContMB(EmitirCartaContribuyenteMB emitirCartaContMB) {
        this.emitirCartaContMB = emitirCartaContMB;
    }

    public EmitirCartaContribuyenteMB getEmitirCartaContMB() {
        return emitirCartaContMB;
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public void setDyCConsultarExpedienteMB(DyCConsultarExpedienteMB dyCConsultarExpedienteMB) {
        this.dyCConsultarExpedienteMB = dyCConsultarExpedienteMB;
    }

    public DyCConsultarExpedienteMB getDyCConsultarExpedienteMB() {
        return dyCConsultarExpedienteMB;
    }

    public void setNruler(String nruler) {
        this.nruler = nruler;
    }

    public String getNruler() {
        return nruler;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }
    
    
}
