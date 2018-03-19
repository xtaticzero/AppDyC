package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper.EjercicioMapper;
import mx.gob.sat.siat.dyc.dao.util.DyccEjercicioDAO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccEjercicioDAOImpl implements DyccEjercicioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccEjercicioDAOImpl.class.getName());

    @Override
    public DyccEjercicioDTO encontrar(Integer id) {
        DyccEjercicioDTO dyccEjercicioDTO = null;
        log.debug(dyccEjercicioDTO);
        try {
            dyccEjercicioDTO =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTA_DYCC_EJERCICIO.toString(), new Object[] { id }, new EjercicioMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_EJERCICIO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
        }
        return dyccEjercicioDTO;
    }

    @Override
    public List<DyccEjercicioDTO> seleccionar() {
        List<DyccEjercicioDTO> lDyccEjercicioDTO = new ArrayList<DyccEjercicioDTO>();
        try {
            lDyccEjercicioDTO = this.getJdbcTemplateDYC().query(SQLOracleDyC.SELECCIONAR_DYCC_EJERCICIO.toString(), new EjercicioMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.SELECCIONAR_DYCC_EJERCICIO);
        }
        return lDyccEjercicioDTO;
    }

    @Override
    public List<DyccEjercicioDTO> obtieneEjercicio() {
        List<DyccEjercicioDTO> ejercicio = new ArrayList<DyccEjercicioDTO>();
        try {
            ejercicio = this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEEJERCICIO.toString(), new EjercicioMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEEJERCICIO);
        }
        return ejercicio;
    }

    /**
     * @param ejercicio
     * @return
     */
    @Override
    public DyccEjercicioDTO obtieneEjercicioPorId(DyccEjercicioDTO ejercicioDTO) {
        List<DyccEjercicioDTO> ejercicio = new ArrayList<DyccEjercicioDTO>();
        try {
            ejercicio =
                    this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEEJERCICIOPORID.toString(), new Object[] { ejercicioDTO.getIdEjercicio() },
                                                    new EjercicioMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEEJERCICIOPORID + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(ejercicioDTO));
        }

        if (ejercicio != null && ejercicio.size() > 0) {
            return ejercicio.get(0);
        }

        return null;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

}
