/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.archivo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCT_ARCHIVO
 * @author Federico Chopin Gachuz
 *
 * */
public class DyctArchivoMapper implements RowMapper<DyctArchivoDTO> {

    @Override
    public DyctArchivoDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyctArchivoDTO dyctArchivoDTO = new DyctArchivoDTO();

        dyctArchivoDTO.setIdArchivo(resultSet.getInt("IDARCHIVO"));
        dyctArchivoDTO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));
        dyctArchivoDTO.setUrl(resultSet.getString("URL"));
        dyctArchivoDTO.setDescripcion(resultSet.getString("DESCRIPCION"));
        dyctArchivoDTO.setFechaRegistro(resultSet.getTimestamp("FECHAREGISTRO"));

        return dyctArchivoDTO;

    }

}
