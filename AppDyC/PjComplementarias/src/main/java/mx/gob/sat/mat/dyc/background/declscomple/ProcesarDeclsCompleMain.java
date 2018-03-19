package mx.gob.sat.mat.dyc.background.declscomple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.gob.sat.mat.dyc.background.declscomple.service.ProcesoDeclaracionComplemenService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ProcesarDeclsCompleMain {
    
    static final Logger LOG = Logger.getLogger(ProcesarDeclsCompleMain.class.getName());

    public static void main(String[] args) {
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

            LOG.info(">>>>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE PjComplementarias el dia " + format.format(new Date()) + "<<<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContextComple.xml");
            appContext.registerShutdownHook();
            ProcesoDeclaracionComplemenService service =
                (ProcesoDeclaracionComplemenService)appContext.getBean("procesoDeclaracionComplemenService");
            service.selectXDeclaracionComplemen();
            LOG.info(">>>>>>>>>>>FIN EJECUTANDO LA VERSION 1.0 DE PjComplementarias el dia " + format.format(new Date()) + "<<<<<<<<<<<<");
        } catch (Exception e) {
            LOG.error("Error en buscar decls complementarias--> " + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
    }
}
