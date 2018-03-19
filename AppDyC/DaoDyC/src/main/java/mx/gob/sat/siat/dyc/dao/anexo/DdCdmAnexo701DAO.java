package mx.gob.sat.siat.dyc.dao.anexo;

import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmAnexo701DTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DdCdmAnexo701DAO {
    void insertar (DdCdmAnexo701DTO objeto) throws SIATException;
}
