/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;


import java.io.IOException;
import java.io.Serializable;

import java.util.Date;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteCortoDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesEstados;
import mx.gob.sat.siat.dyc.util.constante.ConstantesExpresionesRegulares;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPerfiles;
import mx.gob.sat.siat.dyc.vo.DyctAnexoDTO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


/**
 * Managed Bean para registrar informacion adicional
 * @author David Guerrero Reyes
 * @author Jesus Alfredo Hernandez Orozco
 * @date  30/10/2013
 */
@ManagedBean(name = "registrarInfAdicionalMB")
@SessionScoped
public class RegistrarInfAdicionalMB extends AbstractPage {

    private boolean banderaBotonEnviar;
    private boolean showAdjuntar;
    private boolean showSolventar;
    private boolean showRegresar;
    private boolean varBotonDocs;
    private boolean varBotonAnexos;

    private String expNumControl;
    private String mgsConfirmar;
    private String msgGeneral;
    private String nombreDocumento;
    private String numControl;
    private String numeroDocumento;
    private String rol;
    
    private AccesoUsr accesoUsr;
    private DyctAnexoDTO dyctAnexoDTO;
    private DyctDeclaracionDTO declaracion;
    private DyctDeclaracionDTO declaracionAux;
    private DyctDocumentoDTO dyctDocumentoDTO;
    private TramiteCortoDTO tramiteCorto;
    private Utils utils;

    private Logger log = Logger.getLogger(RegistrarInfAdicionalMB.class.getName());

    @ManagedProperty ("#{consultarExpedienteService}")
    private ConsultarExpedienteService consultarExpedienteService;

    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService consultarDyccMensajeUsrService;

    @ManagedProperty("#{solventarRequerimientoService}")
    private SolventarRequerimientoService solventarRequerimientoService;

    @ManagedProperty("#{adjuntarDocumentoMB}")
    private AdjuntarDocumentoMB adjuntarDocumentoMB;

    @ManagedProperty("#{solventarRequerimientoMB}")
    private SolventarRequerimientoMB solventarRequerimientoMB;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    /**
     * Constructor
     */
    public RegistrarInfAdicionalMB() {
        super();
        expNumControl = ConstantesExpresionesRegulares.EXP_REG_NUM_CONTROL;
        accesoUsr      = new AccesoUsr();
        declaracion    = new DyctDeclaracionDTO();
        declaracionAux = new DyctDeclaracionDTO();
    }

    @PostConstruct
    public void init() {
        nombreDocumento = null;

        this.setDataModel(new SIATDataModel<Serializable>());
        this.setDataModelAnexo(new SIATDataModel<Serializable>());

        accesoUsr = serviceObtenerSesion.execute();
        rol = accesoUsr.getRoles();

        varBotonDocs   = Boolean.TRUE;
        varBotonAnexos = Boolean.TRUE;
        showSolventar  = Boolean.TRUE;
        showAdjuntar   = Boolean.TRUE;
        showRegresar   = false;
    }

    public void buscaNumeroControl() throws IOException, SIATException {

        FacesContext context = FacesContext.getCurrentInstance();
        
        if (rol.contains(ConstantesPerfiles.PERFIL_CONTRIBUYENTE)) {
            tramiteCorto = consultarExpedienteService.buscaNumeroControl(getNumeroDocumento(), accesoUsr.getUsuario());
        } else if (rol.contains(ConstantesPerfiles.PERFIL_ASESOR_FISCAL) ||
                   rol.contains(ConstantesPerfiles.PERFIL_DICTAMINADOR) ||
                   rol.contains(ConstantesPerfiles.PERFIL_ADM_APROBADOR)) {
            tramiteCorto = consultarExpedienteService.buscaNumeroControl(getNumeroDocumento());
        }

        if (tramiteCorto != null) {
            if (tramiteCorto.getEstadoDoc ().equals (ConstantesDyC.NOTIFICADO) ||
                                                     (tramiteCorto.getEstadoReq ().equals (ConstantesDyC.AUTORIZADO) &&
                                                      tramiteCorto.getEstadoDoc ().equals (ConstantesDyC.APROBADO))) {
                this.setShowSolventar(false);
            } else {
                this.setShowSolventar(Boolean.TRUE);
            }

            this.setShowAdjuntar(false);

        } else {

            this.setShowAdjuntar(Boolean.TRUE);
            this.setShowSolventar(Boolean.TRUE);
            context.addMessage(null,
                               new FacesMessage(consultarDyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_3,
                                                                                              ConstantesDyCNumerico.VALOR_37).getDescripcion(),
                                                null));

        }
    }

    public void buscaNumeroControl(String numControl) throws IOException, SIATException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (rol.contains(ConstantesPerfiles.PERFIL_CONTRIBUYENTE)) {
            tramiteCorto = consultarExpedienteService.buscaNumeroControl(numControl, accesoUsr.getUsuario());
        } else if (rol.contains(ConstantesPerfiles.PERFIL_ASESOR_FISCAL) ||
                   rol.contains(ConstantesPerfiles.PERFIL_DICTAMINADOR) ||
                   rol.contains(ConstantesPerfiles.PERFIL_ADM_APROBADOR)) {
            tramiteCorto = consultarExpedienteService.buscaNumeroControl(numControl);
        }

        setNumeroDocumento(numControl);
        this.showRegresar = Boolean.TRUE;

        if (tramiteCorto != null) {
            if (tramiteCorto.getEstadoDoc ().equals (ConstantesDyC.NOTIFICADO) ||
                                                     (tramiteCorto.getEstadoReq ().equals (ConstantesDyC.AUTORIZADO) &&
                                                      tramiteCorto.getEstadoDoc ().equals (ConstantesDyC.APROBADO))) {
                this.setShowSolventar(false);
            } else {
                this.setShowSolventar(Boolean.TRUE);
            }

            this.setShowAdjuntar(false);

        } else {

            this.setShowAdjuntar(Boolean.TRUE);
            this.setShowSolventar(Boolean.TRUE);
            context.addMessage(null,
                               new FacesMessage(consultarDyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_3,
                                                                                              ConstantesDyCNumerico.VALOR_37).getDescripcion(),
                                                null));

        }
    }

    public String adjuntarDocumentos() {
        adjuntarDocumentoMB.enviaDatos(getNumeroDocumento(), tramiteCorto.getAdm (),tramiteCorto.getRfc (),null );
        adjuntarDocumentoMB.init();
        return "adjuntaDocumentosAdicionales";
    }

    public String solventarReq() {
        FacesContext context = FacesContext.getCurrentInstance();
        Date fechaCalculada;
        Date fechaActual = new Date();
        try {

            if (tramiteCorto.getFechaNotificacion() != null){
            if (tramiteCorto.getNumRequerimiento() == 1) {
                fechaCalculada =
                        diaHabilService.buscarDiaFederalRecaudacion(tramiteCorto.getFechaNotificacion(), ConstantesDyCNumerico.VALOR_20);
            } else {
                fechaCalculada =
                        diaHabilService.buscarDiaFederalRecaudacion(tramiteCorto.getFechaNotificacion(), ConstantesDyCNumerico.VALOR_10);
            }

            if (fechaCalculada.after(fechaActual)) {
                    solventarRequerimientoMB.enviaDatos(getNumeroDocumento(), tramiteCorto.getNumRequerimiento(),tramiteCorto.getAdm (),tramiteCorto.getRfc (),tramiteCorto.getNumControlDoc (), null );
                solventarRequerimientoMB.init();
                return "solventaRequerimiento";
            } else {
                solventarRequerimientoService.actualizaRequerimientoVencido(ConstantesEstados.ESTADO_REQ_VENCIDO,
                            getNumeroDocumento(),
                                                                            tramiteCorto.getNumControlDoc ());
                this.setShowSolventar(false);
                context.addMessage(null,
                                   new FacesMessage(consultarDyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_1,
                                                                                                  ConstantesDyCNumerico.VALOR_37).getDescripcion(),
                                                    null));
            }

            }else {
                solventarRequerimientoMB.enviaDatos(getNumeroDocumento(), tramiteCorto.getNumRequerimiento(),tramiteCorto.getAdm (),tramiteCorto.getRfc (),tramiteCorto.getNumControlDoc (), null );
                solventarRequerimientoMB.init();
                return "solventaRequerimiento";
            }

        } catch (SIATException siatE) {
            log.error(siatE);
        } catch (Exception e) {
            log.error(e);
        }

        return null;
    }

    public void mensaje() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(null, "Número de control no encontrado"));
    }
    
    public void validaSolventacion() {
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession (false);
       
        if (session.getAttribute("statusSolventacion") != null){
            setShowSolventar ((session.getAttribute ("statusSolventacion").equals ("solventado"))?Boolean.TRUE:false);
            session.removeAttribute ("statusSolventacion");
        }    
    }

    public void onRowSelectDocs() {
        varBotonDocs = false;
    }

    public void onRowSelectAnexos() {
        varBotonAnexos = false;
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    public Utils getUtils() {
        return utils;
    }

    public void setConsultarExpedienteService(ConsultarExpedienteService consultarExpedienteService) {
        this.consultarExpedienteService = consultarExpedienteService;
    }

    public ConsultarExpedienteService getConsultarExpedienteService() {
        return consultarExpedienteService;
    }

    public void setDyctDocumentoDTO(DyctDocumentoDTO dyctDocumentoDTO) {
        this.dyctDocumentoDTO = dyctDocumentoDTO;
    }

    public DyctDocumentoDTO getDyctDocumentoDTO() {
        return dyctDocumentoDTO;
    }

    public void setMsgGeneral(String msgGeneral) {
        this.msgGeneral = msgGeneral;
    }

    public String getMsgGeneral() {
        return msgGeneral;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setDeclaracion(DyctDeclaracionDTO declaracion) {
        this.declaracion = declaracion;
    }

    public DyctDeclaracionDTO getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracionAux(DyctDeclaracionDTO declaracionAux) {
        this.declaracionAux = declaracionAux;
    }

    public DyctDeclaracionDTO getDeclaracionAux() {
        return declaracionAux;
    }

    public void setDyctAnexoDTO(DyctAnexoDTO dyctAnexoDTO) {
        this.dyctAnexoDTO = dyctAnexoDTO;
    }

    public DyctAnexoDTO getDyctAnexoDTO() {
        return dyctAnexoDTO;
    }

    public void setVarBotonDocs(boolean varBotonDocs) {
        this.varBotonDocs = varBotonDocs;
    }

    public boolean isVarBotonDocs() {
        return varBotonDocs;
    }

    public void setVarBotonAnexos(boolean varBotonAnexos) {
        this.varBotonAnexos = varBotonAnexos;
    }

    public boolean isVarBotonAnexos() {
        return varBotonAnexos;
    }

    public void setTramiteCorto(TramiteCortoDTO tramiteCorto) {
        this.tramiteCorto = tramiteCorto;
    }

    public TramiteCortoDTO getTramiteCorto() {
        return tramiteCorto;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setShowSolventar(boolean showSolventar) {
        this.showSolventar = showSolventar;
    }

    public boolean getShowSolventar() {
        return showSolventar;
    }

    public void setShowAdjuntar(boolean showAdjuntar) {
        this.showAdjuntar = showAdjuntar;
    }

    public boolean getShowAdjuntar() {
        return showAdjuntar;
    }

    public void setConsultarDyccMensajeUsrService(DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public void setMgsConfirmar(String mgsConfirmar) {
        this.mgsConfirmar = mgsConfirmar;
    }

    public String getMgsConfirmar() {
        return mgsConfirmar;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setAdjuntarDocumentoMB(AdjuntarDocumentoMB adjuntarDocumentoMB) {
        this.adjuntarDocumentoMB = adjuntarDocumentoMB;
    }

    public AdjuntarDocumentoMB getAdjuntarDocumentoMB() {
        return adjuntarDocumentoMB;
    }

    public void setSolventarRequerimientoMB(SolventarRequerimientoMB solventarRequerimientoMB) {
        this.solventarRequerimientoMB = solventarRequerimientoMB;
    }

    public SolventarRequerimientoMB getSolventarRequerimientoMB() {
        return solventarRequerimientoMB;
    }

    public void setShowRegresar(boolean showRegresar) {
        this.showRegresar = showRegresar;
    }

    public boolean isShowRegresar() {
        return showRegresar;
    }

    public void setExpNumControl(String expNumControl) {
        this.expNumControl = expNumControl;
    }

    public String getExpNumControl() {
        return expNumControl;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public void setSolventarRequerimientoService(SolventarRequerimientoService solventarRequerimientoService) {
        this.solventarRequerimientoService = solventarRequerimientoService;
    }

    public SolventarRequerimientoService getSolventarRequerimientoService() {
        return solventarRequerimientoService;
    }

    public void setBanderaBotonEnviar(boolean banderaBotonEnviar) {
        this.banderaBotonEnviar = banderaBotonEnviar;
    }

    public boolean isBanderaBotonEnviar() {
        return banderaBotonEnviar;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
