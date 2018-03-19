package mx.gob.sat.siat.pjextractordeanexos.service;

import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * <html>
 * <body>
 * Se utiliza este servicio para leer de los registros que son importados de la base de datos los exceles que han sido
 * adjuntados por el contribuyente, los cuales se depositaran con este en la base de datos en las tablas:<br />
 *  - DD_CDM_ANEXO701<br />
 *  - DD_CDM_SDA1OEN1<br />
 *  - DD_CDM_SDA1OEN2<br />
 * </body>
 * </html>
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface LeerExcelService {
    
    /**
     * <html>
     * <body>
     * Se le pasa un objeto que contiene donde se encuentra el excel (extraido de la tabla de Dyca_SolAnexoTram) y se 
     * utiliza para extraer la informacion que se guardará en la base de datos.
     * </body>
     * </html>
     *
     * @param objeto
     * @throws SIATException
     */
    void importarInformacionDeExcel(DycaSolAnexoTramDTO objeto) throws SIATException;
    
    /**
     * <html>
     * <body>
     * Se le pasa un objeto que contiene donde se encuentra el excel (extraido de la tabla de Dyct_AnexoReq) y se 
     * utiliza para extraer la informacion que se guardará en la base de datos.
     * </body>
     * </html>
     *
     * @param objeto
     * @throws SIATException
     */
    void importarInformacionDeExcel(DyctAnexoReqDTO objeto) throws SIATException;
}
