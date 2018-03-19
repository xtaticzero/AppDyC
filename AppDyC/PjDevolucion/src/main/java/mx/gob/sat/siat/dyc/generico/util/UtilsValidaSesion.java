package mx.gob.sat.siat.dyc.generico.util;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


public final class UtilsValidaSesion {

    private static final Logger LOGGER = Logger.getLogger(UtilsValidaSesion.class);

    private UtilsValidaSesion() {
    }

    private static UtilsValidaSesion instance = null;

    /**
     * Creador sincronizado para protegerse de posibles problemas multi-hilo
     */
    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new UtilsValidaSesion();
        }
    }

    public static UtilsValidaSesion getInstance() {
        createInstance();
        return instance;
    }

    /**
     * Valida si el usuario del portal de empleado tiene derecho a abrir la
     * pantalla solicitada.
     *
     * @param idProceso
     */
    public static void validarSesion(AccesoUsr accesoUsr, String idProceso) {
        HttpSession session = null;

        try {
            session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession( Boolean.TRUE);

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.debug("accesoUsr.getProcesos() ->" + accesoUsr.getProcesos());
        if (!accesoUsr.getProcesos().containsValue(idProceso)) {

            LOGGER.error("Se intenta ingresar a un proceso no asignado. RFC:" + accesoUsr.getRfcCorto() + ", ruta: " +
                         ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());
            session.setAttribute("tipo", "2");
            session.setAttribute("error", "No se tiene permisos suficientes para acceder a este proceso");
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                                                                                "/faces/resources/pages/errores/errorProceso.jsf");

            } catch (Exception e2) {
                LOGGER.error("Error al direccionar a la pantalla de error.");
            }
        }
    }
}
