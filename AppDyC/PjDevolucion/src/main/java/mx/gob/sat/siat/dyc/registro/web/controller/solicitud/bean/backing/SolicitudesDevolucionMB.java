/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * Managed Bean que corresponde a la vista SOLICITUDES TEMPORALES
 *
 * @author JEFG
 */
@ManagedBean(name = "solicitudesDevolucionMB")
@ViewScoped
public class SolicitudesDevolucionMB extends AbstractPage {
    private static final Logger LOGGER = Logger.getLogger(SolicitudesDevolucionMB.class);
    @ManagedProperty(value = "#{registraSolDevService}")
    private RegistraSolDevService registraSolDevService;
    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService personaIDCService;
    @ManagedProperty(value = "#{dyccMensajeUsrService}")
    private DyccMensajeUsrService dyccMensajeUsrService;
    @ManagedProperty(value = "#{datosContribuyenteMB}")
    private DatosContribuyenteMB datosContribuyentePage;
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private AccesoUsr accesoUsr;
    private boolean error;
    private String messageInfo;

    /**
     *
     */
    @PostConstruct
    public void init() {
        LOGGER.info("INICIA REGISTRO DE SOLICITUD DE DEVOLUCION POR CONTRIBUYENTE");
        accesoUsr = serviceObtenerSesion.execute();
        if (null != accesoUsr) {
            LOGGER.info("ENTRO EN SESSION EL CONTRIBUYENTE=> "
                    + accesoUsr.getUsuario()
                    + " "
                    + (accesoUsr.getTipoAuth().equals("1") ? "AUTENTICADO_POR_CONTRASEÑA"
                            : "AUTENTICADO_POR_FIEL"));
            LOGGER.info("NOMBRE: " + this.accesoUsr.getNombreCompleto());
            this.setDataModel(new SIATDataModel<Serializable>());
            this.setResultList(false);
        } else {
            LOGGER.error("ERROR AL RECIBIR LA SESIÓN DEL PORTAL: "
                    + ExtractorUtil.ejecutar(accesoUsr));
            setError(Boolean.TRUE);
        }
    }

    /**
     *
     * @throws SIATException
     */
    public void findContribuyente() throws SIATException {
        PersonaDTO contribuyente = getPersonaIDCService().findContribuyente(
                this.accesoUsr.getUsuario()).getOutput();
        if (null != contribuyente) {
            datosContribuyentePage.setContribuyente(validaDatos(contribuyente));

            datosContribuyentePage.getRolesContribuyente().setRoles(
                    personaIDCService.buscaRolesPorBoId(contribuyente));
            this.datosContribuyentePage.init();
        } else {
            LOGGER.error("Datos de session invalidos para repositorio IDC: "
                    + accesoUsr.getUsuario());
            setError(Boolean.TRUE);
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

    /**
     * 
     * @param registraSolDevService 
     */
    public void setRegistraSolDevService(RegistraSolDevService registraSolDevService) {
        this.registraSolDevService = registraSolDevService;
    }

    /**
     *
     * @return
     */
    public RegistraSolDevService getRegistraSolDevService() {
        return registraSolDevService;
    }

    /**
     *
     * @param datosContribuyentePage
     */
    public void setDatosContribuyentePage(DatosContribuyenteMB datosContribuyentePage) {
        this.datosContribuyentePage = datosContribuyentePage;
    }

    /**
     *
     * @return
     */
    public DatosContribuyenteMB getDatosContribuyentePage() {
        return datosContribuyentePage;
    }

    /**
     *
     * @param accesoUsr
     */
    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    /**
     *
     * @return
     */
    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    /**
     *
     * @param personaIDCService
     */
    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
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
     * @param error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    public boolean isError() {
        return error;
    }

    /**
     *
     * @param dyccMensajeUsrService
     */
    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    /**
     *
     * @return
     */
    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    /**
     *
     * @param messageInfo
     */
    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    /**
     *
     * @return
     */
    public String getMessageInfo() {
        return messageInfo;
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
}
