package mx.gob.sat.siat.dyc.devolucionaut.catalogos.web.controller.bean.backing;

import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyccModeloService;
import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.MotivoRiesgoService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto.FrmMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author Jose Luis Aguilar
 */
@ManagedBean(name = "motivoRiesgo")
@ViewScoped
public class CatalogoMotivoRiesgoMB  extends AbstractPage {
    
    private String headerDialog;
    private boolean banderaBotones;
    private String codigo;
    private String regla;
    private String modelo;
    private String mensajeNotif = "";
    private int estado = 1;
    private FacesMessage mensajePantalla;
    private FacesMessage msgDialiogMotivoRiesgo;
    private DycMotivoRiesgoDTO motivoRiesgoDTO;
    private DycMotivoRiesgoDTO motivoRiesgoSeleccionado;
    private List<DycMotivoRiesgoDTO> motivoRiesgoARegistrar;
    private List<DycMotivoRiesgoDTO> listaMotivoRiesgo;
    private List<DyccModelo> listaModelo;
    private AccesoUsr accesoUsr;
    private HttpServletRequest request;
    private static final Logger LOGGER = Logger.getLogger(CatalogoMotivoRiesgoMB.class);
    private static final String ERROR_CONSULTA = "Hubo un error al recoger los datos del motivo de riesgo";
    private static final String ERROR_REGISTRAR = "Ocurrio un error al registrar los datos del motivo de riesgo";
    private static final String INGRESAR_MOTIVO_RIESGO = "Ingresar motivo de riesgo";
    private static final String CONSULTAR_MOTIVO_RIESGO = "Consultar motivo de riesgo";
    private static final String MODIFICAR_MOTIVO_RIESGO = "Modificar motivo de riesgo";
    private static final String ALTA = "ALTA";
    private static final String CONSULTAR = "CONSULTAR";
    private static final String MODIFICAR = "MODIFICAR";
    private static final String DIALOG_HIDE = "dlgMotivoRiesgo.hide();";
    private static final String ALL_ENTER="\r\n";
    private static final String ENTER="\n";
    
    @ManagedProperty(value = "#{motivoRiesgoService}")
    private MotivoRiesgoService motivoRiesgoService;
    
    
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{dyccModeloService}")
    private DyccModeloService dyccModeloService;
    
    private FrmMotivoRiesgoDTO frmMotivoRiesgoDTO;

    public CatalogoMotivoRiesgoMB() {
        super();
    }
    
    /**
     * Consulta todos los datos necesarios para mostrar los datos en pantalla
     */
    @PostConstruct
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00106);
        try {
            this.frmMotivoRiesgoDTO = new FrmMotivoRiesgoDTO();
            banderaBotones = Boolean.TRUE;
            listaModelo = dyccModeloService.consultarTodos();
            listaMotivoRiesgo = motivoRiesgoService.consultarTodos();
        } catch (SIATException siate) {
            LOGGER.error(ERROR_CONSULTA);
            mensajePantalla = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ERROR_CONSULTA);
        }
    }
    
    public void addMotivoRiesgoListener() {
        RequestContext.getCurrentInstance().reset("formMotivoRiesgo:pnlMotivoRiesgo");
        this.codigo = null;
        this.regla = null;
        this.modelo = null;
        this.frmMotivoRiesgoDTO.setBandActivoCodigo(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandActivoModelo(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandCodigo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandRegla(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandModelo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandBtnGuardar(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandEstado(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBanderaMsg(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setAccion(ALTA);
        this.frmMotivoRiesgoDTO.setTituloDialog(INGRESAR_MOTIVO_RIESGO);
    }
    
    public void selectMotivoRiesgoLitener() {
        RequestContext.getCurrentInstance().reset("formMotivoRiesgo:pnlMotivoRiesgo");
        this.codigo = null;
        this.modelo = null;
        this.frmMotivoRiesgoDTO.setBandActivoCodigo(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandActivoModelo(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandCodigo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandRegla(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandModelo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandBtnGuardar(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandBtnBuscar(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandEstado(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBanderaMsg(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setAccion(CONSULTAR);
        this.frmMotivoRiesgoDTO.setTituloDialog(CONSULTAR_MOTIVO_RIESGO);
        
    }
    
    public void modifyMotivoRiesgoLitener() {
        this.codigo = motivoRiesgoSeleccionado.getCodigo().toString();
        this.regla = motivoRiesgoSeleccionado.getRegla();
        this.modelo = "1";
        if (motivoRiesgoSeleccionado.getModelo().equals("MORSA")) {
            this.modelo = "2";
        }
        this.estado = 1;
        this.frmMotivoRiesgoDTO.setBandActivoCodigo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandActivoModelo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandActivoEstado(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandCodigo(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandRegla(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandModelo(Boolean.TRUE);
        if (motivoRiesgoSeleccionado.getEstado().equals("Inactivo")) {
            this.estado = 0;
            this.frmMotivoRiesgoDTO.setBandActivoEstado(Boolean.FALSE);
        }
        this.frmMotivoRiesgoDTO.setBandBtnGuardar(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandEstado(Boolean.TRUE);
        this.frmMotivoRiesgoDTO.setBanderaMsg(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setAccion(MODIFICAR);
        this.frmMotivoRiesgoDTO.setTituloDialog(MODIFICAR_MOTIVO_RIESGO);
    }
    
    public void muestraMensaje() {
        this.frmMotivoRiesgoDTO.setBandBtnGuardar(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandBtnBuscar(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBandEstado(Boolean.FALSE);
        this.frmMotivoRiesgoDTO.setBanderaMsg(Boolean.TRUE);
        RequestContext.getCurrentInstance().execute("notifDlgVar.show();");
    }
    
    public void execGuardar() {
        LOGGER.info("tam regla= "+regla.length());
        regla=StringUtils.replace(regla,ALL_ENTER ,ENTER);
        LOGGER.info("tam regla M= "+regla.length());
        if (frmMotivoRiesgoDTO.getAccion().equals(ALTA)) {
            try {
                    motivoRiesgoDTO = new DycMotivoRiesgoDTO();
                    motivoRiesgoDTO.setCodigo(Integer.parseInt(codigo));
                    motivoRiesgoDTO.setModelo(modelo);                    
                    motivoRiesgoDTO.setRegla(regla);
                    motivoRiesgoARegistrar = motivoRiesgoService.existe(motivoRiesgoDTO);
                    if (!motivoRiesgoARegistrar.isEmpty()) {
                        this.mensajeNotif = "El código " +  motivoRiesgoDTO.getCodigo() + " ya fue registrado anteriormente en el catálogo";
                        muestraMensaje();
                        RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                    } else {
                        this.mensajeNotif = "Se registra el motivo de riesgo  " + motivoRiesgoDTO.getCodigo() + 
                        ", ingresado por "+ accesoUsr.getNombreCompleto() + ".";
                        this.motivoRiesgoService.registrar(motivoRiesgoDTO, accesoUsr);
                        listaMotivoRiesgo = motivoRiesgoService.consultarTodos();
                        this.banderaBotones = Boolean.TRUE;
                        motivoRiesgoSeleccionado = null;
                        muestraMensaje();
                        RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                    }
            } catch (SIATException siate) {
                this.msgDialiogMotivoRiesgo = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMotivoRiesgo);
            }
        }else if  (frmMotivoRiesgoDTO.getAccion().equals(MODIFICAR)) {
            try {
                    motivoRiesgoDTO = motivoRiesgoSeleccionado;                    
                    motivoRiesgoDTO.setRegla(regla);
                    motivoRiesgoDTO.setEstado("" + estado);
                    this.mensajeNotif = "Se actualiza el motivo de riesgo " +  motivoRiesgoDTO.getCodigo() + ", por "+ accesoUsr.getNombreCompleto() + ".";
                    motivoRiesgoService.actualizar(motivoRiesgoDTO, accesoUsr);
                    listaMotivoRiesgo = motivoRiesgoService.consultarTodos();
                    this.banderaBotones = Boolean.TRUE;
                    motivoRiesgoSeleccionado = null;
                    muestraMensaje();
                    RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
            } catch (SIATException siate) {
                this.msgDialiogMotivoRiesgo = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMotivoRiesgo);
            }
        }
        
    }
    
    public void execBuscar() {
        try {
                motivoRiesgoDTO = new DycMotivoRiesgoDTO();
                motivoRiesgoDTO.setCodigo(Integer.parseInt(codigo));
                motivoRiesgoDTO.setModelo(modelo);
                listaMotivoRiesgo = motivoRiesgoService.consultarFiltro(motivoRiesgoDTO);
                this.banderaBotones = Boolean.TRUE;
                motivoRiesgoSeleccionado = null;
                RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
            } catch (SIATException siate) {
                this.msgDialiogMotivoRiesgo = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_CONSULTA);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMotivoRiesgo);
            }
    }
    
    public void removeMotivoRiesgoLitener() {
        try {
                    motivoRiesgoDTO = motivoRiesgoSeleccionado;
                    motivoRiesgoService.inActivar(motivoRiesgoDTO, accesoUsr);
                    listaMotivoRiesgo = motivoRiesgoService.consultarTodos();
                    this.mensajeNotif = "Ha sido dado de Baja el motivo de riesgo " + motivoRiesgoDTO.getCodigo();
                    this.banderaBotones = Boolean.TRUE;
                    motivoRiesgoSeleccionado = null;
                    muestraMensaje();
            } catch (SIATException siate) {
                this.msgDialiogMotivoRiesgo = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, msgDialiogMotivoRiesgo);
            }
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRegla() {
        return regla;
    }

    public void setRegla(String regla) {
        this.regla = regla;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMensajeNotif() {
        return mensajeNotif;
    }

    public void setMensajeNotif(String mensajeNotif) {
        this.mensajeNotif = mensajeNotif;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public FacesMessage getMensajePantalla() {
        return mensajePantalla;
    }

    public void setMensajePantalla(FacesMessage mensajePantalla) {
        this.mensajePantalla = mensajePantalla;
    }

    public DycMotivoRiesgoDTO getMotivoRiesgoDTO() {
        return motivoRiesgoDTO;
    }

    public void setMotivoRiesgoDTO(DycMotivoRiesgoDTO motivoRiesgoDTO) {
        this.motivoRiesgoDTO = motivoRiesgoDTO;
    }

    public DycMotivoRiesgoDTO getMotivoRiesgoSeleccionado() {
        return motivoRiesgoSeleccionado;
    }

    public void setMotivoRiesgoSeleccionado(DycMotivoRiesgoDTO motivoRiesgoSeleccionado) {
        this.motivoRiesgoSeleccionado = motivoRiesgoSeleccionado;
    }

    public List<DycMotivoRiesgoDTO> getListaMotivoRiesgo() {
        return listaMotivoRiesgo;
    }

    public void setListaMotivoRiesgo(List<DycMotivoRiesgoDTO> listaMotivoRiesgo) {
        this.listaMotivoRiesgo = listaMotivoRiesgo;
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

    public MotivoRiesgoService getMotivoRiesgoService() {
        return motivoRiesgoService;
    }

    public void setMotivoRiesgoService(MotivoRiesgoService motivoRiesgoService) {
        this.motivoRiesgoService = motivoRiesgoService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public FrmMotivoRiesgoDTO getFrmMotivoRiesgoDTO() {
        return frmMotivoRiesgoDTO;
    }

    public void setFrmMotivoRiesgoDTO(FrmMotivoRiesgoDTO frmMotivoRiesgoDTO) {
        this.frmMotivoRiesgoDTO = frmMotivoRiesgoDTO;
    }

    public FacesMessage getMsgDialiogMotivoRiesgo() {
        return msgDialiogMotivoRiesgo;
    }

    public void setMsgDialiogMotivoRiesgo(FacesMessage msgDialiogMotivoRiesgo) {
        this.msgDialiogMotivoRiesgo = msgDialiogMotivoRiesgo;
    }

    public List<DycMotivoRiesgoDTO> getMotivoRiesgoARegistrar() {
        return motivoRiesgoARegistrar;
    }

    public void setMotivoRiesgoARegistrar(List<DycMotivoRiesgoDTO> motivoRiesgoARegistrar) {
        this.motivoRiesgoARegistrar = motivoRiesgoARegistrar;
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

}
