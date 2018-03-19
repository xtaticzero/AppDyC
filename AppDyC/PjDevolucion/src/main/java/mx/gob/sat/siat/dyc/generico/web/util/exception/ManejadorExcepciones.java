/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.generico.web.util.exception;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.TransactionTimedOutException;


public class ManejadorExcepciones extends ExceptionHandlerWrapper {

    private static final Logger LOG = Logger.getLogger(ManejadorExcepciones.class);
    private static final long LIMITE_FOLIO = 999999999;
    private static final String ERROR = "DC-E";
    private ExceptionHandler wrapped;


    public ManejadorExcepciones(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() { 
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isProjectStage(ProjectStage.Development)) {
            getWrapped().handle();
        } else {
            Iterator<ExceptionQueuedEvent> excepciones = getUnhandledExceptionQueuedEvents().iterator();
            while (excepciones.hasNext()) {
                ExceptionQueuedEvent excepcion = excepciones.next();
                ExceptionQueuedEventContext contexto = (ExceptionQueuedEventContext)excepcion.getSource();
                Throwable throwable = contexto.getException();
                try {
                    throwable = obtenerExceptionRoot(throwable);
                    enviarExcepcion(throwable);
                } finally {
                    excepciones.remove();
                }
            }
        }
    }

    public static void enviarExcepcion(Throwable throwable) {
        String folio = ERROR + (int)(Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession)context.getExternalContext().getSession(Boolean.TRUE);
        hs.setAttribute(ConstantesDyC1.IDENTIFICADOR_EXCEPCION, throwable);
        LOG.info("*************************TRAZA DE EXCEPTION***************************************");
        LOG.info(establecerFecha());
        LOG.info("FOLIO DE EXCEPTION: " + folio);
        LOG.error(identificarExcepcion(throwable));
        NavigationHandler nh = context.getApplication().getNavigationHandler();
        nh.handleNavigation(context, null, ConstantesDyC1.DIRECCION_PAGINA_ERROR + folio);
    }

    private static String identificarExcepcion(Throwable t) {
        if (t instanceof DataAccessResourceFailureException) {
            LOG.info("Entro a la instancia de DataAccessResourceFailureException");

        } else if (t instanceof TransactionTimedOutException) {
            LOG.info("Entro a la instancia de TransactionTimedOutException");

        } else if (t instanceof TransactionSystemException) {
            LOG.info("Entro a la instancia de TransactionSystemException");

        } else if (t instanceof DataAccessException) {
            LOG.info("Entro a la instancia de DataAccessException");

        } else if (t instanceof NestedRuntimeException) {
            LOG.info("Entro a la instancia de NestedRuntimeException");

        } else if (t instanceof ViewExpiredException) {
            LOG.info("Entro a la instancia de Session expirada");

        } else if (t instanceof SQLException) {
            LOG.info("Entro a la instancia de SQLException");

        } else if (t instanceof SIATException) {
            LOG.info("Entro a la instancia de SIATException");

        }
        return ExceptionUtils.getStackTrace(t);
    }


    public static String printLongerTrace(Throwable t) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement e : t.getStackTrace()) {
            sb.append(e);
        }
        return sb.toString();
    }

    private static Throwable obtenerExceptionRoot(Throwable throwable) {
        Throwable throwableRoot, throwableTmp;
        throwableTmp = throwable;
        throwableRoot = throwable;
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            sb.append(ExceptionUtils.getStackTrace(throwableRoot));
            throwableTmp = throwableRoot.getCause();
            if (throwableTmp == null) {
                break;
            } else {
                throwableRoot = throwableTmp;
            }
        }

        return throwableRoot;
    }


    private static String establecerFecha() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return (formateador.format(new Date()));
    }
}
