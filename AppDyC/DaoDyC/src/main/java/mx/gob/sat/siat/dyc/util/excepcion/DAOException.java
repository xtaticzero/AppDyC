package mx.gob.sat.siat.dyc.util.excepcion;

import java.util.Arrays;


/**
 * Excepción para la capa de acceso a datos.
 *
 * @author Jesús Alfredo Hernández Orozco.
 */
public class DAOException extends Exception {
    @SuppressWarnings("compatibility:8540638330937841448")
    private static final long serialVersionUID = 1L;

    private transient Object[] parametros;
    private String mensaje;

    public DAOException() {
        super();
    }
    
    public DAOException(String message) {
        super(message);
    }
    
    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Construtor donde s&oacute;lo se pasan los par&aacute;metros sobre  el cual se trabaja en el m&eacute;todo
     * para hacer el registro en BD.
     *
     * @param parametros Es la informaci&aacute;n que se utiliza para realizar el registro en BD.
     */
    public DAOException(Object[] parametros) {
        if (parametros != null) {
            this.parametros = parametros.clone();
        } 
    }

    /**
     * Constructor con par&aacute;metros y mensaje.
     *
     * @param parametros Es la informaci&aacute;n que se utiliza para realizar el registro en BD.
     * @param mensaje Mensaje que ayude a describir o identificar el error.
     */
    public DAOException(Object[] parametros, String mensaje) {
        if (parametros != null) {
            this.parametros = parametros.clone();
        } 
        this.mensaje = mensaje;
    }

    /**
     * Constructor con par&aacute;metros, mensaje y excepci&oacute;n arrojada durante la ejecuci&oacute;n.
     *
     * @param parametros Es la informaci&aacute;n que se utiliza para realizar el registro en BD.
     * @param mensaje Mensaje que ayude a describir o identificar el error.
     * @param excepcion Error arrojado durante la ejecuci&oacute;n.
     */
    public DAOException(Object[] parametros, String mensaje, Throwable excepcion) {
        super(mensaje, excepcion);
        if (parametros != null) {
            this.parametros = parametros.clone();
        } 
        this.mensaje = mensaje;
    }

    public void setParametros(Object[] parametros) {
        if (parametros != null) {
            this.parametros = parametros.clone();
        } 
    }

    public Object[] getParametros() {
        if (parametros != null) {
            return Arrays.copyOf(parametros, parametros.length);
        } else {
            return new String[0];
        }
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
