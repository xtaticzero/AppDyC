package mx.gob.sat.siat.archivoshistorico;

import mx.gob.sat.siat.archivoshistorico.service.ArchivosHistoricoService;
import mx.gob.sat.siat.archivoshistorico.service.GenerarArchivosService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class Principal {

    private static final Logger LOGGER = Logger.getLogger(Principal.class);

    private Principal() {
        super();
    }

    public static void main(String[] args) throws SIATException {

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
        appContext.registerShutdownHook();
        
        if (args!=null && args.length>0) {
            GenerarArchivosService service = (GenerarArchivosService)appContext.getBean("generarArchivosService");
            try {
                service.generarArchivosDePueba(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
            } catch (Exception e) {
                LOGGER.error(e);
            }
        } else {
            LOGGER.error("INICIA EL FLUJO PARA MOVER LOS ARCHIVOS");
            ArchivosHistoricoService archivosHistoricoService = (ArchivosHistoricoService)appContext.getBean("archivosHistoricoService");
            archivosHistoricoService.acutalizarRegistrosDentroDelFileSystem();
            LOGGER.error("****TERMINA EL FLUJO PARA MOVER LOS ARCHIVOS******");
        }        
        
    }
}