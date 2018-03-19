package mx.gob.sat.siat.pjenvionyv.mqenvionyv.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.pjenvionyv.service.RealizarEnvioService;

import org.apache.log4j.Logger;

/**
 *
 * @author christian.ventura
 */
public class ProcesoEnvioNyVRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ProcesoEnvioNyVRunnable.class);
    private static final int THREAD_SLEEP = 7000;

    private RealizarEnvioService realizarEnvioService;
    private String mensaje;

    public ProcesoEnvioNyVRunnable(RealizarEnvioService realizarEnvioService, String mensaje) {
        this.realizarEnvioService = realizarEnvioService;
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        try {
            // se hace una espera para que de tiempo que el proceso que mando el
            // mensaje haga commit
            Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException ex) {
            LOGGER.error(ex);
        }
        try {
            realizarEnvioService.realizarCambios(mensaje);
        } catch (SIATException ex) {
            LOGGER.error(ex);
        }
    }

}
