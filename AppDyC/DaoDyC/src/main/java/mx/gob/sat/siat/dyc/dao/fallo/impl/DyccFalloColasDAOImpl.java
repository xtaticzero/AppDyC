package mx.gob.sat.siat.dyc.dao.fallo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.fallo.DyccFalloColasDAO;
import mx.gob.sat.siat.dyc.dao.fallo.impl.mapper.DyccFalloColasMapper;
import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloColasDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Softtek
 *
 */
@Repository(value = "dyccFalloColasDAO")
public class DyccFalloColasDAOImpl implements DyccFalloColasDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOGGER = Logger.getLogger(DyccFalloColasDAOImpl.class);

    private static final String FIND_DYCC_FALLOCOLAS = "SELECT * FROM DYCC_FALLOCOLAS WHERE IDFALLOCOLAS=?";
    private static final String GETALL_DYCC_FALLOCOLAS = "SELECT * FROM DYCC_FALLOCOLAS";
    private static final String INSERT_DYCC_FALLOCOLAS = "INSERT INTO DYCC_FALLOCOLAS(IDFALLOCOLAS, DESCRIPCION) VALUES(?, ?)";
    private static final String NEXTID_DYCC_FALLOCOLAS = "SELECT DYCQ_IDFALLOCOLAS.NEXTVAL FROM DUAL";

    @Override
    public DyccFalloColasDTO find(Integer id) {
        DyccFalloColasDTO dyccFalloColasDTO = null;

        try {
            dyccFalloColasDTO = jdbcTemplateDYC.queryForObject(FIND_DYCC_FALLOCOLAS,
                    new Object[] { id }, new DyccFalloColasMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s %s %d", ConstantesDyC1.TEXTO_1_ERROR_DAO,
                    dae.getMessage(), ConstantesDyC1.TEXTO_2_ERROR_DAO, FIND_DYCC_FALLOCOLAS,
                    ConstantesDyC1.TEXTO_3_ERROR_DAO, id));
        }

        return dyccFalloColasDTO;
    }

    @Override
    public void insertFalloColas(DyccFalloColasDTO dyccFalloColasDTO) {
        try {
            dyccFalloColasDTO.setIdFalloColas(jdbcTemplateDYC.queryForObject(
                    NEXTID_DYCC_FALLOCOLAS, Integer.class));
            jdbcTemplateDYC.update(
                    INSERT_DYCC_FALLOCOLAS,
                    new Object[] { dyccFalloColasDTO.getIdFalloColas(),
                            dyccFalloColasDTO.getDescripcion() });
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO,
                    INSERT_DYCC_FALLOCOLAS));
        }
    }

    @Override
    public List<DyccFalloColasDTO> getAllFalloColas() {
        List<DyccFalloColasDTO> dyccFalloColasDTOList = new ArrayList<DyccFalloColasDTO>();

        try {
            dyccFalloColasDTOList = jdbcTemplateDYC.query(GETALL_DYCC_FALLOCOLAS,
                    new DyccFalloColasMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO,
                    dae.getMessage(), ConstantesDyC1.TEXTO_2_ERROR_DAO, GETALL_DYCC_FALLOCOLAS));
        }

        return dyccFalloColasDTOList;
    }

}
