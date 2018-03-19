package mx.gob.sat.siat.pjenvionyv.mqenvionyv.service.impl;

import mx.gob.sat.siat.pjenvionyv.mqenvionyv.service.ValidaHorarioHabil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import java.io.FileInputStream; 
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service
public class ValidaHorarioHabilImpl implements ValidaHorarioHabil{

    private static final Logger LOGGER = Logger.getLogger(ValidaHorarioHabilImpl.class);
    
    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;
    
    @Override
    public void validaHorario() {
        LOGGER.debug("validaHorario");
        try {
            LOGGER.debug("Listener.isRunning()"+defaultMessageListenerContainer.isRunning());
            if(esHorarioLaboral() && !defaultMessageListenerContainer.isRunning() ){
                LOGGER.info("es horario laboral");
                defaultMessageListenerContainer.start();
            }
            if(!esHorarioLaboral() && defaultMessageListenerContainer.isRunning() ){
                LOGGER.info("no es horario laboral");
                defaultMessageListenerContainer.stop();
            }
        } catch (SIATException ex) {
            LOGGER.error(ex);
        }
    }
    
    /**
     * Retorna true si la ejecuci&oacute;n se realiza en un horario de 09:15 a 17:45 y false en caso contrario.
     * @return 
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    public static final boolean esHorarioLaboral() throws SIATException {
       
        String fechaFinHorario    = "";
        String fechaInicioHorario = "";
        String fechaStringFin     = "";
        String fechaStringInicio  = "";
       
        Date fechaFin    = null;
        Date fechaInicio = null;
        Date fechaActual = new Date();
        Properties propiedades = new Properties();
        FileInputStream archivo = null;
       
        SimpleDateFormat formatoFechaConHoras = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatoFechaSinHoras = new SimpleDateFormat("dd/MM/yyyy");
       
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
            fechaInicioHorario = propiedades.getProperty("fechaInicioHorario");
            fechaFinHorario = propiedades.getProperty("fechaFinHorario");
        } catch (IOException e){
            throw new SIATException(e);
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                LOGGER.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }
       
        fechaStringFin    = formatoFechaSinHoras.format(fechaActual)+" "+fechaFinHorario;
        fechaStringInicio = formatoFechaSinHoras.format(fechaActual)+" "+fechaInicioHorario;
       
        try {
            fechaFin    = formatoFechaConHoras.parse(fechaStringFin);
            fechaInicio = formatoFechaConHoras.parse(fechaStringInicio);
           
            if(fechaActual.after(fechaInicio) && fechaActual.before(fechaFin)) {
                return Boolean.TRUE;
            }           
        } catch (ParseException e) {
            throw new SIATException(e);
        }
        return false;
    }
   
}