/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.montoresolucion.web.controller.bean.backing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.support.DyccTipoTramiteVO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMontoResolucionService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto.FrmMontoSaldoAFavorDTO;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author root
 */
@ManagedBean(name = "catalogoMontoResolucion")
@ViewScoped
public class CatalogoMontoResolucionMB {

    private static final Logger LOG = Logger.getLogger(CatalogoMontoResolucionMB.class);

    @ManagedProperty(value = "#{dyccMontoResolucionService}")
    private DyccMontoResolucionService dyccMontoResolucionService;

    @ManagedProperty(value = "#{dyccOrigenSaldoService}")
    private DyccOrigenSaldoService dyccOrigenSaldoService;

    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{comentarioSer}")
    private ComentarioService comentarioSer;

    private List<DyccMontoResolucionDTO> lstMontosResolucion;
    private DyccMontoResolucionDTO montoResolucion;
    private List<DyccOrigenSaldoDTO> listaOrigenSaldo;
    private List<DyccTipoTramiteVO> listaTipoTramite;
    private DyccMontoResolucionDTO montoResolucionSeleccionado;
    private boolean banderaBotones;
    private Integer tipoTramite;
    private String descTipoTramite;
    private Integer origenDevolucion;
    private BigDecimal montoSaldoAFavor;
    private FrmMontoSaldoAFavorDTO frmMontoSaldoAFavorDTO;
    private Integer estado;
    private FacesMessage message;
    private AccesoUsr accesoUsr;

    private static final int IDGRUPOOPERACION = ConstantesDyCNumerico.VALOR_105;

    private static final String ALTA = "ALTA";
    private static final String MODIFICAR = "MODIFICAR";
    private static final String INGRESAR_MONTO = "Adicionar Monto de saldo autorizado";
    private static final String MODIFICAR_MONTO = "Modificar Monto de saldo autorizado";
    private static final String ERROR_CONSULTA = "Hubo un error al recoger los datos del monto saldo a favor";
    private static final String DIALOG_HIDE = "dlgMontoResol.hide();";
    private static final String MSG_AGREGAR_MONTO = "Se ha adicionado la información del monto de saldo autorizado de manera exitosa.";
    private static final String MSG_EDITAR_MONTO = "Se ha modificado la información del monto de saldo autorizado de manera exitosa.";
    private static final String MSG_ELIMINAR_MONTO = "Se ha eliminado la información del monto de saldo autorizado de manera exitosa.";
    private static final String MSG_INFO_EXISTENTE = "El Tipo de trámite ingresado ya existe, por favor verifique e intente de nuevo.";
    private static final String ERROR_REGISTRAR = "Ocurrio un error al registrar los datos del monto saldo autorizado";

    @PostConstruct
    public void init() {

        try {
            this.accesoUsr = serviceObtenerSesion.execute();
            this.frmMontoSaldoAFavorDTO = new FrmMontoSaldoAFavorDTO();
            lstMontosResolucion = dyccMontoResolucionService.findAllMontos();
            listaOrigenSaldo = dyccOrigenSaldoService.obtieneOrigenesSaldo();
            banderaBotones = Boolean.TRUE;
        } catch (SIATException ex) {
            java.util.logging.Logger.getLogger(CatalogoMontoResolucionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMontoResolucionListener() {
        RequestContext.getCurrentInstance().reset("formMontoResolucion:pnlMontoResol");
        this.origenDevolucion = 0;
        this.tipoTramite = 0;
        this.montoSaldoAFavor = null;
        this.frmMontoSaldoAFavorDTO.setBandEstado(Boolean.FALSE);
        this.frmMontoSaldoAFavorDTO.setAccion(ALTA);
        this.frmMontoSaldoAFavorDTO.setTituloDialog(INGRESAR_MONTO);
    }

    public void modificarMontoResolListener() {
        this.origenDevolucion = montoResolucionSeleccionado.getIdOrigenDevolucion();
        this.descTipoTramite = montoResolucionSeleccionado.getTipoTramite();
        this.montoSaldoAFavor = montoResolucionSeleccionado.getMontoAutorizado();
        actualizaTipoTramite();
        this.tipoTramite = montoResolucionSeleccionado.getIdTipoTramite();
        this.estado = montoResolucionSeleccionado.getEstado();
        this.frmMontoSaldoAFavorDTO.setBandEstado(Boolean.TRUE);
        this.frmMontoSaldoAFavorDTO.setAccion(MODIFICAR);
        this.frmMontoSaldoAFavorDTO.setTituloDialog(MODIFICAR_MONTO);
    }

    public void execGuardar() {
        if (frmMontoSaldoAFavorDTO.getAccion().equals(ALTA)) {
            try {
                String descripcionTramite = null;
                DyccMontoResolucionDTO montoResolARegistrar;
                montoResolucion = new DyccMontoResolucionDTO();
                montoResolucion.setIdOrigenDevolucion(origenDevolucion);
                montoResolucion.setIdTipoTramite(tipoTramite);
                montoResolARegistrar = dyccMontoResolucionService.existeMontoAutorizado(montoResolucion);

                if (montoResolARegistrar != null) {
                    descripcionTramite = montoResolARegistrar.getTipoTramite();
                }
                if (descripcionTramite != null) {
                    this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, MSG_INFO_EXISTENTE);
                } else {
                    LOG.info("Monto saldo a favor: " + montoSaldoAFavor);
                    montoResolucion.setMontoAutorizado(montoSaldoAFavor);
                    montoResolucion.setEstado(ConstantesDyCNumerico.VALOR_1);
                    this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, MSG_AGREGAR_MONTO);
                    dyccMontoResolucionService.insertarMonto(montoResolucion, crearPistaAuditoria(ConstantesDyCNumerico.VALOR_135, ConstantesDyCNumerico.VALOR_1434));
                    lstMontosResolucion = dyccMontoResolucionService.findAllMontos();
                    montoResolucionSeleccionado = null;
                    this.banderaBotones = Boolean.TRUE;
                    RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
                }
            } catch (SIATException siate) {
                LOG.error(siate);
                this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else if (frmMontoSaldoAFavorDTO.getAccion().equals(MODIFICAR)) {
            try {
                montoResolucion = montoResolucionSeleccionado;
                montoResolucion.setMontoAutorizado(montoSaldoAFavor);
                montoResolucion.setEstado(estado);
                montoResolucion.setIdTipoTramite(tipoTramite);
                montoResolucion.setIdOrigenDevolucion(origenDevolucion);
                this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, MSG_EDITAR_MONTO);
                dyccMontoResolucionService.actualizarMonto(montoResolucion, crearPistaAuditoria(ConstantesDyCNumerico.VALOR_136, ConstantesDyCNumerico.VALOR_1435));
                lstMontosResolucion = dyccMontoResolucionService.findAllMontos();
                this.banderaBotones = Boolean.TRUE;
                montoResolucionSeleccionado = null;
                RequestContext.getCurrentInstance().execute(DIALOG_HIDE);
            } catch (SIATException siate) {
                LOG.error(siate);
                this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ERROR_REGISTRAR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void removeMontoAutorizadoListener() {
        try {
            montoResolucion = montoResolucionSeleccionado;
            dyccMontoResolucionService.inactivarMonto(montoResolucion, crearPistaAuditoria(ConstantesDyCNumerico.VALOR_137, ConstantesDyCNumerico.VALOR_1436));
            lstMontosResolucion = dyccMontoResolucionService.findAllMontos();
            this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, MSG_ELIMINAR_MONTO);
            this.banderaBotones = Boolean.TRUE;
            this.montoResolucionSeleccionado = null;
        } catch (SIATException siate) {
            LOG.error(siate);
            this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ERROR_REGISTRAR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizaTipoTramite() {
        try {
            long idOrigenSaldo = origenDevolucion;
            LOG.info("Consultando tipo tramite de acuerdo al id de origen de la devolución= " + idOrigenSaldo);
            if (idOrigenSaldo > ConstantesDyC1.CERO) {
                listaTipoTramite = catalogoTipoTramiteService.obtieneTipoTramite(idOrigenSaldo);
            } else {
                listaTipoTramite = new ArrayList<DyccTipoTramiteVO>();
            }
        } catch (SIATException e) {
            LOG.error(ERROR_CONSULTA, e);
        }
    }

    private PistaAuditoriaVO crearPistaAuditoria(int idMensaje, int idMovimiento) {

        PistaAuditoriaVO rPistaAutitoria = new PistaAuditoriaVO();
        rPistaAutitoria.setIdProceso(Procesos.DYC00104);
        rPistaAutitoria.setIdGrupoOperacion(IDGRUPOOPERACION);
        rPistaAutitoria.setIdMensaje(idMensaje);
        rPistaAutitoria.setMovimiento(idMovimiento);
        rPistaAutitoria.setIdentificador(accesoUsr.getNumeroEmp());
        LOG.info("Se mapearon pistas de auditoria: " + accesoUsr.getNumeroEmp());
        return rPistaAutitoria;
    }

    public void limpiarCombosOrigenTramite() {
        LOG.info("Limpiando combos");
        listaOrigenSaldo = dyccOrigenSaldoService.obtieneOrigenesSaldo();
        listaTipoTramite = new ArrayList<DyccTipoTramiteVO>();
    }

    public void activarBotones(SelectEvent event) {
        this.banderaBotones = Boolean.FALSE;
    }

    public Integer getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(Integer tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Integer getOrigenDevolucion() {
        return origenDevolucion;
    }

    public void setOrigenDevolucion(Integer origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }

    public BigDecimal getMontoSaldoAFavor() {
        return montoSaldoAFavor;
    }

    public void setMontoSaldoAFavor(BigDecimal montoSaldoAFavor) {
        this.montoSaldoAFavor = montoSaldoAFavor;
    }

    public List<DyccMontoResolucionDTO> getLstMontosResolucion() {
        return lstMontosResolucion;
    }

    public void setLstMontosResolucion(List<DyccMontoResolucionDTO> lstMontosResolucion) {
        this.lstMontosResolucion = lstMontosResolucion;
    }

    public DyccMontoResolucionDTO getMontoResolucion() {
        return montoResolucion;
    }

    public void setMontoResolucion(DyccMontoResolucionDTO montoResolucion) {
        this.montoResolucion = montoResolucion;
    }

    public List<DyccOrigenSaldoDTO> getListaOrigenSaldo() {
        return listaOrigenSaldo;
    }

    public void setListaOrigenSaldo(List<DyccOrigenSaldoDTO> listaOrigenSaldo) {
        this.listaOrigenSaldo = listaOrigenSaldo;
    }

    public List<DyccTipoTramiteVO> getListaTipoTramite() {
        return listaTipoTramite;
    }

    public void setListaTipoTramite(List<DyccTipoTramiteVO> listaTipoTramite) {
        this.listaTipoTramite = listaTipoTramite;
    }

    public DyccMontoResolucionDTO getMontoResolucionSeleccionado() {
        return montoResolucionSeleccionado;
    }

    public void setMontoResolucionSeleccionado(DyccMontoResolucionDTO montoResolucionSeleccionado) {
        this.montoResolucionSeleccionado = montoResolucionSeleccionado;
    }

    public boolean isBanderaBotones() {
        return banderaBotones;
    }

    public void setBanderaBotones(boolean banderaBotones) {
        this.banderaBotones = banderaBotones;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public DyccOrigenSaldoService getDyccOrigenSaldoService() {
        return dyccOrigenSaldoService;
    }

    public void setDyccOrigenSaldoService(DyccOrigenSaldoService dyccOrigenSaldoService) {
        this.dyccOrigenSaldoService = dyccOrigenSaldoService;
    }

    public CatalogoTipoTramiteService getCatalogoTipoTramiteService() {
        return catalogoTipoTramiteService;
    }

    public void setCatalogoTipoTramiteService(CatalogoTipoTramiteService catalogoTipoTramiteService) {
        this.catalogoTipoTramiteService = catalogoTipoTramiteService;
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

    public FacesMessage getMessage() {
        return message;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
    }

    public String getDescTipoTramite() {
        return descTipoTramite;
    }

    public void setDescTipoTramite(String descTipoTramite) {
        this.descTipoTramite = descTipoTramite;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public ComentarioService getComentarioSer() {
        return comentarioSer;
    }

    public void setComentarioSer(ComentarioService comentarioSer) {
        this.comentarioSer = comentarioSer;
    }

    public DyccMontoResolucionService getDyccMontoResolucionService() {
        return dyccMontoResolucionService;
    }

    public void setDyccMontoResolucionService(DyccMontoResolucionService dyccMontoResolucionService) {
        this.dyccMontoResolucionService = dyccMontoResolucionService;
    }
}
