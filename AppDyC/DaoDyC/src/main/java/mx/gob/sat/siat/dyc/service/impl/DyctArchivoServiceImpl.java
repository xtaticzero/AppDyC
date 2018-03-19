/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 20, 2014
 *
 * */
@Service("dyctArchivoService")
public class DyctArchivoServiceImpl implements DyctArchivoService {

    @Autowired
    private DyctArchivoDAO dyctArchivoDAO;

    private Logger log = Logger.getLogger(DyctArchivoServiceImpl.class);

    public DyctArchivoServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para consulta de documentos adjuntos
     * @param numControl
     * @return 
     *
     * */
    @Transactional(readOnly = true)
    public List<DyctArchivoDTO> buscarDocsAdjuntos(String numControl) {

        List<DyctArchivoDTO> lDyctArchivoDTO = new ArrayList<DyctArchivoDTO>();


        try {
            lDyctArchivoDTO = dyctArchivoDAO.buscarDocsAdjuntos(numControl);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return lDyctArchivoDTO;

    }

    /**
     * Metodo de servicio para insercion de documentos adjuntos
     *
     * */
    @Transactional(propagation = Propagation.MANDATORY)
    public void insertarDocumento(DyctArchivoDTO paramInputTO) throws SIATException{

        try {

            dyctArchivoDAO.insertarDocumento(paramInputTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }

    }

    /**
     * Metodo de servicio para eliminacion de archivos
     *
     * */
    @Transactional(readOnly = false)
    public void eliminarArchivo(DyctArchivoDTO dyctArchivoDTO) {
        try {
            dyctArchivoDAO.eliminarArchivo(dyctArchivoDTO);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }
    
    @Transactional(readOnly = false)
    public void eliminarArchivoPorUrl(DyctArchivoDTO dyctArchivoDTO) throws SIATException{
        try {
            dyctArchivoDAO.eliminarArchivoPorUrl(dyctArchivoDTO);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createDocumentoEdoCta(DyctArchivoDTO dyctArchivoDTO) throws SIATException {
        log.info("INIT CREATE EDO CUANTA DOC");
        try {
            dyctArchivoDAO.createDocumentoEdoCta(dyctArchivoDTO);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    /**
     *Metodo que busca el archivo por numero de control
     * --LADP[Luis ALberto Dominguez Palomino]
     * @param numControl
     * @return
     */
    @Override
    public DyctArchivoDTO buscaArchivoXNumCtrl(String numControl) throws SIATException{
        return dyctArchivoDAO.buscaArchivoXNumCtrl(numControl);
    }

    /**
     *Metodo que recibe el objeto dyctArchivoDTO para actualizar la informacion del archivo estado de cuenta por id archivo
     * --LADP[Luis Alberto Dominguez Palomino]
     * @param archivo
     * @param idArchivo
     * @return
     */
    @Override
    public boolean actualizarArchivo(DyctArchivoDTO archivo, Integer idArchivo) throws SIATException{
        return dyctArchivoDAO.actualizarArchivo(archivo, idArchivo);
    }

    /**
     * Metodo de servicio para insercion de documentos CU consultar expediente
     *
     * */
    @Transactional(readOnly = false)
    public void insertarDocumentoExpediente(DyctArchivoDTO dyctArchivoDTO) throws SIATException{

        try {

            dyctArchivoDAO.insertarDocumentoExpediente(dyctArchivoDTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    /**
     * Obtiene los archivos adjuntos por el contribuyente a traves del numero de control
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyctArchivoDTO> getDocumentosXNumeroControl(String numControl) throws SIATException {
        return dyctArchivoDAO.getDocumentosXNumeroControl(numControl);
    }
    
    @Override
    public List<DyctArchivoDTO> getDocumentosXNumeroControlCont(String numControl, boolean isEmpleado) throws SIATException {
        return dyctArchivoDAO.getDocumentosXNumeroControlCont(numControl, isEmpleado);
    }
    
    @Transactional(readOnly = false)
    public void reactivaDocumentoExpediente(DyctArchivoDTO dyctArchivoDTO) throws SIATException {
        try {

            dyctArchivoDAO.reactivaDocumentoExpediente(dyctArchivoDTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }
}
