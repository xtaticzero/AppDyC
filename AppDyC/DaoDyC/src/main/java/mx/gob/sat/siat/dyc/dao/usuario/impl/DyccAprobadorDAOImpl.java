package mx.gob.sat.siat.dyc.dao.usuario.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.AprobadorDTOMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.AprobadorMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorSolMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DyccAprobadorAdministrarSolMApper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.MAprobadorMapper;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class DyccAprobadorDAOImpl implements DyccAprobadorDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static Logger log = Logger.getLogger(DyccAprobadorDAOImpl.class.getName());

    private List<AprobadorVO> aprobadores;

    @Override
    public List<DyccAprobadorDTO> seleccionar() {
        return jdbcTemplateDYC.query("SELECT * FROM DYCC_APROBADOR ", new AprobadorDTOMapper());
    }

    @Override
    public DyccAprobadorDTO encontrar(Integer numEmpleado) {
        try {
            final String query = SQLOracleDyC.CONSULTA_DYCC_APROVADOR + " AND ACTIVO = 1";
            return jdbcTemplateDYC.queryForObject(query, new Object[] { numEmpleado }, new AprobadorDTOMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_APROVADOR + " AND ACTIVO = 1" + ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleado);
        }
        return null;
    }
    
    @Override
    public DyccAprobadorDTO encontrarEmpAp(Integer numEmpleado){
        try {
            final String query = SQLOracleDyC.CONSULTA_DYCC_APROVADOR.toString();
            return jdbcTemplateDYC.queryForObject(query, new Object[] { numEmpleado }, new AprobadorDTOMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_APROVADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleado);
        }
        return null;
    }

    @Override
    public List<DyccAprobadorDTO> obtenerListaAprobadores(DyccAprobadorDTO aprobador) {
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(aprobador);
            return template.query(SQLOracleDyC.CONSULTA_APROBADORES_DYCC.toString().replace(ConstantesDyC1.AND,
                                                                                            "AND A.ACTIVO = 1 "),
                                  sqlNamedParameters, new AprobadorDTOMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_APROBADORES_DYCC.toString().replace(ConstantesDyC1.AND, " ") +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(aprobador));
            throw dae;
        }
    }


    @Override
    public List<AprobadorVO> obtenerAprobadores(AprobadorVO aprobador, boolean isActivo) {
        this.aprobadores = new ArrayList<AprobadorVO>();
        StringBuilder consulta = new StringBuilder();
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(aprobador);
            consulta.append(SQLOracleDyC.CONSULTA_APROBADORES.toString());
            consulta.replace(consulta.indexOf(ConstantesDyC1.INNER),
                             consulta.indexOf(ConstantesDyC1.INNER) + ConstantesDyC1.INNER.length(),
                             SQLOracleDyC.CONSULTA_INNERJOIN_APROBADORES.toString());
            consulta.replace(consulta.indexOf(ConstantesDyC1.ROWS),
                             consulta.indexOf(ConstantesDyC1.ROWS) + ConstantesDyC1.ROWS.length(),
                             SQLOracleDyC.CONSULTA_APROBADORES_OBS.toString());

            /**if (null != aprobador.getClaveSir()) {
                log.info("Aprobador valido");
                if (aprobador.getClaveSir() == ConstantesDyC1.ACFECF ||
                    aprobador.getClaveSir() == ConstantesDyC1.ACFSF ||
                    aprobador.getClaveSir() == ConstantesDyC1.ACFGCD) {
                    consulta.replace(consulta.indexOf(ConstantesDyC1.AND),
                                     consulta.indexOf(ConstantesDyC1.AND) + ConstantesDyC1.AND.length(),
                                     CONSULTA_IS_ACFGC + "  " + ConstantesDyC1.AND);
                }
            }*/
            consulta.replace(consulta.indexOf(ConstantesDyC1.AND),
                             consulta.indexOf(ConstantesDyC1.AND) + ConstantesDyC1.AND.length(),
                             SQLOracleDyC.CONSULTA_APROBADORES_ACTIVO.toString());

            this.aprobadores = template.query(consulta.toString(), sqlNamedParameters, new MAprobadorMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      consulta.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(aprobador));
            throw dae;
        }
        return this.aprobadores;
    }

    @Override
    public AprobadorVO obtenerAprobadoresDycc(Integer numEmpleadoAprov) {
        try {
            return (AprobadorVO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ALTA_APROBADORES.toString(),
                                                               new Object[] { numEmpleadoAprov },
                                                               new AprobadorMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DICTAMINADOR_DYCC + ConstantesDyC1.TEXTO_3_ERROR_DAO);
        }

        return null;
    }

    @Override
    public void insertarAprobador(AprobadorVO aprobador) {
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(aprobador);
            template.update(SQLOracleDyC.INSERTA_APROBADOR.toString(), sqlNamedParameters);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTA_APROBADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(aprobador));
            throw dae;
        }
    }

    @Override
    public void actualizarAprobador(AprobadorVO aprobador) {
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(aprobador);
            template.update(SQLOracleDyC.ACTUALIZA_APROBADOR.toString(), sqlNamedParameters);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZA_APROBADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(aprobador));
            throw dae;
        }
    }

    @Override
    public List<DyccAprobadorDTO> consultarAprobadores(int unidad, int centroCosto) {
        try {
            return this.jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERJEFESSUPERIORES.toString(),
                                              new Object[] { unidad, centroCosto },
                                              new DyccAprobadorAdministrarSolMApper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERJEFESSUPERIORES + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      unidad);
            throw dae;
        }
    }

    @Override
    public List<DyccAprobadorDTO> consultarAprobadoresAprobarResol(int unidad, int centroCosto, int numEmpleado,
                                                                   int nivel) {
        try {
            return this.jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERJEFESSUPERIORES_APROBARRESOL.toString(),
                                              new Object[] { unidad, numEmpleado, nivel, centroCosto, numEmpleado,
                                                             nivel }, new DyccAprobadorAdministrarSolMApper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERJEFESSUPERIORES_APROBARRESOL.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + unidad);
            throw dae;
        }
    }

    @Override
    public DyccAprobadorDTO consultarAprobadorActivoXClaveAdm(Integer claveAdm) {
        DyccAprobadorDTO dyccAprobadorDTO;
        try {
            dyccAprobadorDTO =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTAR_APROBADOR_ACTIVO_X_CLAVEADMA.toString(),
                                                        new Object[] { claveAdm }, new AprobadorMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTAR_APROBADOR_ACTIVO_X_CLAVEADMA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      claveAdm);
            throw dae;
        }
        return dyccAprobadorDTO;
    }
    
    @Override
    public DyccAprobadorDTO consultarAprobadorPorClaveAdm(Integer claveAdm) {
        DyccAprobadorDTO dyccAprobadorDTO;
        try {
            dyccAprobadorDTO =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGO_CLAVEADMAPROBADOR.toString(),
                                                        new Object[] { claveAdm }, new AprobadorMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGO_CLAVEADMAPROBADOR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      claveAdm);
            throw dae;
        }
        return dyccAprobadorDTO;
    }

    @Override
    public List<DyccAprobadorDTO> consultaAprobadorConCargaTrabajo(Integer claveAdm, Integer centroCostoOp,
                                                                   Integer opcion) {
        String query =
            "SELECT * " + "FROM (" + "  SELECT Q.* " + "  ,(SELECT NVL(SUM(DOCTOS),0) " + "    FROM ( " + "      SELECT COUNT(*) AS DOCTOS, DD.NUMEMPLEADOAPROB " +
            "      FROM DYCP_SOLICITUD DS " +
            "      INNER JOIN DYCP_SERVICIO DSER ON DSER.NUMCONTROL = DS.NUMCONTROL " +
            "      INNER JOIN DYCT_DOCUMENTO DD ON DD.NUMCONTROL = DS.NUMCONTROL " +
            "      WHERE DD.IDESTADODOC IN (5, 10, 11)  " + "      GROUP BY DD.NUMEMPLEADOAPROB " + "      UNION " +
            "      SELECT COUNT(*) AS DOCTOS, DC.NUMEMPLEADOAPROB " + "      FROM DYCP_COMPENSACION DC " +
            "      INNER JOIN DYCP_SERVICIO DSER ON DSER.NUMCONTROL = DC.NUMCONTROL " +
            "      WHERE IDESTADOCOMP IN(4, 8)  " + "      GROUP BY DC.NUMEMPLEADOAPROB " + "      UNION " +
            "      SELECT COUNT(*) AS DOCTOS, DF.NUMEMPLEADOAPROB " + "      FROM DYCT_FACULTADES DF " +
            "      WHERE DF.FECHAFIN IS NULL  " + "      GROUP BY DF.NUMEMPLEADOAPROB " + "    ) SQ " +
            "      WHERE SQ.NUMEMPLEADOAPROB = Q.NUMEMPLEADO " + "  ) AS DOCTOS " + "  FROM ( " +
            "  SELECT AP.*, CA.DEPIDOP, CA.CCOSTOOP " + "  FROM DYCC_APROBADOR AP " +
            "  INNER JOIN DYCV_EMPLEADO EMP ON EMP.NO_EMPLEADO = AP.NUMEMPLEADO " +
            "  LEFT JOIN SEGT_CAMBIOADSCRIPCION CA ON CA.RFCEMPLEADO = AP.RFC AND CA.FECHAFIN IS NULL " +
            "  WHERE AP.CENTROCOSTO = ? AND" + " AP.CLAVEADM = ? " + "  AND AP.ACTIVO = 1 " +
            "  AND EMP.ESTATUS = 'A' " + "    UNION ALL " + "  SELECT AP.*, CA.DEPIDOP, CA.CCOSTOOP " +
            "  FROM DYCC_APROBADOR AP " + "  INNER JOIN DYCV_EMPLEADO EMP ON EMP.NO_EMPLEADO = AP.NUMEMPLEADO " +
            "  INNER JOIN SEGT_CAMBIOADSCRIPCION CA ON AP.RFC = CA.RFCEMPLEADO " + "  AND CA.CCOSTOOP = ? " +
            "  AND CA.FECHAFIN IS NULL " + "  AND AP.ACTIVO = 1 " + "  AND EMP.ESTATUS = 'A' " + "  ) Q " + ") QP " +
            (opcion == ConstantesDyCNumerico.VALOR_1 ? " WHERE QP.DOCTOS > 0" : "");

        log.info("###################################################################################");
        log.info("CONSULTA - APROBADORES @@@");
        log.info("Parametros : [ claveAdm : " + claveAdm + " ] [ centroCostoOp : " + centroCostoOp + " ]");
        log.info("Consulta [[ " + query + " ]]");
        log.info("###################################################################################");

        try {
            return jdbcTemplateDYC.query(query, new Object[] { centroCostoOp, claveAdm, centroCostoOp }, new AprobadorDTOMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query);
            throw dae;
        }
    }

    public void setAprobadores(List<AprobadorVO> aprobadores) {
        this.aprobadores = aprobadores;
    }

    public List<AprobadorVO> getAprobadores() {
        return aprobadores;
    }

    @Override
    public List<DictaminadorSolBean> consultarTramitesAsociadosAprobador(Integer numeroEmpleado) {
        List<DictaminadorSolBean> listaTramites;

        log.info("Cargando la lista de los tramites del aprobador -  dao");

        try {
            listaTramites =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_TRAMITES_APROBADOR.toString(), new Object[] { numeroEmpleado,
                                                                                                                   numeroEmpleado,
                                                                                                                   numeroEmpleado,
                                                                                                                   numeroEmpleado,
                                                                                                                   numeroEmpleado },
                                               new DictaminadorSolMapper());

        } catch (DataAccessException dae) {

            log.info("numeroEmpleado : " + numeroEmpleado);
            log.info(String.format("consulta : [ %s ] ", SQLOracleDyC.CONSULTA_TRAMITES_APROBADOR.toString()));

            throw dae;
        }

        log.info("Cargando la lista de los tramites del aprobador - dao : " + listaTramites.size());

        return listaTramites;
    }

    @Override
    public List<DictaminadorSolBean> consultarTramitesAsociadosAprobadorPorFecha(Integer numEmpleado, Date fechaInicio,
                                                                                 Date fechaFin) {
        List<DictaminadorSolBean> listaTramites;

        log.info("Cargando la lista de los tramites del aprobador filtrado por fecha -  dao");

        try {
            listaTramites =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_TRAMITES_APROBADOR_FILTROXFECHA.toString(),
                                               new Object[] { numEmpleado, fechaInicio, fechaFin, numEmpleado,
                                                              numEmpleado, fechaInicio, fechaFin, numEmpleado,
                                                              fechaInicio, fechaFin }, new DictaminadorSolMapper());

        } catch (DataAccessException dae) {
            log.info("numeroEmpleado : " + numEmpleado);
            log.info(String.format("consulta : [ %s ] ", SQLOracleDyC.CONSULTA_TRAMITES_APROBADOR.toString()));

            throw dae;
        }

        log.info("Cargando la lista de los tramites del aprobador - dao : " + listaTramites.size());

        return listaTramites;
    }
}
