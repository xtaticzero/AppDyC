/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.generico.util.ConsultaUtils;
import static mx.gob.sat.siat.dyc.generico.util.Utils.muestraMensaje;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.consultardevolucionescontribuyente.ConsultarDevolucionContribuyenteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPaginador;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.util.ComponentUtils;


/**
 * Managed Bean que corresponde a la vista consultarDevolucionContribuyenteTramite.jsf
 * @author Alfredo Ramirez
 * @author Jesus Alfredo Hernandez Orozco.
 * @since  08/10/2013
 */
@ManagedBean(name = "consultarDevolucionesContribuyenteMB")
@ViewScoped
public class ConsultarDevolucionesContribuyenteMB {

    private AccesoUsr accesoUsr;

    private String idSolicitudOCompensacion;
    private String numControlDoc;
    private String rfc;

    private int totalSolicitudes;

    private boolean verBotonAdjuntar;
    private boolean verBotonSolventar;
    private boolean muestraPaginador;

    private Logger log = Logger.getLogger(ConsultarDevolucionesContribuyenteMB.class);

    private ConsultarDevolucionesContribuyenteDTO selectedSolicitud;
    private List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudes;
    private List<SelectItem> filtro;

    private static final Integer COMPENSACION = 99;

    @ManagedProperty("#{consultarDevolucionContribuyenteService}")
    private ConsultarDevolucionContribuyenteService consultarDevolucionContribuyenteService;

    @ManagedProperty(value = "#{sessionHandler}")
    private SessionHandler sessionHandler;

    @ManagedProperty("#{adjuntarDocumentoMB}")
    private AdjuntarDocumentoMB adjuntarDocumentoMB;

    @ManagedProperty("#{solventarRequerimientoMB}")
    private SolventarRequerimientoMB solventarRequerimientoMB;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    public ConsultarDevolucionesContribuyenteMB() {
        filtro = new ArrayList<SelectItem>();
    }

    /**
     * Obtiene de los datos de sesion el RFC del contribuyente para poder consultar la lista de solicitudes que tiene
     * registradas.
     */
    @PostConstruct
    public void init() {
        AccesoUsr au = serviceObtenerSesion.execute();
        if (ConsultaUtils.desdeTramitesYNoEstaAmparado(au.getUsuario())) {
            muestraMensaje("Seguimiento de trámites y requerimientos");
        } else {
            cargarInformacion();
            listaSolicitudes = new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        }
    }

    public void cargarInformacion() {

        rfc = null;

        verBotonAdjuntar = Boolean.TRUE;
        verBotonSolventar = Boolean.TRUE;
        try {
            this.accesoUsr = serviceObtenerSesion.execute();
            rfc = accesoUsr.getUsuario();

            crearFiltro();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Consulta los diferentes estados de las solicitudes que existen y las cruza con los estados de las solicitudes que
     * tiene disponible el contribuyente para mostrar solo estatus que correspondan con los de las solicitudes.
     *
     * @throws SIATException
     */
    private void crearFiltro() throws SIATException {

        SelectItemGroup grupoCompensaciones = new SelectItemGroup("Compensaciones");
        SelectItemGroup grupoDevoluciones = new SelectItemGroup("Devoluciones");
        List<SelectItem> opcionDevolucion = new ArrayList<SelectItem>();

        Map<String, String> mapa = consultarDevolucionContribuyenteService.consultarEstadosDeSolicitud(rfc);
        if (mapa.size() > 0) {
            for (Map.Entry entry : mapa.entrySet()) {
                opcionDevolucion.add(new SelectItem(entry.getKey(), (String)entry.getValue()));
            }
            grupoDevoluciones.setSelectItems(opcionDevolucion.toArray(new SelectItem[opcionDevolucion.size()]));
            filtro.add(grupoDevoluciones);
        }

        if (consultarDevolucionContribuyenteService.consultarSiTieneCompensacion(rfc) != null) {
            grupoCompensaciones.setSelectItems(new SelectItem[] { new SelectItem(COMPENSACION, "Requerido") });
            filtro.add(grupoCompensaciones);
        }
    }

    /**
     * Al seleccionar un elemento de la lista se verifica si se van a activar los botones de solventar requerimiento o
     * registrar informacion adicional.
     *
     * Los botones se van a activar siempre y cuando la solicitud se encuentre en alguno de los siguietes status:
     * - (2)Recibida
     * - (3)En Proceso
     * - (4)Requerida
     * - (6)Pendiente de Resolver
     * - (7)En Revisión
     *
     * Ademas, si el estado del requerimiento esta notificado o (el estatus del documento es aprobado y el requerimiento
     * es autorizado) se podra solventar el requerimiento. (siempre y cuando la solicitud este en los estatus anteriores).
     *
     * Adjuntar informacion adicional se activara siempre y cuando la solicitud se encuentre en alguno de los estatus
     * anteriores.
     */
    public void onRowSelect() {

        numControlDoc = null;

        verBotonAdjuntar = Boolean.TRUE;
        verBotonSolventar = Boolean.TRUE;

        boolean banderaCompensacion = Boolean.FALSE;
        boolean bandera1 =
            selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_2 || selectedSolicitud.getIdEstadoSolicitud() ==
            ConstantesDyCNumerico.VALOR_3;
        boolean bandera2 =
            selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_4 || selectedSolicitud.getIdEstadoSolicitud() ==
            ConstantesDyCNumerico.VALOR_6 || selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_7;
        String numcontrol = selectedSolicitud.getNumControl();
        try {
            selectedSolicitud = consultarDevolucionContribuyenteService.obtenerDocumento(numcontrol);
            if (selectedSolicitud != null) {
                numControlDoc = selectedSolicitud.getNumControlDoc();
            } else {
                selectedSolicitud = consultarDevolucionContribuyenteService.obtenercompensacion(numcontrol, rfc);
                if (selectedSolicitud != null) {
                    numControlDoc = selectedSolicitud.getNumControlDoc();
                    banderaCompensacion = Boolean.TRUE;
                }
            }

            if (bandera1 || bandera2) {
                if (selectedSolicitud != null &&
                    (selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_4 ||
                     banderaCompensacion)) {
                    verBotonSolventar = Boolean.FALSE;
                }
                verBotonAdjuntar = Boolean.FALSE;

            } else {
                verBotonAdjuntar = Boolean.TRUE;
            }
        } catch (SIATException siate) {
            log.error("Hubo un error al seleccionar una solicitud y validar sus status: " + siate);
        }
    }

    /**
     * Lista la informacion de los tramites que se pueden solventar
     */
    public void listarInformacion() {
        verBotonAdjuntar = Boolean.TRUE;
        verBotonSolventar = Boolean.TRUE;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        DataTable table = (DataTable)ComponentUtils.findComponent(facesContext.getViewRoot(), "dtlDocumentos");
        table.reset();
        try {
            listaSolicitudes =
                    this.consultarDevolucionContribuyenteService.listaSolicitudesPorContribuyente(rfc, idSolicitudOCompensacion);
            totalSolicitudes = listaSolicitudes.size();

            if (ConstantesPaginador.NO_COLS_PAGINA_DEV_CONT < totalSolicitudes) {
                this.muestraPaginador = Boolean.TRUE;
            } else {
                this.muestraPaginador = Boolean.FALSE;
            }
        } catch (SIATException siate) {
            log.error("Hubo un error al mostrar las solicitudes a solventar: " + siate);
        }

    }

    /**
     * Muestra los botones de adjuntar archivos a la soicitud
     *
     * @return
     * @throws IOException
     * @throws SIATException
     */
    public String mostrarAdjuntar() throws IOException, SIATException {
        adjuntarDocumentoMB.enviaDatos(selectedSolicitud.getNumControl(),
                                       selectedSolicitud.getDycpServicioDTO().getClaveAdm(),
                                       selectedSolicitud.getDycpServicioDTO().getRfcContribuyente(),
                                       "regresarConsultarDevCont");
        adjuntarDocumentoMB.init();
        return "regresaAdjuntar";
    }

    /**
     * Muestra los botones de solventar requerimiento
     *
     * @return
     * @throws IOException
     * @throws SIATException
     */
    public String mostrarSolventar() throws IOException, SIATException {
        solventarRequerimientoMB.enviaDatos(selectedSolicitud.getNumControl(), 0,
                                            selectedSolicitud.getDycpServicioDTO().getClaveAdm(),
                                            selectedSolicitud.getDycpServicioDTO().getRfcContribuyente(),
                                            numControlDoc, "regresarConsultarDevCont");
        solventarRequerimientoMB.init();
        return "regresaSolventar";
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setConsultarDevolucionContribuyenteService(ConsultarDevolucionContribuyenteService consultarDevolucionContribuyenteService) {
        this.consultarDevolucionContribuyenteService = consultarDevolucionContribuyenteService;
    }

    public ConsultarDevolucionContribuyenteService getConsultarDevolucionContribuyenteService() {
        return consultarDevolucionContribuyenteService;
    }

    public void setListaSolicitudes(List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public List<ConsultarDevolucionesContribuyenteDTO> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setSelectedSolicitud(ConsultarDevolucionesContribuyenteDTO selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }

    public ConsultarDevolucionesContribuyenteDTO getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setTotalSolicitudes(int totalSolicitudes) {
        this.totalSolicitudes = totalSolicitudes;
    }

    public int getTotalSolicitudes() {
        return totalSolicitudes;
    }

    public void setMuestraPaginador(boolean muestraPaginador) {
        this.muestraPaginador = muestraPaginador;
    }

    public boolean isMuestraPaginador() {
        return muestraPaginador;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAdjuntarDocumentoMB(AdjuntarDocumentoMB adjuntarDocumentoMB) {
        this.adjuntarDocumentoMB = adjuntarDocumentoMB;
    }

    public AdjuntarDocumentoMB getAdjuntarDocumentoMB() {
        return adjuntarDocumentoMB;
    }

    public void setSolventarRequerimientoMB(SolventarRequerimientoMB solventarRequerimientoMB) {
        this.solventarRequerimientoMB = solventarRequerimientoMB;
    }

    public SolventarRequerimientoMB getSolventarRequerimientoMB() {
        return solventarRequerimientoMB;
    }

    public void setVerBotonAdjuntar(boolean verBotonAdjuntar) {
        this.verBotonAdjuntar = verBotonAdjuntar;
    }

    public boolean isVerBotonAdjuntar() {
        return verBotonAdjuntar;
    }

    public void setVerBotonSolventar(boolean verBotonSolventar) {
        this.verBotonSolventar = verBotonSolventar;
    }

    public boolean isVerBotonSolventar() {
        return verBotonSolventar;
    }

    public void setFiltro(List<SelectItem> filtro) {
        this.filtro = filtro;
    }

    public List<SelectItem> getFiltro() {
        return filtro;
    }

    public void setIdSolicitudOCompensacion(String idSolicitudOCompensacion) {
        this.idSolicitudOCompensacion = idSolicitudOCompensacion;
    }

    public String getIdSolicitudOCompensacion() {
        return idSolicitudOCompensacion;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
