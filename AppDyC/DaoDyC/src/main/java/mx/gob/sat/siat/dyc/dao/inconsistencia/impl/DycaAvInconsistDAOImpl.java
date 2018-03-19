/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.inconsistencia.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.inconsistencia.DycaAvInconsistDAO;
import mx.gob.sat.siat.dyc.dao.inconsistencia.impl.mapper.AvInconsistMapper;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Repository
public class DycaAvInconsistDAOImpl implements DycaAvInconsistDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycaAvInconsistDAOImpl.class.getName());

    @Override
    public List<DycaAvInconsistDTO> selecXNumControl(String numControl) {
        List<DycaAvInconsistDTO> lDycaAvInconsistDTO = new ArrayList<DycaAvInconsistDTO>();
        log.info(lDycaAvInconsistDTO);
        try {
            lDycaAvInconsistDTO =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DYCA_AVINCONSIST_X_NUMCONT.toString(), new Object[] { numControl },
                                               new AvInconsistMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCA_AVINCONSIST_X_NUMCONT + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
        }

        return lDycaAvInconsistDTO;
    }

    @Override
    public void insertar(final List<DycaAvInconsistDTO> listaInconsistencias) {
        try {
            jdbcTemplateDYC.batchUpdate(SQLOracleDyC.INSERTAR_DYCA_AVINCONSIST.toString(), new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        DycaAvInconsistDTO inconsistencia = listaInconsistencias.get(i);
                        ps.setInt(ConstantesDyCNumerico.VALOR_1,
                                  inconsistencia.getDyccInconsistDTO().getIdInconsistencia());
                        ps.setString(ConstantesDyCNumerico.VALOR_2, inconsistencia.getDescripcion());
                        ps.setDate(ConstantesDyCNumerico.VALOR_3,
                                   new java.sql.Date(inconsistencia.getFechaRegistro().getTime()));
                        ps.setString(ConstantesDyCNumerico.VALOR_4, inconsistencia.getDycpAvisoCompDTO().getFolioAviso());
                    }

                    @Override
                    public int getBatchSize() {
                        return listaInconsistencias.size();
                    }
                });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCA_AVISOANEXO);
            throw dae;
        }
    }

}
