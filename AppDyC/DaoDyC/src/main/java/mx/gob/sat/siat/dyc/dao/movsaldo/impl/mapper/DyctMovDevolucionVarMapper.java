package mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class DyctMovDevolucionVarMapper implements RowMapper<DyctMovDevolucionDTO> {
    public DyctMovDevolucionVarMapper() {
        super();
    }

    @Override
    public DyctMovDevolucionDTO mapRow(ResultSet resultSet, int i) throws SQLException
    {
        DyctMovDevolucionDTO dyctMovDevolucionDTO = new DyctMovDevolucionDTO();

        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();                   
        dyctSaldoIcepDTO.setIdSaldoIcep         (resultSet.getInt("IDSALDOICEP"));

        dyctMovDevolucionDTO.setNumControl      (resultSet.getString("NUMCONTROL"));
        dyctMovDevolucionDTO.setFechaResolucion (resultSet.getDate("FECHARESOLUCION"));
        dyctMovDevolucionDTO.setImporteSolDev(resultSet.getBigDecimal("IMPORTESOLDEV"));
        dyctMovDevolucionDTO.setImporteAutorizado  (resultSet.getBigDecimal("IMPORTEAUTORIZADO"));
        dyctMovDevolucionDTO.setImporteNegado      (resultSet.getBigDecimal("IMPORTENEGADO"));
        dyctMovDevolucionDTO.setActualizacion      (resultSet.getBigDecimal("ACTUALIZACION"));
        dyctMovDevolucionDTO.setIntereses          (resultSet.getBigDecimal("INTERESES"));
        dyctMovDevolucionDTO.setImporteNetoDev(resultSet.getBigDecimal("IMPORTENETODEV"));
        dyctMovDevolucionDTO.setIdTipoResol        (BuscadorConstantes.obtenerTipoResolucion(resultSet.getInt("IDTIPORESOL")));    
        dyctMovDevolucionDTO.setIdMovDevolucion(resultSet.getInt("IDMOVDEVOLUCION"));    
        dyctMovDevolucionDTO.setDyctSaldoIcepDTO   (dyctSaldoIcepDTO);    
        dyctMovDevolucionDTO.setRetIntereses(resultSet.getBigDecimal("RETINTERESES"));    
        dyctMovDevolucionDTO.setImpCompensado(resultSet.getBigDecimal("IMPCOMPENSADO"));
        dyctMovDevolucionDTO.setFechaFin(resultSet.getDate("FECHAFIN"));

        return dyctMovDevolucionDTO;
    }
}
