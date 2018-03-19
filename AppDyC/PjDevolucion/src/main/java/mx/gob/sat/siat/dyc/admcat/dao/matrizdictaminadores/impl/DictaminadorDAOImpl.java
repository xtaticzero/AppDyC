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

import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.DictaminadorDAO;
import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper.AccesoUsuarioMapper;
import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper.CambiosAdscripcionMapper;
import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper.DeptoAgsMapper;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.SegtAccesoUsuarioVO;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.SegtCambioAdscripcionVO;
import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


/**
 * Implementaci&oacute;n DAO para Mantener Matriz Dictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 * @date Julio 29, 2014
 */
@Repository(value = "dictaminadorDAO")
public class DictaminadorDAOImpl implements DictaminadorDAO{
    private static Logger log = Logger.getLogger("loggerDYC");

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateADM;

    private List<DictaminadorVO> listaDictaminadores;
    private DictaminadorVO dictaminador;
    private NamedParameterJdbcTemplate nombreTemplate;


    public DictaminadorDAOImpl() {
        super();
        listaDictaminadores = new ArrayList<DictaminadorVO>();
    }

    /**
     * @param jdbcTemplateDYC
     */
    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    /**
     * @return
     */
    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }


    public void setListaDictaminadores(List<DictaminadorVO> listaDictaminadores) {
        this.listaDictaminadores = listaDictaminadores;
    }

    public List<DictaminadorVO> getListaDictaminadores() {
        return listaDictaminadores;
    }

    public void setDictaminador(DictaminadorVO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public DictaminadorVO getDictaminador() {
        return dictaminador;
    }

    public void setJdbcTemplateADM(JdbcTemplate jdbcTemplateADM) {
        this.jdbcTemplateADM = jdbcTemplateADM;
    }

    public JdbcTemplate getJdbcTemplateADM() {
        return jdbcTemplateADM;
    }

    public void setNombreTemplate(NamedParameterJdbcTemplate nombreTemplate) {
        this.nombreTemplate = nombreTemplate;
    }

    public NamedParameterJdbcTemplate getNombreTemplate() {
        return nombreTemplate;
    }

    public boolean dictActivo(DictaminadorVO dictaminadorCmb) throws SQLException {
        boolean paso = Boolean.TRUE;
        try {
            NamedParameterJdbcTemplate pTtemplateADM = new NamedParameterJdbcTemplate(this.getJdbcTemplateADM());
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminadorCmb);

            SegtAccesoUsuarioVO accu =
                pTtemplateADM.query(SQLOracleDyC.CONSULTA_ACCESO_USUARIO_TMP.toString(), sqlNamedParameters, new AccesoUsuarioMapper()).get(0);

            paso = null == accu ? Boolean.TRUE : Boolean.FALSE;

        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_ACCESO_USUARIO_TMP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminadorCmb));
            throw e;
        } catch (Exception ex) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_ACCESO_USUARIO_TMP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminadorCmb));
        }
        return paso;
    }


    public DictaminadorVO dictAdscripcion(DictaminadorVO dictaminadorCmb) throws SQLException {
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.getJdbcTemplateDYC());
            NamedParameterJdbcTemplate templateADM = new NamedParameterJdbcTemplate(this.jdbcTemplateADM);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminadorCmb);


            SegtCambioAdscripcionVO cads;
            DyccDeptAgsDTO deptoAgs;

            cads =
templateADM.query(SQLOracleDyC.CONSULTA_CAMBIO_ADSCRIPCION.toString(), sqlNamedParameters, new CambiosAdscripcionMapper()).get(0);
            SqlParameterSource sqlNamedParametersCads = new BeanPropertySqlParameterSource(cads);

            // EMPLEADO CAMBIO ADSCRIPCIÓN
            deptoAgs = template.query(SQLOracleDyC.CONSULTA_DEPTO_AGS.toString(), sqlNamedParametersCads, new DeptoAgsMapper()).get(0);
            dictaminadorCmb.setDescComision(deptoAgs.getDescripcion());

        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_CAMBIO_ADSCRIPCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminadorCmb));
            throw e;
        } catch (Exception ex) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_CAMBIO_ADSCRIPCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminadorCmb));
        }
        return dictaminadorCmb;
    }

}
