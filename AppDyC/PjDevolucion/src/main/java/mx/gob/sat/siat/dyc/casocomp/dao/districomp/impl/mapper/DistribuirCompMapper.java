package mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.casocomp.dto.districomp.DistribuirCompVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


public class DistribuirCompMapper implements RowMapper<DistribuirCompVO> {
    public DistribuirCompMapper() {
        super();
    }

    @Override
    public DistribuirCompVO mapRow(ResultSet rs, int i) throws SQLException {
        DistribuirCompVO distribuirCompDTO = new DistribuirCompVO();

        distribuirCompDTO.setNumControl(rs.getString("numControl"));
        distribuirCompDTO.setEstadoAviso(rs.getInt("estado"));
        distribuirCompDTO.setImportCompensado(rs.getBigDecimal("importeComp"));
        distribuirCompDTO.setTipoDeclaracion(rs.getInt("tipoDeclara"));
        distribuirCompDTO.setUnidadAvisoComp(rs.getInt("claveAdm"));
        distribuirCompDTO.setImportSaldoOrig(rs.getBigDecimal("saldoaplicar"));
        distribuirCompDTO.setServicio(rs.getInt("servicio"));

        if (distribuirCompDTO.getServicio() == ConstantesDyCNumerico.VALOR_3) {
            distribuirCompDTO.setInconsistencia(" ");
        }

        if (distribuirCompDTO.getServicio() == ConstantesDyCNumerico.VALOR_4) {
            distribuirCompDTO.setInconsistencia(rs.getString("inconsistencia"));
            if (distribuirCompDTO.getInconsistencia() == null) {
                distribuirCompDTO.setInconsistencia(" ");
            } else {
                distribuirCompDTO.getInconsistencia();
            }
        }
        
        distribuirCompDTO.setConcepto(rs.getInt("conceptoOri"));
        distribuirCompDTO.setEjercicio(rs.getInt("ejercicioOri"));
        distribuirCompDTO.setPeriodo(rs.getInt("periodoOri"));
        distribuirCompDTO.setRfcContribunyente(rs.getString("rfc"));
        distribuirCompDTO.setFechaPresentaDec(rs.getDate("fechaPresentDec"));
        distribuirCompDTO.setIdSaldoIcepDestino(rs.getInt("idsaldoIcepD"));
        distribuirCompDTO.setIdSaldoIcepOrigen(rs.getInt("idsaldoIcepO"));
        
        return distribuirCompDTO;
    }
}
