package mx.gob.sat.siat.dyc.dao.usuario.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorMapperDycc;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorSolMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorVsMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DyccSolDictaminadorMapper;
import mx.gob.sat.siat.dyc.dao.util.DBLinkDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.DyccDictaminadorSolDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


/**
 * @autor J. Isaac Carbajal Bernal
 */
@Repository
public class DyccDictaminadorDAOImpl implements DyccDictaminadorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Autowired(required =true)
    private DBLinkDAO bBLinkDAO;

    private static final Logger LOG = Logger.getLogger(DyccDictaminadorDAOImpl.class);
    private static final String Y = " y ";
    private static final String QUERY_DICTAMINADORES_1 =
        "SELECT D.NUMEMPLEADO, D.RFC, " + "'' un, '' admon_gral,'' activo_portal,'' claveadmop,'' dcomision, " +
        "    D.nombre as nombre, " + "    D.apellidopaterno as paterno, " + "    D.apellidomaterno as materno, " +
        "    D.clavedepto, " + "    D.centrocosto, " + "    D.claveadm, " + "    D.observaciones, " +
        "    D.activo, " + "    nvl(D.ccostoop,0) as ccomision, " + "    D.email, " + "    D.centro_costo_descr254, " +
        "    D.DOCTOS AS cargatrabajo " + "FROM ( " + "  SELECT Q.* " + "  , (SELECT NVL(SUM(PUNTOS),0) " +
        "    FROM ( " + "      select nvl(sum(PUNTOSASIGNACION),0) as puntos, numempleadodict " +
        "      from dycp_servicio s " +
        "      inner join dycc_tipotramite t  on t.idtipotramite=s.idtipotramite     " +
        "      INNER JOIN DYCP_SOLICITUD A ON S.NUMCONTROL = A.NUMCONTROL AND A.IDESTADOSOLICITUD IN(3,4,6,16) " +
        "      group by numempleadodict " + "      " + "        UNION ALL " + "        " +
        "    select nvl(sum(PUNTOSASIGNACION),0) as puntos, numempleadodict " + "    from dycp_servicio s " +
        "    inner join dycc_tipotramite t  on t.idtipotramite=s.idtipotramite     " +
        "    INNER JOIN DYCP_COMPENSACION B ON S.NUMCONTROL = B.NUMCONTROL AND B.IDESTADOCOMP IN(3,4,6) " +
        "    group by numempleadodict " + "    ) SQ " + "      WHERE SQ.numempleadodict = Q.NUMEMPLEADO " +
        "  ) AS DOCTOS " + "  FROM ( " + "  SELECT DI.*, CA.DEPIDOP, CA.CCOSTOOP, EMP.centro_costo_descr254 " +
        "  FROM DYCC_DICTAMINADOR DI " +
        "  INNER JOIN  SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP ON EMP.NO_EMPLEADO = DI.NUMEMPLEADO" +
        "  LEFT JOIN SEGT_CAMBIOADSCRIPCION CA ON CA.RFCEMPLEADO = DI.RFC AND CA.FECHAFIN IS NULL " + "  WHERE  ";

    private static final String QUERY_DICTAMINADORES_CONDICION_CLAVEADM = " DI.CLAVEADM = ? AND ";

    private static final String QUERY_DICTAMINADORES_2 =
        "      DI.ACTIVO = 1 " + "  AND CA.DEPIDOP IS NULL " + "  AND EMP.ESTATUS = 'A' " + "    UNION ALL " +
        "  SELECT DI.*, CA.DEPIDOP, CA.CCOSTOOP,  '' centro_costo_descr254 " + "  FROM DYCC_DICTAMINADOR DI " +
        "  INNER JOIN  SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP ON EMP.NO_EMPLEADO = DI.NUMEMPLEADO " +
        "  INNER JOIN SEGT_CAMBIOADSCRIPCION CA ON DI.RFC = CA.RFCEMPLEADO AND ";

    private static final String QUERY_DICTAMINADORES_CONDICION_C_COSTO = " CA.CCOSTOOP = ? AND ";

    private static final String QUERY_DICTAMINADORES_3 =
        "      CA.FECHAFIN IS NULL " + "  AND DI.ACTIVO = 1 " + "  AND EMP.ESTATUS = 'A' " + "  AND DI.CLAVEADM = ? " +
        "  ) Q " + ") D " + "WHERE 1=1 ";

    private static final String QUERY_DICTAMINADORES_3_SIN_ADM =
        "      CA.FECHAFIN IS NULL " + "  AND DI.ACTIVO = 1 " + "  AND EMP.ESTATUS = 'A' " + "  ) Q " + ") D " +
        "WHERE 1=1 ";


    private static final String QUERY_DICTAMINADORES =
        QUERY_DICTAMINADORES_1 + QUERY_DICTAMINADORES_CONDICION_CLAVEADM + QUERY_DICTAMINADORES_2 +
        QUERY_DICTAMINADORES_CONDICION_C_COSTO + QUERY_DICTAMINADORES_3;

    private static final String QUERY_DICTAMINADORES_CONDICION_NUM_EMP_DIC = " AND D.NUMEMPLEADO = ? ";

    private static final String CONSULTA_DICTAMINADOR_POR_NUMERO_EMPLEADO =
        QUERY_DICTAMINADORES_1 + QUERY_DICTAMINADORES_2 + QUERY_DICTAMINADORES_3_SIN_ADM +
        QUERY_DICTAMINADORES_CONDICION_NUM_EMP_DIC;

    private static final String BUSQUEDA_CAMPOS_DICTAMINADORES =
        "SELECT VT.*,\nREPLACE(INITCAP(NVL(DTO.NOMBRE,' ')),'') DCOMISION, \nNVL(DTO.CLAVE_AGRS,0) CLAVEADMOP  \nFROM (  \n";
    private static final String BUSQUEDA_DICTAMINADORES_UNION = "            UNION ALL \n";
    private static final String CIERRE_BUSQUEDA_DICTAMINADORES =
        ") VT \nLEFT JOIN DYCC_UNIDADADMVA DTO ON VT.CLAVEADM = DTO.CLAVE_SIR WHERE 1=1";

    private static final String BUSQUEDA_DICTAMINADORES_1 =
        "        SELECT EMP.EMPLEADO NUMEMPLEADO,EMP.UN,EMP.ADMON_GRAL, EMP.RFC,\n" +
        "        REPLACE(INITCAP(EMP.NOMBRE),'') NOMBRE,\n" +
        "        REPLACE(INITCAP(EMP.PATERNO),'') PATERNO,\n" +
        "        REPLACE(INITCAP(EMP.MATERNO),'') MATERNO,\n" +
        "        EMP.GENERAL_DEPTID CLAVEDEPTO, EMP.CENTRO_COSTO CENTROCOSTO,\n" +
        "        REPLACE(INITCAP(EMP.CENTRO_COSTO_DESCR254),'') CENTRO_COSTO_DESCR254 ,\n" +
        "        NVL(CA.CCOSTOOP,0) CCOMISION,\n" +
        "        DECODE( EMP.ESTATUS, 'A', 1 , 0 ) ACTIVO_PORTAL,\n" +
        "        DI.OBSERVACIONES, DI.ACTIVO, DI.CARGATRABAJO,\n" +
        "        EMP.EMAIL,\n" +
        "        DI.CLAVEADM\n" +
        "        FROM DYCC_DICTAMINADOR DI\n" +
        "        INNER JOIN  SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP ON EMP.NO_EMPLEADO = DI.NUMEMPLEADO \n" +
        "        LEFT JOIN SEGT_CAMBIOADSCRIPCION CA ON CA.RFCEMPLEADO = DI.RFC AND CA.FECHAFIN IS NULL\n" +
        "        WHERE DI.CLAVEADM = :claveAdm AND CA.DEPIDOP IS NULL \n";

    private static final String BUSQUEDA_DICTAMINADORES_2 =
        "        SELECT EMP.EMPLEADO NUMEMPLEADO,EMP.UN,EMP.ADMON_GRAL, EMP.RFC,\n" +
        "        REPLACE(INITCAP(EMP.NOMBRE),'') NOMBRE,\n" +
        "        REPLACE(INITCAP(EMP.PATERNO),'') PATERNO,\n" +
        "        REPLACE(INITCAP(EMP.MATERNO),'') MATERNO,\n" +
        "        EMP.GENERAL_DEPTID CLAVEDEPTO, EMP.CENTRO_COSTO CENTROCOSTO,\n" +
        "        REPLACE(INITCAP(EMP.CENTRO_COSTO_DESCR254),'') CENTRO_COSTO_DESCR254 ,\n" +
        "        NVL(CA.CCOSTOOP,0) CCOMISION,\n" +
        "        DECODE( EMP.ESTATUS, 'A', 1 , 0 ) ACTIVO_PORTAL,\n" +
        "        DI.OBSERVACIONES, DI.ACTIVO, DI.CARGATRABAJO,\n" +
        "        EMP.EMAIL,\n" +
        "        DI.CLAVEADM\n" +
        "        FROM DYCC_DICTAMINADOR DI \n" +
        "        INNER JOIN  SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP ON EMP.NO_EMPLEADO = DI.NUMEMPLEADO \n" +
        "        INNER JOIN SEGT_CAMBIOADSCRIPCION CA ON DI.RFC = CA.RFCEMPLEADO \n" +
        "        AND CA.CCOSTOOP = (SELECT CLAVE_AGRS FROM DYCC_UNIDADADMVA WHERE CLAVE_SIR = :claveAdm) \n" +
        "        AND DI.CLAVEADM = :claveAdm \n" +
        "        AND CA.FECHAFIN IS NULL \n";

    private static final String CONDICION_DICTAMINADOR_ACTIVO = "        AND DI.ACTIVO = 1 AND EMP.ESTATUS = 'A' \n";

    private static final String ERROR_CONEXION_BD_CATALOGOS =
        "No se pudo establecer la conexión al DBLINK al abrir el catalogo";

    private List<DyccDictaminadorDTO> lista;
    private List<DictaminadorVO> listaDictaminadorVO;
    private List<DyccDictaminadorSolDTO> dyccDictaminadorSol;
    private List<DictaminadorSolBean> dictaminadorSol;

    private NamedParameterJdbcTemplate nombreTemplate;

    public DyccDictaminadorDAOImpl() {
        lista = new ArrayList<DyccDictaminadorDTO>();
        listaDictaminadorVO = new ArrayList<DictaminadorVO>();
        dyccDictaminadorSol = new ArrayList<DyccDictaminadorSolDTO>();
    }

    @Override
    public DyccDictaminadorDTO encontrar(Integer numEmpleado) {
        LOG.debug("numEmpleado ->" + numEmpleado + "<-");
        try {
            return this.jdbcTemplateDYC.queryForObject(" SELECT * FROM DYCC_DICTAMINADOR WHERE NUMEMPLEADO = ? AND ACTIVO = 1",
                                                       new Object[] { numEmpleado }, new DictaminadorMapper());
        } catch (DataAccessException dae) {
            LOG.info(dae.getMessage() + "; numEmpleado ->" + numEmpleado + "<-");
        }
        return null;
    }
    
    @Override
    public DyccDictaminadorDTO encontrarEmpDic(Integer numEmpleado) {
        LOG.debug("numEmpleado ->" + numEmpleado + "<-");
        try {
            return this.jdbcTemplateDYC.queryForObject(" SELECT * FROM DYCC_DICTAMINADOR WHERE NUMEMPLEADO = ?",
                                                       new Object[] { numEmpleado }, new DictaminadorMapper());
        } catch (DataAccessException dae) {
            LOG.info(dae.getMessage() + "; numEmpleado ->" + numEmpleado + "<-");
        }
        return null;
    }

    @Override
    public List<DyccDictaminadorDTO> seleccionar() {
        return this.jdbcTemplateDYC.query(" SELECT * FROM DYCC_DICTAMINADOR ", new DictaminadorMapper());
    }

    @Override
    public List<DyccDictaminadorDTO> seleccionarOrder(String order) {
        String query = " SELECT * FROM DYCC_DICTAMINADOR " + order;
        return this.jdbcTemplateDYC.query(query, new DictaminadorMapper());
    }

    @Override
    public List<DyccDictaminadorDTO> selecOrderXClaveadm(Integer claveAdm, String order) {
        String query = " SELECT * FROM DYCC_DICTAMINADOR WHERE CLAVEADM = ? AND ACTIVO = 1 " + order;
        return this.jdbcTemplateDYC.query(query, new Object[] { claveAdm }, new DictaminadorMapper());
    }

    @Override
    public List<DyccDictaminadorDTO> consultarDictaminadores(int unidad) {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEDICTAMINADORES.toString(),
                                         new Object[] { unidad }, new DictaminadorMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEDICTAMINADORES + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      unidad);
            throw dae;
        }
    }

    @Override
    public List<DictaminadorVO> consultarDictaminadorPorCargaAleatorio(int idUnidadAdmva, String centroCosto) {
        try {
            String query = QUERY_DICTAMINADORES + " ORDER BY D.DOCTOS ASC, DBMS_RANDOM.VALUE";
            return jdbcTemplateDYC.query(query, new Object[] { idUnidadAdmva, centroCosto, idUnidadAdmva },
                                         new DictaminadorVsMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_GENERAL_CARGA_TODO + SQLOracleDyC.CONSULTA_UNION_PUNTOS +
                      SQLOracleDyC.CONSULTA_CARGA_TODO + SQLOracleDyC.CONSULTA_GROUP_TODO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + idUnidadAdmva + Y + centroCosto);
            throw dae;
        }
    }

    @Override
    public List<DictaminadorVO> consultarDictaminadoresPorCveSIR(int cveSIR, String queryParams, String claveAgrs) {
        String query = QUERY_DICTAMINADORES + queryParams + " ORDER BY D.NOMBRE";

        try {
            listaDictaminadorVO =
                    jdbcTemplateDYC.query(query, new Object[] { cveSIR, claveAgrs, cveSIR }, new DictaminadorVsMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_GENERAL_CARGA_MAYOR + SQLOracleDyC.CONSULTA_UNION_PUNTOS +
                      SQLOracleDyC.CONSULTA_CARGA_MAYOR_TRABAJO + queryParams +
                      SQLOracleDyC.CONSULTA_GROUP_CARGAMAYOR + ConstantesDyC1.TEXTO_3_ERROR_DAO + cveSIR + Y +
                      claveAgrs);
            throw dae;
        }
        return this.getListaDictaminadorVO();
    }

    @Override
    public List<DictaminadorVO> consultarDictaminadoresPorCveSIRTodos(int cveSIR, String queryParams,
                                                                      String claveAgrs) {
        try {
            listaDictaminadorVO =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_GENERAL_CARGA_TODO.toString() + SQLOracleDyC.CONSULTA_UNION_PUNTOS.toString() +
                                               SQLOracleDyC.CONSULTA_CARGA_TODO.toString() + queryParams +
                                               SQLOracleDyC.CONSULTA_GROUP_TODO.toString(),
                                               new Object[] { cveSIR, claveAgrs }, new DictaminadorVsMapper());
            /**Se ordena la lista de dictaminadores por nombre completo*/
            Collections.sort(listaDictaminadorVO, new Comparator<DictaminadorVO>() {
                    public int compare(DictaminadorVO dict1, DictaminadorVO dict2) {
                        return dict1.getNombreCompleto().compareTo(dict2.getNombreCompleto());
                    }
                });
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_GENERAL_CARGA_TODO + SQLOracleDyC.CONSULTA_UNION_PUNTOS +
                      SQLOracleDyC.CONSULTA_CARGA_TODO + queryParams + SQLOracleDyC.CONSULTA_GROUP_TODO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + cveSIR + Y + claveAgrs);
            throw dae;
        }
        return this.getListaDictaminadorVO();
    }

    @Override
    public List<DyccDictaminadorSolDTO> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador) {
        try {
            dyccDictaminadorSol =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR.toString(),
                                               new Object[] { selectedDictaminador.getNumEmpleado() },
                                               new DyccSolDictaminadorMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(selectedDictaminador));
            throw dae;
        }
        return dyccDictaminadorSol;
    }

    @Override
    public List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(long numEmpleado) {
        try {
            lista =
this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARDICTAMINADORPORNOEMP.toString(), new Object[] { numEmpleado },
                           new DictaminadorMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARDICTAMINADORPORNOEMP + ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleado);
            throw dae;
        }
        return this.getLista();
    }

    public void setLista(List<DyccDictaminadorDTO> lista) {
        this.lista = lista;
    }

    public List<DyccDictaminadorDTO> getLista() {
        return lista;
    }

    public void setListaDictaminadorVO(List<DictaminadorVO> listaDictaminadorVO) {
        this.listaDictaminadorVO = listaDictaminadorVO;
    }

    public List<DictaminadorVO> getListaDictaminadorVO() {
        return listaDictaminadorVO;
    }

    @Override
    public List<DictaminadorSolBean> consultarSolicitudesAsociadasDictaminador(DyccDictaminadorDTO selectedDictaminador) {
        try {
            dictaminadorSol =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR.toString(),
                                               new Object[] { selectedDictaminador.getNumEmpleado(),
                                                              selectedDictaminador.getNumEmpleado() },
                                               new DictaminadorSolMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(selectedDictaminador));
            throw dae;
        }
        return dictaminadorSol;
    }

    @Override
    public List<DictaminadorSolBean> consultarSolicitudesAsociadasDictaminadorXFecha(DyccDictaminadorDTO selectedDictaminador,
                                                                                     Date fechaInicio, Date fechaFin) {
        try {
            dictaminadorSol =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR_FILTROXFECHA.toString(),
                                               new Object[] { selectedDictaminador.getNumEmpleado(), fechaInicio,
                                                              fechaFin, selectedDictaminador.getNumEmpleado(),
                                                              fechaInicio, fechaFin }, new DictaminadorSolMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR_FILTROXFECHA +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(selectedDictaminador));
            throw dae;
        }
        return dictaminadorSol;
    }

    public void setDictaminadorSol(List<DictaminadorSolBean> dictaminadorSol) {
        this.dictaminadorSol = dictaminadorSol;
    }

    public List<DictaminadorSolBean> getDictaminadorSol() {
        return dictaminadorSol;
    }

    @Override
    public List<DictaminadorVO> obtenerDictaminadores(DictaminadorVO dictaminador,
                                                      Boolean activo) throws SQLException {
        String consulta = getConsultaObtenerDictaminadores();

        try {

            if (bBLinkDAO.dbLinkConexion()) {
                NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
                SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminador);

                listaDictaminadorVO = template.query(consulta, sqlNamedParameters, new DictaminadorVsMapper());

            } else {
                LOG.error(ERROR_CONEXION_BD_CATALOGOS);
            }

        } catch (DataAccessException e) {
            LOG.error(getMensajeErrorConsultaObtenerDictaminadores(consulta, e, dictaminador));
            throw e;
        }

        return this.listaDictaminadorVO;
    }

    private String getConsultaObtenerDictaminadores() {
        StringBuilder consulta = new StringBuilder();

        consulta.append(BUSQUEDA_CAMPOS_DICTAMINADORES).append(BUSQUEDA_DICTAMINADORES_1).append(BUSQUEDA_DICTAMINADORES_UNION).append(BUSQUEDA_DICTAMINADORES_2).append(CIERRE_BUSQUEDA_DICTAMINADORES);

        return consulta.toString();
    }

    private String getMensajeErrorConsultaObtenerDictaminadores(String consulta, DataAccessException e,
                                                                DictaminadorVO dictaminador) {

        StringBuilder mensaje = new StringBuilder();

        mensaje.append(ConstantesDyC1.TEXTO_1_ERROR_DAO).append(e.getMessage()).append(ConstantesDyC1.TEXTO_2_ERROR_DAO).append(consulta).append(ConstantesDyC1.TEXTO_3_ERROR_DAO).append(ExtractorUtil.ejecutar(dictaminador));

        return mensaje.toString();
    }

    @Override
    public List<DictaminadorVO> obtenerDictaminadoresActivos(DictaminadorVO dictaminador) throws SQLException {
        String consulta = getConsultaObtenerDictaminadoresActivos();

        try {

            if (bBLinkDAO.dbLinkConexion()) {
                NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
                SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminador);

                listaDictaminadorVO = template.query(consulta, sqlNamedParameters, new DictaminadorVsMapper());

            } else {
                LOG.error(ERROR_CONEXION_BD_CATALOGOS);
            }

        } catch (DataAccessException e) {
            LOG.error(getMensajeErrorConsultaObtenerDictaminadores(consulta, e, dictaminador));
            throw e;
        }

        return this.listaDictaminadorVO;
    }

    private String getConsultaObtenerDictaminadoresActivos() {
        StringBuilder consulta = new StringBuilder();

        consulta.append(BUSQUEDA_CAMPOS_DICTAMINADORES).append(BUSQUEDA_DICTAMINADORES_1).append(CONDICION_DICTAMINADOR_ACTIVO).append(BUSQUEDA_DICTAMINADORES_UNION).append(BUSQUEDA_DICTAMINADORES_2).append(CONDICION_DICTAMINADOR_ACTIVO).append(CIERRE_BUSQUEDA_DICTAMINADORES);

        return consulta.toString();
    }

    @Override
    public List<DictaminadorVO> obtenerDictaminadoresAgs(DictaminadorVO dictaminador,
                                                         Boolean activo) throws SIATException {
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminador);

            if (activo) {
                this.setListaDictaminadorVO(template.query(SQLOracleDyC.CONSULTA_INFO_EMPLEADO.toString().replace(ConstantesDyC1.INNER,
                                                                                                                  SQLOracleDyC.CONSULTA_INNERJOIN_DICTAMINADOR).replace(ConstantesDyC1.ROWS,
                                                                                                                                                                        SQLOracleDyC.CONSULTA_DICTAMINADOR_OBS).replace(ConstantesDyC1.AND,
                                                                                                                                                                                                                        SQLOracleDyC.CONSULTA_DICTAMINADOR_ACTIVO),
                                                           sqlNamedParameters, new DictaminadorVsMapper()));
            } else if (!activo) {
                this.setListaDictaminadorVO(template.query(SQLOracleDyC.CONSULTA_INFO_EMPLEADO.toString().replace(ConstantesDyC1.INNER,
                                                                                                                  SQLOracleDyC.CONSULTA_INNERJOIN_DICTAMINADOR).replace(ConstantesDyC1.ROWS,
                                                                                                                                                                        SQLOracleDyC.CONSULTA_DICTAMINADOR_OBS).replace(ConstantesDyC1.AND,
                                                                                                                                                                                                                        SQLOracleDyC.CONSULTA_DICTAMINADOR_OR_ACTIVO),
                                                           sqlNamedParameters, new DictaminadorVsMapper()));
            }
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_INFO_EMPLEADO.toString().replace(ConstantesDyC1.INNER,
                                                                             SQLOracleDyC.INNERJOINDYCCDICTAMINADOR).replace(ConstantesDyC1.ROWS,
                                                                                                                             SQLOracleDyC.CONSULTA_DICTAMINADOR_OBS).replace(ConstantesDyC1.AND,
                                                                                                                                                                             SQLOracleDyC.CONSULTA_DICTAMINADOR_OR_ACTIVO) +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dictaminador));
            throw new SIATException(dae);
        }
        return this.listaDictaminadorVO;
    }


    @Override
    public DictaminadorVO obtenerDictaminador(Integer numeroEmpleadoDictaminador) throws SQLException {
        try {
            String query = CONSULTA_DICTAMINADOR_POR_NUMERO_EMPLEADO;
            return jdbcTemplateDYC.queryForObject(query, new Object[] { numeroEmpleadoDictaminador },
                                                  new DictaminadorVsMapper());

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      numeroEmpleadoDictaminador + " -- " + CONSULTA_DICTAMINADOR_POR_NUMERO_EMPLEADO + " : " +
                      numeroEmpleadoDictaminador);

            throw dae;
        }
    }


    public List<DycpServicioDTO> obtenerSolicitudServicio(DyccDictaminadorDTO dictaminador) throws SIATException {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_SOLICITUDES_COMPENSACIONES.toString(),
                                         new Object[] { dictaminador.getNumEmpleado(), dictaminador.getNumEmpleado() },
                                         new DycpServicioMapper());

        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_SOLICITUDES_COMPENSACIONES + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            throw new SIATException(dae);
        }
    }


    /**
     * @param dictaminador
     * @throws ClassNotFoundException
     * @throws SQLException
     *
     */
    @Override
    public void insertarDictaminador(DictaminadorVO dictaminador) throws SQLException {
        try {
            this.nombreTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminador);

            this.nombreTemplate.update(SQLOracleDyC.INSERTAR_DICTAMINADOR.toString(), sqlNamedParameters);

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_DICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            ManejadorLogException.getTraceError(dae);
            throw dae;
        }
    }

    @Override
    public void actualizarDictaminador(DictaminadorVO dictaminador) throws SQLException {
        try {
            this.nombreTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(dictaminador);
            this.nombreTemplate.update(SQLOracleDyC.ACTUALIZA_DICTAMINADOR.toString(), sqlNamedParameters);
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZA_DICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador));
            throw dae;
        }
    }

    public void setNombreTemplate(NamedParameterJdbcTemplate nombreTemplate) {
        this.nombreTemplate = nombreTemplate;
    }

    public NamedParameterJdbcTemplate getNombreTemplate() {
        return nombreTemplate;
    }

    @Override
    public Integer obtenerClaveAdmDictaminador(Integer numEmpleadoDict) throws SQLException {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ADM_DICTAMINADOR.toString(),
                                                  new Object[] { numEmpleadoDict }, Integer.class);
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_ADM_DICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO);
        }

        return null;
    }

    @Override
    public DictaminadorVO obtenerDyccDictaminador(Integer numEmpleadoDict) throws SQLException {
        try {
            return (DictaminadorVO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ALTA_DICTAMINADOR.toString(),
                                                                  new Object[] { numEmpleadoDict },
                                                                  new DictaminadorMapperDycc());
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DICTAMINADOR_DYCC + ConstantesDyC1.TEXTO_3_ERROR_DAO);
        }

        return null;
    }
}
