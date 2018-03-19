/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.dao.AnexoDAO;
import mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper.AnexoMapper;
import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
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
 *
 * @author  Alfredo Ramirez
 * @since   20/05/2014
 *
 */
@Repository(value = "anexoDAO")
public class AnexoDAOImpl implements AnexoDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(AnexoDAOImpl.class);

    private List<AnexoVO> anexosVO;

    public AnexoDAOImpl() {
        super();
    }

    @Override
    public List<AnexoVO> buscarAnexosARequerir(String tipoTramite) {
        try {
            this.anexosVO =
                    jdbcTemplateDYC.query(SQLOracleDyC.BUSCAR_ANEXOS_A_REQUERIR.toString().replace("<tiposTramites>", tipoTramite),
                                          new Object[] { }, new AnexoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.BUSCAR_ANEXOS_A_REQUERIR.toString() +
                      " con los siguientes parametros: idTipoTramite = " + tipoTramite);
            throw dae;
        }
        return this.getAnexosVO();
    }

    @Override
    public void insertar(final List<AnexoVO> anexos, final String folioAviso) {
        try {
            jdbcTemplateDYC.batchUpdate(SQLOracleDyC.INSERTAR_DYCA_AVISOANEXO, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        AnexoVO anexo = anexos.get(i);
                        ps.setString(ConstantesDyCNumerico.VALOR_1, folioAviso);
                        ps.setInt(ConstantesDyCNumerico.VALOR_2, anexo.getIdAnexo());
                        ps.setInt(ConstantesDyCNumerico.VALOR_3, anexo.getTipoTramite());
                        ps.setString(ConstantesDyCNumerico.VALOR_4, anexo.getNombreAnexo());
                        ps.setString(ConstantesDyCNumerico.VALOR_5, anexo.getUrl());
                    }

                    @Override
                    public int getBatchSize() {
                        return anexos.size();
                    }
                });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCA_AVISOANEXO);
            throw dae;
        }
    }

    @Override
    public List<AnexoVO> buscarAnexosPorNumcontrol(String numControl) {
        String sql =
            "SELECT IDANEXO, NOMBREARCHIVO AS NOMBREANEXO, '' AS DESCRIPCIONANEXO, FECHAREGISTRO AS FECHAINICIO, FECHAREGISTRO AS FECHAFIN, URL, IDTIPOTRAMITE   FROM DYCA_SOLANEXOTRAM WHERE NUMCONTROL = ?";
        try {
            this.anexosVO = jdbcTemplateDYC.query(sql, new Object[] { numControl }, new AnexoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sql + " con los siguientes parametros: idTipoTramite = " +
                      numControl);
            throw dae;
        }
        return this.getAnexosVO();
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setAnexosVO(List<AnexoVO> anexosVO) {
        this.anexosVO = anexosVO;
    }

    public List<AnexoVO> getAnexosVO() {
        return anexosVO;
    }
}
