package mx.gob.sat.siat.dyc.dao.fallo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.fallo.DyccFalloDetalleDAO;
import mx.gob.sat.siat.dyc.dao.fallo.impl.mapper.DyccFalloDetalleMapper;
import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;
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
@Repository(value = "dyccFalloDetalleDAO")
public class DyccFalloDetalleDAOImpl implements DyccFalloDetalleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOGGER = Logger.getLogger(DyccFalloDetalleDAOImpl.class);

    private static final String FIND_DYCC_FALLODETALLE = "SELECT * FROM DYCC_FALLODETALLE WHERE IDFALLODETALLE=?";
    private static final String FINDBYDESCRIPCION_DYCC_FALLODETALLE =
        "SELECT * FROM DYCC_FALLODETALLE WHERE DESCRIPCION LIKE ?";
    private static final String GETALL_DYCC_FALLODETALLE = "SELECT * FROM DYCC_FALLODETALLE";
    private static final String INSERT_DYCC_FALLODETALLE =
        "INSERT INTO DYCC_FALLODETALLE(IDFALLODETALLE, DESCRIPCION, IDFALLOCOLAS) VALUES(?, ?, ?)";
    private static final String NEXTID_DYCC_FALLODETALLE = "SELECT DYCQ_IDFALLODETALLE.NEXTVAL FROM DUAL";

    @Override
    public DyccFalloDetalleDTO find(Integer id) {
        DyccFalloDetalleDTO dyccFalloDetalleDTO = null;

        try {
            dyccFalloDetalleDTO =
                    jdbcTemplateDYC.queryForObject(FIND_DYCC_FALLODETALLE, new Object[] { id }, new DyccFalloDetalleMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s %s %d", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, FIND_DYCC_FALLODETALLE,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, id));
        }

        return dyccFalloDetalleDTO;
    }

    @Override
    public DyccFalloDetalleDTO findByDescripcion(String descripcion) {
        DyccFalloDetalleDTO dyccFalloDetalleDTO = null;

        try {
            dyccFalloDetalleDTO =
                    jdbcTemplateDYC.queryForObject(FINDBYDESCRIPCION_DYCC_FALLODETALLE, new Object[] { descripcion },
                                                   new DyccFalloDetalleMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, FINDBYDESCRIPCION_DYCC_FALLODETALLE,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, descripcion));
        }

        return dyccFalloDetalleDTO;
    }

    @Override
    public DyccFalloDetalleDTO insertFalloDetalle(DyccFalloDetalleDTO dyccFalloDetalleDTO) {
        try {
            dyccFalloDetalleDTO.setIdFalloDetalle(jdbcTemplateDYC.queryForObject(NEXTID_DYCC_FALLODETALLE,
                                                                                 Integer.class));
            jdbcTemplateDYC.update(INSERT_DYCC_FALLODETALLE,
                                   new Object[] { dyccFalloDetalleDTO.getIdFalloDetalle(), dyccFalloDetalleDTO.getDescripcion(),
                                                  dyccFalloDetalleDTO.getIdFalloColas().getIdFalloColas() });
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, INSERT_DYCC_FALLODETALLE));
        }

        return dyccFalloDetalleDTO;
    }

    @Override
    public List<DyccFalloDetalleDTO> getAllFalloDetalle() {
        List<DyccFalloDetalleDTO> dyccFalloDetalleDTOList = new ArrayList<DyccFalloDetalleDTO>();

        try {
            dyccFalloDetalleDTOList = jdbcTemplateDYC.query(GETALL_DYCC_FALLODETALLE, new DyccFalloDetalleMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, GETALL_DYCC_FALLODETALLE));
        }

        return dyccFalloDetalleDTOList;
    }

}
