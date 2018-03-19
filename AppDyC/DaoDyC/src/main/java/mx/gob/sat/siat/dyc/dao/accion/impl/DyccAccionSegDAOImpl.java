package mx.gob.sat.siat.dyc.dao.accion.impl;

import mx.gob.sat.siat.dyc.dao.accion.DyccAccionSegDAO;
import mx.gob.sat.siat.dyc.dao.accion.impl.mapper.AccionSegMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccAccionSegDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccAccionSegDAOImpl implements DyccAccionSegDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccAccionSegDAOImpl.class.getName());

    @Override
    public DyccAccionSegDTO encontrar(Integer id) {
        DyccAccionSegDTO dyccAccionSegDTO = new DyccAccionSegDTO();
        log.info(dyccAccionSegDTO);
        try {
            dyccAccionSegDTO =
                    this.getJdbcTemplateDYC().queryForObject("SELECT * FROM DYCC_ACCIONSEG WHERE IDACCIONSEG = ?",
                                                             new Object[] { id }, new AccionSegMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     "agregar query" + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
        }
        return dyccAccionSegDTO;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
