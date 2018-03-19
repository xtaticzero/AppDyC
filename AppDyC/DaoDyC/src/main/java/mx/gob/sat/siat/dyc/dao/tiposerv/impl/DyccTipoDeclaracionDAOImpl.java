package mx.gob.sat.siat.dyc.dao.tiposerv.impl;


import java.util.List;

import mx.gob.sat.siat.dyc.dao.tiposerv.DyccTipoDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper.TipoDeclaracionMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccTipoDeclaracionDAOImpl implements DyccTipoDeclaracionDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccTipoDeclaracionDAOImpl.class.getName());

    @Override
    public DyccTipoDeclaraDTO encontrar(Integer idTipoDeclaracion) {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DYCC_TIPODECLARACION.toString(), new Object[] { idTipoDeclaracion },
                                                             new TipoDeclaracionMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_TIPODECLARACION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoDeclaracion);
        }

        return null;
    }

    @Override
    public List<DyccTipoDeclaraDTO> obtenerTiposDeDeclaraciones() {
        return jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_TIPOS_DECLARACIONES.toString(), new Object[] { }, new TipoDeclaracionMapper());
    }
}
