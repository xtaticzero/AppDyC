/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.icep;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;


/**
 * @author  Israel Chavez
 */
public interface IcepDAO {

    /**
     * @param icep
     * @return
     */
    IcepUrdcFatDTO obtieneIcepUrdFat(IcepUrdcFatDTO icep) throws SQLException;

    List<IcepUrdcsilDTO> obtieneIcepUrdcsil(IcepUrdcsilDTO icep) throws SQLException;

    IcepSioUrucple1DTO retrieveIcepSpSioUrucple1(IcepSioUrucple1DTO icep) throws SQLException;

    IcepSioUrucple1DTO obtieneIcepUrdcsil1(IcepSioUrucple1DTO icepDTO) throws SQLException;
    
    IcepUrdcFatDTO obtieneIcepUrdcFat1(IcepUrdcFatDTO icep, String spDb2) throws SQLException;
}
