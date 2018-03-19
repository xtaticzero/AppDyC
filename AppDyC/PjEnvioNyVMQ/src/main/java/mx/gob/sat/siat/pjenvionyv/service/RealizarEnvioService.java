package mx.gob.sat.siat.pjenvionyv.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * Esta clase se encarga de realizar el envio de todos los documentos que se van a enviar a NyV, cada documento que
 * se envia es tomado de un documento de word y se convierte a pdf para poder ser enviado.
 * 
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface RealizarEnvioService {
    
    void realizarCambios(String mensaje) throws SIATException;
    
}
