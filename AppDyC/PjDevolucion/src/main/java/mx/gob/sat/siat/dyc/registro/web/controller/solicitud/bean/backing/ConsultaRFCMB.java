/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido
* uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAprobarDoc;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


/**
 * Managed Bean que corresponde a la vista consultaRFC.jsf
 * @author Paola Rivera
 */
@ManagedBean(name = "consultaRFCMB")
@ViewScoped
public class ConsultaRFCMB {

    private static final Logger LOG = Logger.getLogger(ConsultaRFCMB.class);
    @ManagedProperty(value = "#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;
    @ManagedProperty(value = "#{datosContribuyenteMB}")
    private DatosContribuyenteMB datosContribuyentePage;
    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService dyccMensajeUsrService;
    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService personaIDCService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{validaTramitesService}")
    private ValidaTramitesService serviceValidar;
            
    /**Busqueda y captura de RFC**/
    private String rfcContribuyente;
    private AdmcUnidadAdmvaDTO localidad;
    

    public ConsultaRFCMB() {
        super();
    }

    @PostConstruct
    public void init() {
        LOG.info("INICIA SOLICITUD DE DEVOLUCION POR VENTANILLA");
        rfcContribuyente = null;
        datosContribuyentePage.setRolesContribuyente(new RolesContribuyenteDTO());
        datosContribuyentePage.setTipoFirmado("EMPLEADO");
        try {
            getAdministracion();
        } catch (SIATException e) {
            e.getMessage();
            JSFUtils.messageGlobal("Ocurrio un error al obtener tus datos de session!..", FacesMessage.SEVERITY_ERROR);
        }
    }

    private void getAdministracion() throws SIATException {
        AccesoUsr accesoUsr = getServiceObtenerSesion().execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00006);
        localidad = new AdmcUnidadAdmvaDTO();
        localidad.setClaveSir(Integer.parseInt(accesoUsr.getClaveSir()));
        LOG.info("AccesoUsr.CLABE_SIR:::: " + accesoUsr.getClaveSir());
        try {
            localidad = admcUnidadAdmvaService.consultarUnidadAdmvaList(localidad).get(ConstantesDyC1.CERO);
            LOG.info("DEPENDENCIA: " + localidad.getNombre() + " |USUARIO: " + accesoUsr.getUsuario() +
                     " |EMPLEADO: " + accesoUsr.getNombreCompleto() + " |LOCALIDAD: " + accesoUsr.getLocalidad() +
                     " |CLAVE_SIR: " + localidad.getClaveSir() + " |C_COSTO:" + accesoUsr.getCentroCosto());
        } catch (Exception e) {
            throw new SIATException(e);
        }
    }


    public String validaRFC() throws SIATException {
        LOG.info(" validaRFC");
        if (null == getLocalidad().getClaveSir()) {
            LOG.error("VERIFIQUE LOS DATOS DE SESSION INVALIDOS... ");
            JSFUtils.messageGlobal("VERIFIQUE EL EMPLEADO NO CUENTA CON ALGUNA DEPENDENCIA!... ",
                                   FacesMessage.SEVERITY_ERROR);
            return null;
        }

        boolean alsc = false, alaf = false, agace = false;

        alsc = admcUnidadAdmvaService.isALSC(getLocalidad().getClaveSir());
        alaf = perteneceALAF(getLocalidad().getClaveAgrs());
        agace = getLocalidad().getClaveAgrs().equals(ConstantesDyC2.CENTRO_COSTOS_AGACE);

        if (!agace) {
            if ((ValidaDatosSolicitud.esAsesor(getLocalidad().getClaveSir()) == ConstantesDyCNumerico.VALOR_1) || alsc) {
                LOG.info("NO_EXISTE_INFERENCIA_ASESOR_FISCAL " + getLocalidad().getClaveSir());
            } else {
                LOG.error("NO ES UN ASESOR");
                message(ConstantesIds.MSG_24, FacesMessage.SEVERITY_ERROR);
                return null;
            }
        }
        rfcContribuyente = rfcContribuyente != null ? rfcContribuyente.toUpperCase() : null;
        PersonaDTO persona = personaIDCService.findContribuyente(rfcContribuyente).getOutput();
        
        if (null != persona)
        {
            List<PersonaRolDTO> roles = personaIDCService.buscaRolesPorBoId(persona);
            datosContribuyentePage.getRolesContribuyente().setRoles(roles);
            datosContribuyentePage.setContribuyente(persona);
            if (tipoContribuyenteNoValido(getLocalidad().getClaveSir(), isGranContribuyente(roles), alsc,
                    alaf, persona.getPersonaIdentificacion().getTipoPersona(), agace)) {
                LOG.error("TIPO CONTRIBUYENTE NO VALIDO");
                message(ConstantesIds.MSG_24, FacesMessage.SEVERITY_ERROR);
                return null;
            }

            Map<String, Object> resultValidRoles = serviceValidar.validarRolesContribuyente(roles);
            if(!(Boolean)resultValidRoles.get("success"))
            {
                StringBuilder detalleErrorValid = new StringBuilder();
                for(String inconsist : (List<String>)resultValidRoles.get("inconsistencias")){
                    detalleErrorValid.append(inconsist);
                    detalleErrorValid.append(" - ");
                }
                
                LOG.error("El contribuyente " + rfcContribuyente + " no cumple con los roles minimos requeridos, se interrumpira la captura de la solicitud de devolucion");
                FacesMessage msjContSinRol = new FacesMessage(FacesMessage.SEVERITY_WARN, "El contribuyente " + rfcContribuyente + " no cumple con los roles minimos requeridos ", 
                        detalleErrorValid.toString());

                FacesContext.getCurrentInstance().addMessage(null, msjContSinRol);
                return null;
            }
            
            datosContribuyentePage.initContAseFis();

            
            return ValidaDatosSolicitud.PAGE_CONTRIBUYENTE;
        } else {
            StringBuilder sbMsjNoExiste = new StringBuilder("El RFC '");
            sbMsjNoExiste.append(rfcContribuyente);
            sbMsjNoExiste.append("' no se encuentra registrado, debes ingresar al Servicio de Identificación al Contribuyente para realizar la búsqueda y/o registro correspondiente");

            FacesMessage msjRfcNoExiste = new FacesMessage(FacesMessage.SEVERITY_ERROR, sbMsjNoExiste.toString(), "");
            FacesContext.getCurrentInstance().addMessage(null, msjRfcNoExiste);
            setRfcContribuyente(null);
        }
        return null;
    }

    private boolean tipoContribuyenteNoValido(Integer depedenciaAsesorFiscal, boolean granContrib, boolean alsc,
            boolean isAlaf, String tipoPersona, boolean agace) {
        LOG.info("\nDEP_ASESOR " + depedenciaAsesorFiscal);
        LOG.info("DEP_GC " + granContrib);
        LOG.info("DEP_ALSC " + alsc);
        LOG.info("PERSONA " + tipoPersona);
        if (agace) {
            LOG.info("isTipoAGACE!");
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(ConstanteValidacionRNFDC10.AF_AGACE);
        } else if (ValidaDatosSolicitud.isTipoACDC(depedenciaAsesorFiscal, granContrib)) {
            LOG.info("isTipoACDC!");
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(ConstanteValidacionRNFDC10.AC_DC);
        } else if (ValidaDatosSolicitud.isTipoAGGC(depedenciaAsesorFiscal, granContrib)) {
            LOG.info("isTipoAGGC!");
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(ConstanteValidacionRNFDC10.ACGC);
        } else if (ValidaDatosSolicitud.isTipoALAF(isAlaf, granContrib)) {
            LOG.info("isTipoALAF!");
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(ConstantesAprobarDoc.ALAF);    
        } else if (ValidaDatosSolicitud.isTipoALSC(alsc, isPFoPM(tipoPersona), granContrib)) {
            LOG.info("Asesor:::: ALSC");
        } else {
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(null);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private boolean isPFoPM(String tipoPersona) {
        boolean persona = false;
        if (tipoPersona.equals(ConstantesTipoRol.PF)) {
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(ConstanteValidacionRNFDC10.ALSC);
            persona = Boolean.TRUE;
            LOG.info("PERSONA:::: FISICA");
        } else if (tipoPersona.equals(ConstantesTipoRol.PM)) {
            datosContribuyentePage.getRolesContribuyente().setEsContribuyente(ConstanteValidacionRNFDC10.ALSC_PM);
            persona = Boolean.TRUE;
            LOG.info("PERSONA:::: MORAL");
        }
        return persona;
    }


    private void message(int idMensaje, Severity severity) {
        DyccMensajeUsrDTO message = null;
        try {
            message =
                    getDyccMensajeUsrService().obtieneMensaje(idMensaje, ConstantesIds.CU_REGISTRO_SOLDEVLINEA, ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
        } catch (SIATException e) {
            LOG.info(e.getMessage());
        }
        if (message != null) {
            JSFUtils.messageGlobal(message.getDescripcion(), severity);
        }
    }


    private boolean isGranContribuyente(List<PersonaRolDTO> roles) throws SIATException {
        
        for (PersonaRolDTO rol : roles) {
            if (!ValidaDatosSolicitud.validaFechaBajaRol(rol.getFechaBajaRol())) {
                continue;
            }
            if (rol.getClaveRol() == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PF) {
                return Boolean.TRUE;
            }
            if (rol.getClaveRol() == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PM) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
    
    private boolean perteneceALAF(String cveAgrs) {
        int intCveAgrs = Integer.parseInt(cveAgrs);
        return (intCveAgrs >= ConstantesIds.CENTRO_COSTOS_INI_ADAF && intCveAgrs <= ConstantesIds.CENTRO_COSTOS_FIN_ADAF);
    }

    public void setDatosContribuyentePage(DatosContribuyenteMB datosContribuyentePage) {
        this.datosContribuyentePage = datosContribuyentePage;
    }

    public DatosContribuyenteMB getDatosContribuyentePage() {
        return datosContribuyentePage;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
    }

    public PersonaIDCService getPersonaIDCService() {
        return personaIDCService;
    }

    public void setLocalidad(AdmcUnidadAdmvaDTO localidad) {
        this.localidad = localidad;
    }

    public AdmcUnidadAdmvaDTO getLocalidad() {
        return localidad;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public ValidaTramitesService getServiceValidar() {
        return serviceValidar;
    }

    public void setServiceValidar(ValidaTramitesService serviceValidar) {
        this.serviceValidar = serviceValidar;
    }
}
