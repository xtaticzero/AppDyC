package mx.gob.sat.siat.dyc.util.common;

import java.util.Map;
import java.util.TreeMap;

public class AsignarException extends Exception{
    
    
   @SuppressWarnings("compatibility:-7688846136160750741")
    private static final long serialVersionUID = 1L;
    
    private Map<String, String> messages;
    
    private String descripcion;

    public AsignarException() {
        super();
        messages = new TreeMap<String, String>();
    }

    public AsignarException(String message) {
        super(message);
        messages = new TreeMap<String, String>();
    }

    public AsignarException(Map<String, String> messages) {
        this.messages = messages;
    }

    public AsignarException(String message, Throwable cause) {
        super(message, cause);
    }

    public AsignarException(String message, String descripcion, Throwable cause) {
        super(message, cause);
        this.descripcion = descripcion;
    }

    public AsignarException(String message, String descripcion) {
        super(message);
        this.descripcion = descripcion;
    }

    public AsignarException(Throwable cause) {
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
