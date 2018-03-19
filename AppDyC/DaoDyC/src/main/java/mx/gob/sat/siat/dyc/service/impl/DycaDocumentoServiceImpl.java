/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service.impl;

import mx.gob.sat.siat.dyc.dao.documento.DycaDocumentoDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.service.DycaDocumentoService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para consulta de documentos adjuntos
 * @author Federico Chopin Gachuz
 *
 */
@Service("dycaDocumentoService")
@Transactional
public class DycaDocumentoServiceImpl implements DycaDocumentoService {
    public DycaDocumentoServiceImpl() {
        super();
    }

    @Autowired
    private DycaDocumentoDAO dycaDocumentoDAO;

    private Logger log = Logger.getLogger(DycaDocumentoServiceImpl.class);

    /**
     * Metodo de servicio para insercion de documentos adjuntos con comentarios
     * @param Objeto <DycaDocumentoDTO>
     *
     * */
    @Transactional(readOnly = false)
    public void insertarDocumentoComentario(DyctArchivoDTO dyctArchivoDTO,
                                            Long idSequencia) throws SIATException {

        try {

            dycaDocumentoDAO.insertarDocumentoComentario(dyctArchivoDTO, idSequencia);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SIATException(e);   
        }
    }

    /**
     * Metodo de servicio para actualizacion de documentos adjuntos
     * @param int <numEmpleadoAprob>
     *
     * */
    @Transactional(readOnly = false)
    public void actualizarDocumentoAprobacion(Integer numEmpleadoAprob, Long idDocumento) throws SIATException {

        try {

            dycaDocumentoDAO.actualizarDocumentoAprobacion(numEmpleadoAprob, idDocumento);

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);   
        }

    }

    public void setDycaDocumentoDAO(DycaDocumentoDAO dycaDocumentoDAO) {
        this.dycaDocumentoDAO = dycaDocumentoDAO;
    }

    public DycaDocumentoDAO getDycaDocumentoDAO() {
        return dycaDocumentoDAO;
    }

}
