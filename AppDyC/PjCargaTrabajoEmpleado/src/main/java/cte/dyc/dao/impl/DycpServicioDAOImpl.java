package cte.dyc.dao.impl;


import cte.dyc.dao.DycpServicioDAO;
import cte.dyc.dao.impl.mapper.DycpServicioMapper;
import cte.dyc.dto.DycpServicioDTO;
import cte.dyc.dto.EmpleadoDTO;
import cte.dyc.generico.ExtractorUtil;
import cte.dyc.generico.util.common.SIATException;
import cte.dyc.generico.util.constante.ConstantesDyC;
import cte.dyc.generico.util.querys.SQLOracleDyC;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository(value = "dycpServicioDAO")
public class DycpServicioDAOImpl implements DycpServicioDAO, SQLOracleDyC {

    private Logger log = Logger.getLogger("loggerDYC");

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;


    public DycpServicioDAOImpl() {
        super();
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }


    @Override
    public List<DycpServicioDTO> obtenerSolicitudServicio(Integer empleado) throws SIATException {
        List<DycpServicioDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_SOLICITUDES_COMPENSACIONES, new Object[]{empleado,empleado,empleado,empleado}, new DycpServicioMapper());

        } catch (Exception dae) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                      CONSULTA_SOLICITUDES_COMPENSACIONES + ConstantesDyC.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            throw new SIATException(dae);
        }
        return lista;
    }

    @Override
    public Integer consultaDictaminador(EmpleadoDTO empleado) {
        int resultado = 0;
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.getJdbcTemplateDYC());
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(empleado);
            resultado = template.queryForObject(CONSULTA_DICTAMINADORES, sqlNamedParameters, Integer.class);
        } catch (EmptyResultDataAccessException emptyExc) {
            resultado = 0;
        }catch (Exception dae) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                      CONSULTA_DICTAMINADORES + ConstantesDyC.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            throw new SIATException(dae);
        }

        return resultado;
    }

    @Override
    public Integer consultaAprobador(EmpleadoDTO empleado) {
        int resultado = 0;
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.getJdbcTemplateDYC());
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(empleado);
            resultado = template.queryForObject(CONSULTA_APROBADORES, sqlNamedParameters, Integer.class);
        } catch (EmptyResultDataAccessException emptyExc) {
            resultado = 0;
        }catch (Exception dae) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                      CONSULTA_APROBADORES + ConstantesDyC.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            throw new SIATException(dae);
        }

        return resultado;
    }
}
