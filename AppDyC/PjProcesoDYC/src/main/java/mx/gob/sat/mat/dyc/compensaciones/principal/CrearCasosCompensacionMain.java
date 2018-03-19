package mx.gob.sat.mat.dyc.compensaciones.principal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.gob.sat.mat.dyc.compensaciones.service.impl.CasoCompensacionPrincipalServiceImpl;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class CrearCasosCompensacionMain
{

    static final Logger LOG = Logger.getLogger(CrearCasosCompensacionMain.class.getName());
    private CrearCasosCompensacionMain(){
    
    }
    public static void main(String[] args) {
        try {
  

            DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            LOG.error(">>>>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE PjProcesoDYC el dia " + formateador.format(new Date()) + "<<<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContextCaso.xml");
            appContext.registerShutdownHook();
            LOG.debug("Se termina de crear el contexto de la aplicacion -->" + appContext);
            
            CasoCompensacionPrincipalServiceImpl delegate = 
                    (CasoCompensacionPrincipalServiceImpl)appContext.getBean("casoCompensacionPrincipalService");
            delegate.procesoCasoCompensacion();
            
            LOG.error(">>>>>>>>>>>>TERMINO DE EJECUTAR LA VERSION 1.0 DE PjProcesoDYC el dia " + formateador.format(new Date()) +
                     "<<<<<<<<<<<<");
        } catch (Exception e) {

            LOG.error("Ocurrio un error al ejecutar el proceso 'Crear casos de compensacion' de MAT-DYC --> " + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
    }
}
