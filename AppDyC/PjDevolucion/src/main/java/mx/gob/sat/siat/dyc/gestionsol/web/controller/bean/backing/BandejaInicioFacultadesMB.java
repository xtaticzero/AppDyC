package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.data.PageEvent;


/**
 * @author Federico Chopin Gachuz
 * */
@ManagedBean(name = "bandejaInicioFacultadesMB")
@SessionScoped
public class BandejaInicioFacultadesMB extends AbstractPage {

    private static final Logger LOG = Logger.getLogger(BandejaInicioFacultadesMB.class);

    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    @ManagedProperty(value = "#{iniciarFacultadesMB}")
    private IniciarFacultadesMB iniciarFacultadesMB;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private List<SolicitudAdministrarSolVO> listaSolicitudesAdministrador = new ArrayList<SolicitudAdministrarSolVO>();

    private SolicitudAdministrarSolVO solicitudAdministrarSolVO;
    private AdministrarSolicitudesVO administrarSolicitudesVO;

    private String rol;
    private AccesoUsr accesoUsr;
    private String roles = "";
    private String numEmpleadoStr = "";
    private String unidad = "";
    private int idUnidad;

    private boolean paginador;
    private boolean varBotonFac;
    private int first;

    private boolean estadoFacultades;


    public void init() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            LOG.info("es postback");
        } else {
            rol = "";
            this.accesoUsr = serviceObtenerSesion.execute();
            Utils.validarSesion(accesoUsr, Procesos.DYC00024);
            roles = accesoUsr.getRoles();
            numEmpleadoStr = accesoUsr.getNumeroEmp();
            unidad = accesoUsr.getLocalidad();
            idUnidad = Integer.parseInt(this.accesoUsr.getClaveSir());
            varBotonFac = Boolean.TRUE;
            obtieneRol(roles);
            setDataModel(new SIATDataModel());

            estadoFacultades = Boolean.FALSE;

            int numeroDias = 0;

            if (rol.equals(ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR)) {

                listaSolicitudesAdministrador =
                        dycpSolicitudService.buscarSolicitudesAdministradorFacultades(idUnidad, numEmpleadoStr);

                if (listaSolicitudesAdministrador.size() > ConstantesDyCNumerico.VALOR_5) {
                    paginador = Boolean.TRUE;
                } else {
                    paginador = Boolean.FALSE;
                }
                LOG.error("Inicio proceso de desistir tramites por requerimiento vencido bandeja aprobador art22 ***");
                if (!listaSolicitudesAdministrador.isEmpty()) {

                    for (SolicitudAdministrarSolVO objeto : listaSolicitudesAdministrador) {

                        List listaDatos = calculoFecha(objeto);
                        objeto.setFechaLimite((Date)listaDatos.get(0));
                        numeroDias = ((Integer)listaDatos.get(1));
                        LOG.debug("El número de días es: " + numeroDias);
                        reglaPlazoReq(objeto.getNumRequerimiento(), objeto.getNumControl(), objeto.getNumControlDoc());
                        objeto.setPeriodo(objeto.getPeriodo() + " " +
                                          objeto.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
                    }

                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario no cuenta con el rol 'SAT_DYC_ADMIN_APRO' para poder ver los registros",
                                                                              ""));
            }

            getDataModel().setWrappedData(listaSolicitudesAdministrador);
        }


    }

    public String irIniciarFacultades() {

        iniciarFacultadesMB.setClaveAdmon(idUnidad);
        iniciarFacultadesMB.setNumEmpDictaminador(solicitudAdministrarSolVO.getDycpServicioDTO().getDyccDictaminadorDTO().getNumEmpleado().toString());
        iniciarFacultadesMB.setNombreDictaminador(solicitudAdministrarSolVO.getNombre() + " " +
                                                  solicitudAdministrarSolVO.getApPaterno() + " " +
                                                  solicitudAdministrarSolVO.getApMaterno());
        iniciarFacultadesMB.setFechaPresentacion(solicitudAdministrarSolVO.getFechaPresentacion());
        iniciarFacultadesMB.setNumControl(solicitudAdministrarSolVO.getNumControl());
        iniciarFacultadesMB.setClaveAdm(solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());
        iniciarFacultadesMB.setRfcContribuyente(solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente());
        iniciarFacultadesMB.setImporteSolicitado(solicitudAdministrarSolVO.getImporteSolicitado());
        iniciarFacultadesMB.setFolio(solicitudAdministrarSolVO.getFolio());
        iniciarFacultadesMB.setFechaInicio(solicitudAdministrarSolVO.getFechaInicio());
        iniciarFacultadesMB.init();

        return "inicioFacultades";
    }

    public void reglaPlazoReq(int numRequerimiento, String numControl, String numControlDoc) {

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = false;

        if (null != administrarSolicitudesVO && null != administrarSolicitudesVO.getFechaNotificacion() &&
            null == administrarSolicitudesVO.getFechaSolventacion()) {

            if (numRequerimiento == ConstantesDyCNumerico.VALOR_1) {
                fechaResultado =
                        getDiaHabilService().buscarDiaFederalRecaudacion(administrarSolicitudesVO.getFechaNotificacion(),
                                                                         ConstantesDyCNumerico.VALOR_21);
            } else if (numRequerimiento == ConstantesDyCNumerico.VALOR_2) {
                fechaResultado =
                        getDiaHabilService().buscarDiaFederalRecaudacion(administrarSolicitudesVO.getFechaNotificacion(),
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
                diasFacultades =
                        administrarSolicitudesService.obtenerDiasFacultades(objeto.getNumControl(), idFacultades);
            }

            int dias = objeto.getDias();
            dias = dias + diasFacultades;

            fechaMaxima =
                    calcularFechasService.calcularFechaFinal(objeto.getFechaPresentacion(), dias, objeto.getTipoDia());

            diasHabNotSol = calculaDiasNotSol(objeto, idFacultades);

            if (diasHabNotSol > 0) {
                fechaLimite =
                        calcularFechasService.calcularFechaFinal(fechaMaxima, diasHabNotSol, objeto.getTipoDia());

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

    public int calculaDiasNotSol(SolicitudAdministrarSolVO objeto, long idFacultades) {

        int diasHabNotSol = 0;
        int diasHabiles = 0;

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

                if (null != administrarSolicitudesVO.getFechaNotificacion() &&
                    null != administrarSolicitudesVO.getFechaSolventacion() &&
                    administrarSolicitudesVO.getDiasHabiles() > ConstantesDyCNumerico.VALOR_0) {
                    List<String> lista = new ArrayList<String>();
                    lista.add("TIPO_CAL_TODOS");
                    diasHabiles =
                            diaHabilService.cantidadDiasHabilesPorFechas(administrarSolicitudesVO.getFechaNotificacion(),
                                                                         administrarSolicitudesVO.getFechaSolventacion(),
                                                                         lista, "LUGAR_FEDERAL", 0);
                }

            }

            diasHabNotSol = diasHabiles;

        } catch (Exception e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        return diasHabNotSol;
    }

    public int sumaSabDom(Date fechaInicial, Date fechaFinal) {

        Calendar fechaInicial2 = Calendar.getInstance();
        Calendar fechaFinal2 = Calendar.getInstance();

        fechaInicial2.setTime(fechaInicial);
        fechaFinal2.setTime(fechaFinal);

        int diffDays = 0;

        while (fechaInicial2.before(fechaFinal2) || fechaInicial2.equals(fechaFinal2)) {

            if (fechaInicial2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                fechaInicial2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                diffDays++;
            }

            fechaInicial2.add(Calendar.DATE, 1);

        }
        return diffDays;

    }

    public void obtenerEstado1(SolicitudAdministrarSolVO objeto) {

        SimpleDateFormat formateador = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);

        String estado = null;
        estado = "Primer Requerimiento ";

        if (null != administrarSolicitudesVO.getFechaSolventacion() &&
            null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + fechaSol;
        } else if (null != administrarSolicitudesVO.getFechaSolventacion() &&
                   null == administrarSolicitudesVO.getFechaNotificacion()) {

            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + ConstantesAdministrarSolicitud.SIN_ESTATUS + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null == administrarSolicitudesVO.getFechaSolventacion() &&
                   null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + ConstantesAdministrarSolicitud.SIN_ESTATUS;
        } else {
            estado = "";
        }

        objeto.setEstadoNotificacion(estado);

    }

    public void obtenerEstado2(SolicitudAdministrarSolVO objeto) {

        SimpleDateFormat formateador = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);

        String estado = null;
        estado = "Segundo Requerimiento ";

        if (null != administrarSolicitudesVO.getFechaSolventacion() &&
            null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null != administrarSolicitudesVO.getFechaSolventacion() &&
                   null == administrarSolicitudesVO.getFechaNotificacion()) {

            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado = estado + ConstantesAdministrarSolicitud.SIN_ESTATUS + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null == administrarSolicitudesVO.getFechaSolventacion() &&
                   null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            estado = estado + fechaNot + ConstantesDyC.SOLVENTADO + ConstantesAdministrarSolicitud.SIN_ESTATUS;
        } else {
            estado = "";
        }

        objeto.setEstadoNotificacion(estado);
    }

    public void obtieneRol(String roles) {
        if (roles.contains("SAT_DYC_ADMIN_APRO")) {
            rol = ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR;
        } else if (roles.contains("SAT_DYC_DICT")) {
            rol = ConstantesAdministrarSolicitud.ROL_DICTAMINADOR;
        } else if (roles.contains("SAT_DYC_USR_FISC")) {
            rol = ConstantesAdministrarSolicitud.ROL_FISCALIZADOR;
        }
    }

    public void onPageChange(PageEvent event) {
        this.setFirst(((DataTable)event.getSource()).getFirst());
    }

    public void onRowSelect() {
        varBotonFac = false;
    }


    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
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

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setListaSolicitudesAdministrador(List<SolicitudAdministrarSolVO> listaSolicitudesAdministrador) {
        this.listaSolicitudesAdministrador = listaSolicitudesAdministrador;
    }

    public List<SolicitudAdministrarSolVO> getListaSolicitudesAdministrador() {
        return listaSolicitudesAdministrador;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public CalcularFechasService getCalcularFechasService() {
        return calcularFechasService;
    }

    public void setAdministrarSolicitudesVO(AdministrarSolicitudesVO administrarSolicitudesVO) {
        this.administrarSolicitudesVO = administrarSolicitudesVO;
    }

    public AdministrarSolicitudesVO getAdministrarSolicitudesVO() {
        return administrarSolicitudesVO;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setVarBotonFac(boolean varBotonFac) {
        this.varBotonFac = varBotonFac;
    }

    public boolean isVarBotonFac() {
        return varBotonFac;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getFirst() {
        return first;
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
