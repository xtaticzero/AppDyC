package mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 04, Diciembre 2013
 */

public class DeclaracionMapper  implements RowMapper<DyctDeclaracionDTO>
{
    @Override
    public DyctDeclaracionDTO mapRow(ResultSet resultSet, int i) throws SQLException
    {
        DyctDeclaracionDTO declaracion = new DyctDeclaracionDTO();
        declaracion.setIdDeclaracion(resultSet.getInt("IDDECLARACION"));
        declaracion.setFechaPresentacion(resultSet.getDate("FECHAPRESENTACION"));
        declaracion.setFechaCausacion(resultSet.getDate("FECHACAUSACION"));
        declaracion.setNumOperacion(resultSet.getString("NUMOPERACION"));
        declaracion.setNumDocumento(resultSet.getString("NUMDOCUMENTO"));
        declaracion.setSaldoAfavor(resultSet.getBigDecimal("SALDOAFAVOR"));
        declaracion.setDevueltoCompensado(resultSet.getBigDecimal("DEVUELTOCOMPENSADO"));
        declaracion.setAcreditamiento(resultSet.getBigDecimal("ACREDITAMIENTO"));
        declaracion.setImporte(resultSet.getBigDecimal("IMPORTE"));
        DyccUsoDecDTO a = new DyccUsoDecDTO();
        a.setIdUsoDec(resultSet.getInt("IDUSODEC"));
        declaracion.setDyccUsoDecDTO(a);
        DyccTipoDeclaraDTO tipoDeclaracion = new DyccTipoDeclaraDTO();
        tipoDeclaracion.setIdTipoDeclaracion(resultSet.getInt("IDTIPODECLARACION"));
        declaracion.setDyccTipoDeclaraDTO(tipoDeclaracion);
        return declaracion;
    }
}
