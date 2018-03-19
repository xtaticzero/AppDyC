/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing.ManagerSesionAdmCasosCompMB;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.generico.util.Reporte;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.dycadministracion.impl.BusquedaTramitesBussinesDel;
import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.utility.FilaGridUnidadesAdmvas;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.utility.TramiteDataModel;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.servicio.common.impl.GeneradorReportes;
import mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing.CatalogosEstaticosMB;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesEstados;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "mbBusqTrams")
@SessionScoped
public class BusquedaTramitesMB {

    private static final Logger LOG = Logger.getLogger(BusquedaTramitesMB.class.getName());

    private Reporte reporteGenerico;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty("#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty(value = "#{dictaminarSolicitudMB}")
    private DictaminarSolicitudMB dictaminarSolicitudMB;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB managerSesionAdmCasosCompMB;

    @ManagedProperty(value = "#{delegateBusquedaTramites}")
    private BusquedaTramitesBussinesDel delegate;

    @ManagedProperty(value = "#{mbCatalogosEstaticos}")
    private CatalogosEstaticosMB mbCatalogosEstaticos;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private transient List<FilaGridTramitesBean> datasource;

    private String palabraClave;

    private List<ItemComboBean> dictaminadores;
    private List<ItemComboBean> tiposTramite;
    private List<ItemComboBean> estadosTramite;

    private String[] selectStatus;

    private AccesoUsr accesoUsr;

    private Date fechaActual;
    
    private static final String DESC_ARTICULO22 = "En revisión artículo 22 CFF";

    private Iterable tramites;
    private FilaGridTramitesBean tramiteSelec;
    private List<FilaGridTramitesBean> lstDictaminados;

    private List<FilaGridUnidadesAdmvas> unidadesAdmvas;
    private List<FilaGridUnidadesAdmvas> unidadesAdmvasSelec;
    private Integer idTipoServicioSelec;
    private Integer idDictaminadorSelec;
    private Integer idImpuestoSelec;
    private Integer idTipoTramite;
    private Integer idEstadoSelec;

    private Date fechaInicioPresTram;
    private Date fechaFinPresTram;

    private Date fechaIniResolucion;
    private Date fechaFinResolucion;
    private Date fechaIniPago;
    private Date fechaFinPago;

    private String lblTotalResultados;
    private Integer fieldsetActivo;

    private Integer tipoPalabraClave;
    private Boolean gridLazy;

    private String infoSessionUnidadAdmva;

    private Boolean mostrarDlgUnidadesAdmvas;

    private String adminsSeleccionadas;
    private String adminsSelecsDialog;

    private static final String MENSAJE_SIN_FILTRO = " - Ver todos - ";
    private static final float IMAGE_SCALE_PERCENT = 20.00f;
    private Boolean mostrarFiltroUnidadesAdmvas;
    private Boolean mostrarFiltroDictaminadores;
    private Boolean enabledBtnLiberar;
    private Boolean desactivaBotonReporte;

    private Integer totalRegs = 0;

    private String mensajeEstadosTramites;
    private boolean selectTipoTramiteComp;
    private boolean selectEdoPagada;

    private StreamedContent reportePDF;
    private StreamedContent reporteXLS;
    private boolean stopPolling;

    @PostConstruct
    public void init() {
        fechaActual = new Date();
        fechaIniResolucion = null;
        fechaFinResolucion = null;
        fechaIniPago = null;
        fechaFinPago = null;

        mensajeEstadosTramites = MENSAJE_SIN_FILTRO;

        reporteGenerico = new Reporte();

        accesoUsr = serviceObtenerSesion.execute();

        Utils.validarSesion(accesoUsr, Procesos.DYC00004);

        fieldsetActivo = 1;

        Map<String, Object> infoInicial = delegate.obtenerInfoInicial(accesoUsr);

        setUnidadesAdmvas((List<FilaGridUnidadesAdmvas>) infoInicial.get("unidadesAdmvas"));

        infoSessionUnidadAdmva = accesoUsr.getClaveSir() + " - " + infoInicial.get("nombreUnidadAdmva");

        mostrarDlgUnidadesAdmvas = Boolean.FALSE;

        adminsSeleccionadas = MENSAJE_SIN_FILTRO;

        enabledBtnLiberar = Boolean.TRUE;
        desactivaBotonReporte = !esRolAdmonDictaminacioAutomatica();
        //Aqui esta el pedo!
        mostrarFiltroUnidadesAdmvas = (Boolean) infoInicial.get("permitirFiltrarUnidadesAdmvas");
        mostrarFiltroDictaminadores = (Boolean) infoInicial.get("permitirFiltrarDictaminadores");

        dictaminadores = (List<ItemComboBean>) infoInicial.get("dictaminadores");
        lstDictaminados = new ArrayList<FilaGridTramitesBean>();
        selectTipoTramiteComp = true;
        selectEdoPagada = true;
    }

    private boolean esRolAdmonDictaminacioAutomatica() {
        String roles = accesoUsr.getRoles();
        LOG.info("Roles Usuario: " + roles);
        boolean esRolAdmonDictaAutomatica;
        esRolAdmonDictaAutomatica = (roles.contains(ConstantesDyC.ROL_ADMON_DICT_AUTOMATICAS_CVE) || roles.contains(ConstantesDyC.ROL_ADMON_DICT_AUTOMATICAS_DESC));
        LOG.info("Roles esRolAdmonDictaAutomatica: " + esRolAdmonDictaAutomatica);
        return esRolAdmonDictaAutomatica;
    }

    public void manejarChangeCmbTipoServicio() {

        idImpuestoSelec = 0;
        idTipoTramite = 0;
        selectStatus = new String[0];
        idDictaminadorSelec = 0;
        mensajeEstadosTramites = MENSAJE_SIN_FILTRO;

        LOG.debug("idTipoServicioSelec ->" + idTipoServicioSelec);

        estadosTramite = obtenerEstadosTramite(idTipoServicioSelec);

        tiposTramite = delegate.obtenerTiposTramite(idTipoServicioSelec, idImpuestoSelec);
        selectTipoTramiteComp = !Integer.valueOf(ConstantesDyCNumerico.VALOR_3).equals(idTipoServicioSelec);
        selectEdoPagada = !Integer.valueOf(ConstantesDyCNumerico.VALOR_3).equals(idTipoServicioSelec);
        LOG.info("Se selecciono opcion diferente a compensacion: " + selectTipoTramiteComp);
    }

    private List<ItemComboBean> obtenerEstadosTramite(Integer idTipoServicioSelec) {
        if (idTipoServicioSelec != null) {

            if (tipoServicioEsDevolucion(idTipoServicioSelec)) {

                return descartarEstatusSolicitud(getMbCatalogosEstaticos().getEstadosSolicitud());
            }

            if (tipoServicioEsCompensacion(idTipoServicioSelec)) {

                return descartarEstatusCompensacion(getMbCatalogosEstaticos().getEstadosCompensacion());
            }
        }

        return null;
    }

    private List<ItemComboBean> descartarEstatusSolicitud(List<ItemComboBean> lstEstatus) {

        List<ItemComboBean> lstEstatusConsulta = new ArrayList<ItemComboBean>();

        for (ItemComboBean item : lstEstatus) {
            ItemComboBean beanEstadoSol = new ItemComboBean();
            switch (item.getId()) {
                case ConstantesDyCNumerico.VALOR_1:
                case ConstantesDyCNumerico.VALOR_2:
                case ConstantesDyCNumerico.VALOR_14:
                    break;

                case ConstantesDyCNumerico.VALOR_6:
                    item.setDescripcion(DESC_ARTICULO22);
                    beanEstadoSol.setId(item.getId());
                    beanEstadoSol.setDescripcion(item.getDescripcion());
                    lstEstatusConsulta.add(beanEstadoSol);
                    break;

                default:
                    beanEstadoSol.setId(item.getId());
                    beanEstadoSol.setDescripcion(item.getDescripcion());
                    lstEstatusConsulta.add(beanEstadoSol);
                    break;

            }
        }

        ordenarAlfabeticamente(lstEstatusConsulta);

        return lstEstatusConsulta;
    }

    private List<ItemComboBean> descartarEstatusCompensacion(List<ItemComboBean> lstEstatus) {

        List<ItemComboBean> lstEstatusConsulta = new ArrayList<ItemComboBean>();

        for (ItemComboBean item : lstEstatus) {
            ItemComboBean beanEstadoSol = new ItemComboBean();
            switch (item.getId()) {
                case ConstantesDyCNumerico.VALOR_4:
                    item.setDescripcion(DESC_ARTICULO22);
                    beanEstadoSol.setId(item.getId());
                    beanEstadoSol.setDescripcion(item.getDescripcion());
                    lstEstatusConsulta.add(beanEstadoSol);
                    break;

                default:
                    beanEstadoSol.setId(item.getId());
                    beanEstadoSol.setDescripcion(item.getDescripcion());
                    lstEstatusConsulta.add(beanEstadoSol);
                    break;

            }
        }

        ordenarAlfabeticamente(lstEstatusConsulta);

        return lstEstatusConsulta;
    }

    private void ordenarAlfabeticamente(List<ItemComboBean> lstEstatus) {
        Collections.sort(lstEstatus, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ItemComboBean item1 = (ItemComboBean) o1;
                ItemComboBean item2 = (ItemComboBean) o2;
                return item1.getDescripcion().compareToIgnoreCase(item2.getDescripcion());
            }

        });
    }

    private boolean tipoServicioEsSolicitudDevolucion(Integer idTipoServicioSelec) {
        return tipoServicioIgual(idTipoServicioSelec, Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
    }

    private boolean tipoServicioEsDevolucionAutomatica(Integer idTipoServicioSelec) {
        return tipoServicioIgual(idTipoServicioSelec, Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
    }

    private boolean tipoServicioEsDevolucion(Integer idTipoServicioSelec) {
        return tipoServicioEsSolicitudDevolucion(idTipoServicioSelec) || tipoServicioEsDevolucionAutomatica(idTipoServicioSelec);
    }

    private boolean tipoServicioEsCasoCompensacion(Integer idTipoServicioSelec) {
        return tipoServicioIgual(idTipoServicioSelec, Constantes.TiposServicio.CASO_COMPENSACION);
    }

    private boolean tipoServicioEsAvisoCompensacion(Integer idTipoServicioSelec) {
        return tipoServicioIgual(idTipoServicioSelec, Constantes.TiposServicio.AVISO_COMPENSACION);
    }

    private boolean tipoServicioEsCompensacion(Integer idTipoServicioSelec) {
        return tipoServicioEsCasoCompensacion(idTipoServicioSelec) || tipoServicioEsAvisoCompensacion(idTipoServicioSelec);
    }

    private boolean tipoServicioIgual(Integer idTipoServicioSelec, DyccTipoServicioDTO tipoServicioDTO) {
        return idTipoServicioSelec == tipoServicioDTO.getIdTipoServicio().intValue();
    }

    public void manejarChangeCmbImpuesto() {
        LOG.debug("idImpuestoSelec ->" + idImpuestoSelec);
        tiposTramite = delegate.obtenerTiposTramite(idTipoServicioSelec, idImpuestoSelec);
    }

    public void clean() {
        palabraClave = "";
    }

    public void buscarXPalabraclave() {
        LOG.debug("INICIO buscarXPalabraclave");
        LOG.debug("Pal clave: ->" + palabraClave);

        palabraClave = palabraClave.toUpperCase().trim();

        tipoPalabraClave = delegate.validarPatron(palabraClave);

        if (tipoPalabraClave != BusquedaTramitesBussinesDel.TiposCadena.PATRON_NO_RECONOCIDO) {
            LOG.debug("el parametro es válido");

            Map<String, Object> paramsBusqueda = new HashMap<String, Object>();
            paramsBusqueda.put("palabraClave", palabraClave);
            paramsBusqueda.put("tipoCadena", tipoPalabraClave);
            paramsBusqueda.put("accesoUsr", accesoUsr);
            Map<String, Object> resultBusqueda = delegate.buscarXPalabraClave(paramsBusqueda);

            tramites = new TramiteDataModel((List<FilaGridTramitesBean>) resultBusqueda.get("tramites"));

            totalRegs = (Integer) resultBusqueda.get("totalRegs");
            if (totalRegs == 1) {
                this.setLblTotalResultados("Se encontró " + totalRegs + " trámite");
            } else {
                this.setLblTotalResultados("Se encontraron " + totalRegs + " trámites");
            }
            String mensaje = (String) resultBusqueda.get("mensaje");
            if (mensaje != null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje));
            }
            gridLazy = Boolean.FALSE;
        } else {
            FacesMessage msjParamNoValido
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El campo búsqueda tiene valores no válidos. Favor de verificar");

            FacesContext.getCurrentInstance().addMessage(null, msjParamNoValido);
        }
        lstDictaminados.clear();
        validaEnableBtnLiberar();
    }

    public void buscarXFiltros() throws SIATException {
        try {
            LOG.debug("INICIO buscarXFiltros");
            tramites = new LazyTramiteDataModel(this, accesoUsr);
            gridLazy = Boolean.TRUE;
            lstDictaminados.clear();
            validaEnableBtnLiberar();
        } catch (Exception e) {
            LOG.error("error en  buscarXFiltros", e);
            throw new SIATException(e);
        }
    }

    /**
     * Metodo que se encarga de enviar el caso de compensacion o solicitud a
     * donde correspond
     *
     * @return e
     */
    public String verDetalle() {
        LOG.debug("tramiteSelec ->" + tramiteSelec);
        if (tramiteSelec != null) {
            if (tramiteSelec.getTramite().equals(Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getDescripcion())
                    || tramiteSelec.getTramite().equals(Constantes.TiposServicio.DEVOLUCION_AUTOMATICA.getDescripcion())) {
                return llamaDictaminarSolicitud();
            } else if (tramiteSelec.getTramite().equals(Constantes.TiposServicio.CASO_COMPENSACION.getDescripcion())
                    || tramiteSelec.getTramite().equals(Constantes.TiposServicio.AVISO_COMPENSACION.getDescripcion())) {
                return llamaCasosCompensacion();
            }
        }
        return null;
    }

    /**
     * Metodo enlaza el caso de uso Consultar Devoluciones y Compensaciones
     * Administracion con Administrar Solicitudes de Devolucion.
     *
     * @return String
     */
    public String llamaDictaminarSolicitud() {
        DycpSolicitudDTO solicitud = delegate.obtenerObjetoDevolucion(tramiteSelec.getNumControl());

        List<SeguimientoAdministrarSolVO> listaDocsSeguimientos
                = administrarSolicitudesService.selecXServicio(solicitud);

        LOG.debug("solicitud ->" + solicitud);
        LOG.debug("solicitud.getDycpServicioDTO().getDyctContribuyenteDTO() ->"
                + solicitud.getDycpServicioDTO().getDyctContribuyenteDTO());
        String rolDictaminado
                = solicitud.getDycpServicioDTO().getDyctContribuyenteDTO().getRolDictaminado() == 1 ? "Si" : "No";
        SolicitudAdministrarSolVO objDictaminarSol = new SolicitudAdministrarSolVO();
        objDictaminarSol.setNumControl(tramiteSelec.getNumControl());
        objDictaminarSol.setDycpServicioDTO(solicitud.getDycpServicioDTO());
        objDictaminarSol.setFechaPresentacion(tramiteSelec.getFechaPresentacion());
        objDictaminarSol.setImporteSolicitado(solicitud.getImporteSolicitado());
        objDictaminarSol.setDyccEstadoSolDTO(solicitud.getDyccEstadoSolDTO());
        objDictaminarSol.setRolDictaminado(rolDictaminado);
        objDictaminarSol.setFechaAprobacion(solicitud.getDyctResolucionDTO() != null
                ? solicitud.getDyctResolucionDTO().getFechaAprobacion() : null);

        objDictaminarSol.setTipoTramite(tramiteSelec.getTipoTramite());
        objDictaminarSol.setEstadoSolicitud(tramiteSelec.getEstado());
        objDictaminarSol.setPeriodo(solicitud.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getDescripcion());
        objDictaminarSol.setDyctSaldoIcepDTO(solicitud.getDyctSaldoIcepDTO());
        objDictaminarSol.setDias(tramiteSelec.getDias());
        objDictaminarSol.setTipoDia(tramiteSelec.getTipoDia());

        dictaminarSolicitudMB.setSolicitudAdministrarSolVO(objDictaminarSol);
        dictaminarSolicitudMB.setListaDocsSeguimientos(listaDocsSeguimientos);

        dictaminarSolicitudMB.setNumEmpleadoStr(this.accesoUsr.getNumeroEmp());
        /**
         * dictaminarSolicitudMB.setUnidad(this.accesoUsr.getLocalidad());
         */
        dictaminarSolicitudMB.setNombreCompleto(this.accesoUsr.getNombreCompleto());

        dictaminarSolicitudMB.setRol(ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR);
        dictaminarSolicitudMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_2);
        dictaminarSolicitudMB.validarRol();
        dictaminarSolicitudMB.opcionesCombo();
        dictaminarSolicitudMB.init();

        return "llamaDictaminarSolicitud";
    }

    public String llamaCasosCompensacion() {
        managerSesionAdmCasosCompMB.setNumControl(tramiteSelec.getNumControl());
        managerSesionAdmCasosCompMB.setRfcContribuyente(tramiteSelec.getRfc());
        managerSesionAdmCasosCompMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_7);

        return "llamaCasosCompensacion";
    }

    public void liberarTramites() {
        FacesContext fc = FacesContext.getCurrentInstance();

        for (FilaGridTramitesBean tramite : lstDictaminados) {
            try {
                if (esTramiteSivadOMorsa(tramite)) {
                    dycpSolicitudService.actualizarStatus(ConstantesDyCNumerico.VALOR_3, tramite.getNumControl());
                }
            } catch (SIATException e) {
                LOG.error(String.format("liberacion(); causa: %s", e.getCause()));
                this.desplegarError(fc,
                        String.format("Ocurrio un error al liberar el tramite con numControl: %s", tramite.getNumControl()));
            }
        }
        LOG.info("Actulizar tabla:*********************** ");
        buscarXPalabraclave();
        enabledBtnLiberar = Boolean.TRUE;
        this.desplegarMensaje(fc, "Se han liberado los tramites seleccionados.");
        LOG.info("Deberia actualizar tabla: ");
    }

    public void desplegarMensaje(FacesContext fc, String msg) {
        fc.addMessage("msgInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
        fc.renderResponse();
    }

    public void desplegarError(FacesContext fc, String msg) {
        fc.addMessage("msgError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg));
        fc.renderResponse();
    }

    public void onRowUnselect(UnselectEvent event) {
        FilaGridTramitesBean object = (FilaGridTramitesBean) event.getObject();
        LOG.info("Fila: " + object.getNumControl());
        validaEnableBtnLiberar();
    }

    public void onRowSelect(SelectEvent event) {
        FilaGridTramitesBean object = (FilaGridTramitesBean) event.getObject();
        LOG.info("Fila: " + object.getNumControl());
        validaEnableBtnLiberar();
    }

    public void validaEnableBtnLiberar() {

        if (lstDictaminados != null && !lstDictaminados.isEmpty() && existeSivadOMorsa()) {
            enabledBtnLiberar = Boolean.FALSE;
        } else {
            enabledBtnLiberar = Boolean.TRUE;
        }
    }

    public void validaBotonValidar(ToggleSelectEvent event) {
        LOG.info("Toggle: " + event.isSelected());
        validaEnableBtnLiberar();
    }

    private boolean esTramiteSivadOMorsa(FilaGridTramitesBean object) {
        String roles = accesoUsr.getRoles();
        boolean esRolAdmonDictaAutomatica = false;
        if (roles != null && (object.getEstado().equalsIgnoreCase(Constantes.EstadosSolicitud.EN_PROCESO_MORSA.getDescripcion())
                || object.getEstado().equalsIgnoreCase(Constantes.EstadosSolicitud.EN_PROCESO_SIVAD.getDescripcion()))) {
            esRolAdmonDictaAutomatica = roles.contains(ConstantesDyC.ROL_ADMON_DICT_AUTOMATICAS_CVE) || roles.contains(ConstantesDyC.ROL_ADMON_DICT_AUTOMATICAS_DESC);

        }
        return esRolAdmonDictaAutomatica;
    }

    private boolean existeSivadOMorsa() {
        boolean existe = false;
        String roles = accesoUsr.getRoles();
        LOG.info("Tam listaDictaminados: +++++++++++++++++++++++++++++++++++++++++++++++++++: " + lstDictaminados.size());
        for (FilaGridTramitesBean object : lstDictaminados) {
            LOG.info("roles: " + roles + "\n object:" + object + " \n Estado: " + object.getEstado());
            if (roles != null && (object.getEstado().equalsIgnoreCase(Constantes.EstadosSolicitud.EN_PROCESO_MORSA.getDescripcion())
                    || object.getEstado().equalsIgnoreCase(Constantes.EstadosSolicitud.EN_PROCESO_SIVAD.getDescripcion()))) {
                existe = roles.contains(ConstantesDyC.ROL_ADMON_DICT_AUTOMATICAS_CVE) || roles.contains(ConstantesDyC.ROL_ADMON_DICT_AUTOMATICAS_DESC);
                if (existe) {
                    LOG.info("Existe tramite SM en la lista y BTN deberi estar Activo##########################");
                    break;
                }
            }
        }
        return existe;
    }

    public void reset() {
        LOG.debug("INICIO reset");

        palabraClave = "";

        tiposTramite = null;
        estadosTramite = null;
        tramites = null;
        tramiteSelec = null;
        unidadesAdmvasSelec = null;
        idTipoServicioSelec = null;
        idDictaminadorSelec = null;
        idImpuestoSelec = null;
        idTipoTramite = null;
        idEstadoSelec = null;
        fechaInicioPresTram = null;
        fechaFinPresTram = null;
        fechaIniPago = null;
        fechaFinPago = null;
        fechaIniResolucion = null;
        fechaFinResolucion = null;
        lblTotalResultados = null;
        tipoPalabraClave = null;

        adminsSeleccionadas = MENSAJE_SIN_FILTRO;
        mensajeEstadosTramites = MENSAJE_SIN_FILTRO;

        lstDictaminados.clear();
        selectStatus = null;

        validaEnableBtnLiberar();

        totalRegs = 0;
        stopPolling = true;
        LOG.debug("FIN reset");
    }

    public void setFechas() {
        LOG.debug("INICIO setFechas");
    }

    public void mostrarDialogSelecAdmins() {
        LOG.debug("INICIO mostrarDialogSelecAdmins");
        mostrarDlgUnidadesAdmvas = Boolean.TRUE;
    }

    public void cancelarSelecAdmins() {
        LOG.debug("INICIO cancelarSelecAdmins");
        mostrarDlgUnidadesAdmvas = Boolean.FALSE;
    }

    public void manejarRowSelectAdmins() {
        LOG.debug("INICIO manejarRowSelectAdmins");
        if (unidadesAdmvasSelec != null) {
            StringBuilder sbAdminsSelec = new StringBuilder();
            for (int i = 0; i < unidadesAdmvasSelec.size(); i++) {
                sbAdminsSelec.append(unidadesAdmvasSelec.get(i).getClaveSir());
                if (i < (unidadesAdmvasSelec.size() - 1)) {
                    sbAdminsSelec.append(", ");
                }
            }

            adminsSelecsDialog = sbAdminsSelec.toString();
        }
    }

    private List<FilaGridTramitesBean> preparaImpresion() {

        Map<String, Object> paramsBusqueda = new HashMap<String, Object>();

        if (!palabraClave.equals("")) {

            palabraClave = palabraClave.toUpperCase().trim();
            tipoPalabraClave = delegate.validarPatron(palabraClave);

            if (esTipoPalabraClavePatronReconocido()) {

                paramsBusqueda.put("tipoCadena", tipoPalabraClave);
                paramsBusqueda.put("palabraClave", palabraClave);
                paramsBusqueda.put("accesoUsr", accesoUsr);

                Map<String, Object> resultBusqueda = delegate.buscarXPalabraClave(paramsBusqueda);
                return getTramitesEncontradosResultado(resultBusqueda);
            }

        } else {
            paramsBusqueda.putAll(getParametrosBusquedaXFiltros());
            paramsBusqueda.put("campoOrdenamiento", LazyTramiteDataModel.CAMPO_ORDENAMIENTO_TIPOTRAMITE_DEFAULT);
            paramsBusqueda.put("tipoOrdenamiento", LazyTramiteDataModel.TIPO_ORDENAMIENTO_ASCENDENTE_CONSULTA);

            Map<String, Object> resultBusqueda = null;

            try {
                resultBusqueda = getDelegate().buscarXFiltrosExportacion(paramsBusqueda, accesoUsr);
            } catch (Exception e) {
                this.desplegarMensaje(FacesContext.getCurrentInstance(), "No se puede consultar los tramites intente mas tarde");
                LOG.error("No se puede crear la consulta debido al centro de costo , " + e.getMessage());
            }

            List<FilaGridTramitesBean> filas = new ArrayList<FilaGridTramitesBean>();
            if (resultBusqueda != null) {
                filas = getTramitesEncontradosResultado(resultBusqueda);
            }
            return filas;
        }
        return null;
    }

    private List<FilaGridTramitesBean> getTramitesEncontradosResultado(Map<String, Object> resultBusqueda) {

        return (List<FilaGridTramitesBean>) resultBusqueda.get(BusquedaTramitesBussinesDel.KEY_TRAMS_ECONTRADOS);
    }

    private boolean esTipoPalabraClavePatronReconocido() {
        return tipoPalabraClave != BusquedaTramitesBussinesDel.TiposCadena.PATRON_NO_RECONOCIDO;
    }

    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.LETTER.rotate());
        pdf.open();
        try {
            Image im = Image.getInstance("/siat/imagenes/logoSAT.jpg");
            im.scalePercent(IMAGE_SCALE_PERCENT);
            pdf.add(im);
        } catch (Exception e) {
            LOG.error("No se encontro el logo: " + e.getMessage());
        }
    }

    public void manejarAceptarSelectAdmins() {
        LOG.debug("INICIO manejarAceptarSelectAdmins");
        LOG.debug("adminsSelecsDialog ->" + adminsSelecsDialog + "<-");
        if (adminsSelecsDialog != null && !"".equals(adminsSelecsDialog)) {
            adminsSeleccionadas = adminsSelecsDialog;
        } else {
            adminsSeleccionadas = MENSAJE_SIN_FILTRO;
        }
        mostrarDlgUnidadesAdmvas = Boolean.FALSE;
        dictaminadores = delegate.obtenerDictaminadores(adminsSelecsDialog);
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setDictaminarSolicitudMB(DictaminarSolicitudMB dictaminarSolicitudMB) {
        this.dictaminarSolicitudMB = dictaminarSolicitudMB;
    }

    public DictaminarSolicitudMB getDictaminarSolicitudMB() {
        return dictaminarSolicitudMB;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public void setReporteGenerico(Reporte reporteGenerico) {
        this.reporteGenerico = reporteGenerico;
    }

    public Reporte getReporteGenerico() {
        return reporteGenerico;
    }

    public void setFechaActual(Date fechaActual) {
        if (null != fechaActual) {
            this.fechaActual = (Date) fechaActual.clone();
        } else {
            this.fechaActual = null;
        }
    }

    public Date getFechaActual() {
        if (null != fechaActual) {
            return (Date) fechaActual.clone();
        } else {
            return null;
        }
    }

    public void setManagerSesionAdmCasosCompMB(ManagerSesionAdmCasosCompMB managerSesionAdmCasosCompMB) {
        this.managerSesionAdmCasosCompMB = managerSesionAdmCasosCompMB;
    }

    public ManagerSesionAdmCasosCompMB getManagerSesionAdmCasosCompMB() {
        return managerSesionAdmCasosCompMB;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public BusquedaTramitesBussinesDel getDelegate() {
        return delegate;
    }

    public void setDelegate(BusquedaTramitesBussinesDel delegate) {
        this.delegate = delegate;
    }

    public Integer getIdTipoServicioSelec() {
        return idTipoServicioSelec;
    }

    public void setIdTipoServicioSelec(Integer idTipoServicioSelec) {
        this.idTipoServicioSelec = idTipoServicioSelec;
    }

    public Integer getIdImpuestoSelec() {
        return idImpuestoSelec;
    }

    public void setIdImpuestoSelec(Integer idImpuestoSelec) {
        this.idImpuestoSelec = idImpuestoSelec;
    }

    public List<ItemComboBean> getDictaminadores() {
        return dictaminadores;
    }

    public void setDictaminadores(List<ItemComboBean> dictaminadores) {
        this.dictaminadores = dictaminadores;
    }

    public Integer getIdDictaminadorSelec() {
        return idDictaminadorSelec;
    }

    public void setIdDictaminadorSelec(Integer idDictaminadorSelec) {
        this.idDictaminadorSelec = idDictaminadorSelec;
    }

    public Integer getIdEstadoSelec() {
        return idEstadoSelec;
    }

    public void setIdEstadoSelec(Integer idEstadoSelec) {
        this.idEstadoSelec = idEstadoSelec;
    }

    public List<ItemComboBean> getEstadosTramite() {
        return estadosTramite;
    }

    public void setEstadosTramite(List<ItemComboBean> estadosTramite) {
        this.estadosTramite = estadosTramite;
    }

    public CatalogosEstaticosMB getMbCatalogosEstaticos() {
        return mbCatalogosEstaticos;
    }

    public void setMbCatalogosEstaticos(CatalogosEstaticosMB mbCatalogosEstaticos) {
        this.mbCatalogosEstaticos = mbCatalogosEstaticos;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public List<ItemComboBean> getTiposTramite() {
        return tiposTramite;
    }

    public void setTiposTramite(List<ItemComboBean> tiposTramite) {
        this.tiposTramite = tiposTramite;
    }

    public Date getFechaInicioPresTram() {
        if (null != fechaInicioPresTram) {
            return (Date) fechaInicioPresTram.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicioPresTram(Date fechaInicioPresTram) {
        if (null != fechaInicioPresTram) {
            this.fechaInicioPresTram = (Date) fechaInicioPresTram.clone();
        } else {
            this.fechaInicioPresTram = null;
        }
    }

    public Date getFechaFinPresTram() {
        if (null != fechaFinPresTram) {
            return (Date) fechaFinPresTram.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinPresTram(Date fechaFinPresTram) {
        if (null != fechaFinPresTram) {
            this.fechaFinPresTram = (Date) fechaFinPresTram.clone();
        } else {
            this.fechaFinPresTram = null;
        }
    }

    public String getLblTotalResultados() {
        return lblTotalResultados;
    }

    public void setLblTotalResultados(String lblTotalResultados) {
        this.lblTotalResultados = lblTotalResultados;
    }

    public FilaGridTramitesBean getTramiteSelec() {
        return tramiteSelec;
    }

    public void setTramiteSelec(FilaGridTramitesBean tramiteSelec) {
        this.tramiteSelec = tramiteSelec;
    }

    public List<FilaGridTramitesBean> getLstDictaminados() {
        return lstDictaminados;
    }

    public void setLstDictaminados(List<FilaGridTramitesBean> lstDictaminados) {
        this.lstDictaminados = lstDictaminados;
    }

    public Iterable getTramites() {
        return tramites;
    }

    public void setTramites(LazyTramiteDataModel tramitesL) {
        this.tramites = tramitesL;
    }

    public Integer getFieldsetActivo() {
        return fieldsetActivo;
    }

    public void setFieldsetActivo(Integer fieldsetActivo) {
        this.fieldsetActivo = fieldsetActivo;
    }

    public Integer getTipoPalabraClave() {
        return tipoPalabraClave;
    }

    public void setTipoPalabraClave(Integer tipoPalabraClave) {
        this.tipoPalabraClave = tipoPalabraClave;
    }

    public String getInfoSessionUnidadAdmva() {
        return infoSessionUnidadAdmva;
    }

    public void setInfoSessionUnidadAdmva(String infoSessionUnidadAdmva) {
        this.infoSessionUnidadAdmva = infoSessionUnidadAdmva;
    }

    public Boolean getMostrarDlgUnidadesAdmvas() {
        return mostrarDlgUnidadesAdmvas;
    }

    public void setMostrarDlgUnidadesAdmvas(Boolean mostrarDlgUnidadesAdmvas) {
        this.mostrarDlgUnidadesAdmvas = mostrarDlgUnidadesAdmvas;
    }

    public List<FilaGridUnidadesAdmvas> getUnidadesAdmvasSelec() {
        return unidadesAdmvasSelec;
    }

    public void setUnidadesAdmvasSelec(List<FilaGridUnidadesAdmvas> unidadesAdmvasSelec) {
        this.unidadesAdmvasSelec = unidadesAdmvasSelec;
    }

    public List<FilaGridUnidadesAdmvas> getUnidadesAdmvas() {
        return unidadesAdmvas;
    }

    public void setUnidadesAdmvas(List<FilaGridUnidadesAdmvas> unidadesAdmvas) {
        this.unidadesAdmvas = unidadesAdmvas;
    }

    public String getAdminsSeleccionadas() {
        return adminsSeleccionadas;
    }

    public void setAdminsSeleccionadas(String adminsSeleccionadas) {
        this.adminsSeleccionadas = adminsSeleccionadas;
    }

    public String getAdminsSelecsDialog() {
        return adminsSelecsDialog;
    }

    public void setAdminsSelecsDialog(String adminsSelecsDialog) {
        this.adminsSelecsDialog = adminsSelecsDialog;
    }

    public Boolean getMostrarFiltroUnidadesAdmvas() {
        return mostrarFiltroUnidadesAdmvas;
    }

    public void setMostrarFiltroUnidadesAdmvas(Boolean mostrarFiltroUnidadesAdmvas) {
        this.mostrarFiltroUnidadesAdmvas = mostrarFiltroUnidadesAdmvas;
    }

    public Boolean getEnabledBtnLiberar() {
        return enabledBtnLiberar;
    }

    public void setEnabledBtnLiberar(Boolean enabledBtnLiberar) {
        this.enabledBtnLiberar = enabledBtnLiberar;
    }

    public Boolean getMostrarFiltroDictaminadores() {
        return mostrarFiltroDictaminadores;
    }

    public void setMostrarFiltroDictaminadores(Boolean mostrarFiltroDictaminadores) {
        this.mostrarFiltroDictaminadores = mostrarFiltroDictaminadores;
    }

    public Integer getTotalRegs() {
        return totalRegs;
    }

    public void setTotalRegs(Integer totalRegs) {
        this.totalRegs = totalRegs;
    }

    public Boolean getGridLazy() {
        return gridLazy;
    }

    public void setGridLazy(Boolean gridLazy) {
        this.gridLazy = gridLazy;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public String[] getSelectStatus() {
        if (selectStatus != null) {
            return Arrays.copyOf(selectStatus, selectStatus.length);
        }
        return null;
    }

    public String getMensajeEstadosTramites() {
        return mensajeEstadosTramites;
    }

    public void cambioEstadosTramites() {
        if (selectStatus != null && selectStatus.length != 0) {
            List<String> listaEstadosMensaje = new ArrayList<String>();

            selectEdoPagada = false;
            for (String elementoSeleccionado : selectStatus) {

                for (ItemComboBean elementoEstados : estadosTramite) {
                    if (elementoSeleccionado.equals(elementoEstados.getId().toString())) {
                        listaEstadosMensaje.add(elementoEstados.getDescripcion());
                        selectEdoPagada = selectEdoPagada ? selectEdoPagada : elementoSeleccionado.equals(ConstantesEstados.ESTADO_SOL_PAGADA.toString());
                        break;
                    }
                }
            }

            String listaEstados = listaEstadosMensaje.toString();
            mensajeEstadosTramites = listaEstados.substring(1, listaEstados.length() - 1);

        } else {
            mensajeEstadosTramites = MENSAJE_SIN_FILTRO;
        }
    }

    public void setSelectStatus(String[] selectStatus) {
        if (selectStatus != null) {
            this.selectStatus = Arrays.copyOf(selectStatus, selectStatus.length);
        }
    }

    public Date getFechaIniResolucion() {
        if (fechaIniResolucion != null) {
            return (Date) fechaIniResolucion.clone();
        }
        return null;
    }

    public void setFechaIniResolucion(Date fechaIniResolucion) {
        if (fechaIniResolucion != null) {
            this.fechaIniResolucion = (Date) fechaIniResolucion.clone();
        } else {
            this.fechaIniResolucion = null;
        }
    }

    public Date getFechaFinResolucion() {
        if (fechaFinResolucion != null) {
            return (Date) fechaFinResolucion.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinResolucion(Date fechaFinResolucion) {
        if (fechaFinResolucion != null) {
            this.fechaFinResolucion = (Date) fechaFinResolucion.clone();
        } else {
            this.fechaFinResolucion = null;
        }
    }

    public Date getFechaIniPago() {
        if (fechaIniPago != null) {
            return (Date) fechaIniPago.clone();
        } else {
            return null;
        }

    }

    public void setFechaIniPago(Date fechaIniPago) {
        if (fechaIniPago != null) {
            this.fechaIniPago = (Date) fechaIniPago.clone();
        } else {
            this.fechaIniPago = null;
        }
    }

    public Date getFechaFinPago() {
        if (fechaFinPago != null) {
            return (Date) fechaFinPago.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinPago(Date fechaFinPago) {
        if (fechaFinPago != null) {
            this.fechaFinPago = (Date) fechaFinPago.clone();
        } else {
            this.fechaFinPago = null;
        }
    }

    /**
     * @return the desactivaBotonReporte
     */
    public Boolean getDesactivaBotonReporte() {
        return desactivaBotonReporte;
    }

    /**
     * @param desactivaBotonReporte the desactivaBotonReporte to set
     */
    public void setDesactivaBotonReporte(Boolean desactivaBotonReporte) {
        this.desactivaBotonReporte = desactivaBotonReporte;
    }

    public List<FilaGridTramitesBean> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<FilaGridTramitesBean> datasource) {
        this.datasource = datasource;
    }

    public Map<String, Object> getParametrosBusquedaXFiltros() {
        Map<String, Object> paramsBusqueda = new HashMap<String, Object>();

        String idsAdminsSelecs = getIdsAdminsSeleccion();

        paramsBusqueda.put("idsUnidadesAdmvas", idsAdminsSelecs);
        paramsBusqueda.put("idTipoServicio", getIdTipoServicioSelec());
        paramsBusqueda.put("numEmpleadoDict", getIdDictaminadorSelec() == null ? null : getIdDictaminadorSelec());

        if (getIdDictaminadorSelec() != null && getIdDictaminadorSelec() == 0) {
            paramsBusqueda.put("numEmpleadoDict", null);
        }

        paramsBusqueda.put("idTipoTramite", getIdTipoTramite());
        paramsBusqueda.put("idEstadoSelec", getSelectStatus());
        paramsBusqueda.put("idImpuesto", getIdImpuestoSelec());
        paramsBusqueda.put("fechaInicioPresTram", getFechaInicioPresTram());
        paramsBusqueda.put("fechaFinPresTra", getFechaFinPresTram());
        paramsBusqueda.put("accesoUsr", getAccesoUsr());

        paramsBusqueda.put("fechaIniResolucion", getFechaIniResolucion());
        paramsBusqueda.put("fechaFinResolucion", getFechaFinResolucion());
        paramsBusqueda.put("fechaIniPago", getFechaIniPago());
        paramsBusqueda.put("fechaFinPago", getFechaFinPago());

        return paramsBusqueda;
    }

    private String getIdsAdminsSeleccion() {

        if (BusquedaTramitesMB.MENSAJE_SIN_FILTRO.equals(adminsSeleccionadas)) {
            return "";
        }

        return adminsSeleccionadas;
    }

    public boolean isSelectTipoTramiteComp() {
        return selectTipoTramiteComp;
    }

    public void setSelectTipoTramiteComp(boolean selectTipoTramiteComp) {
        this.selectTipoTramiteComp = selectTipoTramiteComp;
    }

    public boolean isSelectEdoPagada() {
        return selectEdoPagada;
    }

    public void setSelectEdoPagada(boolean selectEdoPagada) {
        this.selectEdoPagada = selectEdoPagada;
    }

    public StreamedContent getReportePDF() {
        return reportePDF;
    }

    public void setReportePDF(StreamedContent reportePDF) {
        this.reportePDF = reportePDF;
    }

    public StreamedContent getReporteXLS() {
        return reporteXLS;
    }

    public void setReporteXLS(StreamedContent reporteXLS) {
        this.reporteXLS = reporteXLS;
    }

    public void monitorPoll() {
        LOG.info("stopPolling:: " + stopPolling);
    }

    public void crearReporteExcel() {
        crearReporteThread(false);
    }

    public void crearReportePDF() {
        crearReporteThread(true);
    }

    public void crearReporteThread(final boolean isPdf) {
        stopPolling = false;
        Thread threadCrearReporte = new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("inicia consulta de datos... ");
                List<FilaGridTramitesBean> listaImpresion = preparaImpresion();
                LOG.info("termina consulta de datos y comienza la construccion del reporte " + (isPdf ? "PDF" : "EXCEL") + " ... ");
                crearReporteExpot(isPdf, listaImpresion);
                LOG.info("termina la construccion del reporte");
                stopPolling = true;
            }
        }, "reporte" + (isPdf ? "PDF" : "EXCEL"));
        threadCrearReporte.start();
    }

    private void crearReporteExpot(boolean isPdf, List<FilaGridTramitesBean> listaImpresion) {
        byte reporteByte[] = null;
        try {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("isPDF", isPdf);
            InputStream fileIS = new FileInputStream("/siat/dyc/reportes/" + (isPdf ? "ConsultaGeneralPDF.jasper" : "ConsultaGeneralExcel.jasper"));
            reporteByte = GeneradorReportes.crearReporte(fileIS, "Reporte." + ((isPdf ? "pdf" : "xls")), hm, listaImpresion);
        } catch (SIATException e) {
            LOG.error("ReporteXLS_SIATException:", e);
        } catch (IOException e) {
            LOG.error("ReporteXLS_IOException:", e);
        }
        if (isPdf) {
            reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(reporteByte), "application/pdf", "Tramites.pdf");
        } else {
            reporteXLS = new DefaultStreamedContent(new ByteArrayInputStream(reporteByte), "application/vnd.ms-excel.12", "Tramites.xls");
        }
    }

    public boolean getStopPolling() {
        return stopPolling;
    }

    public void setStopPolling(boolean stopPolling) {
        this.stopPolling = stopPolling;
    }
}
