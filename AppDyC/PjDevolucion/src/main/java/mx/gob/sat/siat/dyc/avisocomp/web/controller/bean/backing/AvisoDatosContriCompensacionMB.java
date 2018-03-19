package mx.gob.sat.siat.dyc.avisocomp.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.periodovac.PeriodoVacService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccConceptoService;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.impl.ContribuyenteServiceImpl;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAvisoCompensacion;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsTiposPersona;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaAdministracion;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


@ManagedBean(name = "avisoDatosContriCompensacionMB")
@ViewScoped
public class AvisoDatosContriCompensacionMB
{
    private Logger log = Logger.getLogger(AvisoDatosContriCompensacionMB.class);

    @ManagedProperty(value = "#{avisoCompensacionEnLineaMB}")
    private AvisoCompensacionEnLineaMB mbAvisoComp;

    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService serviceIDC;

    @ManagedProperty(value = "#{insertaContribuyenteServiceImpl}")
    private ContribuyenteServiceImpl serviceContte;

    @ManagedProperty(value = "#{dyccConceptoService}")
    private DyccConceptoService dyccConceptoService;
    
    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService consultarDyccMensajeUsrService;
    
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{periodoVacService}")
    private PeriodoVacService periodoVacService;
     
    private AccesoUsr accesoUsr;
    private Mensaje mensaje;
    private String mensajeP = "";
    private PersonaDTO contribuyente;
    private RolesContribuyenteDTO roles;
    private List<PersonaRolDTO> listaRoles;
    private boolean datosCorrectos = Boolean.TRUE;
    private Integer tipoRol;
    private boolean asPeriodo;

    @PostConstruct
    public void init() {
         
         validarPeriodo();
        accesoUsr = serviceObtenerSesion.execute();
        
        contribuyente = new PersonaDTO();
        contribuyente.setRfc(accesoUsr.getUsuario());
        tipoRol = ConstantesDyCNumerico.VALOR_0;
        mostrarDatosContribuyente();
    }

    public void mostrarDatosContribuyente()
    {
        try {
            contribuyente = serviceIDC.buscaPersona(contribuyente);
            if (contribuyente != null) {                
                contribuyente.setPersonaIdentificacion(serviceIDC.buscaPersonaBoId(contribuyente));
                validarDatosContribuyente();
                contribuyente.setDomicilio(serviceIDC.buscaUbicacion(contribuyente));
                listaRoles = serviceIDC.buscaRolesPorBoId(contribuyente);
                roles = serviceContte.rolesContribuyente(listaRoles);
                roles.setRoles(listaRoles);
                if (roles.isPersonaFisica()) {
                    tipoRol = IdsTiposPersona.PERSONA_FISICA;
                } else if (roles.isPersonaMoral()) {
                    tipoRol = IdsTiposPersona.PERSONA_MORAL;
                } else if (roles.isSociedadControladora()) {
                    tipoRol = ConstantesValidaAdministracion.SOC_MERCAN_CONTROL;
                }
            }
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }
    
    private void validarDatosContribuyente() {
        if(contribuyente.getPersonaIdentificacion() != null){
            if(contribuyente.getPersonaIdentificacion().getNombre() == null) {
                contribuyente.getPersonaIdentificacion().setNombre("");
            }
            if(contribuyente.getPersonaIdentificacion().getApPaterno() == null) {
                contribuyente.getPersonaIdentificacion().setApPaterno("");
            }
            if(contribuyente.getPersonaIdentificacion().getApMaterno() == null) {
                contribuyente.getPersonaIdentificacion().setApMaterno("");
            }
        }
    }

    public String confirmarDatos()
    {
        validarPeriodo();
        if(asPeriodo)
        {
        return "goConfirmarDatosContribuyente";
        }
            
            
        log.debug("------------------------------ MUESTRA LOS DATOS DEL CONTRIBUYENTE -----------------------------");
        try {
            if (datosCorrectos) {
                mbAvisoComp.setAccesoUsr(accesoUsr);
                mbAvisoComp.setContribuyente(contribuyente);
                mbAvisoComp.setRoles(roles);
                mbAvisoComp.setListaRoles(listaRoles);
                mbAvisoComp.ejecutaDatosContribuyente();
                mbAvisoComp.setListaConceptos(mostrarListaConceptos(tipoRol));
                mbAvisoComp.confirmarDatos();
                roles = new RolesContribuyenteDTO();
                listaRoles = new ArrayList<PersonaRolDTO>();
                return "goRegistrarAvisoCompensacionEnLinea";
            } else {
                mensaje.addInfo(ConstantesAvisoCompensacion.MSG_GENERAL,
                        consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_1, ConstantesMensajes.CU_53).getDescripcion());
            }
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public List<DyccConceptoDTO> mostrarListaConceptos(int tipoRol) {
        return dyccConceptoService.obtenerConceptosPorTipoRol(tipoRol);
    }

    public void setServiceIDC(PersonaIDCService personaIDCService) {
        this.serviceIDC = personaIDCService;
    }

    public PersonaIDCService getServiceIDC() {
        return serviceIDC;
    }

    public void setServiceContte(ContribuyenteServiceImpl insertaContribuyenteServiceImpl) {
        this.serviceContte = insertaContribuyenteServiceImpl;
    }

    public ContribuyenteServiceImpl getServiceContte() {
        return serviceContte;
    }

    public void setContribuyente(PersonaDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public PersonaDTO getContribuyente() {
        return contribuyente;
    }

    public void setRoles(RolesContribuyenteDTO roles) {
        this.roles = roles;
    }

    public RolesContribuyenteDTO getRoles() {
        return roles;
    }

    public void setListaRoles(List<PersonaRolDTO> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<PersonaRolDTO> getListaRoles() {
        return listaRoles;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setDatosCorrectos(boolean datosCorrectos) {
        this.datosCorrectos = datosCorrectos;
    }

    public boolean isDatosCorrectos() {
        return datosCorrectos;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMbAvisoComp(AvisoCompensacionEnLineaMB avisoCompensacionEnLineaMB) {
        this.mbAvisoComp = avisoCompensacionEnLineaMB;
    }

    public AvisoCompensacionEnLineaMB getMbAvisoComp() {
        return mbAvisoComp;
    }

    public void setDyccConceptoService(DyccConceptoService dyccConceptoService) {
        this.dyccConceptoService = dyccConceptoService;
    }

    public DyccConceptoService getDyccConceptoService() {
        return dyccConceptoService;
    }

    public void setConsultarDyccMensajeUsrService(DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public PeriodoVacService getPeriodoVacService() {
        return periodoVacService;
    }

    public void setPeriodoVacService(PeriodoVacService periodoVacService) {
        this.periodoVacService = periodoVacService;
    }

    public String getMensajeP() {
        return mensajeP;
    }

    public void setMensajeP(String mensajeP) {
        this.mensajeP = mensajeP;
    }

    public Integer getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(Integer tipoRol) {
        this.tipoRol = tipoRol;
    }

    public boolean isAsPeriodo() {
        return asPeriodo;
    }

    public void setAsPeriodo(boolean asPeriodo) {
        this.asPeriodo = asPeriodo;
    }

 public void validarPeriodo()
 {
     try {
             asPeriodo = false;
                mensaje = new Mensaje();
                DycpPeriodoVacDTO dTO = periodoVacService.buscaPeriodoVacActivo();
                
                if(dTO != null && dTO.getMensaje()!=null)
                {                
                    mensajeP = dTO.getMensaje();
                    asPeriodo = true;
                    
                }
               } catch (SIATException ex) {
                log.error(ex); 
               }
 }
    
}
