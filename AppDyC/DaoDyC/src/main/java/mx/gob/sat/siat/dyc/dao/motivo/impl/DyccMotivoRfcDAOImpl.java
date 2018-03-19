package mx.gob.sat.siat.dyc.dao.motivo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.motivo.DyccMotivoRfcDAO;
import mx.gob.sat.siat.dyc.dao.motivo.impl.mapper.DyccMotivoRfcMapper;
import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccMotivoRfcDAO")
public class DyccMotivoRfcDAOImpl implements DyccMotivoRfcDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DyccMotivoRfcDAOImpl() {
        super();
    }
    
    @Override
    public List<DyccMotivoRfcDTO> obtenerMotivosPorTipoAccion(Integer tipoAccion) {
        return jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_TIPOMOTIVOS.toString(), new Object[] { tipoAccion }, new DyccMotivoRfcMapper());
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

}
