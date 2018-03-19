package mx.gob.sat.siat.pjenvionyv.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.pjenvionyv.dao.EnvioANyVDAO;
import mx.gob.sat.siat.pjenvionyv.service.EnvioANyVService;
import mx.gob.sat.siat.pjenvionyv.service.RealizarEnvioService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Esta clase lee todos los documentos que se pueden enviar a NyV para que posteriormente se realize su envio.
 * 
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "envioANyVService")
public class EnvioANyVServiceImpl implements EnvioANyVService {

    private static final Logger LOGGER = Logger.getLogger(EnvioANyVServiceImpl.class);

    @Autowired
    private EnvioANyVDAO envioANyVDAO;
    
    @Autowired
    private RealizarEnvioService realizarEnvioService;
    
    public EnvioANyVServiceImpl() {
        super();
    }
    /**
     * Lista todos los documentos a enviar para NyV (son todos aquellos documentos que estan en estatus 2 de aprobado, 
     * que tienen firma autógrafa y que no tienen un folio NyV asignado).
     *
     * @return
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = true)
    public void buscarDocumentosAEnviar() {
        List<DyctDocumentoDTO> listaDeDocumentosAEnviar = null;
        try {
            listaDeDocumentosAEnviar = envioANyVDAO.listarDocumentosAEnviar();
            
            for (DyctDocumentoDTO objetoDocumento : listaDeDocumentosAEnviar) {
                try {
                    realizarEnvioService.realizarCambios(objetoDocumento);
                } catch (SIATException e) {
                    LOGGER.error("Hubo un error al actualizar el documento: "+objetoDocumento.getNumControlDoc()+ ". Error: "+e);
                }
            }
            
        } catch (Exception siate) {
            LOGGER.error("Hubo un error al actualizar los datos al enviar a NyV, error: "+siate);
        }
    }
}