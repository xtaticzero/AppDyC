package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaDocumentosService;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.model.LazyBandejaDocsAprobadorDataModel;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.model.LazyDataModel;


/**
 * @author Ericka Janth Ibarra Ponce
 * @author Jesus Alfredo Hernandez Orozco
 * @date 12/01/2014
 *
 * */
@ManagedBean(name = "bandejaDocMB")
@ViewScoped
public class BandejaDocMB extends AbstractPage {

    private boolean varBotonCon;
    private String nombreDoc;
    private String numControl;
    private String numEmpleadoStr;
    private String unidad;

    private AccesoUsr accesoUsr;
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    private LazyDataModel<BandejaDocumentosDTO> listaBandejaDoc;
    
    private AdministrarSolicitudesVO administrarSolicitudesVO;

    private Logger log = Logger.getLogger(BandejaDocMB.class.getName());

    @ManagedProperty(value = "#{resumenDevMB}")
    private ResumenDevolucionMB resumenDevMB;

    @ManagedProperty("#{bandejaDocumentosSer}")
    private BandejaDocumentosService bandejaDocumentosSer;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;
    
    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;
    
    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    public BandejaDocMB() {
        nombreDoc = "";
        numEmpleadoStr = "";
        unidad = "";
    }

    @PostConstruct
    public void init() {
        this.accesoUsr = serviceObtenerSesion.execute();
        numEmpleadoStr = accesoUsr.getNumeroEmp();
        unidad = accesoUsr.getLocalidad();
        Utils.validarSesion(accesoUsr, Procesos.DYC00010);
        cargarBandeja();
    }

    public void recargar() {
        cargarBandeja();
    }

    public void cargarBandeja() {
        try {
            setVarBotonCon(Boolean.TRUE);
            setDataModel(new SIATDataModel());
            listaBandejaDoc =
                    new LazyBandejaDocsAprobadorDataModel(bandejaDocumentosSer, calcularFechasService, administrarSolicitudesService, diaHabilService, numEmpleadoStr );

            
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
            resumenDevMB.setEsAbonoNoEfectuado( false );
            resumenDevMB.setEsSinDocumento(Boolean.FALSE);
            resumenDevMB.init();

        } catch (Exception e) {
            log.error("irAresumenDevolucion(): " + e);
        }
        return "resumenDevolucion";
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
        setVarBotonCon(false);
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setVarBotonCon(boolean varBotonCon) {
        this.varBotonCon = varBotonCon;
    }

    public boolean isVarBotonCon() {
        return varBotonCon;
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

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setBandejaDocumentosSer(BandejaDocumentosService bandejaDocumentosSer) {
        this.bandejaDocumentosSer = bandejaDocumentosSer;
    }

    public BandejaDocumentosService getBandejaDocumentosSer() {
        return bandejaDocumentosSer;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public CalcularFechasService getCalcularFechasService() {
        return calcularFechasService;
    }

    public void setListaBandejaDoc(LazyDataModel<BandejaDocumentosDTO> listaBandejaDoc) {
        this.listaBandejaDoc = listaBandejaDoc;
    }

    public LazyDataModel<BandejaDocumentosDTO> getListaBandejaDoc() {
        return listaBandejaDoc;
    }

    public void setResumenDevMB(ResumenDevolucionMB resumenDevMB) {
        this.resumenDevMB = resumenDevMB;
    }

    public ResumenDevolucionMB getResumenDevMB() {
        return resumenDevMB;
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

    public AdministrarSolicitudesVO getAdministrarSolicitudesVO() {
        return administrarSolicitudesVO;
    }

    public void setAdministrarSolicitudesVO(AdministrarSolicitudesVO administrarSolicitudesVO) {
        this.administrarSolicitudesVO = administrarSolicitudesVO;
    }
    
    
    
}
