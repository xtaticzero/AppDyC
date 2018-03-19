/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.util.DyctCasoPendienteDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyctCasoPendienteMapper;
import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value="dyctCasoPendienteDAO")
public class DyctCasoPendienteDAOImpl implements DyctCasoPendienteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctCasoPendienteDAOImpl.class.getName());

    public DyctCasoPendienteDAOImpl() {
        super();
    }

    @Override
    public List<DyctCasoPendienteDTO> obtenerDeclaraciones() {
        String sql = "SELECT * FROM DYCT_CASOPENDIENTE WHERE FECHAFIN IS NULL";
        return jdbcTemplateDYC.query(sql, new DyctCasoPendienteMapper());
    }

    @Override
    public void insertar(DyctCasoPendienteDTO dyctCasoPendienteDTO) throws SIATException{
        String sql = "INSERT INTO DYCT_CASOPENDIENTE (IDDECLARACION,FECHAPROCESAMIENTO,IDMOTIVO, IDIMPUESTO, \n" +
            "                                IDCONCEPTO, IDEJERCICIO, IDPERIODO, NUMOPERACION, FECHAFIN) \n" +
            "                                values (?,SYSDATE,?,?,?,?,?,?,NULL)";
        try {
            jdbcTemplateDYC.update(sql,
                                   new Object[] { dyctCasoPendienteDTO.getIdDeclaracion(), dyctCasoPendienteDTO.getDyccMotivoCasoDTO().getIdMotivo(),
                                                  dyctCasoPendienteDTO.getIdImpuesto(),
                                                  dyctCasoPendienteDTO.getIdConcepto(),
                                                  dyctCasoPendienteDTO.getIdEjercicio(),
                                                  dyctCasoPendienteDTO.getIdPeriodo(),
                                                  dyctCasoPendienteDTO.getNumOperacion() });
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctCasoPendienteDTO));
            throw new SIATException(siatE);
        }
    }

    @Override
    public void actualizar(Integer idDeclaracion) {
        String sql = "UPDATE DYCT_CASOPENDIENTE SET FECHAFIN = SYSDATE WHERE IDDECLARACION = ?";
        try {
            jdbcTemplateDYC.update(sql, new Object[] { idDeclaracion });
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + idDeclaracion);
        }
    }

    @Override
    public Integer buscarCasoPendiente(DyctCasoPendienteDTO dyctCasoPendienteDTO) throws SIATException {
        String result = null;
        Integer resul = 0;
        String sql = "select count(*) from dyct_casopendiente \n" + 
        "                where iddeclaracion = ? and idmotivo=? and idimpuesto=? and \n" + 
        "                      idconcepto=? and idejercicio=? and idperiodo=?";
        try {
            result =
                    jdbcTemplateDYC.queryForObject(sql, new Object[] { dyctCasoPendienteDTO.getIdDeclaracion(), dyctCasoPendienteDTO.getDyccMotivoCasoDTO().getIdMotivo(),
                                                                       dyctCasoPendienteDTO.getIdImpuesto(),
                                                                       dyctCasoPendienteDTO.getIdConcepto(),
                                                                       dyctCasoPendienteDTO.getIdEjercicio(),
                                                                       dyctCasoPendienteDTO.getIdPeriodo()},
                                                   String.class);
            resul = Integer.parseInt(result);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      sql + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctCasoPendienteDTO));
            throw new SIATException(dae);
        }
        return resul;
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

}
