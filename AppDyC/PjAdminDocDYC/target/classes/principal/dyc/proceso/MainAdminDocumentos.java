package principal.dyc.proceso;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import principal.service.AdminDocumentoService;


/**
 * @author Luis ALberto Dominguez Palomino
 * @version 1.0
 * Proceso para realizar administrar documentos
 */

public class MainAdminDocumentos {

    public static final Logger LOG = Logger.getLogger(MainAdminDocumentos.class);

    public static void main(String[] args) {
        try {
            
            Date fechaSistem = new Date();
            String fecha = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(fechaSistem);
            
            LOG.info(">>>>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE PjAdminDocDYC el dia "+ fecha + "<<<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContextDoc.xml");
            appContext.registerShutdownHook();
            AdminDocumentoService adminDoc = (AdminDocumentoService)appContext.getBean("adminDocumentoService");
            
            LOG.info("<<<<<<<<<<<< Inicia proceso de Eliminar Archivos Temporales >>>>>>>>>>>>");
            adminDoc.eliminarArchivosTemp();
            LOG.info("<<<<<<<<<<<< Inicia proceso de Eliminar Archivos de Solicitudes y Compensaciones >>>>>>>>>>>>");
            adminDoc.eliminar();
            LOG.info("<<<<<<<<<<<< Inicia proceso de Respaldar Archivos de Solicitudes y Compensaciones >>>>>>>>>>>>");
            adminDoc.moverBloque();
            LOG.info("<<<<<<<<<<<< Inicia proceso de Respaldar TESOFE >>>>>>>>>>>>");
            adminDoc.moverTesofe();
            LOG.info(">>>>>>>>>>>>TERMINO DE EJECUTAR LA VERSION 1.0 DE PjAdminDocDYC el dia "+ fecha + "<<<<<<<<<<<<");
        } catch (Exception e) {
            LOG.error("Error --> " + e.getMessage());
        }
    }

}
