package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DatosTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.GuardadoTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.utility.PickListATablasTipoTramite;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.generico.util.common.Messages;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;

import org.apache.log4j.Logger;

import org.primefaces.model.DualListModel;


@ManagedBean(name = "altaTipoTramite")
@ViewScoped
public class AltaTipoTramiteMB extends PickListATablasTipoTramite {

    private boolean banderaBoton;

    private Integer concepto;
    private Integer idTramite;
    private Integer impuesto;
    private Integer numeroDeDias;
    private Integer origenSaldo;
    private Integer puntosAsignacion;
    private Integer requiereCCI;
    private Integer resolucionAutomatica;
    private Integer tipoPlazo;
    private Integer tipoServicio;

    private String claveContable;
    private String claveComputo;
    private String descripcion;

    private static final Integer INSERCION_NO_EXITOSA = 3;
    private static final Logger LOGGER = Logger.getLogger(AltaTipoTramiteMB.class);

    private List<DyccConceptoDTO> listaConceptos;
    private List<DyccImpuestoDTO> listaImpuestos;
    private List<DyccInfoARequerirDTO> listaInfoARequerirDestino;
    private List<DyccMatrizAnexosDTO> listaMatrizAnexosDestino;
    private List<DyccOrigenSaldoDTO> listaOrigenSaldo;
    private List<DyccRolDTO> listaRolesDestino;
    private List<DyccSubOrigSaldoDTO> listaSubOrigenesSaldoDestino;
    private List<DyccSubTramiteDTO> listaSubtramitesDestino;
    private List<DyccTipoPeriodoDTO> listaTipoPeriodoDestino;
    private List<DyccTipoPlazoDTO> listaTipoPlazo;
    private List<DyccTipoServicioDTO> listaTipoServicio;
    private List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtvaDestino;

    private DualListModel<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtvaTipo;
    private DualListModel<DyccInfoARequerirDTO> listaInfoARequerir;
    private DualListModel<DyccMatrizAnexosDTO> listaMatrizAnexos;
    private DualListModel<DyccRolDTO> listaRoles;
    private DualListModel<DyccSubOrigSaldoDTO> listaSubOrigenesSaldo;
    private DualListModel<DyccSubTramiteDTO> listaSubtramites;
    private DualListModel<DyccTipoPeriodoDTO> listaTipoPeriodo;

    private DatosTipoTramiteVO datos;


    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;


    /**
     * Constructor
     */
    public AltaTipoTramiteMB() {
        listaUnidadAdmtvaDestino = new ArrayList<AdmcUnidAdmvaTipoDTO>();
        listaInfoARequerirDestino = new ArrayList<DyccInfoARequerirDTO>();
        listaMatrizAnexosDestino = new ArrayList<DyccMatrizAnexosDTO>();
        listaOrigenSaldo = new ArrayList<DyccOrigenSaldoDTO>();
        listaRolesDestino = new ArrayList<DyccRolDTO>();
        listaSubOrigenesSaldoDestino = new ArrayList<DyccSubOrigSaldoDTO>();
        listaSubtramitesDestino = new ArrayList<DyccSubTramiteDTO>();
        listaTipoPeriodoDestino = new ArrayList<DyccTipoPeriodoDTO>();
    }

    /**
     * Obtiene los datos necesarios para cargar los catalogos en pantalla
     */
    @PostConstruct
    public void init() {
        UtilsValidaSesion.validarSesion(serviceObtenerSesion.execute(), Procesos.DYC00014);
        try {
            datos = catalogoTipoTramiteService.listarDatosParaTipoDeTramite();
        } catch (SIATException siate) {
            LOGGER.error("Error al cargar la informacion: " + siate);
        }
        listaImpuestos = datos.getListaImpuestos();
        listaTipoPlazo = datos.getListaTipoPlazo();
        listaTipoServicio = datos.getListaTipoServicio();

        listaUnidadAdmtvaTipo =
                new DualListModel<AdmcUnidAdmvaTipoDTO>(datos.getListaUnidadAdmtvaTipo(), listaUnidadAdmtvaDestino);
        listaInfoARequerir =
                new DualListModel<DyccInfoARequerirDTO>(datos.getListaInfoARequerir(), listaInfoARequerirDestino);
        listaMatrizAnexos =
                new DualListModel<DyccMatrizAnexosDTO>(datos.getListaMatrizAnexos(), listaMatrizAnexosDestino);
        listaRoles = new DualListModel<DyccRolDTO>(datos.getListaRoles(), listaRolesDestino);
        listaSubOrigenesSaldo =
                new DualListModel<DyccSubOrigSaldoDTO>(datos.getListaSubOrigenesSaldo(), listaSubOrigenesSaldoDestino);
        listaSubtramites = new DualListModel<DyccSubTramiteDTO>(datos.getListaSubtramites(), listaSubtramitesDestino);
        listaTipoPeriodo = new DualListModel<DyccTipoPeriodoDTO>(datos.getListaTipoPeriodo(), listaTipoPeriodoDestino);
    }

    /**
     * Valida si el tramite que se ingresa ya existe
     *
     */
    public void validarIdTramite() throws SIATException {
        if (catalogoTipoTramiteService.validarIdTramite(idTramite) == 1) {
            FacesMessage message =
                Messages.getMessage("El trámite ya existe.", new Object[] { "altaTipoTramite:idMensajeTipoTramite",
                                                                            "idMensajeTipoTramite" });
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            banderaBoton = Boolean.TRUE;
        } else {
            banderaBoton = Boolean.FALSE;
        }
    }

    /**
     * Regresa a la pagina de catalogosTipoTramite
     *
     * @param resultado si envia el valor de 1, quiere decir que el resultado fué exitoso. 3 en caso contrario.
     * @return catalogoTipoTramite.jsf
     */
    public void redireccionar(int resultado) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                                                                            "/faces/resources/pages/admcat/catalogoTipoTramite.jsf?resultado=" +
                                                                            resultado);

        } catch (Exception e) {
            LOGGER.error("Hubo un error al tratar de modificar el tipo de trámite, intente mas tarde.");
        }
    }

    /**
     * Regresa a la pagina de catalogosTipoTramite
     *
     * @return catalogoTipoTramite.jsf
     */
    public String regresar() {
        return "catalogoTipoTramite";
    }

    /**
     * Guarda el nuevo tramite y lo registra en base de datos.
     */
    public void addTipoTramiteListener(ActionEvent de) {

        GuardadoTipoTramiteVO objeto = new GuardadoTipoTramiteVO();

        objeto.setTipoTramite(obtenerDatosTipoTramite());
        objeto.setUnidadTramite(obtenerUnidadesTramite(listaUnidadAdmtvaTipo.getTarget(), idTramite));
        objeto.setTramiteRol(obtenerTramiteRol(listaRoles.getTarget(), idTramite));
        objeto.setAnexoTramite(obtenerAnexoTramite(listaMatrizAnexos.getTarget(), idTramite));
        objeto.setInfoTramite(obtenerInforTramite(listaInfoARequerir.getTarget(), idTramite));
        objeto.setTtSubtramite(obtenerSubtramite(listaSubtramites.getTarget(), idTramite));
        objeto.setSubSaldoTram(obtenerSUbSaldoTramite(listaSubOrigenesSaldo.getTarget(), idTramite));
        objeto.setTipoPeriodoTt(obtenerTipoPeriodoTt(listaTipoPeriodo.getTarget(), idTramite));
        objeto.setOrigenTramite(obtenerOrigenTramite(tipoServicio, idTramite, origenSaldo));

        try {
            catalogoTipoTramiteService.guardarNuevoTramite(objeto);
            redireccionar(1);

        } catch (SIATException e) {
            LOGGER.error("Error al guardar el tipo del tramite: " + e);
            redireccionar(INSERCION_NO_EXITOSA);
        }

        objeto = null;
    }

    /**
     * Filtra los origenes del saldo a partir del tipo de servicio seleccionado.
     *
     * @param de
     */
    public void addOrigenSaldoListener(AjaxBehaviorEvent de) {
        origenSaldo = null;
        try {
            if (tipoServicio != null) {
                if (tipoServicio == 0) {
                    listaOrigenSaldo.clear();
                } else {
                    listaOrigenSaldo = catalogoTipoTramiteService.obtenerOrigenSaldo(tipoServicio);
                }
            } else {
                listaOrigenSaldo.clear();
            }
        } catch (SIATException siate) {
            LOGGER.error(siate);
        }
    }

    /**
     * Filtra los conceptos del saldo a partir del impuesto seleccionado.
     *
     * @param de
     */
    public void addConceptoListener(AjaxBehaviorEvent de) {
        concepto = null;
        try {
            if (impuesto != null) {
                if (impuesto == 0) {
                    listaConceptos.clear();
                } else {
                    listaConceptos = catalogoTipoTramiteService.obtenerConcepto(impuesto);
                }
            } else {
                listaConceptos.clear();
            }
        } catch (SIATException siate) {
            LOGGER.error(siate);
        }
    }

    /**
     * Recoge de la pantalla de alta de tipo de tramite los datos seleccionados para insertar en la tabla de Dycc_tipoTramite
     *
     * @return
     */
    private DyccTipoTramiteDTO obtenerDatosTipoTramite() {

        DyccConceptoDTO objetoConcepto = new DyccConceptoDTO();
        DyccTipoPlazoDTO objetoPlazo = new DyccTipoPlazoDTO();
        DyccTipoTramiteDTO objeto = new DyccTipoTramiteDTO();

        objeto.setIdTipoTramite(idTramite);
        objeto.setDescripcion(descripcion);
        objeto.setClaveContable(claveContable);
        objeto.setClaveComputo(claveComputo);

        if (requiereCCI == 1) {
            objeto.setRequiereCCI(Boolean.TRUE);
        } else {
            objeto.setRequiereCCI(Boolean.FALSE);
        }
        objeto.setResolAutomatica(resolucionAutomatica);
        objeto.setPuntosAsignacion(puntosAsignacion);

        objetoConcepto.setIdConcepto(concepto);
        objeto.setDyccConceptoDTO(objetoConcepto);

        objeto.setPlazo(numeroDeDias);

        objetoPlazo.setIdTipoPlazo(tipoPlazo);
        objeto.setDyccTipoPlazoDTO(objetoPlazo);

        return objeto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdTramite(Integer idTramite) {
        this.idTramite = idTramite;
    }

    public Integer getIdTramite() {
        return idTramite;
    }

    public void setTipoServicio(Integer tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Integer getTipoServicio() {
        return tipoServicio;
    }

    public void setOrigenSaldo(Integer origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public Integer getOrigenSaldo() {
        return origenSaldo;
    }

    public void setCatalogoTipoTramiteService(CatalogoTipoTramiteService catalogoTipoTramiteService) {
        this.catalogoTipoTramiteService = catalogoTipoTramiteService;
    }

    public CatalogoTipoTramiteService getCatalogoTipoTramiteService() {
        return catalogoTipoTramiteService;
    }

    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }

    public Integer getConcepto() {
        return concepto;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public void setClaveContable(String claveContable) {
        this.claveContable = claveContable;
    }

    public String getClaveContable() {
        return claveContable;
    }

    public void setRequiereCCI(Integer requiereCCI) {
        this.requiereCCI = requiereCCI;
    }

    public Integer getRequiereCCI() {
        return requiereCCI;
    }

    public void setResolucionAutomatica(Integer resolucionAutomatica) {
        this.resolucionAutomatica = resolucionAutomatica;
    }

    public Integer getResolucionAutomatica() {
        return resolucionAutomatica;
    }

    public void setPuntosAsignacion(Integer puntosAsignacion) {
        this.puntosAsignacion = puntosAsignacion;
    }

    public Integer getPuntosAsignacion() {
        return puntosAsignacion;
    }

    public void setListaTipoServicio(List<DyccTipoServicioDTO> listaTipoServicio) {
        this.listaTipoServicio = listaTipoServicio;
    }

    public List<DyccTipoServicioDTO> getListaTipoServicio() {
        return listaTipoServicio;
    }

    public void setListaConceptos(List<DyccConceptoDTO> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public List<DyccConceptoDTO> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaImpuestos(List<DyccImpuestoDTO> listaImpuestos) {
        this.listaImpuestos = listaImpuestos;
    }

    public List<DyccImpuestoDTO> getListaImpuestos() {
        return listaImpuestos;
    }

    public void setNumeroDeDias(Integer numeroDeDias) {
        this.numeroDeDias = numeroDeDias;
    }

    public Integer getNumeroDeDias() {
        return numeroDeDias;
    }

    public void setListaTipoPlazo(List<DyccTipoPlazoDTO> listaTipoPlazo) {
        this.listaTipoPlazo = listaTipoPlazo;
    }

    public List<DyccTipoPlazoDTO> getListaTipoPlazo() {
        return listaTipoPlazo;
    }

    public void setTipoPlazo(Integer tipoPlazo) {
        this.tipoPlazo = tipoPlazo;
    }

    public Integer getTipoPlazo() {
        return tipoPlazo;
    }

    public void setListaInfoARequerirDestino(List<DyccInfoARequerirDTO> listaInfoARequerirDestino) {
        this.listaInfoARequerirDestino = listaInfoARequerirDestino;
    }

    public List<DyccInfoARequerirDTO> getListaInfoARequerirDestino() {
        return listaInfoARequerirDestino;
    }

    public void setListaMatrizAnexosDestino(List<DyccMatrizAnexosDTO> listaMatrizAnexosDestino) {
        this.listaMatrizAnexosDestino = listaMatrizAnexosDestino;
    }

    public List<DyccMatrizAnexosDTO> getListaMatrizAnexosDestino() {
        return listaMatrizAnexosDestino;
    }

    public void setListaOrigenSaldo(List<DyccOrigenSaldoDTO> lisdtaOrigenSaldoDestino) {
        this.listaOrigenSaldo = lisdtaOrigenSaldoDestino;
    }

    public List<DyccOrigenSaldoDTO> getListaOrigenSaldo() {
        return listaOrigenSaldo;
    }

    public void setListaRolesDestino(List<DyccRolDTO> listaRolesDestino) {
        this.listaRolesDestino = listaRolesDestino;
    }

    public List<DyccRolDTO> getListaRolesDestino() {
        return listaRolesDestino;
    }

    public void setListaSubOrigenesSaldoDestino(List<DyccSubOrigSaldoDTO> listaSubOrigenesSaldoDestino) {
        this.listaSubOrigenesSaldoDestino = listaSubOrigenesSaldoDestino;
    }

    public List<DyccSubOrigSaldoDTO> getListaSubOrigenesSaldoDestino() {
        return listaSubOrigenesSaldoDestino;
    }

    public void setListaSubtramitesDestino(List<DyccSubTramiteDTO> listaSubtramitesDestino) {
        this.listaSubtramitesDestino = listaSubtramitesDestino;
    }

    public List<DyccSubTramiteDTO> getListaSubtramitesDestino() {
        return listaSubtramitesDestino;
    }

    public void setListaTipoPeriodoDestino(List<DyccTipoPeriodoDTO> listaTipoPeriodoDestino) {
        this.listaTipoPeriodoDestino = listaTipoPeriodoDestino;
    }

    public List<DyccTipoPeriodoDTO> getListaTipoPeriodoDestino() {
        return listaTipoPeriodoDestino;
    }

    public void setListaInfoARequerir(DualListModel<DyccInfoARequerirDTO> listaInfoARequerir) {
        this.listaInfoARequerir = listaInfoARequerir;
    }

    public DualListModel<DyccInfoARequerirDTO> getListaInfoARequerir() {
        return listaInfoARequerir;
    }

    public void setListaMatrizAnexos(DualListModel<DyccMatrizAnexosDTO> listaMatrizAnexos) {
        this.listaMatrizAnexos = listaMatrizAnexos;
    }

    public DualListModel<DyccMatrizAnexosDTO> getListaMatrizAnexos() {
        return listaMatrizAnexos;
    }

    public void setListaRoles(DualListModel<DyccRolDTO> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public DualListModel<DyccRolDTO> getListaRoles() {
        return listaRoles;
    }

    public void setListaSubOrigenesSaldo(DualListModel<DyccSubOrigSaldoDTO> listaSubOrigenesSaldo) {
        this.listaSubOrigenesSaldo = listaSubOrigenesSaldo;
    }

    public DualListModel<DyccSubOrigSaldoDTO> getListaSubOrigenesSaldo() {
        return listaSubOrigenesSaldo;
    }

    public void setListaSubtramites(DualListModel<DyccSubTramiteDTO> listaSubtramites) {
        this.listaSubtramites = listaSubtramites;
    }

    public DualListModel<DyccSubTramiteDTO> getListaSubtramites() {
        return listaSubtramites;
    }

    public void setListaTipoPeriodo(DualListModel<DyccTipoPeriodoDTO> listaTipoPeriodo) {
        this.listaTipoPeriodo = listaTipoPeriodo;
    }

    public DualListModel<DyccTipoPeriodoDTO> getListaTipoPeriodo() {
        return listaTipoPeriodo;
    }

    public void setDatos(DatosTipoTramiteVO datos) {
        this.datos = datos;
    }

    public DatosTipoTramiteVO getDatos() {
        return datos;
    }

    public void setListaUnidadAdmtvaTipo(DualListModel<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtvaTipo) {
        this.listaUnidadAdmtvaTipo = listaUnidadAdmtvaTipo;
    }

    public DualListModel<AdmcUnidAdmvaTipoDTO> getListaUnidadAdmtvaTipo() {
        return listaUnidadAdmtvaTipo;
    }

    public void setListaUnidadAdmtvaDestino(List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtvaDestino) {
        this.listaUnidadAdmtvaDestino = listaUnidadAdmtvaDestino;
    }

    public List<AdmcUnidAdmvaTipoDTO> getListaUnidadAdmtvaDestino() {
        return listaUnidadAdmtvaDestino;
    }

    public void setBanderaBoton(boolean banderaBoton) {
        this.banderaBoton = banderaBoton;
    }

    public boolean isBanderaBoton() {
        return banderaBoton;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public void setClaveComputo(String claveComputo) {
        this.claveComputo = claveComputo;
    }

    public String getClaveComputo() {
        return claveComputo;
    }
}
