/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Implementaci&oacute;n DAO para consulta contribuyente
 * @author Federico Chopin Gachuz
 *
 * */
public class ConsultarExpedienteDAOMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        DyctContribuyenteDTO dyctContribuyenteDTO = new DyctContribuyenteDTO();

        if (null != resultSet.getAsciiStream("DATOSCONTRIBUYENTE")) {
            dyctContribuyenteDTO.setDatosContribuyente(resultSet.getAsciiStream("DATOSCONTRIBUYENTE"));
        }
       
        return dyctContribuyenteDTO;

    }
}
