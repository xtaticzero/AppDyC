/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.batch.dictautomaticas;

import mx.gob.sat.mat.batch.dictautomaticas.util.FechaUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import mx.gob.sat.mat.batch.dictautomaticas.service.DyctDictAutomaticasDel;

/**
 *
 * @author RRAL
 */
public final class DictAutomaticasMain {
    
    private static final Logger LOGGER = Logger.getLogger(DictAutomaticasMain.class);
    
    private DictAutomaticasMain(){}
    
    public static void main(String[] args) {
        LOGGER.info(">>>>>>>>>>EJECUTANDO PjDevAutomaticas v1.0 el dia " + FechaUtil.getFechaHora() + "<<<<<<<<<<");

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        appContext.registerShutdownHook();
        DyctDictAutomaticasDel dycAutomaticasDel = (DyctDictAutomaticasDel) appContext.getBean("dycDictAutomaticasDel");
        dycAutomaticasDel.exec();
    }
    
}
