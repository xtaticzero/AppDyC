package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.contribuyente.ContribuyenteSectorAgroService;
import mx.gob.sat.siat.dyc.admcat.vo.DyccRfcSectorAgroVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


@ManagedBean(name = "contribuyenteSectorAgroMB")
@ViewScoped
public class ContribuyenteSectorAgroMB {

    private static final Logger LOG = Logger.getLogger(ContribuyenteSectorAgroMB.class);

    @ManagedProperty(value = "#{contribuyenteSectorAgroService}")
    private ContribuyenteSectorAgroService contribuyenteSectorAgroService;
    
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private List<DyccRfcSectorAgroVO> listaRFC;
    private String rfc;
    private String nombre;
    private Integer activo = 1;
    private String rfcBus;
    private String nombreBus;
    private Integer activoBus = 1;
    private String rfcModif;
    private String nombreModif;
    private Integer activoModif = 1;

    private boolean banCmdAgregar = Boolean.TRUE;
    private boolean banCmdActualizar = Boolean.FALSE;
    private boolean banCmdConsultar = Boolean.FALSE;
    private boolean deshabilitarTexto = Boolean.FALSE;
    private boolean deshabilitarCmdModifElim = Boolean.TRUE;
    private boolean banCmdEliminar = Boolean.FALSE;
    private boolean showRestriccion = Boolean.FALSE;
    private boolean paginador;
    private boolean btnRegresar;

    private static final int OPCION_AGREGAR = 1;
    private static final int OPCION_MODIFICAR = 2;
    private static final int OPCION_CONSULTAR = 3;
    private static final int OPCION_ELIMINAR = 4;

    private String titulo = "";
    private DyccRfcSectorAgroVO selectedRfcSectorAgroDTO;

    @PostConstruct
    public void init() {
        AccesoUsr user = serviceObtenerSesion.execute();
        if (null != user && (user.getClaveSir().trim().matches("90|91|97"))) {
            setShowRestriccion(Boolean.TRUE);
        } else {
            getCatalogoSecAgro();
        }
    }

    public void getCatalogoSecAgro() {
        cambiarOpcion(OPCION_AGREGAR);
        consultarTodos();
        setSelectedRfcSectorAgroDTO(null);
    }

    private void cambiarOpcion(int opcion) {
        if (opcion == OPCION_AGREGAR) {
            banCmdAgregar = Boolean.TRUE;
            banCmdActualizar = Boolean.FALSE;
            banCmdConsultar = Boolean.FALSE;
            deshabilitarTexto = Boolean.FALSE;
            banCmdEliminar = Boolean.FALSE;
        } else if (opcion == OPCION_MODIFICAR) {
            banCmdAgregar = Boolean.FALSE;
            banCmdActualizar = Boolean.TRUE;
            banCmdConsultar = Boolean.FALSE;
            deshabilitarTexto = Boolean.TRUE;
            banCmdEliminar = Boolean.FALSE;
        } else if (opcion == OPCION_CONSULTAR) {
            banCmdAgregar = Boolean.FALSE;
            banCmdActualizar = Boolean.FALSE;
            banCmdConsultar = Boolean.TRUE;
            deshabilitarTexto = Boolean.FALSE;
            banCmdEliminar = Boolean.FALSE;
        } else if (opcion == OPCION_ELIMINAR) {
            banCmdAgregar = Boolean.FALSE;
            banCmdActualizar = Boolean.FALSE;
            banCmdConsultar = Boolean.FALSE;
            deshabilitarTexto = Boolean.TRUE;
            banCmdEliminar = Boolean.TRUE;
        }
    }

    private void consultarTodos() {
        paginador = Boolean.FALSE;
        setSelectedRfcSectorAgroDTO(null);
        setBtnRegresar(Boolean.FALSE);
        setDeshabilitarCmdModifElim(Boolean.TRUE);
        try {
            listaRFC = contribuyenteSectorAgroService.consultar();
            if (listaRFC.size() > 0) {
                paginador = Boolean.TRUE;
            }
        } catch (SIATException e) {
            muestraMensajeError(e.getDescripcion());
            LOG.info(e.getDescripcion());
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
    }

    public void consultar() {
        paginador = Boolean.FALSE;
        try {
            DyccRfcSectorAgroVO dyccRfcSectorAgroVO = new DyccRfcSectorAgroVO();
            dyccRfcSectorAgroVO.setRfc(this.rfcBus.toUpperCase());
            dyccRfcSectorAgroVO.setNombre(this.nombreBus.toUpperCase());
            dyccRfcSectorAgroVO.setActivo(this.activoBus);
            listaRFC = contribuyenteSectorAgroService.consultar(dyccRfcSectorAgroVO);
            limpiarCampos();
            setBtnRegresar(Boolean.TRUE);
            if (listaRFC.size() > 0) {
                paginador = Boolean.TRUE;
            }
        } catch (SIATException e) {
            muestraMensajeError(e.getDescripcion());
            LOG.info(e.getDescripcion());
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
    }

    private void consultar(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) {
        paginador = Boolean.FALSE;
        try {
            listaRFC = contribuyenteSectorAgroService.consultar(dyccRfcSectorAgroVO);
            setBtnRegresar(Boolean.TRUE);
            if (listaRFC.size() > 0) {
                paginador = Boolean.TRUE;
            }
        } catch (SIATException e) {
            muestraMensajeError(e.getDescripcion());
            LOG.info(e.getDescripcion());
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
    }

    public void insert() {
        try {
            DyccRfcSectorAgroVO dyccRfcSectorAgroVO = new DyccRfcSectorAgroVO();
            dyccRfcSectorAgroVO.setRfc(this.rfc.toUpperCase());
            dyccRfcSectorAgroVO.setNombre(this.nombre.toUpperCase());
            dyccRfcSectorAgroVO.setFechaFin(null);
            dyccRfcSectorAgroVO.setActivo(this.activo);
            contribuyenteSectorAgroService.insert(dyccRfcSectorAgroVO);
            setBtnRegresar(Boolean.TRUE);
            consultar(dyccRfcSectorAgroVO);
            limpiarCampos();
            muestraMensajeInfo("El RFC"+ this.rfc.toUpperCase() +" se ha agregado correctamente");
        } catch (SIATException e) {
            muestraMensajeError(e.getDescripcion());
        }
    }

    public void update() {
        try {
            DyccRfcSectorAgroVO dyccRfcSectorAgroVO = new DyccRfcSectorAgroVO();
            dyccRfcSectorAgroVO.setRfc(this.rfcModif.toUpperCase());
            dyccRfcSectorAgroVO.setNombre(this.nombreModif.toUpperCase());
            dyccRfcSectorAgroVO.setActivo(this.activoModif);
            contribuyenteSectorAgroService.update(dyccRfcSectorAgroVO);
            deshabilitarCmdModifElim = Boolean.TRUE;
            setBtnRegresar(Boolean.TRUE);
            consultar(dyccRfcSectorAgroVO);
            cambiarOpcion(OPCION_AGREGAR);
            limpiarCampos();
            muestraMensajeInfo("Se ha modificado la información del Contribuyente del Sector Agropecuario de manera exitosa");
        } catch (SIATException e) {
            muestraMensajeError(e.getDescripcion());
        }
    }

    public void limpiarCampos() {
        setSelectedRfcSectorAgroDTO(null);
        setDeshabilitarCmdModifElim(Boolean.TRUE);
        rfc = "";
        nombre = "";
        activo = 1;
        rfcBus = "";
        nombreBus = "";
        activoBus = 1;
        rfcModif = "";
        nombreModif = "";
        activoModif = 1;
    }

    public void onRowSelected() {
        deshabilitarCmdModifElim = false;
    }

    public void muestraMensajeInfo(String mensaje) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void muestraMensajeError(String mensaje) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void cargarRegSelected() {
        if (selectedRfcSectorAgroDTO != null) {
            rfcModif = selectedRfcSectorAgroDTO.getRfc();
            nombreModif = selectedRfcSectorAgroDTO.getNombre();
            activoModif = selectedRfcSectorAgroDTO.getActivo();
            setBtnRegresar(Boolean.TRUE);
            cambiarOpcion(OPCION_MODIFICAR);
        } else {
            muestraMensajeInfo("Primero debe seleccionar un registro");
        }
    }

    public void cancelarUpdate() {
        cambiarOpcion(OPCION_AGREGAR);
        limpiarCampos();
    }

    public void cambiarTituloAgregar() {
        titulo = "Agregar RFC de Contribuyente de Sector Agropecuario";
        activo = 1;
        limpiarCampos();
        setSelectedRfcSectorAgroDTO(null);
        cambiarOpcion(OPCION_AGREGAR);
    }

    public void cambiarTituloModificar() {
        titulo = "Modificar RFC de Contribuyente de Sector Agropecuario";
        if (selectedRfcSectorAgroDTO != null) {
            rfcModif = selectedRfcSectorAgroDTO.getRfc();
            nombreModif = selectedRfcSectorAgroDTO.getNombre();
            activoModif = selectedRfcSectorAgroDTO.getActivo();

            cambiarOpcion(OPCION_MODIFICAR);
        } else {
            muestraMensajeInfo("Primero debe seleccionar un registro");
        }
    }

    public void cambiarTituloConsultar() {
        limpiarCampos();
        setSelectedRfcSectorAgroDTO(null);
        titulo = "Buscar RFC de Contribuyente de Sector Agropecuario";
        cambiarOpcion(OPCION_CONSULTAR);
    }

    public void cambiarTituloEliminar() {
        titulo = "Eliminar RFC de Contribuyente de Sector Agropecuario";
        if (selectedRfcSectorAgroDTO != null) {
            rfc = selectedRfcSectorAgroDTO.getRfc();
            nombre = selectedRfcSectorAgroDTO.getNombre();
            activo = selectedRfcSectorAgroDTO.getActivo();

            cambiarOpcion(OPCION_ELIMINAR);
        }
    }

    public void eliminar() {
        if (selectedRfcSectorAgroDTO != null) {
            try {
                contribuyenteSectorAgroService.eliminar(selectedRfcSectorAgroDTO.getRfc());
                muestraMensajeInfo("Registro eliminado");
                consultarTodos();
                deshabilitarCmdModifElim = Boolean.TRUE;
                setBtnRegresar(Boolean.TRUE);
            } catch (SIATException e) {
                muestraMensajeError("El registro no pudo ser eliminado");
            }
        } else {
            muestraMensajeInfo("Primero debe seleccionar un registro");
        }
    }

    public void setContribuyenteSectorAgroService(ContribuyenteSectorAgroService contribuyenteSectorAgroService) {
        this.contribuyenteSectorAgroService = contribuyenteSectorAgroService;
    }

    public ContribuyenteSectorAgroService getContribuyenteSectorAgroService() {
        return contribuyenteSectorAgroService;
    }

    public void setListaRFC(List<DyccRfcSectorAgroVO> listaRFC) {
        this.listaRFC = listaRFC;
    }

    public List<DyccRfcSectorAgroVO> getListaRFC() {
        return listaRFC;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setBanCmdAgregar(boolean banCmdAgregar) {
        this.banCmdAgregar = banCmdAgregar;
    }

    public boolean isBanCmdAgregar() {
        return banCmdAgregar;
    }

    public void setBanCmdActualizar(boolean banCmdActualizar) {
        this.banCmdActualizar = banCmdActualizar;
    }

    public boolean isBanCmdActualizar() {
        return banCmdActualizar;
    }

    public void setSelectedRfcSectorAgroDTO(DyccRfcSectorAgroVO selectedRfcSectorAgroDTO) {
        this.selectedRfcSectorAgroDTO = selectedRfcSectorAgroDTO;
    }

    public DyccRfcSectorAgroVO getSelectedRfcSectorAgroDTO() {
        return selectedRfcSectorAgroDTO;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setBanCmdConsultar(boolean banCmdConsultar) {
        this.banCmdConsultar = banCmdConsultar;
    }

    public boolean isBanCmdConsultar() {
        return banCmdConsultar;
    }

    public void setDeshabilitarTexto(boolean deshabilitarTexto) {
        this.deshabilitarTexto = deshabilitarTexto;
    }

    public boolean isDeshabilitarTexto() {
        return deshabilitarTexto;
    }

    public void setBanCmdEliminar(boolean banCmdEliminar) {
        this.banCmdEliminar = banCmdEliminar;
    }

    public boolean isBanCmdEliminar() {
        return banCmdEliminar;
    }

    public void setDeshabilitarCmdModifElim(boolean deshabilitarCmdModifElim) {
        this.deshabilitarCmdModifElim = deshabilitarCmdModifElim;
    }

    public boolean isDeshabilitarCmdModifElim() {
        return deshabilitarCmdModifElim;
    }

    public void setRfcBus(String rfcBus) {
        this.rfcBus = rfcBus;
    }

    public String getRfcBus() {
        return rfcBus;
    }

    public void setNombreBus(String nombreBus) {
        this.nombreBus = nombreBus;
    }

    public String getNombreBus() {
        return nombreBus;
    }

    public void setActivoBus(Integer activoBus) {
        this.activoBus = activoBus;
    }

    public Integer getActivoBus() {
        return activoBus;
    }

    public void setRfcModif(String rfcModif) {
        this.rfcModif = rfcModif;
    }

    public String getRfcModif() {
        return rfcModif;
    }

    public void setNombreModif(String nombreModif) {
        this.nombreModif = nombreModif;
    }

    public String getNombreModif() {
        return nombreModif;
    }

    public void setActivoModif(Integer activoModif) {
        this.activoModif = activoModif;
    }

    public Integer getActivoModif() {
        return activoModif;
    }

    public void setShowRestriccion(boolean showRestriccion) {
        this.showRestriccion = showRestriccion;
    }

    public boolean isShowRestriccion() {
        return showRestriccion;
    }

    public void setBtnRegresar(boolean btnRegresar) {
        this.btnRegresar = btnRegresar;
    }

    public boolean isBtnRegresar() {
        return btnRegresar;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
