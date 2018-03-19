package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * @author Federico Chopin Gachuz
 *
 */
@ManagedBean(name = "administrarSolicitudesMB")
@ViewScoped
public class AdministrarSolicitudesMB extends AbstractPage {

    private static final Logger LOG = Logger.getLogger(AdministrarSolicitudesMB.class);

    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty(value = "#{dictaminarSolicitudMB}")
    private DictaminarSolicitudMB dictaminarSolicitudMB;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    @ManagedProperty(value = "#{iniciarFacultadesMB}")
    private IniciarFacultadesMB iniciarFacultadesMB;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private LazyDataModel<SolicitudAdministrarSolVO> listaSolicitudesDictaminador;
    private List<SolicitudAdministrarSolVO> datasource;

    private SolicitudAdministrarSolVO solicitudAdministrarSolVO;
    private AdministrarSolicitudesVO administrarSolicitudesVO;

    private int idUnidad;
    private int centroCosto;

    private boolean pnlSelSol;
    private boolean pnlDevolucion;
    private boolean btnDictaminar;
    private String seleccionCombo;
    private boolean varBotonDic;

    private boolean varBotonCan;
    private boolean rolDictaminador;
    private boolean rolAdministrador;
    private boolean rolFiscalizador;
    private String otrosReq;
    private AccesoUsr accesoUsr;
    private String roles = "";
    private String rol;
    private String numEmpleadoStr = "";
    private String unidad = "";
    private String nombreCompleto = "";

    private boolean estadoFacultades;

    private String estadoDocumento;

    @PostConstruct
    public void init() {

        rol = "";

        this.accesoUsr = serviceObtenerSesion.execute();

        Utils.validarSesion(accesoUsr, Procesos.DYC00005);

        roles = accesoUsr.getRoles();

        numEmpleadoStr = accesoUsr.getNumeroEmp();
        unidad = accesoUsr.getLocalidad();
        nombreCompleto = accesoUsr.getNombreCompleto();

        idUnidad = Integer.parseInt(this.accesoUsr.getClaveSir());
        centroCosto = Integer.parseInt(this.accesoUsr.getCentroCosto());

        varBotonDic = Boolean.TRUE;

        varBotonCan = Boolean.TRUE;

        setDataModel(new SIATDataModel());
        obtieneRol(roles);

        validaRol(rol);

        estadoFacultades = false;

        if (rol.equals(ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR)) {
            listaSolicitudesDictaminador = new LazyDataModel<SolicitudAdministrarSolVO>() {
                public SolicitudAdministrarSolVO getRowData(String rowKey) {
                    for (SolicitudAdministrarSolVO solicitud : datasource) {
                        if (solicitud.getNumControl().equals(rowKey)) {
                            return solicitud;
                        }
                    }

                    return null;
                }

                public Object getRowKey(SolicitudAdministrarSolVO solicitud) {
                    return solicitud.getNumControl();
                }

                public List<SolicitudAdministrarSolVO> load(int first, int pageSize, String sortField,
                        SortOrder sortOrder, Map<String, String> filters) {
                    Paginador paginador = new Paginador();
                    paginador.setNPaginaActual((first + pageSize) / pageSize);
                    paginador.setNRegPorPagina(pageSize);
                    int nReg = 0;

                    String numControl = "";
                    String rfc = "";

                    for (Map.Entry<String, String> entry : filters.entrySet()) {
                        if (entry.getKey().equals("numControl")) {
                            numControl = entry.getValue().toUpperCase();
                        } else if (entry.getKey().equals("dycpServicioDTO.rfcContribuyente")) {
                            rfc = entry.getValue().toUpperCase();
                        } else {
                            LOG.error(String.format("No se encuentra el filtro: %s", entry.getKey()));
                        }
                    }

                    LOG.debug(String.format("Filtrado por numControl: %s, rfc: %s", numControl, rfc));

                    if ((numControl.length() == ConstantesDyCNumerico.VALOR_0
                            || numControl.length() >= ConstantesDyCNumerico.VALOR_4)
                            && (rfc.length() == ConstantesDyCNumerico.VALOR_0
                            || rfc.length() >= ConstantesDyCNumerico.VALOR_3)) {
                        desistirSolicitudes(dycpSolicitudService.buscarSolicitudesAdministradorPaginador(idUnidad, numControl,
                                rfc, paginador));
                        nReg = dycpSolicitudService.countSolicitudesAdministrador(idUnidad, numControl, rfc).intValue();
                        datasource = dycpSolicitudService.buscarSolicitudesAdministradorPaginador(idUnidad, numControl,
                                rfc, paginador);
                        this.setRowCount(nReg);
                        if (datasource != null && !datasource.isEmpty()) {
                            varBotonCan = Boolean.FALSE;
                        } else {
                            varBotonCan = Boolean.TRUE;
                        }
                    }

                    return datasource;
                }
            };
        }

        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String mensaje = parameterMap.get("mensajeId");

        if (mensaje != null && !mensaje.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje,
                            null));
        }
    }

    public void desistirSolicitudes(List<SolicitudAdministrarSolVO> datasource) {
        LOG.error("Inicio proceso de desistir tramites por requerimiento vencido bandeja dictaminador ***");
        if (datasource != null && !datasource.isEmpty()) {
            for (SolicitudAdministrarSolVO objeto : datasource) {
                if (null != objeto.getNumControlDoc()) {
                    administrarSolicitudesVO = administrarSolicitudesService.obtenerDiferencia(objeto.getNumControlDoc());
                } else {
                    administrarSolicitudesVO = null;
                }
                reglaPlazoReq(objeto.getNumRequerimiento(), objeto.getNumControl(), objeto.getNumControlDoc());
                estadoFacultades = false;
                List listaDatos = calculoFecha(objeto);
                objeto.setFechaLimite((Date) listaDatos.get(0));
                objeto.setPeriodo(objeto.getPeriodo() + " "
                        + objeto.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
            }
        }
    }

    public void obtieneRol(String roles) {

        if (roles.contains("SAT_DYC_DICT")) {

            rol = ConstantesAdministrarSolicitud.ROL_DICTAMINADOR;

        } else if (roles.contains("SAT_DYC_ADMIN_APRO")) {

            rol = ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR;

        } else if (roles.contains("SAT_DYC_USR_FISC")) {

            rol = ConstantesAdministrarSolicitud.ROL_FISCALIZADOR;

        }

    }

    public void buscarSolicitudes() {
        varBotonDic = Boolean.TRUE;

        if (rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR)) {

            listaSolicitudesDictaminador = new LazyDataModel<SolicitudAdministrarSolVO>() {
                public SolicitudAdministrarSolVO getRowData(String rowKey) {
                    for (SolicitudAdministrarSolVO solicitud : datasource) {
                        if (solicitud.getNumControl().equals(rowKey)) {
                            return solicitud;
                        }
                    }

                    return null;
                }

                public Object getRowKey(SolicitudAdministrarSolVO solicitud) {
                    return solicitud.getNumControl();
                }

                public List<SolicitudAdministrarSolVO> load(int first, int pageSize, String sortField,
                        SortOrder sortOrder, Map<String, String> filters) {
                    Paginador paginador = new Paginador();
                    paginador.setNPaginaActual((first + pageSize) / pageSize);
                    paginador.setNRegPorPagina(pageSize);
                    int nReg = 0;

                    String numControl = "";
                    String rfc = "";

                    for (Map.Entry<String, String> entry : filters.entrySet()) {
                        if (entry.getKey().equals("numControl")) {
                            numControl = entry.getValue().toUpperCase();
                        } else if (entry.getKey().equals("dycpServicioDTO.rfcContribuyente")) {
                            rfc = entry.getValue().toUpperCase();
                        } else {
                            LOG.error(String.format("No se encuentra el filtro: %s", entry.getKey()));
                        }
                    }

                    LOG.debug(String.format("Filtrado por numControl: %s, rfc: %s", numControl, rfc));

                    if ((numControl.length() == ConstantesDyCNumerico.VALOR_0
                            || numControl.length() >= ConstantesDyCNumerico.VALOR_4)
                            && (rfc.length() == ConstantesDyCNumerico.VALOR_0
                            || rfc.length() >= ConstantesDyCNumerico.VALOR_3)) {
                        desistirSolicitudes(dycpSolicitudService.buscarSolicitudesDictaminadorPaginador(
                                numEmpleadoStr,
                                seleccionCombo,
                                numControl,
                                rfc,
                                paginador
                        ));

                        nReg = dycpSolicitudService.countSolicitudesDictaminador(
                                numEmpleadoStr,
                                seleccionCombo,
                                numControl,
                                rfc
                        ).intValue();

                        datasource = dycpSolicitudService.buscarSolicitudesDictaminadorPaginador(
                                numEmpleadoStr,
                                seleccionCombo,
                                numControl,
                                rfc,
                                paginador
                        );

                        for (SolicitudAdministrarSolVO objeto : datasource) {
                            List listaDatos = calculoFecha(objeto);
                            objeto.setFechaLimite((Date) listaDatos.get(0));
                            objeto.setPeriodo(objeto.getPeriodo() + " "
                                    + objeto.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
                        }
                        this.setRowCount(nReg);
                        if (datasource != null && !datasource.isEmpty()) {
                            varBotonCan = Boolean.FALSE;
                        } else {
                            varBotonCan = Boolean.TRUE;
                        }
                    }

                    return datasource;
                }
            };
        }
    }

    public static final Comparator<SolicitudAdministrarSolVO> CLASE_PARA_ORDENAR = new Comparator<SolicitudAdministrarSolVO>() {

        @Override
        public int compare(SolicitudAdministrarSolVO dto1, SolicitudAdministrarSolVO dto2) {
            int result = 0;
            if ((!dto1.getEstadoDocumento().equals("") || !dto2.getEstadoDocumento().equals("")) && !(!dto1.getEstadoDocumento().equals("") && !dto2.getEstadoDocumento().equals(""))) {
                result = !dto1.getEstadoDocumento().equals("") && dto2.getEstadoDocumento().equals("") ? -1 : !dto2.getEstadoDocumento().equals("") && dto1.getEstadoDocumento().equals("") ? 1 : 0;
                if (result != 0) {
                    return result;
                }
            }
            if (null != dto1.getFechaLimite() && null != dto2.getFechaLimite()) {
                result = (dto1.getFechaLimite().compareTo(dto2.getFechaLimite()));
            }
            return result != 0 ? result : dto1.getNumControl().compareTo(dto2.getNumControl());
        }
    };

    public void reglaPlazoReq(int numRequerimiento, String numControl, String numControlDoc) {

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = false;
        administrarSolicitudesVO = administrarSolicitudesService.obtenerDiferencia(numControlDoc);

        if (null != administrarSolicitudesVO && null != administrarSolicitudesVO.getFechaNotificacion()
                && null == administrarSolicitudesVO.getFechaSolventacion()) {
            if (numRequerimiento == ConstantesDyCNumerico.VALOR_1) {
                fechaResultado
                        = getDiaHabilService().buscarDiaFederalRecaudacion(administrarSolicitudesVO.getFechaNotificacion(),
                                ConstantesDyCNumerico.VALOR_21);
            } else if (numRequerimiento == ConstantesDyCNumerico.VALOR_2) {
                fechaResultado
                        = getDiaHabilService().buscarDiaFederalRecaudacion(administrarSolicitudesVO.getFechaNotificacion(),
                                ConstantesDyCNumerico.VALOR_11);
            }

            fechaHoy = new Date();

            SimpleDateFormat formatResultado = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);
            String fResultado = formatResultado.format(fechaResultado);

            SimpleDateFormat formatHoy = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);
            String fHoy = formatHoy.format(fechaHoy);

            try {

                fechaResultado = formatResultado.parse(fResultado);
                fechaHoy = formatHoy.parse(fHoy);

            } catch (ParseException e) {
                LOG.error("ERROR: " + e.getMessage());
            }

            if (null != fechaResultado && fechaHoy.after(fechaResultado)) {
                fechaMayor = Boolean.TRUE;
            }

        }
        if (fechaMayor) {
            administrarSolicitudesService.actualizarEstados(numControl, numControlDoc,true);
        }

    }

    public List calculoFecha(SolicitudAdministrarSolVO objeto) {

        List listaDatos = new ArrayList();

        Date fechaMaxima = null;
        Date fechaLimite = null;
        int diasHabNotSol = 0;

        try {

            long idFacultades = objeto.getIdFacultades();
            int diasFacultades = 0;

            if (idFacultades > 0) {
                diasFacultades
                        = administrarSolicitudesService.obtenerDiasFacultades(objeto.getNumControl(), idFacultades);
            }

            int dias = objeto.getDias();
            dias = dias + diasFacultades;

            fechaMaxima
                    = calcularFechasService.calcularFechaFinal(objeto.getFechaPresentacion(), dias, objeto.getTipoDia());

            diasHabNotSol = calculaDiasNotSol(objeto, idFacultades);

            if (diasHabNotSol > 0) {
                fechaLimite
                        = calcularFechasService.calcularFechaFinal(fechaMaxima, diasHabNotSol, objeto.getTipoDia());

            } else {
                fechaLimite = fechaMaxima;
            }

            listaDatos.add(fechaLimite);
            listaDatos.add(diasHabNotSol);

            return listaDatos;

        } catch (Exception e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        return listaDatos;
    }

    public void obtenerEstadoDocumento(Integer idDocumento) {

        switch (idDocumento) {

            case ConstantesDyCNumerico.VALOR_1:
                estadoDocumento = "Adjuntado";
                break;

            case ConstantesDyCNumerico.VALOR_8:
                estadoDocumento = "Notificado";
                break;

            case ConstantesDyCNumerico.VALOR_2:
                estadoDocumento = "Aprobado";
                break;

            case ConstantesDyCNumerico.VALOR_3:
                estadoDocumento = "Generado";
                break;

            case ConstantesDyCNumerico.VALOR_4:
                estadoDocumento = "En Modificación";
                break;

            case ConstantesDyCNumerico.VALOR_5:
                estadoDocumento = "En Aprobación";
                break;

            case ConstantesDyCNumerico.VALOR_6:
                estadoDocumento = "Rechazado";
                break;

            case ConstantesDyCNumerico.VALOR_7:
                estadoDocumento = "Diligenciado";
                break;

            case ConstantesDyCNumerico.VALOR_9:
                estadoDocumento = "En revisión central";
                break;

            case ConstantesDyCNumerico.VALOR_10:
                estadoDocumento = "Aprobado por revisor central";
                break;

            case ConstantesDyCNumerico.VALOR_11:
                estadoDocumento = "Rechazado por revisor central";
                break;

            default:
                estadoDocumento = "";
                break;

        }

    }

    public int calculaDiasNotSol(SolicitudAdministrarSolVO objeto, long idFacultades) {

        int diasHabNotSol = 0;
        int diasHabiles = 0;

        Integer idDocumento = administrarSolicitudesService.obtenerIdEstadoDocumento(objeto.getNumControl());

        obtenerEstadoDocumento(idDocumento);
        objeto.setEstadoDocumento(estadoDocumento);

        if (idFacultades > 0) {
            objeto.setEstadoNotificacion("Inicio de facultades");
            estadoFacultades = Boolean.TRUE;
        }

        try {

            if (null != objeto.getNumControlDoc()) {
                administrarSolicitudesVO = administrarSolicitudesService.obtenerDiferencia(objeto.getNumControlDoc());
            } else {
                administrarSolicitudesVO = null;
            }

            if (null != administrarSolicitudesVO) {

                if (!estadoFacultades) {

                    if (objeto.getNumRequerimiento() == ConstantesDyCNumerico.VALOR_1) {
                        obtenerEstado1(objeto);
                    } else {
                        obtenerEstado2(objeto);
                    }
                }

                /**
                 * diasNormales = administrarSolicitudesVO.getDiasHabiles();
                 */
                if (null != administrarSolicitudesVO.getFechaNotificacion()
                        && null != administrarSolicitudesVO.getFechaSolventacion()
                        && administrarSolicitudesVO.getDiasHabiles() > ConstantesDyCNumerico.VALOR_0) {
                    List<String> lista = new ArrayList<String>();
                    lista.add("TIPO_CAL_TODOS");
                    diasHabiles
                            = diaHabilService.cantidadDiasHabilesPorFechas(administrarSolicitudesVO.getFechaNotificacion(),
                                    administrarSolicitudesVO.getFechaSolventacion(),
                                    lista, "LUGAR_FEDERAL", 0);
                    /**
                     * sabDom = sumaSabDom(administrarSolicitudesVO.
                     * getFechaNotificacion(),
                     * administrarSolicitudesVO.getFechaSolventacion());
                     */
                }

            }

            diasHabNotSol = diasHabiles;

        } catch (Exception e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        return diasHabNotSol;
    }

    public void obtenerEstado1(SolicitudAdministrarSolVO objeto) {

        SimpleDateFormat formateador = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);

        String estado = null;
        estado = "Primer Requerimiento ";

        if (null != administrarSolicitudesVO.getFechaSolventacion()
                && null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + fechaSol;
        } else if (null != administrarSolicitudesVO.getFechaSolventacion()
                && null == administrarSolicitudesVO.getFechaNotificacion()) {

            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + ConstantesAdministrarSolicitud.SIN_ESTATUS + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null == administrarSolicitudesVO.getFechaSolventacion()
                && null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + ConstantesAdministrarSolicitud.SIN_ESTATUS;
        } else {
            estado = "";
        }

        objeto.setEstadoNotificacion(estado);

    }

    public void obtenerEstado2(SolicitudAdministrarSolVO objeto) {

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        String estado = null;
        estado = "Segundo Requerimiento ";

        if (null != administrarSolicitudesVO.getFechaSolventacion()
                && null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null != administrarSolicitudesVO.getFechaSolventacion()
                && null == administrarSolicitudesVO.getFechaNotificacion()) {

            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + ConstantesAdministrarSolicitud.SIN_ESTATUS + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null == administrarSolicitudesVO.getFechaSolventacion()
                && null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + ConstantesAdministrarSolicitud.SIN_ESTATUS;
        } else {
            estado = "";
        }

        objeto.setEstadoNotificacion(estado);

    }

    public int sumaSabDom(Date fechaInicial, Date fechaFinal) {

        Calendar fechaInicial2 = Calendar.getInstance();
        Calendar fechaFinal2 = Calendar.getInstance();

        fechaInicial2.setTime(fechaInicial);
        fechaFinal2.setTime(fechaFinal);

        int diffDays = 0;

        while (fechaInicial2.before(fechaFinal2) || fechaInicial2.equals(fechaFinal2)) {

            if (fechaInicial2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    || fechaInicial2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                diffDays++;
            }

            fechaInicial2.add(Calendar.DATE, 1);

        }
        return diffDays;

    }

    public void buscarSolicitudes1() {
        varBotonDic = Boolean.TRUE;
        varBotonCan = Boolean.TRUE;

        if (datasource != null) {
            datasource.clear();
            getDataModel().setWrappedData(datasource);
        }
    }

    public void validaRol(String rol) {
        if (rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR)) {
            pnlDevolucion = Boolean.TRUE;
            pnlSelSol = Boolean.TRUE;
            btnDictaminar = Boolean.TRUE;
        } else if (rol.equals(ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR)) {
            pnlDevolucion = Boolean.TRUE;
            pnlSelSol = Boolean.FALSE;
            btnDictaminar = Boolean.TRUE;
        } else if (rol.equals(ConstantesAdministrarSolicitud.ROL_FISCALIZADOR)) {
            pnlDevolucion = Boolean.TRUE;
            pnlSelSol = Boolean.FALSE;
            btnDictaminar = Boolean.TRUE;
        } else {
            pnlDevolucion = Boolean.FALSE;
            pnlSelSol = Boolean.FALSE;
            btnDictaminar = Boolean.FALSE;
        }
    }

    public void onRowSelect() {
        varBotonDic = Boolean.FALSE;
    }

    public String dictaminar() {

        dictaminarSolicitudMB.setSolicitudAdministrarSolVO(solicitudAdministrarSolVO);
        dictaminarSolicitudMB.setRol(rol);
        dictaminarSolicitudMB.setNumEmpleadoStr(numEmpleadoStr);
        dictaminarSolicitudMB.setIdUnidad(idUnidad);
        dictaminarSolicitudMB.setCentroCosto(centroCosto);
        dictaminarSolicitudMB.setNombreCompleto(nombreCompleto);
        dictaminarSolicitudMB.setSeleccionCombo(seleccionCombo);
        dictaminarSolicitudMB.validarRol();
        dictaminarSolicitudMB.opcionesCombo();
        dictaminarSolicitudMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_0);
        dictaminarSolicitudMB.init();

        return "dictaminarSolicitud";
    }

    public void setPnlSelSol(boolean pnlSelSol) {
        this.pnlSelSol = pnlSelSol;
    }

    public boolean isPnlSelSol() {
        return pnlSelSol;
    }

    public void setBtnDictaminar(boolean btnDictaminar) {
        this.btnDictaminar = btnDictaminar;
    }

    public boolean isBtnDictaminar() {
        return btnDictaminar;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setSeleccionCombo(String seleccionCombo) {
        this.seleccionCombo = seleccionCombo;
    }

    public String getSeleccionCombo() {
        return seleccionCombo;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setVarBotonDic(boolean varBotonDic) {
        this.varBotonDic = varBotonDic;
    }

    public boolean isVarBotonDic() {
        return varBotonDic;
    }

    public void setVarBotonCan(boolean varBotonCan) {
        this.varBotonCan = varBotonCan;
    }

    public boolean isVarBotonCan() {
        return varBotonCan;
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

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setOtrosReq(String otrosReq) {
        this.otrosReq = otrosReq;
    }

    public String getOtrosReq() {
        return otrosReq;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setRolDictaminador(boolean rolDictaminador) {
        this.rolDictaminador = rolDictaminador;
    }

    public boolean isRolDictaminador() {
        return rolDictaminador;
    }

    public void setRolAdministrador(boolean rolAdministrador) {
        this.rolAdministrador = rolAdministrador;
    }

    public boolean isRolAdministrador() {
        return rolAdministrador;
    }

    public void setRolFiscalizador(boolean rolFiscalizador) {
        this.rolFiscalizador = rolFiscalizador;
    }

    public boolean isRolFiscalizador() {
        return rolFiscalizador;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setPnlDevolucion(boolean pnlDevolucion) {
        this.pnlDevolucion = pnlDevolucion;
    }

    public boolean isPnlDevolucion() {
        return pnlDevolucion;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public CalcularFechasService getCalcularFechasService() {
        return calcularFechasService;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setAdministrarSolicitudesVO(AdministrarSolicitudesVO administrarSolicitudesVO) {
        this.administrarSolicitudesVO = administrarSolicitudesVO;
    }

    public AdministrarSolicitudesVO getAdministrarSolicitudesVO() {
        return administrarSolicitudesVO;
    }

    public LazyDataModel<SolicitudAdministrarSolVO> getListaSolicitudesDictaminador() {
        return listaSolicitudesDictaminador;
    }

    public void setListaSolicitudesDictaminador(LazyDataModel<SolicitudAdministrarSolVO> listaSolicitudesDictaminador) {
        this.listaSolicitudesDictaminador = listaSolicitudesDictaminador;
    }

    public void setSolicitudAdministrarSolVO(SolicitudAdministrarSolVO solicitudAdministrarSolVO) {
        this.solicitudAdministrarSolVO = solicitudAdministrarSolVO;
    }

    public SolicitudAdministrarSolVO getSolicitudAdministrarSolVO() {
        return solicitudAdministrarSolVO;
    }

    public void setIniciarFacultadesMB(IniciarFacultadesMB iniciarFacultadesMB) {
        this.iniciarFacultadesMB = iniciarFacultadesMB;
    }

    public IniciarFacultadesMB getIniciarFacultadesMB() {
        return iniciarFacultadesMB;
    }

    public void setEstadoFacultades(boolean estadoFacultades) {
        this.estadoFacultades = estadoFacultades;
    }

    public boolean isEstadoFacultades() {
        return estadoFacultades;
    }

    public void setCentroCosto(int centroCosto) {
        this.centroCosto = centroCosto;
    }

    public int getCentroCosto() {
        return centroCosto;
    }

    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public String getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
