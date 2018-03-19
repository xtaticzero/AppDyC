/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.batch.dictautomaticas.exception;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class DictAutomaticasException extends Exception implements Serializable {


    public DictAutomaticasException(Throwable throwable) {
        super(throwable);
    }

    public DictAutomaticasException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DictAutomaticasException(String string) {
        super(string);
    }

    public DictAutomaticasException() {
        super();
    }
}
