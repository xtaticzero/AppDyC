/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.dycadministracion.impl;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dao.dycadministracion.DyCAdministracionDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.dycadministracion.impl.mapper.AdmSolicitudesdycMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion.AdmSolicitudesdycVO;
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


@Repository(value = "dyCAdministracionDAO")
public class DyCAdministracionDAOImpl implements DyCAdministracionDAO{

    private static Logger log = Logger.getLogger(DyCAdministracionDAOImpl.class.getName());

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List<AdmSolicitudesdycVO> obtenerListaSolicitud(AdmSolicitudesdycVO solicitud) throws SQLException {
        try {

            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);

            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(solicitud);
            
            return template.query(SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSOLICITUDESPARAM.toString().concat(
                                                              "ORDER BY NUMCONTROL DESC "), sqlNamedParameters,
                                                              new AdmSolicitudesdycMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSOLICITUDESPARAM);
            throw dae;
        }
    }

    @Override
    public List<AdmSolicitudesdycVO> obtenerListaCompensaciones(AdmSolicitudesdycVO solicitud) throws SQLException {
        try {

            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);

            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(solicitud);

            return (template.query(SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSCOMPENSACIONPARAM.toString(),
                                                              sqlNamedParameters, new AdmSolicitudesdycMapper()));

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSCOMPENSACIONPARAM.toString());
            
            throw dae;
        }
      
    }

    @Override
    public List<AdmSolicitudesdycVO> obtenerListaGeneral(AdmSolicitudesdycVO solicitud) throws SQLException {
        try {

            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);

            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(solicitud);

            return (template.query(SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSOLICITUDESPARAM.toString().concat(" UNION ALL ").concat(
                                                              SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSCOMPENSACIONPARAM.toString()),
                                                              sqlNamedParameters, new AdmSolicitudesdycMapper()));

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSOLICITUDESPARAM + " UNION ALL" +
                      SQLOracleDyC.CONSULTARDYC_OBTENERFILTROSCOMPENSACIONPARAM);
            throw dae;
        }
    }
}
