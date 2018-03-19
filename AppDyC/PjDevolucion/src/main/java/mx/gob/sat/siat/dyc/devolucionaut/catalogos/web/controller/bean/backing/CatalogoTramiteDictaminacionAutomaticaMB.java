package mx.gob.sat.siat.dyc.devolucionaut.catalogos.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyccModeloService;
import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.TramiteDictaminacionAutomaticaService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.ConsultaTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.support.DyccTipoTramiteVO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto.FrmTramiteDictaminacionAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author Jose Luis Aguilar
 */
@ManagedBean(name = "tramiteDictaminacionAutomatica")
@ViewScoped
public class CatalogoTramiteDictaminacionAutomaticaMB  extends AbstractPage {
    
    private String headerDialog;
    private boolean banderaBotones;
    private String tipoTramite = "";
    private String origenDevolucion;
    private String idTramiteDicAut;
    private String descTipoTramite;
    private String modelo;
    private String concepto;
    private String impuesto;
    private String dictamenAutomatico;
    private String mensajeNotif = "";
    private int estado = 1;
    private FacesMessage mensajePantalla;
    private FacesMessage msgTramiteDevAu;
    private DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO;
    private DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaSeleccionado;
    private ConsultaTipoTramiteVO tipoTramiteVO;
    private DyccConceptoDTO conceptoDTO;
    private DyccImpuestoDTO impuestoDTO;
    private List<DycTramiteDictaminacionAutomaticaDTO> listaDictaminacionAutomatica;
    private List<DyccModelo> listaModelo;
    private List<DycTramiteDictaminacionAutomaticaDTO> tramiteARegistrar;
    private List<DyccOrigenSaldoDTO> listaOrigenSaldo;
    private List<DyccTipoTramiteVO> listaTipoTramite;
    private AccesoUsr accesoUsr;
    private HttpServletRequest request;
    private Boolean estadoBtnGuardar;
    private static final Logger LOGGER = Logger.getLogger(CatalogoMontoSaldoAFavorMB.class);
    private static final String ERROR_CONSULTA = "Hubo un error al consultar los datos de trámites dictaminación automática";
    private static final String ERROR_REGISTRAR = "Ocurrio un error al registrar los datos de trámites dictaminación automática";
    private static final String ERROR_ACTIVAR = "Ocurrio un error al activar los datos de trámites dictaminación automática";
    private static final String INGRESAR_TRAMITE = "Ingresar trámite";
    private static final String CONSULTAR_TRAMITE = "Consultar trámite";
    private static final String ACTIVAR_TRAMITE = "Activar trámite";
    private static final String ALTA = "ALTA";
    private static final String CONSULTAR = "CONSULTAR";
    private static final String ACTIVAR = "ACTIVAR";
    private static final String ACTIVO = "Activo";
    private static final String DIALOG_HIDE = "dlgTramiteDA.hide();";
    
   
    
    @ManagedProperty(value = "#{tramiteDictaminacionAutomaticaService}")
    private TramiteDictaminacionAutomaticaService tramiteDictaminacionAutomaticaService;
    
    @ManagedProperty(value = "#{dyccOrigenSaldoService}")
    private DyccOrigenSaldoService dyccOrigenSaldoService;

    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;
    
    @ManagedProperty(value = "#{dyccModeloService}")
    private DyccModeloService dyccModeloService;
    
    private FrmTramiteDictaminacionAutomaticaDTO frmDictaminacionAutomaticaDTO;

    public CatalogoTramiteDictaminacionAutomaticaMB() {
        super();
    }
    
    /**
     * Consulta todos los datos necesarios para mostrar los datos en pantalla
     */
    @PostConstruct
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00105);
        request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());

        try {
            this.frmDictaminacionAutomaticaDTO = new FrmTramiteDictaminacionAutomaticaDTO();
            banderaBotones = Boolean.TRUE;            
            listaOrigenSaldo = dyccOrigenSaldoService.obtieneOrigenesSaldo();
            listaModelo = dyccModeloService.consultarTodos();
            listaDictaminacionAutomatica = tramiteDictaminacionAutomaticaService.consultarTodos();
        } catch (SIATException siate) {
            LOGGER.error(ERROR_CONSULTA);
            mensajePantalla = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ERROR_CONSULTA);
        }
    }
    
    public void addTramiteDAListener() {
        RequestContext.getCurrentInstance().reset("formTramiteDA:pnlTramiteDA");
        this.origenDevolucion = null;
        this.tipoTramite = null;
        this.modelo = null;
        this.impuesto = "";
        this.concepto = "";
        this.dictamenAutomatico = "1";
        this.frmDictaminacionAutomaticaDTO.setRegistroActivo(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDev(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTram(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnGuardar(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandModelo(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnConsultar(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandConceptoTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandImpuestoTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDevTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTramTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandModeloCbo(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandModeloTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandDictamenAut(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBanderaMsg(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setAccion(ALTA);
        this.frmDictaminacionAutomaticaDTO.setTituloDialog(INGRESAR_TRAMITE);
        this.estadoBtnGuardar = Boolean.TRUE;
    }
    
    public void selectTramiteDALitener() {
        RequestContext.getCurrentInstance().reset("formTramiteDA:pnlTramiteDA");
        this.origenDevolucion = null;
        this.tipoTramite = null;
        this.modelo = null;
        this.impuesto = "";
        this.concepto = "";
        this.dictamenAutomatico = "";
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDev(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTram(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnGuardar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandModelo(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandConceptoTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandImpuestoTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandModeloCbo(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandModeloTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnBuscar(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnConsultar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDevTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTramTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandDictamenAut(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBanderaMsg(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setAccion(CONSULTAR);
        this.frmDictaminacionAutomaticaDTO.setTituloDialog(CONSULTAR_TRAMITE);
    }
    
    public void activateTramiteDALitener() {
        this.origenDevolucion = tramiteDictaminacionAutomaticaSeleccionado.getOrigenSaldo();
        this.tipoTramite = tramiteDictaminacionAutomaticaSeleccionado.getTipoTramite();
        this.modelo = tramiteDictaminacionAutomaticaSeleccionado.getModelo();
        this.impuesto = tramiteDictaminacionAutomaticaSeleccionado.getImpuesto();
        this.concepto = tramiteDictaminacionAutomaticaSeleccionado.getConcepto();
        this.dictamenAutomatico = "1";
        this.frmDictaminacionAutomaticaDTO.setRegistroActivo(Boolean.TRUE);
        if (!tramiteDictaminacionAutomaticaSeleccionado.getDictamenAutomatico().equals(ACTIVO)) {
            this.frmDictaminacionAutomaticaDTO.setRegistroActivo(Boolean.FALSE);
            this.dictamenAutomatico = "0";
        }
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDev(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnGuardar(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandModelo(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandModeloCbo(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandModeloTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnConsultar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDevTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTramTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTram(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandDictamenAut(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandConceptoTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBandImpuestoTxt(Boolean.TRUE);
        this.frmDictaminacionAutomaticaDTO.setBanderaMsg(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setTituloDialog(ACTIVAR_TRAMITE);
        this.frmDictaminacionAutomaticaDTO.setAccion(ACTIVAR);
        this.estadoBtnGuardar=Boolean.FALSE;
    }
    
    public void muestraMensaje() {
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDev(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTram(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnGuardar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandOrigenDevTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandTipoTramTxt(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBandDictamenAut(Boolean.FALSE);
        this.frmDictaminacionAutomaticaDTO.setBanderaMsg(Boolean.TRUE);
        RequestContext.getCurrentInstance().execute("notifDlgVar.show();");
    }
    
    public void execGuardar() {
        if (frmDictaminacionAutomaticaDTO.getAccion().equals(ALTA))
        {
            try {
                tramiteDictaminacionAutomaticaDTO = new DycTramiteDictaminacionAutomaticaDTO();
                tramiteDictaminacionAutomaticaDTO.setIdOrigenSaldo(Integer.parseInt(origenDevolucion));
                tramiteDictaminacionAutomaticaDTO.setIdTipoTramite(Integer.parseInt(tipoTramite));
                tramiteDictaminacionAutomaticaDTO.setModelo(modelo);
                tramiteDictaminacionAutomaticaDTO.setIdConcepto(tipoTramiteVO.getConcepto().getIdConcepto());
                tramiteDictaminacionAutomaticaDTO.setIdImpuesto(tipoTramiteVO.getImpuesto().getIdImpuesto());
                this.tramiteDictaminacionAutomaticaService.registrar(tramiteDictaminacionAutomaticaDTO, accesoUsr);
                descTipoTramite = tramiteDictaminacionAutomaticaService.existe(tramiteDictaminacionAutomaticaDTO).get(0).getTipoTramite();
                this.mensajeNotif = "Se registra el trámite " +  descTipoTramite + " , en el proceso de dictaminación automática, ingresado por " +
                accesoUsr.getNombreCompleto() + " " + accesoUsr.getNumeroEmp() ;
                listaDictaminacionAutomatica = tramiteDictaminacionAutomaticaService.consultarTodos();
                banderaBotones = Boolean.TRUE;
                tramiteDictaminacionAutomaticaSeleccionado = null;
                RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                muestraMensaje();
                } catch (SIATException siate) {
                    this.msgTramiteDevAu = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                    FacesContext.getCurrentInstance().addMessage(null, msgTramiteDevAu);
             }
        } else {
            try {
                    if (dictamenAutomatico.equals("1")){
                        tramiteDictaminacionAutomaticaDTO = tramiteDictaminacionAutomaticaSeleccionado;
                        tramiteDictaminacionAutomaticaService.activar(tramiteDictaminacionAutomaticaDTO, accesoUsr);
                        listaDictaminacionAutomatica = tramiteDictaminacionAutomaticaService.consultarTodos();
                        banderaBotones = Boolean.TRUE;
                        tramiteDictaminacionAutomaticaSeleccionado = null;
                        this.mensajeNotif = "Se activa el trámite " +  tramiteDictaminacionAutomaticaDTO.getTipoTramite() + " , en el proceso de dictaminación automática, actualizado por " +
                        accesoUsr.getNombreCompleto() + " " + accesoUsr.getNumeroEmp() ;
                        tramiteDictaminacionAutomaticaSeleccionado = null;
                        muestraMensaje();
                        RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                    } else { 
                        RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                    }
            } catch (SIATException siate) {
                this.msgTramiteDevAu = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_ACTIVAR);
                FacesContext.getCurrentInstance().addMessage(null, msgTramiteDevAu);
            }
        }
    }
    
    public void execBuscar() {
        try {
                    tramiteDictaminacionAutomaticaDTO = new DycTramiteDictaminacionAutomaticaDTO();
                    tramiteDictaminacionAutomaticaDTO.setIdOrigenSaldo(Integer.parseInt(origenDevolucion));
                    tramiteDictaminacionAutomaticaDTO.setIdTipoTramite(Integer.parseInt(tipoTramite));
                    tramiteDictaminacionAutomaticaDTO.setModelo(this.modelo);
                    listaDictaminacionAutomatica = tramiteDictaminacionAutomaticaService.consultarFiltro(tramiteDictaminacionAutomaticaDTO);
                    banderaBotones = Boolean.TRUE;
                    tramiteDictaminacionAutomaticaSeleccionado = null;
                    RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
            } catch (SIATException siate) {
                this.msgTramiteDevAu = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_CONSULTA);
                FacesContext.getCurrentInstance().addMessage(null, msgTramiteDevAu);
            }
    }
    
    public void execConsultar() {
        try {
            tramiteDictaminacionAutomaticaDTO = new DycTramiteDictaminacionAutomaticaDTO();
            tramiteDictaminacionAutomaticaDTO.setIdTipoTramite(Integer.parseInt(tipoTramite));
            tramiteDictaminacionAutomaticaDTO.setModelo(this.modelo);
            tramiteARegistrar = tramiteDictaminacionAutomaticaService.existe(tramiteDictaminacionAutomaticaDTO);
            descTipoTramite = null;
            if (!tramiteARegistrar.isEmpty()){
                descTipoTramite = tramiteARegistrar.get(0).getTipoTramite();
            }
            if (descTipoTramite != null) {
                this.mensajeNotif = "El tipo de trámite " +  tramiteARegistrar.get(0).getTipoTramite() + " , se encuentra registrado en el catálogo";
                RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                muestraMensaje();

            } else {
                tipoTramiteVO = catalogoTipoTramiteService.consultarDatosDeUnTramite(Integer.parseInt(tipoTramite));
                this.conceptoDTO = tipoTramiteVO.getConcepto();
                this.impuestoDTO = tipoTramiteVO.getImpuesto();
                concepto = conceptoDTO.getDescripcion();
                impuesto = impuestoDTO.getDescripcion();
            }
            this.estadoBtnGuardar=false;
        } catch (SIATException siate) {
            this.msgTramiteDevAu = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_CONSULTA);
                FacesContext.getCurrentInstance().addMessage(null, msgTramiteDevAu);
        }
    }
            
    public void removeTramiteDALitener() {
        try {
                    tramiteDictaminacionAutomaticaDTO = tramiteDictaminacionAutomaticaSeleccionado;
                    tramiteDictaminacionAutomaticaService.inActivar(tramiteDictaminacionAutomaticaDTO, accesoUsr);
                    listaDictaminacionAutomatica = tramiteDictaminacionAutomaticaService.consultarTodos();
                    banderaBotones = Boolean.TRUE;
                    tramiteDictaminacionAutomaticaSeleccionado = null;
                    this.mensajeNotif = "Ha sido dado de baja el tipo de trámite " + tramiteDictaminacionAutomaticaDTO.getTipoTramite();
                    muestraMensaje();
            } catch (SIATException siate) {
                this.msgTramiteDevAu = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgTramiteDevAu);
            }
    }
    
    public void actualizaTipoTramite(){        
     try{        
        if(origenDevolucion!=null && !origenDevolucion.isEmpty()){
            long idOrigenSaldo=Long.parseLong(origenDevolucion);
            LOGGER.info("Consultando cc tipo tramite OS id= "+idOrigenSaldo);
            if(idOrigenSaldo>ConstantesDyC1.CERO){
                listaTipoTramite = catalogoTipoTramiteService.obtieneTipoTramite(idOrigenSaldo);
            } else {
             listaTipoTramite= new ArrayList<DyccTipoTramiteVO>();
            }        
        } else{
            LOGGER.info("id OS no select");
            listaTipoTramite= new ArrayList<DyccTipoTramiteVO>();
        }
     }
     catch(SIATException e){
         LOGGER.error(ERROR_CONSULTA, e);    
      }   
    }
    public void limpiarCombosOrigenTramite(){
        LOGGER.info("Limpiando combos O_T ***************************");
        listaOrigenSaldo = dyccOrigenSaldoService.obtieneOrigenesSaldo();
        listaTipoTramite= new ArrayList<DyccTipoTramiteVO>();
    }
    public void activarBotones(SelectEvent event) {
        this.banderaBotones = Boolean.FALSE;
    }

    public String getHeaderDialog() {
        return headerDialog;
    }

    public void setHeaderDialog(String headerDialog) {
        this.headerDialog = headerDialog;
    }

    public boolean isBanderaBotones() {
        return banderaBotones;
    }

    public void setBanderaBotones(boolean banderaBotones) {
        this.banderaBotones = banderaBotones;
    }

    public FacesMessage getMensajePantalla() {
        return mensajePantalla;
    }

    public void setMensajePantalla(FacesMessage mensajePantalla) {
        this.mensajePantalla = mensajePantalla;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getOrigenDevolucion() {
        return origenDevolucion;
    }

    public void setOrigenDevolucion(String origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }

    public DyccOrigenSaldoService getDyccOrigenSaldoService() {
        return dyccOrigenSaldoService;
    }

    public void setDyccOrigenSaldoService(DyccOrigenSaldoService dyccOrigenSaldoService) {
        this.dyccOrigenSaldoService = dyccOrigenSaldoService;
    }

    public List<DyccOrigenSaldoDTO> getListaOrigenSaldo() {
        return listaOrigenSaldo;
    }

    public void setListaOrigenSaldo(List<DyccOrigenSaldoDTO> listaOrigenSaldo) {
        this.listaOrigenSaldo = listaOrigenSaldo;
    }

    public CatalogoTipoTramiteService getCatalogoTipoTramiteService() {
        return catalogoTipoTramiteService;
    }

    public void setCatalogoTipoTramiteService(CatalogoTipoTramiteService catalogoTipoTramiteService) {
        this.catalogoTipoTramiteService = catalogoTipoTramiteService;
    }

    public List<DyccTipoTramiteVO> getListaTipoTramite() {
        return listaTipoTramite;
    }

    public void setListaTipoTramite(List<DyccTipoTramiteVO> listaTipoTramite) {
        this.listaTipoTramite = listaTipoTramite;
    }

    public FacesMessage getMsgDialiogMontoSAF() {
        return msgTramiteDevAu;
    }

    public void setMsgDialiogMontoSAF(FacesMessage msgTramiteDevAu) {
        this.msgTramiteDevAu = msgTramiteDevAu;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDescTipoTramite() {
        return descTipoTramite;
    }

    public void setDescTipoTramite(String descTipoTramite) {
        this.descTipoTramite = descTipoTramite;
    }

    public String getMensajeNotif() {
        return mensajeNotif;
    }

    public void setMensajeNotif(String mensajeNotif) {
        this.mensajeNotif = mensajeNotif;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getIdTramiteDicAut() {
        return idTramiteDicAut;
    }

    public void setIdTramiteDicAut(String idTramiteDicAut) {
        this.idTramiteDicAut = idTramiteDicAut;
    }

    public FacesMessage getMsgTramiteDevAu() {
        return msgTramiteDevAu;
    }

    public void setMsgTramiteDevAu(FacesMessage msgTramiteDevAu) {
        this.msgTramiteDevAu = msgTramiteDevAu;
    }

    public DycTramiteDictaminacionAutomaticaDTO getTramiteDictaminacionAutomaticaDTO() {
        return tramiteDictaminacionAutomaticaDTO;
    }

    public void setTramiteDictaminacionAutomaticaDTO(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO) {
        this.tramiteDictaminacionAutomaticaDTO = tramiteDictaminacionAutomaticaDTO;
    }

    public DycTramiteDictaminacionAutomaticaDTO getTramiteDictaminacionAutomaticaSeleccionado() {
        return tramiteDictaminacionAutomaticaSeleccionado;
    }

    public void setTramiteDictaminacionAutomaticaSeleccionado(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaSeleccionado) {
        this.tramiteDictaminacionAutomaticaSeleccionado = tramiteDictaminacionAutomaticaSeleccionado;
    }

    public List<DycTramiteDictaminacionAutomaticaDTO> getListaDictaminacionAutomatica() {
        return listaDictaminacionAutomatica;
    }

    public void setListaDictaminacionAutomatica(List<DycTramiteDictaminacionAutomaticaDTO> listaDictaminacionAutomatica) {
        this.listaDictaminacionAutomatica = listaDictaminacionAutomatica;
    }

    public TramiteDictaminacionAutomaticaService getTramiteDictaminacionAutomaticaService() {
        return tramiteDictaminacionAutomaticaService;
    }

    public void setTramiteDictaminacionAutomaticaService(TramiteDictaminacionAutomaticaService tramiteDictaminacionAutomaticaService) {
        this.tramiteDictaminacionAutomaticaService = tramiteDictaminacionAutomaticaService;
    }

    public FrmTramiteDictaminacionAutomaticaDTO getFrmDictaminacionAutomaticaDTO() {
        return frmDictaminacionAutomaticaDTO;
    }

    public void setFrmDictaminacionAutomaticaDTO(FrmTramiteDictaminacionAutomaticaDTO frmDictaminacionAutomaticaDTO) {
        this.frmDictaminacionAutomaticaDTO = frmDictaminacionAutomaticaDTO;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getDictamenAutomatico() {
        return dictamenAutomatico;
    }

    public void setDictamenAutomatico(String dictamenAutomatico) {
        this.dictamenAutomatico = dictamenAutomatico;
    }

    public ConsultaTipoTramiteVO getTipoTramiteVO() {
        return tipoTramiteVO;
    }

    public void setTipoTramiteVO(ConsultaTipoTramiteVO tipoTramiteVO) {
        this.tipoTramiteVO = tipoTramiteVO;
    }

    public DyccConceptoDTO getConceptoDTO() {
        return conceptoDTO;
    }

    public void setConceptoDTO(DyccConceptoDTO conceptoDTO) {
        this.conceptoDTO = conceptoDTO;
    }

    public DyccImpuestoDTO getImpuestoDTO() {
        return impuestoDTO;
    }

    public void setImpuestoDTO(DyccImpuestoDTO impuestoDTO) {
        this.impuestoDTO = impuestoDTO;
    }

    public List<DycTramiteDictaminacionAutomaticaDTO> getTramiteARegistrar() {
        return tramiteARegistrar;
    }

    public void setTramiteARegistrar(List<DycTramiteDictaminacionAutomaticaDTO> tramiteARegistrar) {
        this.tramiteARegistrar = tramiteARegistrar;
    }

    public List<DyccModelo> getListaModelo() {
        return listaModelo;
    }

    public void setListaModelo(List<DyccModelo> listaModelo) {
        this.listaModelo = listaModelo;
    }

    public DyccModeloService getDyccModeloService() {
        return dyccModeloService;
    }

    public void setDyccModeloService(DyccModeloService dyccModeloService) {
        this.dyccModeloService = dyccModeloService;
    }
    
    public Boolean getEstadoBtnGuardar() {
        return estadoBtnGuardar;
    }

    public void setEstadoBtnGuardar(Boolean estadoBtnGuardar) {
        this.estadoBtnGuardar = estadoBtnGuardar;
    }
}