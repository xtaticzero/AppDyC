/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.MatrizDictaminadoresDAO;
import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper.MatrizDictaminadoresMapper;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.MatrizDictaminadorVO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementaci&oacute;n DAO para Mantener Matriz Dictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 * @date Agosto 5, 2013
 */
@Repository(value = "matrizDictaminadoresDAO")
public class MatrizDictaminadoresDAOImpl implements MatrizDictaminadoresDAO {
    private static Logger log = Logger.getLogger(MatrizDictaminadoresDAOImpl.class);

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    private List<MatrizDictaminadorVO> listaDictaminadores;
    private MatrizDictaminadorVO dictaminador;

    public MatrizDictaminadoresDAOImpl() {
        super();
        listaDictaminadores = new ArrayList<MatrizDictaminadorVO>();
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<MatrizDictaminadorVO> obtenerListaDictaminadores(AdmcUnidadAdmvaDTO admva) throws SQLException {
        try {
            listaDictaminadores =
                    jdbcTemplateDYC.query(SQLOracleDyC.MATRIZDICTAMINADORES_LISTADICTAMINADORESTODO.toString(), new Object[] { admva.getClaveSir(),
                                                                                                       admva.getClaveSir() },
                                          new MatrizDictaminadoresMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.MATRIZDICTAMINADORES_LISTADICTAMINADORESTODO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(admva));
            throw e;
        }
        return listaDictaminadores;
    }

    /**
     * @throws Exception
     */
    @Override
    public MatrizDictaminadorVO buscaDictaminador(MatrizDictaminadorVO dictaminadorDTO) throws SQLException {
        try {
            this.dictaminador =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.MATRIZDICTAMINADORES_LISTADICTAMINADORES.toString().concat(SQLOracleDyC.MATRIZDICTAMINADORES_LISTADICTAMINADORESWHEREEMP),
                                                   new Object[] { dictaminadorDTO.getNumEmpleado() },
                                                   new MatrizDictaminadoresMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.MATRIZDICTAMINADORES_LISTADICTAMINADORES + SQLOracleDyC.MATRIZDICTAMINADORES_LISTADICTAMINADORESWHEREEMP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dictaminadorDTO));
            this.dictaminador = new MatrizDictaminadorVO();
            throw e;
        }
        return this.dictaminador;
    }

    /**
     * @param dictaminador
     * @throws ClassNotFoundException
     * @throws SQLException
     *
     */
    @Override
    public void insertarDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.MATRIZDICTAMINADORES_INSERTARDICTAMINADOR.toString(),
                                   new Object[] { dictaminador.getObservaciones(), dictaminador.getNumEmpleado(),
                                                  dictaminador.getClaveAdm(), dictaminador.getNombre(),
                                                  dictaminador.getApellidoPaterno(),
                                                  dictaminador.getApellidoMaterno() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.MATRIZDICTAMINADORES_INSERTARDICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            throw dae;
        }
    }

    /**     @Override
    public void actualizarDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException {
        try {
            jdbcTemplateDYC.update(MATRIZDICTAMINADORES_ACTUALIZARDICTAMINADOR, dictaminador.getObservaciones(),
                                   dictaminador.getActivo(), dictaminador.getCargaTrabajo(),
                                   dictaminador.getNumEmpleado());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                      MATRIZDICTAMINADORES_ACTUALIZARDICTAMINADOR + ConstantesDyC.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            throw dae;
        }
    } */

    /**     public void comisionarDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException {
        try {
            jdbcTemplateDYC.update(MATRIZDICTAMINADORES_COMISIONARDICTAMINADOR, /*dictaminador.getComisionado(),
                                   dictaminador.getIdEstado(), dictaminador.getClaveAdmcomision(),
                                   dictaminador.getNumEmpleado());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                      MATRIZDICTAMINADORES_COMISIONARDICTAMINADOR + ConstantesDyC.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            throw dae;
        }
    } */

    public void terminarComisionDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException {

        try {
            jdbcTemplateDYC.update(SQLOracleDyC.MATRIZDICTAMINADORES_TERMINARCOMISIONDICTAMINADOR.toString(), dictaminador.getNumEmpleado());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.MATRIZDICTAMINADORES_TERMINARCOMISIONDICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            throw dae;
        }
    }


    public void setListaDictaminadores(List<MatrizDictaminadorVO> listaDictaminadores) {
        this.listaDictaminadores = listaDictaminadores;
    }

    public List<MatrizDictaminadorVO> getListaDictaminadores() {
        return listaDictaminadores;
    }

    public void setDictaminador(MatrizDictaminadorVO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public MatrizDictaminadorVO getDictaminador() {
        return dictaminador;
    }
}
