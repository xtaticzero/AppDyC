package mx.gob.sat.siat.pjenvionyv.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * Esta clase lee todos los documentos que se pueden enviar a NyV para que posteriormente se realize su envio.
 * 
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface EnvioANyVService {
    void buscarDocumentosAEnviar() throws SIATException;
}
