/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.tipotramite;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DyccPlazoLegalTipoDTO;


/**
 * Clase Interface DAO para la tabla [DYCC_PLAZOLEGAL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
public interface DyccPlazoLegalTipoDAO {

    /**
     * @return ArrayList DyccPlazoLegalTipoDTO
     * @throws SQLException
     * @throws Exception
     */
    List<DyccPlazoLegalTipoDTO> obtenerListaPlazo() throws SQLException;

}
