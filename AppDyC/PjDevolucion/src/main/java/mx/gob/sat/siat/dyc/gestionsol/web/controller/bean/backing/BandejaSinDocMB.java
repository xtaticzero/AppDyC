/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaSinDocumentosService;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.model.LazyBandejaSinDocAprobadorDataModel;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author root
 */
@ManagedBean(name = "bandejaSinDocMB")
@ViewScoped
public class BandejaSinDocMB extends AbstractPage {

    private boolean varBotonCon;
    private String nombreDoc;
    private String numControl;
    private String numEmpleadoStr;
    private String unidad;

    private AccesoUsr accesoUsr;
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    private LazyDataModel<BandejaDocumentosDTO> listaBandejaDoc;

    private AdministrarSolicitudesVO administrarSolicitudesVO;

    private static final String ADM_APROBADOR = "SAT_DYC_ADMIN_APRO";

    private Logger log = Logger.getLogger(BandejaSinDocMB.class.getName());

    @ManagedProperty(value = "#{resumenDevMB}")
    private ResumenDevolucionMB resumenDevMB;

    @ManagedProperty("#{bandejaSinDocumentosService}")
    private BandejaSinDocumentosService bandejaSinDocumentosSer;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    public BandejaSinDocMB() {
        nombreDoc = "";
        numEmpleadoStr = "";
        unidad = "";
    }

    @PostConstruct
    public void init() {
        log.info("BandejaSinDocMB init() #######################################");
        this.accesoUsr = serviceObtenerSesion.execute();
        numEmpleadoStr = accesoUsr.getNumeroEmp();
        unidad = accesoUsr.getLocalidad();
        validaAdminAprbador();
    }

    private void validaAdminAprbador() {

        log.info("validaAdminAprbador" + accesoUsr.getRoles());
        log.info("validaAdminAprbador" + accesoUsr.getProcesos());

        if (accesoUsr.getRoles().contains(ADM_APROBADOR)) {

            cargarBandejaSinDoc();
        } else {
            HttpSession session = null;
            try {
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
                log.error("Se intenta ingresar a un proceso no asignado. RFC:" + accesoUsr.getRfcCorto() + ", ruta: "
                        + ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());
                session.setAttribute("tipo", "2");
                session.setAttribute("error", "No se tiene permisos suficientes para acceder a este proceso");

                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                        + "/faces/resources/pages/errores/errorProceso.jsf");

            } catch (Exception e) {
                log.error("Error al direccionar a la pantalla de error.");
            }
        }
    }

    public void recargar() {
        cargarBandejaSinDoc();
    }

    public void cargarBandejaSinDoc() {
        try {
            log.info("cargarBandejaSinDoc()");
            setVarBotonCon(Boolean.TRUE);
            setDataModel(new SIATDataModel());
            listaBandejaDoc
                    = new LazyBandejaSinDocAprobadorDataModel(bandejaSinDocumentosSer, calcularFechasService, administrarSolicitudesService, diaHabilService, numEmpleadoStr);

        } catch (Exception e) {
            log.error(e.getCause());
        }
    }

    public String irAresumenDevolucion() {
        try {
            numControl = bandejaDocumentosSolDTO.getNumControl();
            nombreDoc = bandejaDocumentosSolDTO.getNombreDocumento();
            resumenDevMB.setNumControl(numControl);
            resumenDevMB.setNombreDoc(nombreDoc);
            resumenDevMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            resumenDevMB.setAccesoUsr(accesoUsr);
            resumenDevMB.setBanderaAprobarORevisionCentral(false);
            resumenDevMB.setEsAbonoNoEfectuado(false);
            resumenDevMB.setEsSinDocumento(Boolean.TRUE);
            resumenDevMB.init();
        } catch (Exception e) {
            log.error("irAresumenDevolucion(): " + e);
        }
        return "resumenDevolucion";
    }

    public boolean isVarBotonCon() {
        return varBotonCon;
    }

    public void setVarBotonCon(boolean varBotonCon) {
        this.varBotonCon = varBotonCon;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
        setVarBotonCon(false);
    }

    public LazyDataModel<BandejaDocumentosDTO> getListaBandejaDoc() {
        return listaBandejaDoc;
    }

    public void setListaBandejaDoc(LazyDataModel<BandejaDocumentosDTO> listaBandejaDoc) {
        this.listaBandejaDoc = listaBandejaDoc;
    }

    public AdministrarSolicitudesVO getAdministrarSolicitudesVO() {
        return administrarSolicitudesVO;
    }

    public void setAdministrarSolicitudesVO(AdministrarSolicitudesVO administrarSolicitudesVO) {
        this.administrarSolicitudesVO = administrarSolicitudesVO;
    }

    public ResumenDevolucionMB getResumenDevMB() {
        return resumenDevMB;
    }

    public void setResumenDevMB(ResumenDevolucionMB resumenDevMB) {
        this.resumenDevMB = resumenDevMB;
    }

    public BandejaSinDocumentosService getBandejaSinDocumentosSer() {
        return bandejaSinDocumentosSer;
    }

    public void setBandejaSinDocumentosSer(BandejaSinDocumentosService bandejaSinDocumentosSer) {
        this.bandejaSinDocumentosSer = bandejaSinDocumentosSer;
    }

    public CalcularFechasService getCalcularFechasService() {
        return calcularFechasService;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

}
