package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesConsultarCompensaciones;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;

import org.apache.log4j.Logger;


/**
 * @author J. Isaac Carbajal Bernal
 */
@ManagedBean(name = "administrarSolicitudesDevolucionesMB")
@SessionScoped
public class AdministrarSolicitudesDevolucionMB extends AbstractPage {
    
    private static final Logger LOG = Logger.getLogger(AdministrarSolicitudesDevolucionMB.class);
    
    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService personaIDCService;

    @ManagedProperty(value = "#{agregarFacturasMB}")
    private AgregarFacturaMB agregarFacturasMB;

    @ManagedProperty(value = "#{aprobadorService}")        
    private AprobadorService aprobadorService;

    @ManagedProperty(value = "#{solventarRequerimientoService}")
    private SolventarRequerimientoService solventarRequerimientoService;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    private List<DyctFacturaReqDTO> listaFacturas = new ArrayList<DyctFacturaReqDTO>();
    private String rfc;

    private SolicitudAdministrarSolVO solicitudAdministrarSolVO;
    private DyctFacturaReqDTO dyctFacturaReqSelectedDTO;
    private PersonaDTO personaDTO = new PersonaDTO();
    
    private List<DyccAprobadorDTO> listaJefesSup = new ArrayList<DyccAprobadorDTO>();

    private boolean varBotonNuevaFactura;
    private boolean varBotonEliminarFactura;
    private boolean varBotonGenerarDocumento;
    private boolean varBotonEnviarAAprobacion;
    private boolean varBotonRFC;
    private Date fechaFac;
    private String fechaMax;

    private String generarDocPlantilla;
    private String generarDocNumControl;
    private String generarDocRFC;

    private String numControl;

    private String numEmpleadoStr;
    private int idUnidad;
    private int centroCosto;

    private int numEmp;
    private int idDocumentoSeq;
    
    private int claveAdm;
    
    private boolean paginador;
    
    private String nombreCompleto;

    public AdministrarSolicitudesDevolucionMB() {
        super();
    }

    public void init() {
        varBotonNuevaFactura = Boolean.TRUE;
        varBotonEliminarFactura = Boolean.TRUE;
        varBotonGenerarDocumento = Boolean.TRUE;
        varBotonEnviarAAprobacion = Boolean.TRUE;
        setVarBotonRFC(Boolean.FALSE);
        idDocumentoSeq = ConstantesDyCNumerico.VALOR_0;
        setDataModel(new SIATDataModel());
    }

    public String regresarDictaminar() {

        return "dicSolicitud";

    }


    public void buscarFacturas() {
        varBotonGenerarDocumento = Boolean.TRUE;
        personaDTO.setRfc(rfc.toUpperCase());

        try {
            if (personaIDCService.buscaPersona(personaDTO) != null) {
            boolean tam = Boolean.FALSE;
            setVarBotonRFC(Boolean.TRUE);
            tam = dycpSolicitudService.existenFacturas(numControl, rfc.toUpperCase());

            if (!tam) {
                
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, "No cuenta con facturas",
                                                                              null));  
                listaFacturas.clear();
            } else {
                listaFacturas = dycpSolicitudService.buscarFacturas(numControl, rfc.toUpperCase());

                if(listaFacturas.size() > ConstantesDyCNumerico.VALOR_5) {
                    paginador = Boolean.TRUE;
                } else {
                    paginador = Boolean.FALSE;
                }

                getDataModel().setWrappedData(listaFacturas);
            }
            varBotonNuevaFactura = Boolean.FALSE;
            varBotonEliminarFactura = Boolean.TRUE;
        } else {

            varBotonNuevaFactura = Boolean.TRUE;
          
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: el RFC ingresado no existe, verifique el dato e ingrese nuevamente",
                                                                          null));
                
                
            listaFacturas.clear();
        }
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }

    public String irAAgregarFactura() {
        init();
        
        agregarFacturasMB.setGenerarDocPlantilla(ConstantesConsultarCompensaciones.PLANTILLA_03);
        agregarFacturasMB.setNumControl(numControl);
        agregarFacturasMB.setNumEmpleadoStr(numEmpleadoStr);
        agregarFacturasMB.setNombreCompleto(nombreCompleto);
        agregarFacturasMB.setGenerarDocRFC(generarDocRFC);
        agregarFacturasMB.setIdUnidad(idUnidad);
        agregarFacturasMB.setCentroCosto(centroCosto);
        agregarFacturasMB.setClaveAdm(claveAdm);
        agregarFacturasMB.init();

        return "agregarFactura";
    }
    
    

    /*MABC*/
    public void eliminarFactura() {
        boolean exito = Boolean.FALSE;
        
        exito = dycpSolicitudService.borrarFactura(dyctFacturaReqSelectedDTO.getNumeroFactura()) ;
        
        if (!exito ){
            muestraMensajeError("No fue posible eliminar las facturas del documento,  favor de intentarlo nuevamente");         
        }
        
        varBotonGenerarDocumento = Boolean.FALSE;
        varBotonEnviarAAprobacion = Boolean.TRUE;
        buscarFacturas();
        
    }
    
    public void muestraMensajeError(String error) {
        FacesMessage errorExiste = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", error);
        FacesContext.getCurrentInstance().addMessage(null, errorExiste);
    }
    
    

    public void reativaBtnNuevoDocumento() {

        varBotonNuevaFactura = Boolean.FALSE;
        varBotonEliminarFactura = Boolean.FALSE;
        varBotonGenerarDocumento = Boolean.FALSE;
    }

    public void listaDeAprobacion() {
        listaJefesSup = this.aprobadorService.consultarAprobadores(idUnidad, centroCosto);
    }

    public void comboJefesSup() {

        listaJefesSup = this.aprobadorService.consultarAprobadores(idUnidad, centroCosto);

    }

    public void onRowSelectFactura() {
        varBotonEliminarFactura = Boolean.FALSE;
        varBotonGenerarDocumento = Boolean.FALSE;
        varBotonEnviarAAprobacion = Boolean.FALSE;
    }

    public void cancelar() {
        varBotonEliminarFactura = Boolean.TRUE;
        varBotonGenerarDocumento = Boolean.TRUE;
        this.rfc = "";
        if (listaFacturas.size() != ConstantesDyCNumerico.VALOR_0) {
            listaFacturas.clear();
            
            if(listaFacturas.size() > ConstantesDyCNumerico.VALOR_5) {
                paginador = Boolean.TRUE;
            } else {
                paginador = Boolean.FALSE;
            }
            
            getDataModel().setWrappedData(listaFacturas);
        }
        init();
    }




    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setFechaFac(Date fechaFac) {
        if (fechaFac != null) {
            this.fechaFac = (Date)fechaFac.clone();
        } else {
            this.fechaFac = null;
        }   
    }

    public Date getfechaFac() {
        if (fechaFac != null) {
            return (Date)fechaFac.clone();
        } else {
            return null;
        }   
    }

    public void setVarBotonNuevaFactura(boolean varBotonNuevaFactura) {
        this.varBotonNuevaFactura = varBotonNuevaFactura;
    }

    public boolean isVarBotonNuevaFactura() {
        return varBotonNuevaFactura;
    }

    public void setVarBotonEliminarFactura(boolean varBotonEliminarFactura) {
        this.varBotonEliminarFactura = varBotonEliminarFactura;
    }

    public boolean isVarBotonEliminarFactura() {
        return varBotonEliminarFactura;
    }


    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }


    public void setDyctFacturaReqSelected(DyctFacturaReqDTO dyctFacturaReqDTO) {
        this.dyctFacturaReqSelectedDTO = dyctFacturaReqDTO;
    }

    public DyctFacturaReqDTO getDyctFacturaReqSelected() {
        return dyctFacturaReqSelectedDTO;
    }

    public void setFechaMax(String fechaMax) {
        this.fechaMax = fechaMax;
    }

    public String getFechaMax() {
        return fechaMax;
    }

    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
    }

    public PersonaIDCService getPersonaIDCService() {
        return personaIDCService;
    }

    public void setPersonaDTO(PersonaDTO personaDTO) {
        this.personaDTO = personaDTO;
    }

    public PersonaDTO getPersonaDTO() {
        return personaDTO;
    }
    
    public void setGenerarDocPlantilla(String generarDocPlantilla) {
        this.generarDocPlantilla = generarDocPlantilla;
    }

    public String getGenerarDocPlantilla() {
        return generarDocPlantilla;
    }

    public void setGenerarDocNumControl(String generarDocNumControl) {
        this.generarDocNumControl = generarDocNumControl;
    }

    public String getGenerarDocNumControl() {
        return generarDocNumControl;
    }

    public void setGenerarDocRFC(String generarDocRFC) {
        this.generarDocRFC = generarDocRFC;
    }

    public String getGenerarDocRFC() {
        return generarDocRFC;
    }
    
    public void setVarBotonGenerarDocumento(boolean varBotonGenerarDocumento) {
        this.varBotonGenerarDocumento = varBotonGenerarDocumento;
    }

    public boolean isVarBotonGenerarDocumento() {
        return varBotonGenerarDocumento;
    }

    public void setListaJefesSup(List<DyccAprobadorDTO> listaJefesSup) {
        this.listaJefesSup = listaJefesSup;
    }

    public List<DyccAprobadorDTO> getListaJefesSup() {
        return listaJefesSup;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setVarBotonEnviarAAprobacion(boolean varBotonEnviarAAprobacion) {
        this.varBotonEnviarAAprobacion = varBotonEnviarAAprobacion;
    }

    public boolean isVarBotonEnviarAAprobacion() {
        return varBotonEnviarAAprobacion;
    }


    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setAgregarFacturasMB(AgregarFacturaMB agregarFacturasMB) {
        this.agregarFacturasMB = agregarFacturasMB;
    }

    public AgregarFacturaMB getAgregarFacturasMB() {
        return agregarFacturasMB;
    }

    public void setListaFacturas(List<DyctFacturaReqDTO> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    public List<DyctFacturaReqDTO> getListaFacturas() {
        return listaFacturas;
    }

    public void setNumEmp(int numEmp) {
        this.numEmp = numEmp;
    }

    public int getNumEmp() {
        return numEmp;
    }

    public void setIdDocumentoSeq(int idDocumentoSeq) {
        this.idDocumentoSeq = idDocumentoSeq;
    }

    public int getIdDocumentoSeq() {
        return idDocumentoSeq;
    }

    public void setSolventarRequerimientoService(SolventarRequerimientoService solventarRequerimientoService) {
        this.solventarRequerimientoService = solventarRequerimientoService;
    }

    public SolventarRequerimientoService getSolventarRequerimientoService() {
        return solventarRequerimientoService;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }


    public boolean isVarBotonRFC() {
        return varBotonRFC;
    }

    public void setVarBotonRFC(boolean varBotonRFC) {
        this.varBotonRFC = varBotonRFC;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }
    
    

    public void setSolicitudAdministrarSolVO(SolicitudAdministrarSolVO solicitudAdministrarSolVO) {
        this.solicitudAdministrarSolVO = solicitudAdministrarSolVO;
    }

    public SolicitudAdministrarSolVO getSolicitudAdministrarSolVO() {
        return solicitudAdministrarSolVO;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public AprobadorService getAprobadorService() {
        return aprobadorService;
    }

    public void setCentroCosto(int centroCosto) {
        this.centroCosto = centroCosto;
    }

    public int getCentroCosto() {
        return centroCosto;
    }
}
