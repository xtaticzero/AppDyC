package mx.gob.sat.siat.pjextractordeanexos.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.DycaSolAnexoTramDAO;
import mx.gob.sat.siat.dyc.dao.DyctAnexoReqDAO;
import mx.gob.sat.siat.dyc.domain.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.domain.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.pjextractordeanexos.service.LeerExcelService;
import mx.gob.sat.siat.pjextractordeanexos.service.ObtenerInformacionAProcesarService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <html>
 * <body>
 * Servicio que se utiliza para obtener la información de todos los archivos de excel que no han sido procesados. La
 * lista de dichos datos se obtiene a partir de 2 tablas: DYCA_SOLANEXOTRAM y DYCT_ANEXOREQ. Una vez que los datos han
 * sido procesados se actualiza el estatus del registro a procesado.
 * </body>
 * </html>
 *
 * @author Jesus Alfredo Hernandez Orozco.
 */
@Service(value = "obtenerInformacionAProcesarService")
public class ObtenerInformacionAProcesarServiceImpl implements ObtenerInformacionAProcesarService {
    public ObtenerInformacionAProcesarServiceImpl() {
        super();
    }

    private static final String QUERY_SOLANEXOTRAM = "SELECT * FROM Dyca_SolAnexoTram where procesado is null and idanexo in (4,6,7)";
    private static final String QUERY_ANEXOREQ    = "SELECT * from dyct_anexoreq where procesado is null and url is not null and idanexo in (4,6,7)";
    
    private static final Logger LOGGER = Logger.getLogger(ObtenerInformacionAProcesarServiceImpl.class);

    @Autowired
    private DycaSolAnexoTramDAO dycaSolAnexoTramDAO;
    
    @Autowired
    private DyctAnexoReqDAO dyctAnexoReqDAO;
    
    @Autowired
    private LeerExcelService leerExcelService;

    /**
     * <html>
     * <body>
     * Consulta todos los datos de la tablas Dyca_SolAnexoTram y dyct_anexoreq que tengan todos aquellos registros que 
     * no hayan sido procesados.
     * </body>
     * </html>
     *
     * @throws SIATException
     */
    @Override
    public void extraerDocumentos() {
        List<DycaSolAnexoTramDTO> listaSolAnexoTram = null;
        List<DyctAnexoReqDTO> listaAnexoReq = null;
        try {
            listaAnexoReq = dyctAnexoReqDAO.consultar(QUERY_ANEXOREQ);
            recorrerListaAnexoReq(listaAnexoReq);
             
            listaSolAnexoTram = dycaSolAnexoTramDAO.listarTramites(QUERY_SOLANEXOTRAM);
            recorrerListaSolAnexoTram(listaSolAnexoTram);
            
        } catch (SIATException e) {
            LOGGER.error("extraerDocumentos(): "+e);
        }
    }
    
    /**
     * <html>
     * <body>
     * Se lee cada uno de los datos de la tabla Dyca_SolAnexoTram que no ha sido porcesado para obtener el excel, y 
     * posteriormente, guardar los datos del excel en base de datos.
     * </body>
     * </html>
     *
     * @param lista
     */
    private void recorrerListaSolAnexoTram(List<DycaSolAnexoTramDTO> lista) {
        for (DycaSolAnexoTramDTO objeto : lista) {
            try {
                leerExcelService.importarInformacionDeExcel(objeto);
                
            } catch (SIATException e) {
                LOGGER.error("recorrerLista(); "+e);
            }
        }
    }
    
    /**
     * <html>
     * <body>
     * Se lee cada uno de los datos de la tabla dyct_anexoreq que no ha sido porcesado para obtener el excel, y 
     * posteriormente, guardar los datos del excel en base de datos.
     * </body>
     * </html>
     *
     * @param lista
     */
    private void recorrerListaAnexoReq(List<DyctAnexoReqDTO> lista) {
        for (DyctAnexoReqDTO objeto : lista) {
            try {
                LOGGER.info("JAHO - URL: "+objeto.getUrl());
                leerExcelService.importarInformacionDeExcel(objeto);
                
            } catch (SIATException e) {
                LOGGER.error("recorrerLista(); "+e);
            }
        }
    }
}
