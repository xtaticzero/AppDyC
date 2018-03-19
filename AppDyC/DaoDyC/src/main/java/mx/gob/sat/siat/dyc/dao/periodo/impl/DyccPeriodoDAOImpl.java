package mx.gob.sat.siat.dyc.dao.periodo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.periodo.DyccPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.PeriodoMapper;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccPeriodoDAOImpl implements DyccPeriodoDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccPeriodoDAOImpl.class.getName());

    @Override
    public DyccPeriodoDTO encontrar(Integer id) {
        DyccPeriodoDTO dyccPeriodoDTO = null;
        try {
            dyccPeriodoDTO =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTA_DYCC_PERIODO.toString(), new Object[] { id }, new PeriodoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_PERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "IDPERIODO =" + id);

        }
        return dyccPeriodoDTO;
    }


    @Override
    public List<DyccPeriodoDTO> selecXTipoPeriodo(DyccTipoPeriodoDTO tipoPeriodo) {
        List<DyccPeriodoDTO> lDyccPeriodoDTO = null;

        try {
            lDyccPeriodoDTO =
                    this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTA_DYCC_PERIODO_X_TIPOPERIODO.toString(), new Object[] { tipoPeriodo.getIdTipoPeriodo() },
                                                    new PeriodoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_PERIODO_X_TIPOPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(tipoPeriodo));
        }
        return lDyccPeriodoDTO;
    }

    /**
     * @param idTipoPeriodo
     * @return
     */
    @Override
    public List<DyccPeriodoDTO> obtienePeriodoPorTipoPeriodo(String idTipoPeriodo) {
        List<DyccPeriodoDTO> periodo = new ArrayList<DyccPeriodoDTO>();
        try {
            periodo =
                    this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEPERIODOPORTIPOPERIODO.toString(), new Object[] { idTipoPeriodo },
                                                    new PeriodoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEPERIODOPORTIPOPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      idTipoPeriodo);
        }
        return periodo;
    }


    /**
     * @param dyccPeriodo
     * @return
     */
    @Override
    public DyccPeriodoDTO obtienePeriodoPorId(DyccPeriodoDTO dyccPeriodo) {
        
        DyccPeriodoDTO  dyccPeriodoReturn = new DyccPeriodoDTO();
        try 
        {
          dyccPeriodoReturn =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEPERIODOPORIDPERIODO.toString(), new Object[] { dyccPeriodo.getIdPeriodo() },
                                                             new PeriodoMapper());
        } 
        catch (DataAccessException dae) 
        {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEPERIODOPORIDPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccPeriodo));
        }
        return dyccPeriodoReturn;
    }

    @Override
    public DyccPeriodoDTO getFIniFFinPeriodo(int idPeriodo, int ejercicio) {

        DyccPeriodoDTO fechas = new DyccPeriodoDTO();
        try {
            fechas =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTARFECHASPORPERIODO.toString(), new Object[] { idPeriodo, ejercicio,
                                                                                                       idPeriodo,
                                                                                                       ejercicio },
                                                             new PeriodoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARFECHASPORPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idPeriodo + ejercicio);
        }
        return fechas;
    }

    @Override
    public List<DyccPeriodoDTO> obtenerPeriodos() {
        List<DyccPeriodoDTO> dyccPeriodo = new ArrayList<DyccPeriodoDTO>();
        try {
            dyccPeriodo = this.getJdbcTemplateDYC().query(SQLOracleDyC.SQL_OBTENER_PERIODOS.toString(), new Object[] { }, new PeriodoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_PERIODOS);
        }
        return dyccPeriodo;
    }

    @Override
    public List<DyccPeriodoDTO> obtenerPeriodosPorTipoDePeriodo(String tipoPeriodo) {
        List<DyccPeriodoDTO> dyccPeriodo = new ArrayList<DyccPeriodoDTO>();
        try {
            dyccPeriodo =
                    this.getJdbcTemplateDYC().query(SQLOracleDyC.SQL_OBTENER_PERIODOS_POR_TIPO_TRAMITE.toString(), new Object[] { tipoPeriodo },
                                                    new PeriodoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_PERIODOS_POR_TIPO_TRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + tipoPeriodo);
        }
        return dyccPeriodo;
    }
    
    @Override
    public String obtenerPeriodoDiot(int idPeriodo) {
       String periodo = "";
        try {
            periodo = jdbcTemplateDYC.queryForObject(SQLOracleDyC.SQL_OBTENER_PERIODO_DIOT.toString(), new Object[]{idPeriodo},String.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_PERIODO_DIOT + ConstantesDyC1.TEXTO_3_ERROR_DAO + idPeriodo);
        }
        return periodo;
    }
    
    @Override
    public String obtenerFinPeriodo(int idPeriodo) {
       String periodo = "";
        try {
            periodo = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTAR_FIN_PERIODO_X_IDPERIODO.toString(), new Object[]{idPeriodo},String.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTAR_FIN_PERIODO_X_IDPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idPeriodo);
        }
        return periodo;
    }
    

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    

}
