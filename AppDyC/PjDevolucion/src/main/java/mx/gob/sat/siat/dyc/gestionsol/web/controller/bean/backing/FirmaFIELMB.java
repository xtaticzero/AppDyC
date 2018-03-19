package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.comunica.service.EjecutaAccDocService;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaDocumentosService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ResumenDevolucionService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesFIEL;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 12/01/2014
 *
 * */
@ManagedBean(name = "firmaFIELMB")
@SessionScoped
public class FirmaFIELMB {
    /**private Logger log = Logger.getLogger(FirmaFIELMB.class.getName());*/
    private int firma;
    @ManagedProperty("#{resumenDevolucionSer}")
    private ResumenDevolucionService resumenDevolucionSer;
    @ManagedProperty("#{bandejaDocumentosSer}")
    private BandejaDocumentosService bandejaDocumentosSer;
    @ManagedProperty(value = "#{sessionHandler}")
    private SessionHandler sessionHandler;
    @ManagedProperty("#{comentarioSer}")
    private ComentarioService comentarioSer;
    @ManagedProperty("#{ejecutaAccDocService}")
    private EjecutaAccDocService ejecutaAccDocService;
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    private List<DyccMatrizDocVO> listaDyccMatrizRR = new ArrayList<DyccMatrizDocVO>();
    private List<ResumenDevolucionDTO> listaResumen = new ArrayList<ResumenDevolucionDTO>();
    private boolean esDatosFIEL =  Boolean.FALSE;
    private int cboEscalar;
    private int cboFirma;
    private int idPlantilla;
    private String txtaComentarios;
    private String idTipoTramite;
    private String numControl;
    private AccesoUsr accesoUsr;
    private String cadenaOriginal;
    private String selloDigital;
    private String step;
    private String rfc;
    private String password;

    @PostConstruct
    public void init() {
        this.accesoUsr = new AccesoUsr();
        this.accesoUsr = sessionHandler.obtenerSession();

    }

    public void aceptar() {
        esDatosFIEL =  Boolean.TRUE;

    }


    public void muestraCadenaSello() {
        setCadenaOriginal(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(ConstantesFIEL.PARAMETRO_CADENA_ORIGINAL));
        setSelloDigital(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(ConstantesFIEL.PARAMETRO_FIRMA_DIGITAL));

    }

    public String getStep() {
        if (null !=
            (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("step")) {
            step = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("step");
        }
        muestraCadenaSello();
        return step;
    }

    public String datosFirma() {
        return "datosFirmaFIEL";

    }


    public String cancelar() {
        return "resumenDevolucion";
    }

    public String regresar() {
        return "bandejaDocumento";
    }

    public void firmarFIEL() {
        FacesContext.getCurrentInstance().addMessage(null,
                                                     new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se marco con el tipo de firma:  " +
                                                                      firma, ""));
    }

    public void setResumenDevolucionSer(ResumenDevolucionService resumenDevolucionSer) {
        this.resumenDevolucionSer = resumenDevolucionSer;
    }

    public ResumenDevolucionService getResumenDevolucionSer() {
        return resumenDevolucionSer;
    }

    public void setBandejaDocumentosSer(BandejaDocumentosService bandejaDocumentosSer) {
        this.bandejaDocumentosSer = bandejaDocumentosSer;
    }

    public BandejaDocumentosService getBandejaDocumentosSer() {
        return bandejaDocumentosSer;
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setEsDatosFIEL(boolean esDatosFIEL) {
        this.esDatosFIEL = esDatosFIEL;
    }

    public boolean isEsDatosFIEL() {
        return esDatosFIEL;
    }

    public void setCboEscalar(int cboEscalar) {
        this.cboEscalar = cboEscalar;
    }

    public int getCboEscalar() {
        return cboEscalar;
    }

    public void setCboFirma(int cboFirma) {
        this.cboFirma = cboFirma;
    }

    public int getCboFirma() {
        return cboFirma;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setListaResumen(List<ResumenDevolucionDTO> listaResumen) {
        this.listaResumen = listaResumen;
    }

    public List<ResumenDevolucionDTO> getListaResumen() {
        return listaResumen;
    }

    public void setComentarioSer(ComentarioService comentarioSer) {
        this.comentarioSer = comentarioSer;
    }

    public ComentarioService getComentarioSer() {
        return comentarioSer;
    }

    public void setTxtaComentarios(String txtaComentarios) {
        this.txtaComentarios = txtaComentarios;
    }

    public String getTxtaComentarios() {
        return txtaComentarios;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setFirma(int firma) {
        this.firma = firma;
    }

    public int getFirma() {
        return firma;
    }

    public void setIdTipoTramite(String idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public String getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setEjecutaAccDocService(EjecutaAccDocService ejecutaAccDocService) {
        this.ejecutaAccDocService = ejecutaAccDocService;
    }

    public EjecutaAccDocService getEjecutaAccDocService() {
        return ejecutaAccDocService;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }


    public void setStep(String step) {
        this.step = step;
    }

    /** public String getStep() {
        if (null !=
            (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("step")) {
            step = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("step");
            generaFirmaDigital();
            //aceptarAct();
        }
        return step;
    }
     */

    public void setListaDyccMatrizRR(List<DyccMatrizDocVO> listaDyccMatrizRR) {
        this.listaDyccMatrizRR = listaDyccMatrizRR;
    }

    public List<DyccMatrizDocVO> getListaDyccMatrizRR() {
        return listaDyccMatrizRR;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }
}
