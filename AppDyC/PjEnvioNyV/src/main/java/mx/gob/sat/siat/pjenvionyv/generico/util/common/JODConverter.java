package mx.gob.sat.siat.pjenvionyv.generico.util.common;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;

import org.springframework.stereotype.Service;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "jodConverter")
public class JODConverter {

    private OfficeManager officeManager;
    private static final Logger LOGGER = Logger.getLogger(JODConverter.class);

    public synchronized void convertToPDF(File archivoDocx, File archivoPDF) {
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        converter.convert(archivoDocx, archivoPDF);
    }
    
    public static final Integer PUERTO_DEFAULT      = 8100;
    public static final Integer PUERTO_ALTERNATIVO1 = 8101;
    public static final Integer PUERTO_ALTERNATIVO2 = 8102;
    public static final Integer PUERTO_ALTERNATIVO3 = 8103;
    public static final Integer PUERTO_ALTERNATIVO4 = 8104;

    /**
     * Valida que se inicie un servicio nuevo cada vez que se da de sube el proyecto, en caso de que un servicio
     * de libre office este ya corriendo se utiliza el que esta dado de alta.
     */
    @PostConstruct
    public void start() {
        try {
            ExternalOfficeManagerConfiguration externalProcessOfficeManager = new ExternalOfficeManagerConfiguration();
            externalProcessOfficeManager.setConnectOnStart(Boolean.TRUE);
            externalProcessOfficeManager.setPortNumber(PUERTO_DEFAULT);
            this.officeManager = externalProcessOfficeManager.buildOfficeManager();
            this.officeManager.start();
            LOGGER.info("INICIO EL SERVICIO DE LIBRE OFFICE CON UNA INSTANCIA YA EXISTENTE");
            
        } catch (OfficeException e) {
            this.officeManager = new DefaultOfficeManagerConfiguration().setPortNumbers(PUERTO_DEFAULT,PUERTO_ALTERNATIVO1,PUERTO_ALTERNATIVO2,PUERTO_ALTERNATIVO3, PUERTO_ALTERNATIVO4).buildOfficeManager();
            this.officeManager.start();
            LOGGER.info("INICIO EL SERVICIO DE LIBRE OFFICE CON UNA INSTANCIA NUEVA");
        }
    }

    @PreDestroy
    public void stop() {
        officeManager.stop();
    }

}