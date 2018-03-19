package mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class DyctMovDevolucionMapper implements RowMapper<DyctMovDevolucionDTO>
{

    @Override
    public DyctMovDevolucionDTO mapRow(ResultSet resultSet, int i) throws SQLException
    {
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep(resultSet.getInt("IDSALDOICEP"));
        
        DyctMovDevolucionDTO devolucionHist = new DyctMovDevolucionDTO();

        devolucionHist.setNumControl(resultSet.getString("NUMCONTROL"));
        devolucionHist.setFechaResolucion(resultSet.getDate("FECHARESOLUCION"));
        devolucionHist.setImporteSolDev(resultSet.getBigDecimal("IMPORTESOLDEV"));
        devolucionHist.setImporteAutorizado(resultSet.getBigDecimal("IMPORTEAUTORIZADO"));
        devolucionHist.setImporteNegado(resultSet.getBigDecimal("IMPORTENEGADO"));
        devolucionHist.setActualizacion(resultSet.getBigDecimal("ACTUALIZACION"));
        devolucionHist.setIntereses(resultSet.getBigDecimal("INTERESES"));
        devolucionHist.setImporteNetoDev(resultSet.getBigDecimal("IMPORTENETODEV"));
        devolucionHist.setIdTipoResol(BuscadorConstantes.obtenerTipoResolucion(resultSet.getInt("IDTIPORESOL")));
        devolucionHist.setIdMovDevolucion(resultSet.getInt("IDMOVDEVOLUCION"));
        devolucionHist.setDyctSaldoIcepDTO(saldoIcep);
        devolucionHist.setRetIntereses(resultSet.getBigDecimal("RETINTERESES"));
        devolucionHist.setImpCompensado(resultSet.getBigDecimal("IMPCOMPENSADO"));

        return devolucionHist;
    }
}
