package mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion;

import java.util.Map;
import java.util.TreeMap;

public class SIATException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> messages;

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

    public SIATException(Throwable cause) {
        super(cause);
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
