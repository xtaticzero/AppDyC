package mx.gob.sat.siat.dyc.generico.util.common;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

public interface DyCCompatibility {
    
    void afterPhase(PhaseEvent arg0);
    void beforePhase(PhaseEvent event);
    PhaseId getPhaseId();
    
}
