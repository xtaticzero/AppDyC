/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstatantesFirmadoElectronico;

import org.apache.log4j.Logger;


@ManagedBean(name = "demoFirmaMB")
@SessionScoped
public class DemoFirmaMB {
    private static final Logger LOG = Logger.getLogger(DemoFirmaMB.class.getName());

    @ManagedProperty(value = "#{sessionHandler}")
    private transient SessionHandler sessionHandler;
    private String cadenaOriginal;
    private String firmaDigital;

    /**
     * Metodo a ejecutar con el boton "Firmar".
     * @throws IOException
     */
    public void firmar() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();

        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
        LOG.info(request.getContextPath());
        response.sendRedirect(request.getContextPath() + "/faces/resources/pages/gestionsol/firmaFIEL.jsp");

    }

    public void testFirmar() throws IOException {
        Utils.getInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        Utils.setFlashParam("accesoEF", sessionHandler.obtenerSession());
        Utils.redirecciona(request.getContextPath() + "/faces/resources/pages/gestionsol/firmaRegistro.jsp");
    }

    /**
     * Metodo que se ejecuta para buscar el paramatro del
     * request que proviene del jsp, el nombre del parametro
     * sera segun el id del componente en cuestion.
     * @param nombreParametro
     * @return
     */
    public String obtenerParametroRequest(String nombreParametro) {
        return (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(nombreParametro);
    }

    public void onActive() {
        FacesContext.getCurrentInstance().addMessage(null,
                                                     new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome Back", "Well, that's a long coffee break!"));
        HttpSession session = (HttpSession)JSFUtils.getExternalContext().getSession(false);
        session.removeAttribute(ConstantesDyC2.TIPO_ACCESO_CONT);
        session.removeAttribute(ConstantesDyC2.TIPO_ACCESO_EMPL);
    }


    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return obtenerParametroRequest(ConstatantesFirmadoElectronico.PARAMETRO_CADENA_ORIGINAL);
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public String getFirmaDigital() {
        firmaDigital = obtenerParametroRequest(ConstatantesFirmadoElectronico.PARAMETRO_FIRMA_DIGITAL);
        return firmaDigital;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public String getCadenaOriginal1() {
        return cadenaOriginal;
    }

    public String getFirmaDigital1() {
        return firmaDigital;
    }

}
