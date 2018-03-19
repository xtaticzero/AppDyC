/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 22, 2014
 *
 * */
public class IdReqConsecutivoMapper implements RowMapper{
    
    @Override
    public Object mapRow(ResultSet resultSet, int i) {
        Long idDocumento = null;
        try {
            idDocumento = resultSet.getLong("IDDOCUMENTO");
        } catch (SQLException e) {
            e.getMessage();
        }
        if (null != idDocumento) {
            return idDocumento;
        }
        return null;
    }
}

