/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.generico.web.controller.bean.backing;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;


@ManagedBean(name = "paginaErrorMB")
@ViewScoped
public class PaginaErrorMB implements Serializable {

    @SuppressWarnings("compatibility:-6066407274368718579")
    private static final long serialVersionUID = -8930026151665767815L;
    private static final Logger LOG = Logger.getLogger(PaginaErrorMB.class);
    private static final String INDEX_CONTRIBUYENTE = "https://siatdev.sat.gob.mx/PTSC/prototipo/index.jsp";
    private static final String INDEX_EMPLEADO = "https://siatdev.sat.gob.mx/PTSC/prototipo/index.jsp";

    private final SimpleDateFormat formateador =
        new SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm 'hrs.'", new Locale("es", "ES"));

    private String fecha;
    private String mensaje = "";
    private String error;
    private boolean messageSession;
    private String logout;
    private String folio;

    @PostConstruct
    public void inicializa() {
        fecha = establecerFecha();
        obtenerExcepcion();
    }

    private void obtenerExcepcion() {
        LOG.debug("INICIO obtenerExcepcion 24 Junio 1 22pm");
        Throwable excepcion;

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
        HttpSession session = (HttpSession)request.getSession();

        if (null != session.getAttribute(ConstantesDyC2.TIPO_ACCESO_CONT)) {
            LOG.info("::::::::::::EXCEPTION DESDE PORTAL CONTRIBUYENTE:::::::::");
            this.setLogout(INDEX_CONTRIBUYENTE);
        } else {
            LOG.info("::::::::::::EXCEPTION DESDE PORTAL EMPLEADOS:::::::::");
            this.setLogout(INDEX_EMPLEADO);
        }
        
        excepcion = (Throwable)session.getAttribute(ConstantesDyC1.IDENTIFICADOR_EXCEPCION);
        StringBuilder sb = new StringBuilder();
        folio = request.getParameter("folio");
        sb.append(folio);
        sb.append("\n");
        if(excepcion != null){
            sb.append(ExceptionUtils.getStackTrace(excepcion));
            LOG.error("Detalle Error: ", excepcion);
        }
        else{
            sb.append("No fue posible obtener la informacion de la excepcion");
        }
        error = sb.toString();
        LOG.error("trza de error ->" + error);

        LOG.info("*************************FIN DE EXCEPTION*****************************************");
    }

    private String establecerFecha() {
        return (formateador.format(new Date()));
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setMessageSession(boolean messageSession) {
        this.messageSession = messageSession;
    }

    public boolean isMessageSession() {
        return messageSession;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getLogout() {
        return logout;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }
}
