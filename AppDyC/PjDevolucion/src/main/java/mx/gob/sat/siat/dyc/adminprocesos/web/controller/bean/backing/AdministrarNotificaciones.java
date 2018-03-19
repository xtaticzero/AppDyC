package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.backing;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.adminprocesos.service.AdministrarNotificacionesService;
import mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.model.LazyNotificacionesDetalleDataModel;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.mqenvionyv.service.NotificacionEnvioService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.NotificacionVO;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;


@ManagedBean(name = "administrarNotificaciones")
@ViewScoped
public class AdministrarNotificaciones implements Serializable {

    @SuppressWarnings("compatibility:6633676878041422301")
    private static final long serialVersionUID = 1708945059468208975L;

    @ManagedProperty(value = "#{administrarNotificacionesService}")
    private transient AdministrarNotificacionesService administrarNotificacionesService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private transient ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{notificacionEnvioService}")
    private transient NotificacionEnvioService notificacionEnvioService;

    private static final Logger LOGGER = Logger.getLogger(AdministrarNotificaciones.class);

    private LazyDataModel<DyctFalloMensajesDTO> falloMensajes;
    private List<DyctFalloMensajesDTO> selectedFalloMensajes;
    private transient NotificacionVO selectedNotificacion;
    private List<NotificacionVO> notificaciones;


    private boolean enabledBtnReprocesar;

    @PostConstruct
    public void init() {
        try {
            Utils.validarSesion(serviceObtenerSesion.execute(), mx.gob.sat.siat.dyc.util.constante.Procesos.DYC00018);
            cargaNotificaciones();
        } catch (Exception e) {
            LOGGER.error(String.format("Error al cargar la informacion: %s", e));
        }
    }

    public void cargaNotificaciones() {
        notificaciones = new ArrayList<NotificacionVO>();
        notificaciones = administrarNotificacionesService.getNotificaciones();
    }

    public void cargaNotificacion() {
        selectedFalloMensajes = new ArrayList<DyctFalloMensajesDTO>();
        enabledBtnReprocesar = false;
        falloMensajes =
                new LazyNotificacionesDetalleDataModel(administrarNotificacionesService, selectedNotificacion.getIdFalloDetalle());
    }

    public void cargaDocRezagados() {
        selectedFalloMensajes = new ArrayList<DyctFalloMensajesDTO>();
        enabledBtnReprocesar = false;
        falloMensajes = new LazyNotificacionesDetalleDataModel(administrarNotificacionesService);
    }

    public void reencolamiento() {
        AprobarDocumentoDTO aprobarDocumento = new AprobarDocumentoDTO();
        FacesContext fc = FacesContext.getCurrentInstance();
        boolean isRezagado = Boolean.TRUE;
        
        LOGGER.info("JAHO - Se reencolan los mensajes seleccionados: "+selectedFalloMensajes.size());
        for (DyctFalloMensajesDTO mensaje : selectedFalloMensajes) {
            try {
                LOGGER.info("Objeto seleccionado: "+ExtractorUtil.ejecutar(mensaje));
                aprobarDocumento.setNumControlDoc(mensaje.getMensaje());
                notificacionEnvioService.enviarANYV(aprobarDocumento);
                if (mensaje.getIdFalloMensaje() != null && mensaje.getIdFalloMensaje() > 0) {
                    administrarNotificacionesService.deleteFalloMensajes(mensaje.getIdFalloMensaje());
                    isRezagado = false;
                }
            } catch (SIATException e) {
                LOGGER.error(String.format("reencolamiento(); causa: %s", e.getCause()));
                this.desplegarError(fc,
                                    String.format("Ocurrio un error al reencolar el documento: %s", mensaje.getMensaje()));
            } finally {
                if (isRezagado) {
                    cargaDocRezagados();
                } else {
                    cargaNotificacion();
                }
            }
        }

        this.desplegarMensaje(fc, "Se han enviado a encolar los mensajes seleccionados.");
    }

    public void desplegarMensaje(FacesContext fc, String msg) {
        fc.addMessage("msgInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, null, msg));
        fc.renderResponse();
    }

    public void desplegarError(FacesContext fc, String msg) {
        fc.addMessage("msgError", new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
        fc.renderResponse();
    }

    public void onRowSelect(SelectEvent event) {
        validaEnableBtnReprocesar();
    }

    public void onRowUnselect(UnselectEvent event) {
        validaEnableBtnReprocesar();
    }

    public void onRowSelect(ToggleSelectEvent event) {
        validaEnableBtnReprocesar();
    }

    private void validaEnableBtnReprocesar() {
        if (!selectedFalloMensajes.isEmpty()) {
            enabledBtnReprocesar = Boolean.TRUE;
        } else {
            enabledBtnReprocesar = Boolean.FALSE;
        }
    }

    public AdministrarNotificacionesService getAdministrarNotificacionesService() {
        return administrarNotificacionesService;
    }

    public void setAdministrarNotificacionesService(AdministrarNotificacionesService administrarNotificacionesService) {
        this.administrarNotificacionesService = administrarNotificacionesService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public NotificacionEnvioService getNotificacionEnvioService() {
        return notificacionEnvioService;
    }

    public void setNotificacionEnvioService(NotificacionEnvioService notificacionEnvioService) {
        this.notificacionEnvioService = notificacionEnvioService;
    }

    public LazyDataModel<DyctFalloMensajesDTO> getFalloMensajes() {
        return falloMensajes;
    }

    public void setFalloMensajes(LazyDataModel<DyctFalloMensajesDTO> falloMensajes) {
        this.falloMensajes = falloMensajes;
    }

    public List<NotificacionVO> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionVO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<DyctFalloMensajesDTO> getSelectedFalloMensajes() {
        return selectedFalloMensajes;
    }

    public void setSelectedFalloMensajes(List<DyctFalloMensajesDTO> selectedFalloMensajes) {
        this.selectedFalloMensajes = selectedFalloMensajes;
    }

    public NotificacionVO getSelectedNotificacion() {
        return selectedNotificacion;
    }

    public void setSelectedNotificacion(NotificacionVO selectedNotificacion) {
        this.selectedNotificacion = selectedNotificacion;
    }

    public boolean isEnabledBtnReprocesar() {
        return enabledBtnReprocesar;
    }

    public void setEnabledBtnReprocesar(boolean enabledBtnReprocesar) {
        this.enabledBtnReprocesar = enabledBtnReprocesar;
    }
}
