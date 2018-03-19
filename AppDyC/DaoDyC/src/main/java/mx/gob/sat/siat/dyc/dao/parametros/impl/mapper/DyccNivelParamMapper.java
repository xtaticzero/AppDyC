package mx.gob.sat.siat.dyc.dao.parametros.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccNivelParamDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class DyccNivelParamMapper implements RowMapper<DyccNivelParamDTO> {
    public DyccNivelParamMapper() {
        super();
    }

    @Override
    public DyccNivelParamDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccNivelParamDTO objeto = new DyccNivelParamDTO();
        objeto.setIdParametro(rs.getInt("IDPARAMETRO"));
        objeto.setClaveADM(rs.getInt("CLAVEADM"));
        objeto.setClaveNivel(rs.getInt("CLAVENIVEL"));
        return objeto;
    }
}
