/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.batch.devautomaticas.exception;

import java.io.Serializable;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class DevAutomaticasException extends Exception implements Serializable {

    private static final long serialVersionUID = 1676288963303202510L;

    public DevAutomaticasException(Throwable throwable) {
        super(throwable);
    }

    public DevAutomaticasException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DevAutomaticasException(String string) {
        super(string);
    }

    public DevAutomaticasException() {
        super();
    }
}
