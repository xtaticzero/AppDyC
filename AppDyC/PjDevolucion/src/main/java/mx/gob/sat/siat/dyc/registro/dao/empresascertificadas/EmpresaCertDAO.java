package mx.gob.sat.siat.dyc.registro.dao.empresascertificadas;

import mx.gob.sat.siat.dyc.registro.dto.empresascertificadas.EmpresaCertVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface EmpresaCertDAO {

    /**
     * @param EmpresaCertVO
     * @return EmpresaCertVO
     * */

    EmpresaCertVO consultaEmpresaCertificada(EmpresaCertVO empresaCert) throws SIATException;

}
