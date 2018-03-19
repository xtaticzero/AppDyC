package mx.gob.sat.siat.dyc.generico.util.common.impl;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import javax.servlet.http.HttpServletResponse;

import mx.gob.sat.siat.dyc.generico.util.common.DyCCompatibility;

import org.apache.log4j.Logger;


public class DyCCompatibilityImpl implements DyCCompatibility, PhaseListener {

    private static Logger log = Logger.getLogger("loggerDYC");

    @SuppressWarnings("compatibility:3034815090216958029")
    private static final long serialVersionUID = 1L;


    @Override
    public void afterPhase(PhaseEvent arg0) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        final FacesContext facesContext = event.getFacesContext();
        final HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        response.addHeader("X-UA-Compatible", "IE=EmulateIE8");
        log.error("Error sitting Head");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
