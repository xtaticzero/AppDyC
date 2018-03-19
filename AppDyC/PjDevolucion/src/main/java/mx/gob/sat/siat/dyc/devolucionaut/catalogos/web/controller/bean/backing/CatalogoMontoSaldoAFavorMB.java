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

import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.MontoSaldoAFavorService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.support.DyccTipoTramiteVO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto.FrmMontoSaldoAFavorDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMontoSaldoAFavorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import java.math.BigDecimal;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;


/**
 *
 * @author Jose Luis Aguilar
 */
@ManagedBean(name = "catalogoMontoSaldoAFavor")
@ViewScoped
public class CatalogoMontoSaldoAFavorMB extends AbstractPage {
    
    private String headerDialog;
    private boolean banderaBotones;
    private String tipoTramite = "";
    private String origenDevolucion;
    private BigDecimal montoSaldoAFavor;
    private String descTipoTramite;
    private String mensajeNotif = "";
    private int estado = 1;
    private FacesMessage mensajePantalla;
    private FacesMessage msgDialiogMontoSAF;
    private DycMontoSaldoAFavorDTO montoSaldoAFavorDTO;
    private DycMontoSaldoAFavorDTO montoSaldoAFavorSeleccionado;
    private List<DycMontoSaldoAFavorDTO> listaMontoSaldoAFavor;
    private List<DycMontoSaldoAFavorDTO> montoSaldoARegistrar;
    private List<DyccOrigenSaldoDTO> listaOrigenSaldo;
    private List<DyccTipoTramiteVO> listaTipoTramite;
    private AccesoUsr accesoUsr;
    private HttpServletRequest request;
    private static final Logger LOGGER = Logger.getLogger(CatalogoMontoSaldoAFavorMB.class);
    private static final String ERROR_CONSULTA = "Hubo un error al recoger los datos del monto saldo a favor";
    private static final String ERROR_REGISTRAR = "Ocurrio un error al registrar los datos del monto saldo a favor";
    private static final String INGRESAR_MONTO = "Ingresar monto";
    private static final String CONSULTAR_MONTO = "Consultar monto";
    private static final String MODIFICAR_MONTO = "Modificar monto";
    private static final String ALTA = "ALTA";
    private static final String CONSULTAR = "CONSULTAR";
    private static final String MODIFICAR = "MODIFICAR";
    private static final String DIALOG_HIDE = "dlgMontoSAF.hide();";
   
    
    @ManagedProperty(value = "#{montoSaldoAFavorService}")
    private MontoSaldoAFavorService montoSaldoAFavorService;
    
    @ManagedProperty(value = "#{dyccOrigenSaldoService}")
    private DyccOrigenSaldoService dyccOrigenSaldoService;

    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;
    
    private FrmMontoSaldoAFavorDTO frmMontoSaldoAFavorDTO;

    public CatalogoMontoSaldoAFavorMB() {
        super();
    }
    
    /**
     * Consulta todos los datos necesarios para mostrar los datos en pantalla
     */
    @PostConstruct
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00104);
        request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());

        try {
            this.frmMontoSaldoAFavorDTO = new FrmMontoSaldoAFavorDTO();
            banderaBotones = Boolean.TRUE;
            
            listaOrigenSaldo = dyccOrigenSaldoService.obtieneOrigenesSaldo();
            listaMontoSaldoAFavor = montoSaldoAFavorService.consultarTodos();
        } catch (SIATException siate) {
            LOGGER.error(ERROR_CONSULTA);
            mensajePantalla = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ERROR_CONSULTA);
        }
    }
    
    public void addMontoSaldoAFavorListener() {
        RequestContext.getCurrentInstance().reset("formMontoSaldoAFavor:pnlMontoSAF");
        this.origenDevolucion = null;
        this.tipoTramite = null;
        this.montoSaldoAFavor = null;
        this.frmMontoSaldoAFavorDTO.setBandOrigenDev(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTram(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandMontoSAF(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandBtnGuardar(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandOrigenDevTxt(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTramTxt(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandEstado(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBanderaMsg(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setAccion(ALTA);
        this.frmMontoSaldoAFavorDTO.setTituloDialog(INGRESAR_MONTO);
    }
    
    public void selectMontoSaldoAFavorLitener() {
        RequestContext.getCurrentInstance().reset("formMontoSaldoAFavor:pnlMontoSAF");
        this.origenDevolucion = null;
        this.tipoTramite = null;
        this.frmMontoSaldoAFavorDTO.setBandOrigenDev(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTram(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandMontoSAF(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandBtnGuardar(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandBtnBuscar(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandOrigenDevTxt(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTramTxt(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandEstado(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBanderaMsg(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setAccion(CONSULTAR);
        this.frmMontoSaldoAFavorDTO.setTituloDialog(CONSULTAR_MONTO);
        
    }
    
    public void modifyMontoSaldoAFavorLitener() {
        this.origenDevolucion = montoSaldoAFavorSeleccionado.getOrigenDevolucion();
        this.tipoTramite = montoSaldoAFavorSeleccionado.getTipoTramite();
        this.montoSaldoAFavor = montoSaldoAFavorSeleccionado.getMontoSaldoFavor();
        this.estado = 1;
        this.frmMontoSaldoAFavorDTO.setBandActivo(Boolean.TRUE);
        if (montoSaldoAFavorSeleccionado.getEstado().equals("Inactivo")) {
            this.estado = 0;
            this.frmMontoSaldoAFavorDTO.setBandActivo(Boolean.FALSE);
        }
        this.frmMontoSaldoAFavorDTO.setBandOrigenDev(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTram(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandMontoSAF(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandBtnGuardar(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandOrigenDevTxt(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTramTxt(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBandEstado(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setBanderaMsg(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setAccion(MODIFICAR);
        this.frmMontoSaldoAFavorDTO.setTituloDialog(MODIFICAR_MONTO);
    }
    
    public void muestraMensaje() {
        this.frmMontoSaldoAFavorDTO.setBandOrigenDev(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTram(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandMontoSAF(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandBtnGuardar(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandOrigenDevTxt(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandTipoTramTxt(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBandEstado(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setBanderaMsg(Boolean.TRUE);
        RequestContext.getCurrentInstance().execute("notifDlgVar.show();");
    }
    
    public void execGuardar() {
        if (frmMontoSaldoAFavorDTO.getAccion().equals(ALTA)) {
            try {
                    montoSaldoAFavorDTO = new DycMontoSaldoAFavorDTO();
                    montoSaldoAFavorDTO.setIdOrigenDevolucion(Integer.parseInt(origenDevolucion));
                    montoSaldoAFavorDTO.setIdTipoTramite(Integer.parseInt(tipoTramite));
                    montoSaldoARegistrar = montoSaldoAFavorService.existe(montoSaldoAFavorDTO);
                    descTipoTramite = null;
                    if (!montoSaldoARegistrar.isEmpty()) {
                        descTipoTramite = montoSaldoARegistrar.get(0).getTipoTramite();
                    }
                    
                    if (descTipoTramite != null) {
                        this.mensajeNotif = "El Monto relacionado al Tipo de trámite " +  descTipoTramite + ", se encuentra registrado en el catálogo";
                        muestraMensaje();
                        RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                    } else {
                        LOGGER.info("Monto saldo a favor: "+montoSaldoAFavor);
                        montoSaldoAFavorDTO.setMontoSaldoFavor(montoSaldoAFavor);
                        this.mensajeNotif = "Se registra Monto de dictaminación automática asociado al tipo de trámite " +  tipoTramite + ", por un Monto de " +
                        montoSaldoAFavor + ", actualizado por " + accesoUsr.getNombreCompleto() + " " + accesoUsr.getNumeroEmp() ;
                        this.montoSaldoAFavorService.registrar(montoSaldoAFavorDTO, accesoUsr);
                        listaMontoSaldoAFavor = montoSaldoAFavorService.consultarTodos();
                        descTipoTramite = montoSaldoAFavorService.existe(montoSaldoAFavorDTO).get(0).getTipoTramite();
                        this.mensajeNotif = "Se registra Monto de dictaminación automática asociado al tipo de trámite " +  descTipoTramite + ", por un Monto de " +
                        montoSaldoAFavor + ", actualizado por " + accesoUsr.getNombreCompleto() + " " + accesoUsr.getNumeroEmp() ;
                        this.banderaBotones = Boolean.TRUE;
                        this.montoSaldoAFavorSeleccionado = null;
                        muestraMensaje();
                        RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                    }
            } catch (SIATException siate) {
                this.msgDialiogMontoSAF = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMontoSAF);
            }
        } else if  (frmMontoSaldoAFavorDTO.getAccion().equals(MODIFICAR)) {
            try {
                    montoSaldoAFavorDTO = montoSaldoAFavorSeleccionado;
                    montoSaldoAFavorDTO.setMontoSaldoFavor(montoSaldoAFavor);
                    montoSaldoAFavorDTO.setEstado("" + estado);
                    this.mensajeNotif = "Se actualiza Monto de dictaminación automática del tipo tramite " +  this.tipoTramite + ", por un Monto de " +
                        montoSaldoAFavor + ", actualizado por " + accesoUsr.getNombreCompleto() + " " + accesoUsr.getNumeroEmp();
                    montoSaldoAFavorService.actualizar(montoSaldoAFavorDTO, accesoUsr);
                    listaMontoSaldoAFavor = montoSaldoAFavorService.consultarTodos();
                    this.banderaBotones = Boolean.TRUE;
                    this.montoSaldoAFavorSeleccionado = null;
                    muestraMensaje();
                    RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
            } catch (SIATException siate) {
                this.msgDialiogMontoSAF = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMontoSAF);
            }
        }
        
    }
    
    public void execBuscar() {
        try {
                    montoSaldoAFavorDTO = new DycMontoSaldoAFavorDTO();
                    montoSaldoAFavorDTO.setIdOrigenDevolucion(Integer.parseInt(origenDevolucion));
                    montoSaldoAFavorDTO.setIdTipoTramite(Integer.parseInt(tipoTramite));
                    listaMontoSaldoAFavor = montoSaldoAFavorService.consultarFiltro(montoSaldoAFavorDTO);
                    this.banderaBotones = Boolean.TRUE;
                    this.montoSaldoAFavorSeleccionado = null;
                    RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
            } catch (SIATException siate) {
                this.msgDialiogMontoSAF = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_CONSULTA);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMontoSAF);
            }
    }
    
    public void removeMontoSaldoAFavorLitener() {
        try {
                    montoSaldoAFavorDTO = montoSaldoAFavorSeleccionado;
                    montoSaldoAFavorService.inActivar(montoSaldoAFavorDTO, accesoUsr);
                    listaMontoSaldoAFavor = montoSaldoAFavorService.consultarTodos();
                    this.mensajeNotif = "Ha sido dado de Baja el Monto.";
                    this.banderaBotones = Boolean.TRUE;
                    this.montoSaldoAFavorSeleccionado = null;
                    muestraMensaje();
            } catch (SIATException siate) {
                this.msgDialiogMontoSAF = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMontoSAF);
            }
    }
    /**
     * Metodo para hacer la func ion en cascada tipo tramite
     */
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
    /**
     * 
     * @param event 
     */
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

    public List<DycMontoSaldoAFavorDTO> getListaMontoSaldoAFavor() {
        return listaMontoSaldoAFavor;
    }

    public void setListaMontoSaldoAFavor(List<DycMontoSaldoAFavorDTO> listaMontoSaldoAFavor) {
        this.listaMontoSaldoAFavor = listaMontoSaldoAFavor;
    }

    public MontoSaldoAFavorService getMontoSaldoAFavorService() {
        return montoSaldoAFavorService;
    }

    public void setMontoSaldoAFavorService(MontoSaldoAFavorService montoSaldoAFavorService) {
        this.montoSaldoAFavorService = montoSaldoAFavorService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public FrmMontoSaldoAFavorDTO getFrmMontoSaldoAFavorDTO() {
        return frmMontoSaldoAFavorDTO;
    }

    public void setFrmMontoSaldoAFavorDTO(FrmMontoSaldoAFavorDTO frmMontoSaldoAFavorDTO) {
        this.frmMontoSaldoAFavorDTO = frmMontoSaldoAFavorDTO;
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

    public BigDecimal getMontoSaldoAFavor() {
        return montoSaldoAFavor;
    }

    public void setMontoSaldoAFavor(BigDecimal montoSaldoAFavor) {
        this.montoSaldoAFavor = montoSaldoAFavor;
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

    public DycMontoSaldoAFavorDTO getMontoSaldoAFavorDTO() {
        return montoSaldoAFavorDTO;
    }

    public void setMontoSaldoAFavorDTO(DycMontoSaldoAFavorDTO montoSaldoAFavorDTO) {
        this.montoSaldoAFavorDTO = montoSaldoAFavorDTO;
    }

    public FacesMessage getMsgDialiogMontoSAF() {
        return msgDialiogMontoSAF;
    }

    public void setMsgDialiogMontoSAF(FacesMessage msgDialiogMontoSAF) {
        this.msgDialiogMontoSAF = msgDialiogMontoSAF;
    }

    public DycMontoSaldoAFavorDTO getMontoSaldoAFavorSeleccionado() {
        return montoSaldoAFavorSeleccionado;
    }

    public void setMontoSaldoAFavorSeleccionado(DycMontoSaldoAFavorDTO montoSaldoAFavorSeleccionado) {
        this.montoSaldoAFavorSeleccionado = montoSaldoAFavorSeleccionado;
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

    public List<DycMontoSaldoAFavorDTO> getMontoSaldoARegistrar() {
        return montoSaldoARegistrar;
    }

    public void setMontoSaldoARegistrar(List<DycMontoSaldoAFavorDTO> montoSaldoARegistrar) {
        this.montoSaldoARegistrar = montoSaldoARegistrar;
    }

}
