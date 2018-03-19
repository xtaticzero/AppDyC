package mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author root
 */
@ManagedBean
public class IdleMonitorView {
    
    private long increment;

    /**
     *
     * @return
     */
    public long getIncrement() {
        return increment;
    }

    /**
     *
     * @param increment
     */
    public void setIncrement(long increment) {
        this.increment = increment;
    }
    
    /**
     *
     */
    public void increment() {
        increment++;
    }
}