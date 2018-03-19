package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.generico.util.ArchivoValida;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumRol;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumTipoAdministracion;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


@ManagedBean(name = "admCasosComp")
@SessionScoped
public class ManagerSesionAdmCasosCompMB {
    private static final Logger LOG = Logger.getLogger(ManagerSesionAdmCasosCompMB.class);

    private EnumRol rol;
    private Integer numEmpleado;
    private Integer claveAdm;
    private String nombreEmpleado;

    private String dictaminador;
    private Integer numDictaminador;
    private Integer nivelAprobador;


    private String numControl;
    private Boolean esGranContribuyente;
    private String rfcContribuyente;

    private String mensaje;
    private String salida;

    private String cargoOrganizacional;
    private EnumTipoAdministracion tipoAdministracion;
    private EnumTipoUnidadAdmvaDyC tipoUnidadAdmva;

    private Integer tipoDocumento;
    private char tipoLiquidacion;

    private List<ItemComboBean> aprobadores;
    private Integer idNumAprob;
    private UploadedFile archivoASubir;
    private StreamedContent archivoDescarga;
    private char estatus;
    private char accButton;
    private String nRule;
    private String nombreArchivoPlantillador;
    private String nombreArchivo;
    private Map<String, Object> paramsLiquida;

    private AccesoUsr accesoUsr;

    private int parametroRegresar;

    private int parametroRegreso;

    private boolean realizarReasignacion = false ;
    
    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceAdmCC;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @PostConstruct
    public void cargarSesion() {
        AccesoUsr accesoUsrL = serviceObtenerSesion.execute();

        LOG.info("accesoUsr ->" + accesoUsrL + "<-");
        LOG.info("roles ->" + accesoUsrL.getRoles() + "<-");
        LOG.info("numeroEmpleado ->" + accesoUsrL.getNumeroEmp() + "<-");
        LOG.info("nombreEmpleado ->" + accesoUsrL.getNombreCompleto() + "<-");
        LOG.info("adminCentral ->" + accesoUsrL.getClaveAdminCentral() + "<-");
        LOG.info("adminGeneral ->" + accesoUsrL.getClaveAdminGral() + "<-");
        LOG.info("tipoAdministracion ->" + accesoUsrL.getClaveAdminGral());

        obtenerRolCasosComp(accesoUsrL.getRoles());
        LOG.info("\n###\n rol ->" + rol + "<-");
        numEmpleado = Integer.parseInt(accesoUsrL.getNumeroEmp());

        nombreEmpleado = accesoUsrL.getNombreCompleto();
        LOG.info("\n###\n ClaveSir ->" + accesoUsrL.getClaveSir() + "<-");
        claveAdm = Integer.parseInt(accesoUsrL.getClaveSir());
        /**tipoAdministracion = obtenerTipoAdministracion(Integer.parseInt(accesoUsrL.getClaveAdminGral()));*/
        tipoUnidadAdmva = serviceAdmCC.obtnerTipoUnidadAdmva(claveAdm);

        aprobadores = serviceAdmCC.obtenerSuperiores(claveAdm, Integer.parseInt(accesoUsrL.getCentroCosto()));
        accesoUsr = accesoUsrL;
    }

    private void obtenerRolCasosComp(String strRoles) {
        if (strRoles.contains(EnumRol.ADMINISTRADOR.getNombre())) {
            rol = EnumRol.ADMINISTRADOR;
        } else if (strRoles.contains(EnumRol.DICTAMINADOR.getNombre())) {
            rol = EnumRol.DICTAMINADOR;
        } else if (strRoles.contains(EnumRol.USUARIO_FISCALIZACION.getNombre())) {
            rol = EnumRol.USUARIO_FISCALIZACION;
        } else {
            rol = null;
        }
    }

    /**
    private EnumTipoAdministracion obtenerTipoAdministracion(int claveAdminGral) {
        LOG.info("INICIO obtenerTipoAdministracion\n\n\nclaveAdminGral ->" + claveAdminGral);
        EnumTipoAdministracion tAdm;
        if (ConstantesDyCNumerico.VALOR_6 == claveAdminGral) {
            tAdm = EnumTipoAdministracion.AGAFF;
        } else if (ConstantesDyCNumerico.VALOR_7 == claveAdminGral) {
            tAdm = EnumTipoAdministracion.ACGC;
        } else {
            tAdm = null;
        }

        return tAdm;
    }
     */

    public void validaArchivoJSF(FileUploadEvent event) {
        archivoASubir = event.getFile();
        this.validaArchivoJSF();
    }

    public void validaArchivoJSF() {
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        ArchivoValida av = new ArchivoValida();
        try {
            LOG.info("####\n" +
                    archivoASubir);
            av.validaciones(archivoASubir, request, nombreArchivoPlantillador, false);
            estatus = 'E';
            nombreArchivo = av.getNombre();
            idNumAprob = ConstantesDyCNumerico.VALOR_0;
        } catch (SIATException e) {
            estatus = 'D';
            FacesContext.getCurrentInstance().addMessage("msgAResol",
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),
                                                                          null));
        }

    }

    public void vistaCargaArchivo() {
        setEstatus('D');
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public EnumRol getRol() {
        return rol;
    }

    public void setRol(EnumRol rol) {
        this.rol = rol;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Boolean getEsGranContribuyente() {
        return esGranContribuyente;
    }

    public void setEsGranContribuyente(Boolean esGranContribuyente) {
        this.esGranContribuyente = esGranContribuyente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCargoOrganizacional() {
        return cargoOrganizacional;
    }

    public void setCargoOrganizacional(String cargoOrganizacional) {
        this.cargoOrganizacional = cargoOrganizacional;
    }

    public EnumTipoAdministracion getTipoAdministracion() {
        return tipoAdministracion;
    }

    public void setTipoAdministracion(EnumTipoAdministracion tipoAdministracion) {
        this.tipoAdministracion = tipoAdministracion;
    }

    public IAdmCasosCompensacionService getServiceAdmCC() {
        return serviceAdmCC;
    }

    public void setServiceAdmCC(IAdmCasosCompensacionService serviceAdmCC) {
        this.serviceAdmCC = serviceAdmCC;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public EnumTipoUnidadAdmvaDyC getTipoUnidadAdmva() {
        return tipoUnidadAdmva;
    }

    public void setTipoUnidadAdmva(EnumTipoUnidadAdmvaDyC tipoUnidadAdmva) {
        this.tipoUnidadAdmva = tipoUnidadAdmva;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getSalida() {
        return salida;
    }

    public void setTipoLiquidacion(char tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    public char getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    public void setAprobadores(List<ItemComboBean> aprobadores) {
        this.aprobadores = aprobadores;
    }

    public List<ItemComboBean> getAprobadores() {
        return aprobadores;
    }

    public void setIdNumAprob(Integer idNumAprob) {
        this.idNumAprob = idNumAprob;
    }

    public Integer getIdNumAprob() {
        return idNumAprob;
    }

    public void setArchivoASubir(UploadedFile archivoASubir) {
        this.archivoASubir = archivoASubir;
    }

    public UploadedFile getArchivoASubir() {
        return archivoASubir;
    }

    public void setEstatus(char estatus) {
        this.estatus = estatus;
    }

    public char getEstatus() {
        return estatus;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setAccButton(char accButton) {
        this.accButton = accButton;
    }

    public char getAccButton() {
        return accButton;
    }

    public void setArchivoDescarga(StreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public StreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setNombreArchivoPlantillador(String nombreArchivoPlantillador) {
        this.nombreArchivoPlantillador = nombreArchivoPlantillador;
    }

    public String getNombreArchivoPlantillador() {
        return nombreArchivoPlantillador;
    }

    public void setParamsLiquida(Map<String, Object> paramsLiquida) {
        this.paramsLiquida = paramsLiquida;
    }

    public Map<String, Object> getParamsLiquida() {
        return paramsLiquida;
    }

    public void setNRule(String nRule) {
        this.nRule = nRule;
    }

    public String getNRule() {
        return nRule;
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setNumDictaminador(Integer numDictaminador) {
        this.numDictaminador = numDictaminador;
    }

    public Integer getNumDictaminador() {
        return numDictaminador;
    }

    public void setNivelAprobador(Integer nivelAprobador) {
        this.nivelAprobador = nivelAprobador;
    }

    public Integer getNivelAprobador() {
        return nivelAprobador;
    }

    public void setParametroRegresar(int parametroRegresar) {
        this.parametroRegresar = parametroRegresar;
    }

    public int getParametroRegresar() {
        return parametroRegresar;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public void setParametroRegreso(int parametroRegreso) {
        this.parametroRegreso = parametroRegreso;
    }

    public int getParametroRegreso() {
        return parametroRegreso;
    }
    
    public void setRealizarReasignacion ( boolean realizarReasignacion ){
        this.realizarReasignacion = realizarReasignacion;
    }
    
    public boolean getRealizarReasignacion (){
        return realizarReasignacion;
    }
}
