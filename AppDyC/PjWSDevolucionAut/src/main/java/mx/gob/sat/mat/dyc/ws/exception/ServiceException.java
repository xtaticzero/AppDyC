package mx.gob.sat.mat.dyc.ws.exception;

import java.io.Serializable;
import javax.xml.ws.WebFault;
import mx.gob.sat.mat.dyc.ws.domain.ServiceFault;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
@WebFault(name = "ServiceException")
public class ServiceException extends Exception implements Serializable {

    private static final long serialVersionUID = 7922427091688796440L;
    private ServiceFault fault;

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super();
    }

    protected ServiceException(ServiceFault faultInfo) {
        super(faultInfo.getFaultString());
        this.fault = faultInfo;
    }

    /**
     *
     * @param message
     * @param faultInfo
     */
    public ServiceException(String message, ServiceFault faultInfo) {
        super(message);
        this.fault = faultInfo;
    }

    /**
     *
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ServiceException(String message, ServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.fault = faultInfo;
    }

    /**
     * @param code
     * @param message
     */
    public ServiceException(String code, String message) {
        super(message);
        this.fault = new ServiceFault();
        this.fault.setFaultString(message);
        this.fault.setFaultCode(code);
    }

    public ServiceException(String code, String motivo, String message) {
        this.fault = new ServiceFault(code, motivo, message);
        this.fault.setMotivoCode(motivo);
        this.fault.setFaultCode(code);
        this.fault.setFaultString(message);
    }

    public ServiceException(String code, String motivo, String message, Throwable cause) {
        super(cause);
        this.fault = new ServiceFault(code, motivo, message);
        this.fault.setMotivoCode(motivo);
        this.fault.setFaultCode(code);
        this.fault.setFaultString(message);
    }

    /**
     *
     * @return
     */
    public ServiceFault getFaultInfo() {
        return fault;
    }

}
