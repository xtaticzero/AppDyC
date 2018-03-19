package mx.gob.sat.mat.dyc.validarreqs;

import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import mx.gob.sat.mat.dyc.validarreqs.service.ValidarReqsServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;

@Component
public class ValidarReqMain
{
    private static final Logger LOG = Logger.getLogger(ValidarReqMain.class);

    public static void main(String[] args)
    {
        try {
            LOG.info("Inicia Validar el vencimiento del plazo de los requerimientos ... " + new Date());
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
            context.registerShutdownHook();
            ValidarReqsServiceImpl validador = context.getBean(ValidarReqsServiceImpl.class);
            validador.execute();
            LOG.info("Termina Validar el vencimiento del plazo de los requerimientos ... " + new Date());
        } catch (Exception e) {
            System.err.println("error ->" + e.getMessage());    
            e.printStackTrace();
        }
    }
}
