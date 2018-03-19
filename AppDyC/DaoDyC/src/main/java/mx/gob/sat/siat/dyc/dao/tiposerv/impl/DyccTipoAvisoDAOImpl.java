package mx.gob.sat.siat.dyc.dao.tiposerv.impl;

import mx.gob.sat.siat.dyc.dao.tiposerv.DyccTipoAvisoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper.TipoAvisoMapper;
import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 03,Diciembre 2013
 */
@Repository
public class DyccTipoAvisoDAOImpl implements DyccTipoAvisoDAO {

    @Autowired    
    private JdbcTemplate jdbcTemplateDYC;
    
    private Logger log = Logger.getLogger(DyccTipoAvisoDAOImpl.class);

    @Override
    public DyccTipoAvisoDTO encontrar(Integer id) {
        DyccTipoAvisoDTO dyccTipoAvisoDTO = null;
        try {
            dyccTipoAvisoDTO = this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTADYCC_TIPOAVISO.toString(), new Object[]{id}, new TipoAvisoMapper());
                

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTADYCC_TIPOAVISO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);

        }


        return dyccTipoAvisoDTO;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
