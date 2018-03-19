package mx.gob.sat.siat.dyc.util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


public final class JSFUtils {
    private static final Logger LOG = Logger.getLogger(JSFUtils.class);    
    /**
     * Constructor privado puesto que es una
     * clase Utils.
     */
    private JSFUtils() {
    }

    /**
     * Regresa objeto de session para el contribuyente(acceso)
     *
     * */
    public static AccesoUsr getUsrContribuyente() {
        return (AccesoUsr)getSessionMap().get(ConstantesDyC2.TIPO_ACCESO_CONT);
    }

    /**
     * Regresa objeto de session para el Asesor Fiscal(accesoEF)
     *
     * */
    public static AccesoUsr getUsrEmpleado() {
        return obtieneAdministracion((AccesoUsr)getSessionMap().get(ConstantesDyC2.TIPO_ACCESO_EMPL));
    }

    private static AccesoUsr obtieneAdministracion(AccesoUsr accesoUsr) {      
        if(accesoUsr==null || accesoUsr.getCentroCostoOp()==null){
            LOG.info("El accesoUsr de sesion es nulo.");
            return accesoUsr;
        }
        
        LOG.info("Clave Admin Central: "+accesoUsr.getClaveAdminCentral());
        if (accesoUsr.getClaveAdminCentral() == null || accesoUsr.getClaveAdminCentral().equals("")) {
                accesoUsr.setClaveAdminCentral("0000000");
                LOG.info("Se cambia la Clave Admin Central");
        }
        
        if (accesoUsr.getCentroCostoOp().equals("900")) {
            if (accesoUsr.getClaveAdminCentral().contains("900I") ||
                accesoUsr.getClaveAdminCentral().contains("1GG") || 
                accesoUsr.getClaveAdminCentral().contains("1GC") ||
                accesoUsr.getClaveAdminCentral().contains("700")) {
                accesoUsr.setClaveSir("97");
            } else if (accesoUsr.getClaveAdminCentral().contains("900F")) {
                accesoUsr.setClaveSir("91");
            } else {
                accesoUsr.setClaveSir("90");
            }
        }
        else if  (accesoUsr.getCentroCostoOp().equals("199")) {
            try{
                if (accesoUsr.getClaveAdminCentral().charAt(ConstantesDyC1.TRES) == 'B') {
                    accesoUsr.setClaveSir("81");
                } else 
                if (accesoUsr.getClaveAdminCentral().charAt(ConstantesDyC1.TRES) == 'C') {
                    accesoUsr.setClaveSir("82");
                } else {
                    accesoUsr.setClaveSir("81");
                }
            }catch(StringIndexOutOfBoundsException siobe){
                accesoUsr.setClaveSir("81");
                LOG.error("Ocurrio un error en accesoUsr.getClaveAdminCentral().charAt(3) con la clave: "+accesoUsr.getClaveAdminCentral());
                LOG.error(siobe.getCause());
                ManejadorLogException.getTraceError(siobe);
            }
            LOG.info("Se asigno la clave "+accesoUsr.getClaveSir()+" al usuario "+accesoUsr.getNombreCompleto()+" con centro costo 199");
        } 
        return accesoUsr;
    }

    /**
     * Regresa la Instancia de la actual Aplicacion JSF
     *
     * @return La objeto <code>Application</code>
     */
    public static Application getApplication() {
        return getFacesContext().getApplication();
    }

    /**
     * Regresa un Map de los atributos de la aplicacion para esta aplicacion
     * web.
     *
     * @return El objeto <code>Map</code> con los atributos de la
     *         aplicaci&oacute;n.
     */
    public static Map<?, ?> getApplicationMap() {
        return getExternalContext().getApplicationMap();
    }

    /**
     * Regresa la instancia del <code>ExternalContext</code> para esta peticion.
     *
     * @return El objecto <code>ExternalContext</code>
     */
    public static ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    /**
     * Regresa la Instancia <code>FacesContext</code> de la actual peticion
     *
     * @return el objecto <code>FacesContext</code>
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Regresa un Map de los atributos de la petici&oacute;n actual.
     *
     * @return El objeto <code>Map</code> con los atributos de la
     *         petici&oacute;n.
     */
    public static Map<?, ?> getRequestMap() {
        return getExternalContext().getRequestMap();
    }

    /**
     * Regresa un Map de los parametros de la petici&oacute;n actual.
     *
     * @return El objeto <code>Map</code> con los parametros de la
     *         petici&oacute;n.
     */
    public static Map<?, ?> getParameterMap() {
        return getExternalContext().getRequestParameterMap();
    }

    /**
     * Regresa un Map de los atributos de la session del usuario.
     *
     * @return El objeto <code>Map</code> con los atributos de la session.
     */
    public static Map<?, ?> getSessionMap() {
        return getExternalContext().getSessionMap();
    }

    public static void messageGlobal(String message, Severity severity) {
        getFacesContext().addMessage(null, new FacesMessage(severity, message, ""));
    }

    /**
     * Agrega un mensaje a un componente especifico en la vista a mostrar.
     *
     * @param componentID
     *            El ID del componente.
     * @param severity
     *            Tipo de mensaje
     * @param summary
     *            El texto que va a ser mostrado en pantalla
     */
    public static void messageByIDComponent(String componentID, Severity severity, String summary) {
        getFacesContext().addMessage(ConstantesDyC1.FORM_2 + componentID, new FacesMessage(severity, summary, null));
    }

    public static void messageGlobalDetail(Severity severity, String summary, String datil) {
        getFacesContext().addMessage(null, new FacesMessage(severity, summary, datil));
    }

   
    
}
