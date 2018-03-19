package mx.gob.sat.siat.dyc.dao.ddcdm;

import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmSda1OEn2DTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DdCdmSda1Oen2DAO {
    
    /**
     * <html>
     * <body>
     * Inserta un registro en la tabla Dd_Cdm_Sda1Oen2 utilizando como parametro el objeto DdCdmSda1OEn2DTO 
     * </body>
     * </html>
     *
     * @param
     * @throws SIATException
     */
    void insertar (DdCdmSda1OEn2DTO objeto) throws SIATException;
}
