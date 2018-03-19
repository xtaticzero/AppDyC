package mx.gob.sat.siat.dyc.dao.util.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.util.DyccValidacionDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccValidacionDAO")
public class DyccValidacionDAOImpl implements DyccValidacionDAO{

    private static final Logger LOGGER = Logger.getLogger(DyccValidacionDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Retorna una lista de enteros la cual se utiliza para validar que el tipo de tramite es valido.
     *
     * @param idValidacion
     * @return
     * @throws SIATException
     */
    @Override
    public List<Integer> listarTipoTramiteXIdValidacion(int idValidacion) throws SIATException {
        List<Integer> listaTipoTramite = null;
        try {
            listaTipoTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_VALIDACIONTIPOTRAMITE.toString(), new Object[] { idValidacion }, new RowMapper<Integer>() {
                        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                            return resultSet.getInt(1);
                        }
                    });

        } catch (Exception dae) {
            LOGGER.error("listarTipoTramiteXIdValidacion(); parametros = idValidacion:" + idValidacion +
                         ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }

        return listaTipoTramite;
    }


}
