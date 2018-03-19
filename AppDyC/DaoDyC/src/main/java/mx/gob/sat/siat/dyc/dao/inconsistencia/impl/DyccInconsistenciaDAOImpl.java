package mx.gob.sat.siat.dyc.dao.inconsistencia.impl;

import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DycaSolInconsistMapper;
import mx.gob.sat.siat.dyc.dao.inconsistencia.DyccInconsistenciaDAO;
import mx.gob.sat.siat.dyc.dao.inconsistencia.impl.mapper.InconsistenciaMapper;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @autor J. Isaac Carbajal Bernal
 */
@Repository
public class DyccInconsistenciaDAOImpl implements DyccInconsistenciaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccInconsistenciaDAOImpl.class.getName());

    @Override
    public DyccInconsistDTO encontrar(Integer idInconsistencia) {

        DyccInconsistDTO dyccInconsistenciaDTO = null;
        try {
            dyccInconsistenciaDTO
                    = this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DYCC_INCONSISTENCIA.toString(), new Object[]{idInconsistencia},
                            new InconsistenciaMapper());

        } catch (DataAccessException dae) {
            log.info("Se presento un error en la ejecucion : " + dae.getMessage() + " con el query : "
                    + SQLOracleDyC.CONSULTA_DYCC_INCONSISTENCIA + " con los parametro IDINCONSISTENCIA= " + idInconsistencia);
        }
        return dyccInconsistenciaDTO;

    }

    /**
     *
     * @param idInconsistencia
     * @param numeroControl
     * @return
     */
    @Override
    public DycaSolInconsistDTO buscarSolicitudInconsitencia(Integer idInconsistencia, String numeroControl) {
        log.info("buscarSolicitudInconsitencia: idInconsistencia -->" + idInconsistencia);
        log.info("buscarSolicitudInconsitencia: numeroControl -->" + numeroControl);
        DycaSolInconsistDTO dycaSolInconsistDTO = null;

        try {

            dycaSolInconsistDTO = this.jdbcTemplateDYC
                    .queryForObject(SQLOracleDyC.FIND_SOLICITUD_INCONSISTENCIA_BY_ID_NUMCONTROL.toString(),
                            new Object[]{idInconsistencia, numeroControl}, new DycaSolInconsistMapper());
        } catch (DataAccessException dae) {
            log.info("Se presento un error en la ejecucion : " + dae.getMessage() + " con el query : "
                    + SQLOracleDyC.FIND_SOLICITUD_INCONSISTENCIA_BY_ID_NUMCONTROL + " con los parametro IDINCONSISTENCIA= " + idInconsistencia
                    + " NUMCONTROL = " + numeroControl);
        }
        return dycaSolInconsistDTO;
    }

    @Override
    public void insertarInconsistencia(DycaSolInconsistDTO dycaSolInconsistDTO) {
        try {
            log.error("insertarInconsistencia:");
            log.error("getDescripcion: -->" + dycaSolInconsistDTO.getDescripcion() + "<--");
            log.error("getIdInconsistencia: -->" + dycaSolInconsistDTO.getDyccInconsistDTO().getIdInconsistencia() + "<--");
            log.error("getNumControl: -->" + dycaSolInconsistDTO.getDycpSolicitudDTO().getNumControl() + "<--");
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAINCONSISTENCIAS.toString(),
                    new Object[]{dycaSolInconsistDTO.getDyccInconsistDTO().getIdInconsistencia(),
                        dycaSolInconsistDTO.getDycpSolicitudDTO().getNumControl(),
                        dycaSolInconsistDTO.getDescripcion()});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.INSERTAINCONSISTENCIAS.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw dae;
        }
    }

    /**
     *
     * @param dTO
     */
    @Override
    public void actualizarSolicitudInconsistencia(DycaSolInconsistDTO dTO) {
        log.info("actualizarSolicitudInconsistencia: ");
        log.info("getDescripcion: -->" + dTO.getDescripcion() + "<---");
        log.info("getIdInconsistencia -->" + dTO.getDyccInconsistDTO().getIdInconsistencia());
        log.info("getNumControl: -->" + dTO.getDycpSolicitudDTO().getNumControl());
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.UPDATE_SOLICITUD_INCONSISTENCIA.toString(),
                    new Object[]{dTO.getDescripcion(), dTO.getDyccInconsistDTO().getIdInconsistencia(),
                        dTO.getDycpSolicitudDTO().getNumControl()});
        } catch (Exception dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.UPDATE_SOLICITUD_INCONSISTENCIA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO);
        }
    }
}
