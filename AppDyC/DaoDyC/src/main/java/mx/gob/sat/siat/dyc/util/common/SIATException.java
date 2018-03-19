package mx.gob.sat.siat.dyc.util.common;

import java.util.Map;
import java.util.TreeMap;

public class SIATException extends Exception {


    @SuppressWarnings("compatibility:-7688846136160750741")
    private static final long serialVersionUID = 1L;
    
    private Map<String, String> messages;
    
    private String descripcion;

    public SIATException() {
        super();
        messages = new TreeMap<String, String>();
    }

    public SIATException(String message) {
        super(message);
        messages = new TreeMap<String, String>();
    }

    public SIATException(Map<String, String> messages) {
        this.messages = messages;
    }

    public SIATException(String message, Throwable cause) {
        super(message, cause);
    }

    public SIATException(String message, String descripcion, Throwable cause) {
        super(message, cause);
        this.descripcion = descripcion;
    }

    public SIATException(String message, String descripcion) {
        super(message);
        this.descripcion = descripcion;
    }

    public SIATException(Throwable cause) {
        super(cause);
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
        
    }
    
    public String getDescripcion(){
        return descripcion;
    }


}
