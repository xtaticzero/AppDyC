/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.plazostramite.service.GestorPlazosTramite;
import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author root
 */
public final class Principal {

    private static final Logger LOGGER = Logger.getLogger(Principal.class);

    private Principal() {
        super();
    }

    public static void main(String[] args) throws SIATException {
        try {
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
            appContext.registerShutdownHook();

            LOGGER.error("INICIA EL FLUJO PARA ACTUALIZAR LOS TRAMITES POR PLAZOS");
            GestorPlazosTramite gestorPlazosTramite = (GestorPlazosTramite) appContext.getBean("gestorPlazosTramite");
            gestorPlazosTramite.iniciaProceso();
        } catch (Exception e) {
            LOGGER.error("ocurrio un error en el proceso", e);
        }
        LOGGER.error("****TERMINA EL FLUJO PARA ACTUALIZAR TRAMITES******");
    }
}
