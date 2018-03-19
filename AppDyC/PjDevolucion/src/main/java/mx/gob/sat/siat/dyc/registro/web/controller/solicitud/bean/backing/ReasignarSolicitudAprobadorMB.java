package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.datamodel.SolDictaminDataModel;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.service.DyccAprobadorService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;


@ManagedBean(name = "reasignarSolicitudAprobadorMB")
@ViewScoped
public class ReasignarSolicitudAprobadorMB {

    private final Logger log = Logger.getLogger(ReasignarSolicitudAprobadorMB.class.getName());

    private static final String MSG = "msg";
    private static final String MOSTRAR_DIALOGO_LISTA_APROBADORES = "lssAprob.show();";
    private static final String MOSTRAR_CONFIRMACION_REASIGNACION_APROBADOR = "cfmCDWv.show();";
    private static final String MOSTRAR_CONFIRMACION_CONTINUACION_REASIGNACION_APROBADOR = "cfmNoWv.show();";
    private static final String REASIGNACION_APROBADOR_EXITOSA = "cfmCDWv.hide(); lssAprob.hide(); wgdAprob.show();";
    private static final String OCULTAR_CONFIRMACION_REASIGNACION_APROBADOR = "cfmCDWv.hide();";

    @ManagedProperty(value = "#{dyccAprobadorService}")
    private DyccAprobadorService dyccAprobadorService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    private List<DyccAprobadorDTO> listaAprobadores;
    private List<DyccAprobadorDTO> listaAprobadoresDialog;
    private DyccAprobadorDTO seleccionAprob;

    private final Mensaje mensaje = new Mensaje();
    private AccesoUsr accesoUsr;

    private SolDictaminDataModel solicitudesAprobador;
    private List<DictaminadorSolBean> listaSeleccionSolicitudes;

    private boolean varBotonSearch = Boolean.TRUE;

    private DyccAprobadorDTO aprobadorDestinoReasignacion;
    private List<DyccAprobadorDTO> listaAprobadoresReasignar = new ArrayList<DyccAprobadorDTO>();

    private String aprobadorNombreAdministracion = "";

    private boolean botonConfirmacionAprobacionBloqueado = Boolean.TRUE;
    private boolean botonMostrarListaAprobadoresBloqueado = Boolean.TRUE;

    /**
     * Filtro de fechas para la búsqueda
     */
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        listaAprobadores = new ArrayList<DyccAprobadorDTO>();
        listaAprobadoresDialog = new ArrayList<DyccAprobadorDTO>();
        seleccionAprob = new DyccAprobadorDTO();
        aprobadorDestinoReasignacion = new DyccAprobadorDTO();
        accesoUsr = serviceObtenerSesion.execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00025);

        mostrarInfoAprobadores();
        inicializaSolicitudesAprobador();

        varBotonSearch = Boolean.TRUE;
        botonConfirmacionAprobacionBloqueado = Boolean.TRUE;
        botonMostrarListaAprobadoresBloqueado = Boolean.TRUE;
    }

    private void inicializaSolicitudesAprobador() {
        solicitudesAprobador = new SolDictaminDataModel(new ArrayList<DictaminadorSolBean>());
    }

    public void mostrarInfoAprobadores() {
        log.info(" #################--#######---############# ");
        log.info(" Valores iniciales busqueda [ClaveSir:" + accesoUsr.getClaveSir() + "][CentroCostoOp:" +
                 accesoUsr.getCentroCostoOp() + "] ");
        log.info(" #################--#######---############# ");
        listaAprobadores =
                dyccAprobadorService.mostrarAprobadorConTrabajo(Integer.parseInt(accesoUsr.getClaveSir()), Integer.parseInt(accesoUsr.getCentroCostoOp()),
                                                                ConstantesDyCNumerico.VALOR_0);
    }

    public void mostrarTramitesAprobador() {
        RequestContext request = RequestContext.getCurrentInstance();
        if (seleccionAprob != null) {
            limpiarSeleccionTramites();
            cargarListaTramitesAprobador(seleccionAprob);
        } else {
            mostrarMensajeSeleccionAprobador(request);
        }
    }

    private void cargarListaTramitesAprobador(DyccAprobadorDTO seleccionAprob) {
        log.info("Cargando la lista de los tramites del aprobador");
        try {
            List<DictaminadorSolBean> listaTramites;
            if (fechaInicio != null && fechaFin != null) {
                listaTramites = dyccAprobadorService.consultarTramitesAsociadosAprobadorPorFecha(seleccionAprob, fechaInicio, fechaFin);
            } else {
                listaTramites = dyccAprobadorService.consultarTramitesAsociadosAprobador(seleccionAprob);
            }

            solicitudesAprobador = new SolDictaminDataModel(listaTramites);

        } catch (SIATException error) {
            log.info(error.getDescripcion());
        }
    }

    private void limpiarSeleccionTramites() {
        if (listaSeleccionSolicitudes != null) {
            listaSeleccionSolicitudes.clear();
            botonMostrarListaAprobadoresBloqueado = Boolean.TRUE;
        }
    }

    private void mostrarMensajeSeleccionAprobador(RequestContext request) {
        mensaje.addInfo(MSG, "Debe seleccionar un registro por favor");
        request.update(MSG);
        reset();
    }

    public void mostrarAprobadores() {
        RequestContext request = RequestContext.getCurrentInstance();
        if (seleccionAprob != null) {
            log.info("Empleado seleccionado --> " + seleccionAprob.getNumEmpleado());
            listaAprobadoresDialog.clear();
            List<DyccAprobadorDTO> lstTemp =
                dyccAprobadorService.mostrarAprobadorConTrabajo(Integer.parseInt(accesoUsr.getClaveSir()),
                                                                Integer.parseInt(accesoUsr.getCentroCostoOp()),
                                                                ConstantesDyCNumerico.VALOR_2);
            if (lstTemp != null && !lstTemp.isEmpty()) {
                for (DyccAprobadorDTO aprobador : lstTemp) {
                    if (!aprobador.getNumEmpleado().equals(seleccionAprob.getNumEmpleado())) {
                        listaAprobadoresDialog.add(aprobador);
                    }
                }
            }

            request.execute("wgdAprob.show()");
        } else {
            mensaje.addInfo(MSG, "Debe seleccionar un registro por favor");
            request.update(MSG);
            reset();
        }
    }

    public void setFechas() {
        log.debug("INICIO setFechas");
    }

    public void reset() {
        init();
    }

    public void setListaAprobadores(List<DyccAprobadorDTO> listaAprobadores) {
        this.listaAprobadores = listaAprobadores;
    }

    public List<DyccAprobadorDTO> getListaAprobadores() {
        return listaAprobadores;
    }

    public void setDyccAprobadorService(DyccAprobadorService dyccAprobadorService) {
        this.dyccAprobadorService = dyccAprobadorService;
    }

    public DyccAprobadorService getDyccAprobadorService() {
        return dyccAprobadorService;
    }

    public void setSeleccionAprob(DyccAprobadorDTO seleccionAprob) {
        this.seleccionAprob = seleccionAprob;
    }

    public DyccAprobadorDTO getSeleccionAprob() {
        return seleccionAprob;
    }

    public void setListaAprobadoresDialog(List<DyccAprobadorDTO> listaAprobadoresDialog) {
        this.listaAprobadoresDialog = listaAprobadoresDialog;
    }

    public List<DyccAprobadorDTO> getListaAprobadoresDialog() {
        return listaAprobadoresDialog;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public void setSolicitudesAprobador(SolDictaminDataModel solicitudesAprobador) {
        this.solicitudesAprobador = solicitudesAprobador;
    }

    public SolDictaminDataModel getSolicitudesAprobador() {
        return solicitudesAprobador;
    }

    public void setListaSeleccionSolicitudes(List<DictaminadorSolBean> listaSeleccionSolicitudes) {
        this.listaSeleccionSolicitudes = listaSeleccionSolicitudes;
    };

    public List<DictaminadorSolBean> getListaSeleccionSolicitudes() {
        return listaSeleccionSolicitudes;
    }

    public void seleccionaAprobador() {
        varBotonSearch = false;

        if (seleccionAprob != null) {
            log.info("Aprobador origen seleccionado - " + seleccionAprob.getNumEmpleado());
        }
    }

    public void setVarBotonSearch(Boolean varBotonSearch) {
        this.varBotonSearch = varBotonSearch;
    }

    public boolean getVarBotonSearch() {
        return varBotonSearch;
    }

    public void setBotonConfirmacionAprobacionBloqueado(boolean botonConfirmacionAprobacionBloqueado) {
        this.botonConfirmacionAprobacionBloqueado = botonConfirmacionAprobacionBloqueado;
    }

    public boolean isBotonConfirmacionAprobacionBloqueado() {
        return botonConfirmacionAprobacionBloqueado;
    }

    public void setBotonMostrarListaAprobadoresBloqueado(boolean botonMostrarListaAprobadoresBloqueado) {
        this.botonMostrarListaAprobadoresBloqueado = botonMostrarListaAprobadoresBloqueado;
    }

    public boolean isBotonMostrarListaAprobadoresBloqueado() {
        return botonMostrarListaAprobadoresBloqueado;
    }

    public void setAprobadorDestinoReasignacion(DyccAprobadorDTO aprobadorDestinoReasignacion) {
        this.aprobadorDestinoReasignacion = aprobadorDestinoReasignacion;
    }

    public DyccAprobadorDTO getAprobadorDestinoReasignacion() {
        return aprobadorDestinoReasignacion;
    }

    public void setListaAprobadoresReasignar(List<DyccAprobadorDTO> listaAprobadoresReasignar) {
        this.listaAprobadoresReasignar = listaAprobadoresReasignar;
    }

    public List<DyccAprobadorDTO> getListaAprobadoresReasignar() {
        return listaAprobadoresReasignar;
    }

    public void cargaListaAprobadoresReasignar() {
        listaAprobadoresReasignar =
                dyccAprobadorService.mostrarAprobadorConTrabajo(Integer.parseInt(accesoUsr.getClaveSir()),
                                                                Integer.parseInt(accesoUsr.getCentroCostoOp()),
                                                                ConstantesDyCNumerico.VALOR_0);
        RequestContext.getCurrentInstance().execute(MOSTRAR_DIALOGO_LISTA_APROBADORES);
    }

    public void confirmarReasignacionTramitesAprobador() {
        if (seleccionAprob != null) {
            aprobadorNombreAdministracion = getNombreAdministracionAprobadorSeleccionado();
            log.info("Si hay aprobador seleccionado");
        } else {
            log.info("No hay aprobador seleccionado");
        }
        RequestContext.getCurrentInstance().execute(MOSTRAR_CONFIRMACION_REASIGNACION_APROBADOR);
    }

    private String getNombreAdministracionAprobadorSeleccionado() {
        return seleccionAprob.getClaveAdm().toString();
    }

    public void confirmarContinuarReasignacionTramitesAprobador() {
        RequestContext.getCurrentInstance().execute(MOSTRAR_CONFIRMACION_CONTINUACION_REASIGNACION_APROBADOR);
    }

    public void realizaReasignacionTramitesAprobador() {
        RequestContext request = RequestContext.getCurrentInstance();

        if (!validacionAgs.aprobadorValido(aprobadorDestinoReasignacion.getNumEmpleado())) {
            validacionAgs.muestraMensajeAprobadorNoValido("msgAResol");

            return;
        }

        /**validacionAgs.ejecutaJs(OCULTAR_CONFIRMACION_REASIGNACION_APROBADOR);*/
        RequestContext.getCurrentInstance().execute(OCULTAR_CONFIRMACION_REASIGNACION_APROBADOR);

        imprimeListaTramitesReasignacion();
        log.info("Empleado destino : " + aprobadorDestinoReasignacion.getNumEmpleado());
        try {

            dyccAprobadorService.reasignacionAprobador(listaSeleccionSolicitudes, aprobadorDestinoReasignacion,
                    Integer.parseInt(accesoUsr.getNumeroEmp()));
            request.execute(REASIGNACION_APROBADOR_EXITOSA);
            request.execute(MOSTRAR_CONFIRMACION_CONTINUACION_REASIGNACION_APROBADOR);
            reset();

        } catch (SIATException e) {
            log.info(e.getMessage());

            mensaje.addError(MSG, "Ocurrió un error al actualizar la información, vuelva intentarlo mas tarde.");
            request.update(MSG);
        }
    }

    private void imprimeListaTramitesReasignacion() {
        log.info("Total tramites : " + listaSeleccionSolicitudes.size());
        for (DictaminadorSolBean tramite : listaSeleccionSolicitudes) {

            log.info(String.format("tramite [ numeroControl : %s ][ tipoTramite : %s ]", tramite.getNumControl(),
                                   tramite.getIdTipoTramite()));

        }

    }

    public void reseteaSeleccionAprobador() {
        seleccionAprob = null;
    }

    public void setAprobadorNombreAdministracion(String aprobadorNombreAdministracion) {
        this.aprobadorNombreAdministracion = aprobadorNombreAdministracion;
    }

    public String getAprobadorNombreAdministracion() {
        return aprobadorNombreAdministracion;
    }

    public void seleccionaAprobadorDestino() {
        botonConfirmacionAprobacionBloqueado = aprobadorDestinoReasignacion == null;
    }

    public void revisaEstadoSeleccion() {
        botonMostrarListaAprobadoresBloqueado =
                listaSeleccionSolicitudes == null || listaSeleccionSolicitudes.isEmpty();
    }

    public void setValidacionAgs(SatAgsEmpleadosMVService validacionAgs) {
        this.validacionAgs = validacionAgs;
    }

    public SatAgsEmpleadosMVService getValidacionAgs() {
        return validacionAgs;
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }
}
