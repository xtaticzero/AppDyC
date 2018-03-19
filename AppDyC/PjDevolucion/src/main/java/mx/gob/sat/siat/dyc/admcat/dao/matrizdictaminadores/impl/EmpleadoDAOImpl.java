/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.EmpleadoDAO;
import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper.EmpleadoVOMapper;
import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper.NivelDireccionMapper;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


/**
 * Implementacion DAO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
@Repository(value = "empleadoDAO")
public class EmpleadoDAOImpl implements EmpleadoDAO{
    private Logger log = Logger.getLogger("loggerDYC");

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    private EmpleadoVO empleado;
    private List<EmpleadoVO> listaEmpleado;

    public EmpleadoDAOImpl() {
        super();
    }

    @Override
    public EmpleadoVO consultaInformacionEmpleado(EmpleadoVO empleado) throws SIATException {
        this.listaEmpleado = new ArrayList<EmpleadoVO>();
        StringBuilder consulta = new StringBuilder();
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.getJdbcTemplateDYC());
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(empleado);
            consulta.append(SQLOracleDyC.CONSULTA_INFO_EMPLEADO);

            consulta.replace(consulta.indexOf(ConstantesDyC1.INNER),
                             (consulta.indexOf(ConstantesDyC1.INNER) + ConstantesDyC1.INNER.length()), "        ");
            consulta.replace(consulta.indexOf(ConstantesDyC1.ROWS),
                             (consulta.indexOf(ConstantesDyC1.ROWS) + ConstantesDyC1.ROWS.length()), "     ");

            consulta.replace(consulta.indexOf(ConstantesDyC1.AND),
                             consulta.indexOf(ConstantesDyC1.AND) + ConstantesDyC1.AND.length(), "    ");
            log.info("\n\n" +
                    consulta);
            listaEmpleado = template.query(consulta.toString(), sqlNamedParameters, new EmpleadoVOMapper());
            if (null != listaEmpleado) {
                this.empleado =
                        ConstantesDyC1.CERO < listaEmpleado.size() ? listaEmpleado.get(ConstantesDyC1.CERO) : new EmpleadoVO();
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + consulta +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(this.empleado));
            throw new SIATException(dae);
        }
        return this.empleado;
    }
    
    @Override
    public EmpleadoVO consultaInfoEmpleadoSinAdm(EmpleadoVO empleado) throws SIATException {
        this.listaEmpleado = new ArrayList<EmpleadoVO>();
        StringBuilder consulta = new StringBuilder();
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.getJdbcTemplateDYC());
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(empleado);
            consulta.append(SQLOracleDyC.CONSULTA_INFO_EMPLEADO_SIN_ADM);

            consulta.replace(consulta.indexOf(ConstantesDyC1.INNER),
                             (consulta.indexOf(ConstantesDyC1.INNER) + ConstantesDyC1.INNER.length()), "        ");
            consulta.replace(consulta.indexOf(ConstantesDyC1.ROWS),
                             (consulta.indexOf(ConstantesDyC1.ROWS) + ConstantesDyC1.ROWS.length()), "     ");

            consulta.replace(consulta.indexOf(ConstantesDyC1.AND),
                             consulta.indexOf(ConstantesDyC1.AND) + ConstantesDyC1.AND.length(), "    ");
            
            log.info("\n\n" + consulta );
            listaEmpleado = template.query(consulta.toString(), sqlNamedParameters, new EmpleadoVOMapper());
            if (null != listaEmpleado && !listaEmpleado.isEmpty() ){
                this.empleado =
                        ConstantesDyC1.CERO < listaEmpleado.size() ? listaEmpleado.get(ConstantesDyC1.CERO) : new EmpleadoVO();
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + consulta +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(this.empleado));
            throw new SIATException(dae);
        }
        return this.empleado;
    }

    public List<EmpleadoVO> consultaNivelDireccion() throws SIATException {
        this.listaEmpleado = new ArrayList<EmpleadoVO>();
        try {
            this.listaEmpleado = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DIRECCION_NIVEL.toString(), new NivelDireccionMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DIRECCION_NIVEL.toString());
            throw new SIATException(dae);
        }
        return this.listaEmpleado;
    }


    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setListaEmpleado(List<EmpleadoVO> listaEmpleado) {
        this.listaEmpleado = listaEmpleado;
    }

    public List<EmpleadoVO> getListaEmpleado() {
        return listaEmpleado;
    }

    public void setEmpleado(EmpleadoVO empleado) {
        this.empleado = empleado;
    }

    public EmpleadoVO getEmpleado() {
        return empleado;
    }
}
