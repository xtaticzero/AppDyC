package mx.gob.sat.mat.dyc.herramientas.analizador.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


public class ObtenerDeclsDWHMapper implements RowMapper<DeclaracionDWHBean>
{
    @Override
    public DeclaracionDWHBean mapRow(ResultSet rs, int i) throws SQLException {
        
        DeclaracionDWHBean icep = new DeclaracionDWHBean();

        icep.setEstatus(String.valueOf(rs.getInt(ConstantesDyCNumerico.VALOR_1)));
        icep.setTipoDeclaracion(rs.getInt(ConstantesDyCNumerico.VALOR_2) != ConstantesDyCNumerico.VALOR_0 ? String.valueOf(rs.getInt(ConstantesDyCNumerico.VALOR_2)) : null);
        icep.setFechPresentacion(rs.getDate(ConstantesDyCNumerico.VALOR_3) != null ? String.valueOf(rs.getDate(ConstantesDyCNumerico.VALOR_3)) : null);
        icep.setNumOper(rs.getString(ConstantesDyCNumerico.VALOR_4) != null ? rs.getString(ConstantesDyCNumerico.VALOR_4) : null);
        icep.setSaldoFavor(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_5) != null ? String.valueOf(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_5)) : null);
        
        return icep;
    }
}