package mx.gob.sat.siat.dyc.dao.tiposerv.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.tiposerv.IDyccTipoServicioDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper.TipoServicioMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Repository
public class DyccTipoServicioDAOImpl implements IDyccTipoServicioDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccTipoServicioDAOImpl.class.getName());

    @Override
    public DyccTipoServicioDTO encontrar(Integer idTipoServicio) {
        DyccTipoServicioDTO dyccTipoServicioDTO = new DyccTipoServicioDTO();
        log.info(dyccTipoServicioDTO);
        try {
            dyccTipoServicioDTO =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DYCC_TIPOSERVICIO.toString(), new Object[] { idTipoServicio },
                                                        new TipoServicioMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_TIPOSERVICIO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoServicio);
        }

        return dyccTipoServicioDTO;
    }

    /**
     * Consulta todos los tipos de servicio que se encuentran dados de alta en base de datos.
     *
     * @return Lista con todos los tipos de servicio.
     */
    @Override
    public List<DyccTipoServicioDTO> seleccionar() 
    {
        return jdbcTemplateDYC.query("SELECT * FROM DYCC_TIPOSERVICIO ", new TipoServicioMapper());
    }

}
