/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.util.Date;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;



import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.periodovac.PeriodoVacService;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



/**
 * Managed Bean que corresponde a la vista datosContribuyenteMB
 *
 * @author Paola Rivera.
 */
@ManagedBean(name = "datosContribuyenteMB")
@SessionScoped
public class DatosContribuyenteMB  {
    
    private static final Logger LOG = Logger.getLogger(DatosContribuyenteMB.class);
    private static final String SI = "1";

    private PersonaDTO contribuyente;
    private String sonDatos;
    private String tipoFirmado;
    private RolesContribuyenteDTO rolesContribuyente;
    private SolicitudDevolucionRegistroVO solDevRegistro;
    private boolean isSolTem;
    private AccesoUsr accesoUsr;
    private Mensaje mensaje;
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private transient ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{personaIDCService}")
    private transient PersonaIDCService personaIDCService;

    @ManagedProperty("#{periodoVacService}")
    private PeriodoVacService periodoVacService;
     
    private String mensajeP = "";
    private boolean asPeriodo;  
      
    /**
     *
     */
    public DatosContribuyenteMB() {
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        LOG.debug("DatosContribuyenteMB INIT");
        accesoUsr = serviceObtenerSesion.execute();
        mensaje = new Mensaje();
        asPeriodo = false;
        if (null != accesoUsr) 
        {    
          
            validarPeriodoInicio();
            
            setRolesContribuyente(new RolesContribuyenteDTO());
            getRolesContribuyente().setEsContribuyente(ValidaDatosSolicitud.CONTRIBUYENTE);
            setTipoFirmado(accesoUsr.getTipoAuth().equals("1") ? "FIRMAR_FIEL" : "AUTENTICADO");
            setIsSolTem(Boolean.FALSE);

            try {
                findContribuyente();
            } catch (SIATException ex) {
                LOG.error(ex);
            }
            sonDatos = SI;
            actualizarBanderasRolesContribuyente();
        }
        
        
        
   
        /**
         * Se omoite implemetacion de Buzon Tributario 9/6/15 "GOFC780624AF3"
         * try { BuzonNotificacionesSoapProxy buzonWS = new
         * BuzonNotificacionesSoapProxy(); BuzonNotificacionesSoap medio =
         * buzonWS.getBuzonNotificacionesSoap();
         * 
         * MedioComunicacion medios[] =
         * medio.obtieneMediosComunicacion(contribuyente.getRfcVigente());
         * MedioComunicacion respuesta = medios[0]; if (null !=
         * respuesta.getMedio() && respuesta.getMedio().equals("")) {
         * setMail(null); } else { setAmparado(1 != respuesta.getAmparado() ?
         * " | No amparado" : " | Amparado"); setMail(respuesta.getMedio());
         * getRolesContribuyente().setAmparado(1 != respuesta.getAmparado() ?
         * false : true); LOG.info("BUZON TRIBUTARIO::::" + this.getMail() + " " 
         * + this.getAmparado() + respuesta.getAmparado()); } } catch (Exception
         * e) { LOG.info("Error al consultar Buzon: " + e.getMessage());
         * setShowButton(Boolean.TRUE); }
         */
    }
    
    public void initContAseFis() throws SIATException
    {
        LOG.debug("DatosContribuyenteMB INIT");
        sonDatos = SI;
        actualizarBanderasRolesContribuyente();
        
    }


    /**
     *
     * @throws SIATException
     */
    public void findContribuyente() throws SIATException {
        PersonaDTO contribuyenteDato = personaIDCService.findContribuyente(
                this.accesoUsr.getUsuario()).getOutput();
        if (null != contribuyenteDato) {
            setContribuyente(validaDatos(contribuyenteDato));
            getRolesContribuyente()
                    .setRoles(personaIDCService.buscaRolesPorBoId(contribuyenteDato));
        } else {
            LOG.error("Datos de session invalidos para repositorio IDC: " + accesoUsr.getUsuario());
        }
    }

    private PersonaDTO validaDatos(PersonaDTO contribuyente) {
        contribuyente
                .getDomicilio()
                .setTelefono1(
                        StringUtils.isNotBlank(contribuyente.getDomicilio().getTelefono1()) ? ValidaDatosSolicitud
                                .cadenaMovil(contribuyente.getDomicilio().getTelefono1()) : null);
        contribuyente
                .getDomicilio()
                .setTelefono2(
                        StringUtils.isNotBlank(contribuyente.getDomicilio().getTelefono2()) ? ValidaDatosSolicitud
                                .cadenaMovil(contribuyente.getDomicilio().getTelefono2()) : null);
        return contribuyente;
    }

    private void actualizarBanderasRolesContribuyente() {
        for (PersonaRolDTO rol : rolesContribuyente.getRoles()) {
            LOG.debug("ROL: " + rol.getClaveRol() + " descripcionRol:" + rol.getDescripcionRol()
                    + " fechaAltaRol:" + rol.getFechaAltaRol() + " fechaBajaRol:"
                    + rol.getFechaBajaRol());
            if (rolActivo(rol.getFechaBajaRol())) {
                actualizarBanderaRol(rol.getClaveRol());
            }
        }
    }

    private void actualizarBanderaRol(int rol) {
        if (rol == ConstantesTipoRol.PERSONA_MORAL || rol == ConstantesTipoRol.ROL_PERSONA_MORAL) {
            rolesContribuyente.setPersonaMoral(Boolean.TRUE);
        } else if (rol == ConstantesTipoRol.PERSONA_FISICA || rol == ConstantesTipoRol.ROL_PERSONA_FISICA) {
            rolesContribuyente.setPersonaFisica(Boolean.TRUE);
        } else if (rol == ConstantesIds.SOC_MERCAN_CONTROL || rol == ConstantesIds.GC_ACFECF_PM) {
            rolesContribuyente.setSociedadControladora(Boolean.TRUE);
        } else if (rol == ConstantesTipoRol.ESTADO_EXTRANJERO) {
            rolesContribuyente.setEstadoExtranjero(Boolean.TRUE);
        } else if (isGranContribuyente(rol)) {
            rolesContribuyente.setGranContribuyente(Boolean.TRUE);
        } else if (isDictaminador(rol)) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        } else if (rol == ConstantesIds.INTEGRADORA_PM) {
            rolesContribuyente.setIntegradoraPM(Boolean.TRUE);
        }
    }

    private boolean isGranContribuyente(int rol) {
        if (rol == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PF) {
            return Boolean.TRUE;
        }
        if (rol == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PM) {
            return Boolean.TRUE;
        }
        return false;
    }

    private boolean isDictaminador(int rol) {
        if (rol == ConstantesIds.CONTRIBUYENTE_DICTAMIN_PF) {
            return Boolean.TRUE;
        }

        if (rol == ConstantesIds.DICTAMINADO_OPCIONAL_PF) {
            return Boolean.TRUE;
        }
        if (rol == ConstantesIds.OBLIGADO_DICTAMEN_PF) {
            return Boolean.TRUE;
        }
        return isDictaminadorPM(rol);
    }

    private boolean isDictaminadorPM(int rol) {
        if (rol == ConstantesIds.OBLIGADO_DICTAMEN_PM) {
            return Boolean.TRUE;
        } else if (rol == ConstantesIds.DICTAMINADO_OPCIONAL_PM) {
            return Boolean.TRUE;
        } else if (rol == ConstantesIds.CONTRIBUYENTE_DICTAMIN_PM) {
            return Boolean.TRUE;
        }
        return false;
    }

    private boolean rolActivo(Date fechaBaja) {
        if (null != fechaBaja && fechaBaja.before(new Date())) {
            return false;
        }

        return Boolean.TRUE;
    }

    /**
     *
     * @param contribuyente
     */
    public void setContribuyente(PersonaDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    /**
     *
     * @return
     */
    public PersonaDTO getContribuyente() {
        return contribuyente;
    }

    /**
     *
     * @param sonDatos
     */
    public void setSonDatos(String sonDatos) {
        this.sonDatos = sonDatos;
    }

    /**
     *
     * @return
     */
    public String getSonDatos() {
        return sonDatos;
    }

    /**
     *
     * @param tipoFirmado
     */
    public void setTipoFirmado(String tipoFirmado) {
        this.tipoFirmado = tipoFirmado;
    }

    /**
     *
     * @return
     */
    public String getTipoFirmado() {
        return tipoFirmado;
    }

    /**
     *
     * @param solDevRegistro
     */
    public void setSolDevRegistro(SolicitudDevolucionRegistroVO solDevRegistro) {
        this.solDevRegistro = solDevRegistro;
    }

    /**
     *
     * @return
     */
    public SolicitudDevolucionRegistroVO getSolDevRegistro() {
        return solDevRegistro;
    }

    /**
     *
     * @param rolesContribuyente
     */
    public void setRolesContribuyente(RolesContribuyenteDTO rolesContribuyente) {
        this.rolesContribuyente = rolesContribuyente;
    }

    /**
     *
     * @return
     */
    public RolesContribuyenteDTO getRolesContribuyente() {
        return rolesContribuyente;
    }

    /**
     *
     * @param isSolTem
     */
    public void setIsSolTem(boolean isSolTem) {
        this.isSolTem = isSolTem;
    }

    /**
     *
     * @return
     */
    public boolean isIsSolTem() {
        return isSolTem;
    }

    /**
     *
     * @return
     */
    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    /**
     *
     * @param serviceObtenerSesion
     */
    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    /**
     *
     * @return
     */
    public PersonaIDCService getPersonaIDCService() {
        return personaIDCService;
    }

    /**
     *
     * @param personaIDCService
     */
    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
    }

    public PeriodoVacService getPeriodoVacService() {
        return periodoVacService;
    }

    public void setPeriodoVacService(PeriodoVacService periodoVacService) {
        this.periodoVacService = periodoVacService;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeP() {
        return mensajeP;
    }

    public void setMensajeP(String mensajeP) {
        this.mensajeP = mensajeP;
    }

    public boolean isAsPeriodo() {
        return asPeriodo;
    }

    public void setAsPeriodo(boolean asPeriodo) {
        this.asPeriodo = asPeriodo;
    }


     public void validarPeriodoInicio()
    {
         try 
         {      
                
                DycpPeriodoVacDTO dTO = periodoVacService.buscaPeriodoVacActivo();
                if(dTO != null && dTO.getMensaje()!=null)
         
                {
                    
                    mensajeP = dTO.getMensaje();
                    asPeriodo = true;
                    
                    
                }
               } 
         catch (SIATException ex) 
         {
                LOG.error(ex);
         }
    }
    
}
