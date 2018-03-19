/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.dao.diot;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;


/**
 * @author  Israel Chavez
 */
public interface DiotDAO {

    /**
     * @param icep
     * @return
     */
    DiotDTO obtieneDiotSP(DiotDTO diotDTO) throws SQLException;

}
