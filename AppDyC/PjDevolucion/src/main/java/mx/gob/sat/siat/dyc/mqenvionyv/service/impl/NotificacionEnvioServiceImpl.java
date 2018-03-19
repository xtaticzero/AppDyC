package mx.gob.sat.siat.dyc.mqenvionyv.service.impl;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.mqenvionyv.service.NotificacionEnvioMq;
import mx.gob.sat.siat.dyc.mqenvionyv.service.NotificacionEnvioService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Service;


/**
 * Este servicio se utiliza para enviar a NYV los documentos que se pretenden
 * notificar. Se utiliza el n&uacute;mero de control del documento para hacer el
 * env&iacute;o.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "notificacionEnvioService")
public class NotificacionEnvioServiceImpl implements NotificacionEnvioService {
    
    @Autowired(required = false)
    private NotificacionEnvioMq notificacionEnvioMq;
    
    private static final Logger LOGGER = Logger.getLogger(NotificacionEnvioServiceImpl.class);
    
    /**
     * Realiza el env&iacute; a NYV a traves de JMS mandando como mensaje el n&uacte;mero de control del documento.
     * 
     * @param aprobarDocumentoDTO
     * @throws SIATException 
     */
    @Override
    public void enviarANYV(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {
        try {
            if(notificacionEnvioMq!=null){
                LOGGER.info("JAHO - Se envía a notificar el documento: "+aprobarDocumentoDTO.getNumControlDoc());
                notificacionEnvioMq.envioMensajeMq(aprobarDocumentoDTO.getNumControlDoc());
            }
        } catch(JmsException ex){
            LOGGER.error("JAHO - Error al notificar el documento: "+aprobarDocumentoDTO.getNumControlDoc(),ex.getCause());
            throw new SIATException(ex);
        }
    }    
}
