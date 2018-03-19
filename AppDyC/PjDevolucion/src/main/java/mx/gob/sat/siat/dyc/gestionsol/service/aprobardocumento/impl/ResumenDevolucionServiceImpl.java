package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.rfc.DycpRfcDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.ResumenDevolucionDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ResumenDevolucionService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Ericka Janeth Ibarra Ponce
 * @author Jesus Alfredo Hernandez Orozco
 * @since 02/2014
 */

@Service("resumenDevolucionSer")
public class ResumenDevolucionServiceImpl implements ResumenDevolucionService {
    public ResumenDevolucionServiceImpl() {
        super();
    }
    @Autowired
    private ResumenDevolucionDAO resumenDevDAO;
    
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;
    
    @Autowired
    private DycpRfcDAO dycpRfcDAO;
    
    @Autowired
    private IDycpServicioDAO dycpServicioDAO;

    /**
     * <html>
     * <body>
     * Obtiene los datos del resumen del numero de control
     * </body>
     * </html>
     *
     * @param numControl
     * @param nombreDoc
     * @param sinDoc si es true; es un Trámite sin oficio de resolución
     * @return
     * @throws SIATException
     */
    public List<ResumenDevolucionDTO> buscarResumen(String numControl,String nombreDoc, boolean sinDoc) throws SIATException{
        return resumenDevDAO.buscarResumen(numControl, nombreDoc, sinDoc);
    }
        
    /**
     * Obtiene la ruta donde se encuentra almacenado el documento para poder descargarlo
     *
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    @Override
    public DyctDocumentoDTO obtenerRutaDocumento(String numControlDoc) throws SIATException {
        return dyctDocumentoDAO.consultarXNumControlDoc(numControlDoc);
    }
    
    /**
     * <pre>
     * Envia el documento al revisor central cuando se cumplen las siguientes reglas: 
     * 
     *  - El documento priviente de una ALAFF (que  no esté en la 90, 91 ó 97).
     *  - Es una resolucion y el tipo de resolución es una de las siguientes: 
     *    - Autorizada total.
     *    - Autorizada parcial con remanente disponible.
     *    - Autorizada parcial con remanente negado.
     *  - Tiene un monto neto a devolver mayor o igual a 20 millones o posee un RFC no confiable y el monto a devolver 
     *  es mayor o igual a 2 millones.
     * </pre>
     *
     * @param numControlDoc Numero de control del documento.
     * @throws SIATException
    */
    @Override
    public void enviarDocumentoARevisorCentral(String numControlDoc) throws SIATException {
        dyctDocumentoDAO.actualizarEstadoDoc(ConstantesDyCNumerico.VALOR_9, numControlDoc);
    }


    /**
     * <pre>
     * Verifica si un RFC se encuentra o no dentro del padron de RFC no confiables
     * </pre>
     *
     * @param rfc RFC del contribuyente
     * @return verdadero si el rfc se encuentra dentro del padron, falso en caso contraio
     * @throws SIATException
     */
    @Override
    public boolean buscarSiEstaDentroDelPadronDeNoConfiables(String rfc) throws SIATException {
        boolean bandera = Boolean.FALSE;
        DycpRfcDTO objeto = dycpRfcDAO.encontrar(rfc);
        if(objeto!=null && (objeto.getEsNoConfiable().equals(1) && objeto.getPadronNoConfiable().equals(1))) {
            bandera = Boolean.TRUE;
        }
        return bandera;
    }

    /**
     * <html>
     * <body>
     * Busca el registro en la tabla de DycpServicio a trav&eacute;s del n&uacute;mero de control.
     * </body>
     * </html>
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    public DycpServicioDTO buscarXNumControl(String numControl) throws SIATException {
        return dycpServicioDAO.encontrar(numControl);
    }
}
