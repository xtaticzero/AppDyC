/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.stereotype.Service;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 20, 2014
 *
 * */
@Service(value = "dyctArchivoService")
public interface DyctArchivoService {

    List<DyctArchivoDTO> buscarDocsAdjuntos(String numControl);

    void insertarDocumento(DyctArchivoDTO paramInputTO) throws SIATException; 

    void eliminarArchivo(DyctArchivoDTO dyctArchivoDTO);
    
    void eliminarArchivoPorUrl(DyctArchivoDTO dyctArchivoDTO) throws SIATException;
    
    DyctArchivoDTO buscaArchivoXNumCtrl(String numControl) throws SIATException;
    
    boolean actualizarArchivo(DyctArchivoDTO archivo, Integer idArchivo) throws SIATException;

    void createDocumentoEdoCta(DyctArchivoDTO dyctArchivoDTO) throws SIATException;
    
    void insertarDocumentoExpediente(DyctArchivoDTO dyctArchivoDTO) throws SIATException;
    
    void reactivaDocumentoExpediente(DyctArchivoDTO dyctArchivoDTO) throws SIATException;
    

    /**
     * Obtiene los archivos adjuntos por el contribuyente a traves del numero de control
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    List<DyctArchivoDTO> getDocumentosXNumeroControl(String numControl) throws SIATException;

    List<DyctArchivoDTO> getDocumentosXNumeroControlCont(String numControl, boolean isEmpleado) throws SIATException;
}
