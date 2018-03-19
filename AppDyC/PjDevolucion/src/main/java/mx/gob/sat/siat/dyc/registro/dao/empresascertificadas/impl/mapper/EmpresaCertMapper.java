package mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.registro.dto.empresascertificadas.EmpresaCertVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


public class EmpresaCertMapper implements RowMapper<EmpresaCertVO> {

    @Override
    public EmpresaCertVO mapRow(ResultSet rs, int i) throws SQLException {

        EmpresaCertVO empresaCertificada = new EmpresaCertVO();
        /** Estatus de localizado 1=Si 0=No 2=error */
        empresaCertificada.setVdStatus(null != rs.getString(ConstantesDyCNumerico.VALOR_1) ?
                                       rs.getString(ConstantesDyCNumerico.VALOR_1) : null);
        /**Llave de Modalidad  */
        empresaCertificada.setVdCveModalRfc(null != rs.getString(ConstantesDyCNumerico.VALOR_2) ?
                                            rs.getString(ConstantesDyCNumerico.VALOR_2) : null);
        return empresaCertificada;
    }
}
