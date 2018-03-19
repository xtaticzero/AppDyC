package mx.gob.sat.mat.dyc.ws.domain;

import java.io.Serializable;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class ServiceFault implements Serializable {

    private static final long serialVersionUID = -2307010979926332212L;
    private String faultCode;
    private String faultString;

    public ServiceFault() {
    }

    public ServiceFault(String faultCode, String faultString) {
        this.faultCode = faultCode;
        this.faultString = faultString;
    }
    
    /**
     * @return the faultCode
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * @param faultCode the faultCode to set
     */
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    /**
     * @return the faultString
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * @param faultString the faultString to set
     */
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

}
