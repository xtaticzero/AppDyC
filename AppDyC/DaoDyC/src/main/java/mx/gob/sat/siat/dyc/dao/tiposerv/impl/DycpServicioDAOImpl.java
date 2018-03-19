/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.tiposerv.impl;

import java.sql.Types;
import java.util.ArrayList;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.BandejaExportacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.CompensacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpSolicitudMapper;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.TipoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.ContribuyenteMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.UtilsDominio;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmvaMapper;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "iDycpServicioDAO")
public class DycpServicioDAOImpl implements IDycpServicioDAO {

    private static final Logger LOG = Logger.getLogger(DycpServicioDAOImpl.class);

    public static final String SALTO_LINEA = " \n ";

    public static final String SEPARADOR = ", ";

    public static final String DYCC_DICTAMINADOR = "DYCC_DICTAMINADOR";

    public static final String DYCC_TIPOTRAMITE = "DYCC_TIPOTRAMITE";

    public static final int DEVOLUCION = 1;
    
    public static final String MENSAJE_QUERY_DINAMICO = "query consulta count dinamico: ";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String JOINS_SERVICIO = " FROM ((DYCP_SERVICIO SERV LEFT OUTER JOIN DYCT_CONTRIBUYENTE C  ON SERV.NUMCONTROL = C.NUMCONTROL) "
            + " LEFT OUTER JOIN DYCC_DICTAMINADOR D ON SERV.NUMEMPLEADODICT = D.NUMEMPLEADO) "
            + " LEFT OUTER JOIN DYCC_UNIDADADMVA U ON   U.CLAVE_SIR   = serv.claveadm "
            + " LEFT OUTER JOIN DYCC_TIPOTRAMITE T ON SERV.IDTIPOTRAMITE = T.IDTIPOTRAMITE ";

    private static final String JOINS_SERV_SOL_COMP_CONCPTO = " LEFT OUTER JOIN DYCP_SOLICITUD SOL ON SOL.NUMCONTROL = SERV.NUMCONTROL "
            + " LEFT OUTER JOIN DYCP_COMPENSACION COMP ON COMP.NUMCONTROL = SERV.NUMCONTROL "
            + " LEFT OUTER JOIN DYCC_CONCEPTO CPTO ON CPTO.IDCONCEPTO = T.IDCONCEPTO ";

    private static final String JOINS_PAGO = " LEFT OUTER JOIN DYCP_SOLPAGO PAG ON PAG.NUMCONTROL = SERV.NUMCONTROL";

    private static final String JOINS_RESOLUCION_DOC = " LEFT OUTER JOIN DYCT_RESOLUCION RES ON RES.NUMCONTROL = SERV.NUMCONTROL " + SALTO_LINEA
            + " LEFT OUTER JOIN DYCT_DOCUMENTO DOC ON DOC.NUMCONTROL = RES.NUMCONTROL ";

    private static final String ULTIMO_SERVICIO_DEVOLUCION = "SELECT * FROM (SELECT S.* FROM DYCP_SERVICIO S \n"
            + "INNER JOIN DYCP_SOLICITUD SOL ON SOL.NUMCONTROL = S.NUMCONTROL \n"
            + "WHERE S.RFCCONTRIBUYENTE = ? ORDER BY SOL.FECHAINICIOTRAMITE DESC) \n"
            + "WHERE ROWNUM = 1";

    private static final String ULTIMO_SERVICIO_COMPENSACION = "SELECT * FROM (SELECT S.* FROM DYCP_SERVICIO S \n"
            + "INNER JOIN DYCP_COMPENSACION COMP ON COMP.NUMCONTROL = S.NUMCONTROL \n"
            + "WHERE S.RFCCONTRIBUYENTE = ? ORDER BY COMP.FECHAINICIOTRAMITE DESC) \n"
            + "WHERE ROWNUM = 1";

    @Override
    public DycpServicioDTO encontrar(String numControl) throws SIATException {
        String query
                = " SELECT SERV.*, C.*, D.*, T.*, T.DESCRIPCION AS DESCRIPCION_TIPOTRAMITE, "
                + UtilsDominio.obtenerAliasColumnas(AdmcUnidadAdmvaDTO.NOMBRE_TABLA, "U", jdbcTemplateDYC)
                + JOINS_SERVICIO
                + " WHERE SERV.NUMCONTROL = ? ORDER BY SERV.NUMCONTROL DESC ";
        try {
            ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
            DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
            AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
            TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
            DycpServicioMapper mapper = new DycpServicioMapper();
            mapper.setMapperDictaminador(mapperDictaminador);
            mapper.setMapperContribuyente(mapperContribuyente);
            mapper.setMapperUnidadAdmva(mapperUnidadAdmva);
            mapper.setMapperTipoTramite(mapperTipoTramite);
            return this.jdbcTemplateDYC.queryForObject(query, new Object[]{numControl}, mapper);

        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + query + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", numControl: " + numControl);
            throw new SIATException(e);
        }
    }

    @Override
    public List<DycpServicioDTO> selecXTiposervicio(DyccTipoServicioDTO tipoServicio) {
        String sentSql = " SELECT * FROM DYCP_SERVICIO WHERE IDTIPOSERVICIO = ?";
        DycpServicioMapper ms = new DycpServicioMapper();
        ms.setTipoServicio(tipoServicio);
        return this.jdbcTemplateDYC.query(sentSql, new Object[]{tipoServicio.getIdTipoServicio()}, ms);
    }

    @Override
    public void insertar(DycpServicioDTO servicio) throws SIATException {

        String sentInsert = " INSERT INTO DYCP_SERVICIO "
                + " (NUMCONTROL, IDTIPOSERVICIO, NUMEMPLEADODICT, IDORIGENSALDO, IDTIPOTRAMITE, "
                + "RFCCONTRIBUYENTE, CLAVEADM, BOID) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int[] tipoDatos
                = new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                    Types.VARCHAR, Types.INTEGER, Types.VARCHAR};

        try {
            Integer claveAdm = Integer.valueOf(servicio.getNumControl().substring(ConstantesDyCNumerico.VALOR_2, ConstantesDyCNumerico.VALOR_4));
            if (!claveAdm.equals(servicio.getClaveAdm())) {
                LOG.info("Servicio con numero de control " + servicio.getNumControl() + ". La clave adm esta mal: "
                        + servicio.getClaveAdm() + " debe ser " + claveAdm);
                servicio.setClaveAdm(claveAdm);
            }

            DycaOrigenTramiteDTO origenTramite = servicio.getDycaOrigenTramiteDTO();
            DycaServOrigenDTO servOrigen = origenTramite.getDycaServOrigenDTO();
            Integer numEmpleadoDict = servicio.getDyccDictaminadorDTO() != null ? servicio.getDyccDictaminadorDTO().getNumEmpleado() : null;

            jdbcTemplateDYC.update(sentInsert,
                    new Object[]{servicio.getNumControl(),
                        servOrigen.getDyccTipoServicioDTO().getIdTipoServicio(),
                        numEmpleadoDict,
                        servOrigen.getDyccOrigenSaldoDTO().getIdOrigenSaldo(),
                        origenTramite.getDyccTipoTramiteDTO().getIdTipoTramite(),
                        servicio.getRfcContribuyente(),
                        servicio.getClaveAdm(),
                        servicio.getBoid()}, tipoDatos);
            LOG.info("Se guardo en dycp_servicio : " + servicio.getNumControl() + " clave adm :: " + servicio.getClaveAdm());
        } catch (DataAccessException siatE) {
            LOG.error("error al insertar en DYCP_SERVICIO, NUMCONTROL ->" + servicio.getNumControl());
            ManejadorLogException.getTraceError(siatE);
            throw new SIATException(siatE);
        }
    }

    @Override
    public int updateDictaminador(Integer numempleado, String numcontrol) {

        try {
            return jdbcTemplateDYC.update("UPDATE DYCP_SERVICIO SET NUMEMPLEADODICT = ? , CLAVEADM =(select claveadm from dycc_dictaminador where numempleado = ?) WHERE NUMCONTROL = ?",
                    new Object[]{numempleado,numempleado, numcontrol});

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + "UPDATE DYCP_SERVICIO SET NUMEMPLEADODICT = ? WHERE NUMCONTROL = ?"
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + numempleado + "y" + numcontrol);
            throw dae;
        }

    }

    @Override
    public List<DycpServicioDTO> obtenerSolicitudServicio(DyccDictaminadorDTO dictaminador) throws SIATException {
        List<DycpServicioDTO> lista;
        try {
            lista = jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_SOLICITUDES_COMPENSACIONES.toString(), new Object[]{dictaminador.getNumEmpleado(),
                dictaminador.getNumEmpleado()},
                    new DycpServicioMapper());

        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_SOLICITUDES_COMPENSACIONES + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(dictaminador));
            throw new SIATException(dae);
        }
        return lista;
    }

    @Override
    public List<DycpServicioDTO> selecXRfc(String rfc) {
        String query = " SELECT SERV.*, C.*, D.*, "
                + UtilsDominio.obtenerAliasColumnas(AdmcUnidadAdmvaDTO.NOMBRE_TABLA, "U", jdbcTemplateDYC) + SEPARADOR
                + " T.*, T.DESCRIPCION AS DESCRIPCION_TIPOTRAMITE "
                + JOINS_SERVICIO
                + " WHERE SERV.RFCCONTRIBUYENTE = ? ";

        ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DycpServicioMapper mapper = new DycpServicioMapper();
        mapper.setMapperDictaminador(mapperDictaminador);
        mapper.setMapperContribuyente(mapperContribuyente);
        mapper.setMapperUnidadAdmva(mapperUnidadAdmva);
        mapper.setMapperTipoTramite(mapperTipoTramite);
        return jdbcTemplateDYC.query(query, new Object[]{rfc}, mapper);
    }

    public List<DycpServicioDTO> selecDinamico2(String clausulas, Object[] parametros) {
        String query = "SELECT * FROM ("
                + " SELECT  ROWNUM AS NUMFILA, "
                + UtilsDominio.obtenerAliasColumnas("DYCP_SERVICIO", "SERV", jdbcTemplateDYC) + SEPARADOR
                + "C.FECHACONSULTA, C.ROLPF, C.ROLPM, C.ROLGRANCONTRIB, C.ROLDICTAMINADO, C.ROLSOCIEDADCONTROL, C.AMPARADO " + SEPARADOR
                + UtilsDominio.obtenerAliasColumnas(DYCC_DICTAMINADOR, "D", jdbcTemplateDYC) + SEPARADOR
                + UtilsDominio.obtenerAliasColumnas(AdmcUnidadAdmvaDTO.NOMBRE_TABLA, "U", jdbcTemplateDYC) + SEPARADOR
                + UtilsDominio.obtenerAliasColumnas(DYCC_TIPOTRAMITE, "T", jdbcTemplateDYC) + SEPARADOR
                + "        SOL.* "
                + UtilsDominio.obtenerAliasColumnas("DYCP_COMPENSACION", "COMP", jdbcTemplateDYC) + SALTO_LINEA
                + JOINS_SERVICIO
                + JOINS_SERV_SOL_COMP_CONCPTO
                + clausulas
                + ") TRAMITESSINPAG WHERE NUMFILA >= ? AND NUMFILA <= ? ";

        ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DycpSolicitudMapper mapperSolicitud = new DycpSolicitudMapper();
        CompensacionMapper mapperCompensacion = new CompensacionMapper();
        DycpServicioMapper mapper = new DycpServicioMapper();
        mapper.setMapperDictaminador(mapperDictaminador);
        mapper.setMapperContribuyente(mapperContribuyente);
        mapper.setMapperUnidadAdmva(mapperUnidadAdmva);
        mapper.setMapperTipoTramite(mapperTipoTramite);
        mapper.setMapperSolicitud(mapperSolicitud);
        mapper.setMapperCompensacion(mapperCompensacion);
        try {
            return jdbcTemplateDYC.query(query, parametros, mapper);
        } catch (Exception sqle) {
            ManejadorLogException.getTraceError(sqle);
            LOG.error("ocurrio un error en selecDinamico2 ->" + sqle.getMessage() + "<-");
            LOG.error("query_selecDinamico2 ->" + query  + " parametros_selecDinamico2 ->");
            StringBuilder paramsRecibidos = new StringBuilder();
            for (Object parametro : parametros) {
                paramsRecibidos.append(parametro);
                paramsRecibidos.append("|");
            }
            LOG.error(paramsRecibidos.toString());
        }

        return null;
    }

    @Override
    public List<DycpServicioDTO> selecDinamico(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento) {

        String query = getConsultaSelectDinamico(clausulas, campoOrdenar, tipoOrdenamiento);
        DycpServicioMapper mapper = getMapperSelectDinamico();

        try {

            return jdbcTemplateDYC.query(query, parametros, mapper);

        } catch (Exception sqle) {

            ManejadorLogException.getTraceError(sqle);

            LOG.error("ocurrio un error en selecDinamico ->" + sqle.getMessage());
            LOG.error("query_selecDinamico  ->" + query + "  parametros_selecDinamico -> " + getCadenaParamatros(parametros) );
        }

        return new ArrayList<DycpServicioDTO>();
    }

    @Override
    public List<FilaGridTramitesBean> selecDinamicoConsulta(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento, Integer idTipoServicio) {

        String query = getConsultaSelectDinamicoConsulta(clausulas, campoOrdenar, tipoOrdenamiento, idTipoServicio);
        BandejaExportacionMapper mapper = getMapperSelectDinamicoExportacion();

        try {
            LOG.info(MENSAJE_QUERY_DINAMICO + query);
            return jdbcTemplateDYC.query(query, parametros, mapper);

        } catch (Exception sqle) {

            ManejadorLogException.getTraceError(sqle);

            LOG.error("ocurrio un error en selecDinamicoConsulta ->" + sqle.getMessage() + "<-");
            LOG.error("query_selecDinamicoConsulta  ->" + query + " parametros_selecDinamicoConsulta   ->" + getCadenaParamatros(parametros));
        }

        return new ArrayList<FilaGridTramitesBean>();
    }

    @Override
    public List<FilaGridTramitesBean> selecDinamicoExportacion(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento, Integer idTipoServicio) {

        String query = getConsultaSelectDinamicoExportacion(clausulas, campoOrdenar, tipoOrdenamiento, idTipoServicio);
        BandejaExportacionMapper mapper = getMapperSelectDinamicoExportacion();

        try {
            LOG.info(MENSAJE_QUERY_DINAMICO + query);
            return jdbcTemplateDYC.query(query, parametros, mapper);

        } catch (Exception sqle) {

            ManejadorLogException.getTraceError(sqle);

            LOG.error("ocurrio un error en selecDinamicoExportacion ->" + sqle.getMessage() + "<-");
            LOG.error("query_selecDinamicoExportacion-> " + query + " parametros_selecDinamicoExportacion ->");
            LOG.error(getCadenaParamatros(parametros));
        }

        return new ArrayList<FilaGridTramitesBean>();
    }

    private String getConsultaSelectDinamico(String clausulas, String campoOrdenar, String tipoOrdenamiento) {
        StringBuilder query = new StringBuilder();

        String columnasDycpServicio = UtilsDominio.obtenerAliasColumnas("DYCP_SERVICIO", "SERV", jdbcTemplateDYC);
        String columnasDyccDictaminador = UtilsDominio.obtenerAliasColumnas(DYCC_DICTAMINADOR, "D", jdbcTemplateDYC);
        String columnasAdmcUnidadAdmvaDTO = UtilsDominio.obtenerAliasColumnas(AdmcUnidadAdmvaDTO.NOMBRE_TABLA, "U", jdbcTemplateDYC);
        String columnasDyccTipoTramite = UtilsDominio.obtenerAliasColumnas(DYCC_TIPOTRAMITE, "T", jdbcTemplateDYC);
        String columnasDycpCompensacion = UtilsDominio.obtenerAliasColumnas("DYCP_COMPENSACION", "COMP", jdbcTemplateDYC);

        query
                .append(" SELECT * FROM(")
                .append(" SELECT TRAMS_RFC.*, ROWNUM NUMERO_FILA FROM")
                .append(" ( SELECT DISTINCT  ")
                .append(columnasDycpServicio)
                .append(SEPARADOR)
                .append("C.FECHACONSULTA, C.ROLPF, C.ROLPM, C.ROLGRANCONTRIB, C.ROLDICTAMINADO, C.ROLSOCIEDADCONTROL, C.AMPARADO ")
                .append(",  ")
                .append(columnasDyccDictaminador)
                .append(SEPARADOR)
                .append(columnasAdmcUnidadAdmvaDTO)
                .append(SEPARADOR)
                .append(columnasDyccTipoTramite)
                .append(SEPARADOR)
                .append("        SOL.*, ")
                .append(columnasDycpCompensacion)
                .append(SALTO_LINEA)
                .append(JOINS_SERVICIO)
                .append(JOINS_SERV_SOL_COMP_CONCPTO)
                .append(JOINS_PAGO)
                .append(JOINS_RESOLUCION_DOC)
                .append(clausulas)
                .append(" ORDER BY ")
                .append(campoOrdenar)
                .append(" ")
                .append(tipoOrdenamiento)
                .append(") TRAMS_RFC ) ")
                .append(" WHERE NUMERO_FILA >= ? AND NUMERO_FILA <= ?");

        return query.toString();
    }

    private String getConsultaSelectDinamicoConsulta(String clausulas, String campoOrdenar, String tipoOrdenamiento, Integer idTipoServicio) {
        StringBuilder query = new StringBuilder();
        query
                .append(" SELECT * FROM(")
                .append(" SELECT TRAMS_RFC.*, ROWNUM NUMERO_FILA FROM")
                .append(" ( ")
                .append(getConsultaSelectDinamicoExportacion(clausulas, campoOrdenar, tipoOrdenamiento, idTipoServicio))
                .append(") TRAMS_RFC ) ")
                .append(" WHERE NUMERO_FILA >= ? AND NUMERO_FILA <= ?");

        return query.toString();
    }

    private String getConsultaSelectDinamicoExportacion(String clausulas, String campoOrdenar, String tipoOrdenamiento, Integer idTipoServicio) {
        StringBuilder query = new StringBuilder();

        String campos;
        String campoTramite;
        String camposResolucion;
        String camposPago;

        if (idTipoServicio.equals(Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio())
                || idTipoServicio.equals(Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio())){
            campos = " ,COMP.FECHAPRESENTACION FECHAPRESENTACION_COMP, COMP.IMPORTECOMPENSADO IMPORTECOMPENSADO_COMP ";
            campoTramite = " ,CASE ECOM.IDESTADOCOMP WHEN 4 THEN 'En revisión artículo 22 CFF' ELSE ECOM.DESCRIPCION END as DESCRIPCION_ESTADOCOMP ";
            camposResolucion = " ,RESC.FECHARESOLUCION FECHARESOLUCION_RESC ";
            camposPago = "";

        } else{
            campos = " ,SOL.FECHAPRESENTACION FECHAPRESENTACION_SOL, SOL.IMPORTESOLICITADO IMPORTESOLICITADO_SOL ";
            campoTramite = " ,CASE ESOL.IDESTADOSOLICITUD WHEN 6 THEN 'En revisión artículo 22 CFF' ELSE ESOL.DESCRIPCION END as DESCRIPCION_ESTADOSOL ";
            camposResolucion = " ,RES.FECHAREGISTRO FECHAREGISTRO_RESOL, RES.FECHAAPROBACION FECHAAPROBACION_RESOL, RES.IMPAUTORIZADO IMPAUTORIZADO_RESOL, RES.IMPORTENETO IMPORTENETO_RESOL ";
            camposPago = " ,PAG.FECHAABONO FECHAABONO_PAGO ";

        }

        query
                .append("SELECT ")
                .append("    U.NOMBRE NOMBRE_UNIDADADMVA, U.NOMABREVIADO NOMABREVIADO_UNIDADADMVA, D.NUMEMPLEADO NUMEMPLEADO_DIC, ")
                .append("    D.NOMBRE NOMBRE_DIC, D.APELLIDOPATERNO APELLIDOPATERNO_DIC, D.APELLIDOMATERNO APELLIDOMATERNO_DIC, ")
                .append("    SERV.CLAVEADM CLAVEADM_SERVICIO, SERV.BOID BOID_SERVICIO, SERV.IDTIPOSERVICIO IDTIPOSERVICIO_SERVICIO, ")
                .append("    SERV.NUMCONTROL NUMCONTROL_SERVICIO, SERV.RFCCONTRIBUYENTE RFCCONTRIBUYENTE_SERVICIO, TSER.DESCRIPCION DESCRIPCION_TSERV, ")
                .append("    PER.DESCRIPCION DESCRIPCION_PERIODO, SALDO.IDEJERCICIO IDEJERCICIO_SALDO,  ")
                .append("    IMP.DESCRIPCION  DESCRIPCION_IMPUESTO, CON.DESCRIPCION DESCRIPCION_CONCEPTO, ")
                .append("    T.DESCRIPCION DESCRIPCION_TIPOTRAMITE, T.PLAZO, T.IDTIPOPLAZO ")
                .append(campos)
                .append(campoTramite)
                .append(camposResolucion)
                .append(camposPago)
                .append(getConsultaSelectDinamicoExportacionCuerpo(clausulas, idTipoServicio))
                .append(" ORDER BY ")
                .append(campoOrdenar)
                .append(" ")
                .append(tipoOrdenamiento);

        return query.toString();
    } 
    
    private String getConsultaSelectDinamicoExportacionCuerpo(String clausulas, Integer idTipoServicio) {
        StringBuilder query = new StringBuilder();
        
        String joinTablaPadre;
        String joinTablaResolucion;
        String estadoTramite;
        String unionSaldoIcep;
        String unionTablaPago;        

        if (idTipoServicio.equals(Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio())
                || idTipoServicio.equals(Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio())){
            joinTablaPadre =" INNER JOIN DYCP_COMPENSACION COMP ON COMP.NUMCONTROL = SERV.NUMCONTROL  ";
            joinTablaResolucion = " LEFT OUTER JOIN DYCT_RESOLCOMP RESC ON RESC.NUMCONTROL = SERV.NUMCONTROL  ";
            estadoTramite = " INNER JOIN DYCC_ESTADOCOMP ECOM ON COMP.IDESTADOCOMP = ECOM.IDESTADOCOMP ";
            unionSaldoIcep = " INNER JOIN DYCT_SALDOICEP SALDO ON SALDO.IDSALDOICEP = COMP.IDSALDOICEPORIGEN ";
            unionTablaPago = " ";

        } else{
            joinTablaPadre =" INNER JOIN DYCP_SOLICITUD SOL ON SOL.NUMCONTROL = SERV.NUMCONTROL  ";
            joinTablaResolucion = " LEFT OUTER JOIN DYCT_RESOLUCION RES ON RES.NUMCONTROL = SERV.NUMCONTROL ";
            estadoTramite = " INNER JOIN DYCC_ESTADOSOL ESOL ON SOL.IDESTADOSOLICITUD = ESOL.IDESTADOSOLICITUD ";
            unionSaldoIcep = " INNER JOIN DYCT_SALDOICEP SALDO ON SALDO.IDSALDOICEP = SOL.IDSALDOICEP ";
            unionTablaPago = " LEFT OUTER JOIN DYCP_SOLPAGO PAG ON PAG.NUMCONTROL = SERV.NUMCONTROL  ";

        }
        
        query
                .append(" FROM ")
                .append("    DYCP_SERVICIO SERV  ")
                .append(joinTablaPadre)
                .append("    INNER JOIN DYCT_CONTRIBUYENTE C ON SERV.NUMCONTROL = C.NUMCONTROL  ")
                .append("    LEFT OUTER JOIN DYCC_DICTAMINADOR D ON SERV.NUMEMPLEADODICT = D.NUMEMPLEADO  ")
                .append("    INNER JOIN DYCC_UNIDADADMVA U ON SERV.CLAVEADM = U.CLAVE_SIR  ")
                .append("    INNER JOIN DYCC_TIPOTRAMITE T ON SERV.IDTIPOTRAMITE = T.IDTIPOTRAMITE  ")
                .append("    INNER JOIN DYCC_CONCEPTO CPTO ON CPTO.IDCONCEPTO = T.IDCONCEPTO  ")
                .append(joinTablaResolucion)
                .append(unionTablaPago)
                .append(estadoTramite)
                .append(unionSaldoIcep)
                .append("    INNER JOIN DYCC_TIPOSERVICIO TSER ON TSER.IDTIPOSERVICIO = SERV.IDTIPOSERVICIO   ")
                .append("    INNER JOIN DYCC_PERIODO PER ON PER.IDPERIODO = SALDO.IDPERIODO     ")
                .append("    INNER JOIN DYCC_CONCEPTO CON ON CON.IDCONCEPTO = SALDO.IDCONCEPTO     ")
                .append("    INNER JOIN DYCC_IMPUESTO IMP ON IMP.IDIMPUESTO = CON.IDIMPUESTO      ")
                .append(clausulas);
        
        return query.toString();
    }

    private DycpServicioMapper getMapperSelectDinamico() {

        DycpServicioMapper mapper = new DycpServicioMapper();
        DycpSolicitudMapper mapperSolicitud = new DycpSolicitudMapper();
        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        CompensacionMapper mapperCompensacion = new CompensacionMapper();
        ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();

        mapper.setMapperSolicitud(mapperSolicitud);
        mapper.setMapperTipoTramite(mapperTipoTramite);
        mapper.setMapperUnidadAdmva(mapperUnidadAdmva);
        mapper.setMapperDictaminador(mapperDictaminador);
        mapper.setMapperCompensacion(mapperCompensacion);
        mapper.setMapperContribuyente(mapperContribuyente);

        return mapper;
    }

    private BandejaExportacionMapper getMapperSelectDinamicoExportacion() {

        BandejaExportacionMapper bandejaExportacionMapper = new BandejaExportacionMapper();

        return bandejaExportacionMapper;
    }

    private String getCadenaParamatros(Object[] parametros) {
        StringBuilder paramsRecibidos = new StringBuilder();

        for (Object parametro : parametros) {
            paramsRecibidos.append(parametro);
            paramsRecibidos.append("|");
        }

        return paramsRecibidos.toString();
    }

    @Override
    public List<DycpServicioDTO> selecDinamicoTodo(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento) {

        String query = getSelectConsultaTodo(clausulas, campoOrdenar, tipoOrdenamiento);
        DycpServicioMapper mapper = getMapperSelectDinamico();

        try {
            return jdbcTemplateDYC.query(query, parametros, mapper);
        } catch (Exception sqle) {
            ManejadorLogException.getTraceError(sqle);
            LOG.error("ocurrio un error en selecDinamicoTodo ->" + sqle.getMessage() + "<-");
            LOG.error("query_selecDinamicoTodo ->" + query + " parametros_selecDinamicoTodo  ->");
            StringBuilder paramsRecibidos = new StringBuilder();
            for (Object parametro : parametros) {
                paramsRecibidos.append(parametro);
                paramsRecibidos.append("|");
            }
            LOG.error(paramsRecibidos.toString());
        }

        return null;
    }

    private String getSelectConsultaTodo(String clausulas, String campoOrdenar, String tipoOrdenamiento){
        StringBuilder query = new StringBuilder();

        String columnasDycpServicio = UtilsDominio.obtenerAliasColumnas("DYCP_SERVICIO", "SERV", jdbcTemplateDYC);
        String columnasDyccDictaminador = UtilsDominio.obtenerAliasColumnas(DYCC_DICTAMINADOR, "D", jdbcTemplateDYC);
        String columnasAdmcUnidadAdmvaDTO = UtilsDominio.obtenerAliasColumnas(AdmcUnidadAdmvaDTO.NOMBRE_TABLA, "U", jdbcTemplateDYC);
        String columnasDyccTipoTramite = UtilsDominio.obtenerAliasColumnas(DYCC_TIPOTRAMITE, "T", jdbcTemplateDYC);
        String columnasDycpCompensacion = UtilsDominio.obtenerAliasColumnas("DYCP_COMPENSACION", "COMP", jdbcTemplateDYC);

        query
                .append("SELECT ")
                .append(columnasDycpServicio)
                .append(SEPARADOR)
                .append("C.FECHACONSULTA, C.ROLPF, C.ROLPM, C.ROLGRANCONTRIB, C.ROLDICTAMINADO, C.ROLSOCIEDADCONTROL, C.AMPARADO ")
                .append(",  ")
                .append(columnasDyccDictaminador)
                .append(SEPARADOR)
                .append(columnasAdmcUnidadAdmvaDTO)
                .append(SEPARADOR)
                .append(columnasDyccTipoTramite)
                .append(SEPARADOR)
                .append("        SOL.*, ")
                .append(columnasDycpCompensacion)
                .append(SALTO_LINEA)
                .append(JOINS_SERVICIO)
                .append(JOINS_SERV_SOL_COMP_CONCPTO)
                .append(JOINS_PAGO)
                .append(" LEFT OUTER JOIN DYCT_RESOLUCION RES ON RES.NUMCONTROL = SERV.NUMCONTROL LEFT OUTER JOIN DYCT_RESOLCOMP RESC ON RESC.NUMCONTROL = SERV.NUMCONTROL ")
                .append(clausulas)
                .append(" ORDER BY ")
                .append(campoOrdenar)
                .append(" ")
                .append(tipoOrdenamiento);

        return query.toString();
    }

    @Override
    public Integer selecCountDinamico(String clausulas, Object[] parametros) {
        try {
            String query = " SELECT COUNT( DISTINCT(serv.NUMCONTROL) ) "
                    + JOINS_SERVICIO
                    + JOINS_SERV_SOL_COMP_CONCPTO
                    + JOINS_RESOLUCION_DOC
                    + JOINS_PAGO
                    + clausulas;

            LOG.error(MENSAJE_QUERY_DINAMICO + query);
            StringBuilder paramsRecibidos = new StringBuilder();
            for (Object parametro : parametros) {
                paramsRecibidos.append(parametro);
                paramsRecibidos.append("|||");
            }
            LOG.error("parametros recibidos:" + paramsRecibidos.toString());
            return jdbcTemplateDYC.queryForObject(query, parametros, Integer.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return 0;
    }

    @Override
    public Integer selecCountDinamicoConsulta(String clausulas, Object[] parametros, Integer idTipoServicio) {
        try {
            String query = " SELECT COUNT( serv.NUMCONTROL ) "
                    + getConsultaSelectDinamicoExportacionCuerpo(clausulas, idTipoServicio);

            LOG.info(MENSAJE_QUERY_DINAMICO + query);
            StringBuilder paramsRecibidos = new StringBuilder();
            for (Object parametro : parametros) {
                paramsRecibidos.append(parametro);
                paramsRecibidos.append("|||");
            }
            LOG.info("parametros recibidos:" + paramsRecibidos.toString());
            return jdbcTemplateDYC.queryForObject(query, parametros, Integer.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return 0;
    }

    @Override
    public Integer selecCountXRfc(String rfc) {
        String query = " SELECT COUNT(*) FROM DYCP_SERVICIO WHERE RFCCONTRIBUYENTE = ?";
        return jdbcTemplateDYC.queryForObject(query, new Object[]{rfc}, Integer.class);
    }

    @Override
    public List<DycpServicioDTO> selecPaginadoXRfc(String rfc, Integer regInicial, Integer regFinal, String campoOrdenar, String tipoOrdenamiento) {
        String query = " SELECT * FROM("
                + " SELECT TRAMS_RFC.*, ROWNUM NUMERO_FILA FROM"
                + " ( SELECT  SERV.*, "
                + UtilsDominio.obtenerAliasColumnas(DYCC_TIPOTRAMITE, "TT", jdbcTemplateDYC) + ",   "
                + UtilsDominio.obtenerAliasColumnas(DYCC_DICTAMINADOR, "DICT", jdbcTemplateDYC) + ",  "
                + UtilsDominio.obtenerAliasColumnas(AdmcUnidadAdmvaDTO.NOMBRE_TABLA, "U", jdbcTemplateDYC) + SALTO_LINEA
                + " FROM DYCP_SERVICIO SERV  "
                + " LEFT OUTER JOIN DYCC_TIPOTRAMITE TT ON SERV.IDTIPOTRAMITE = TT.IDTIPOTRAMITE "
                + " LEFT OUTER JOIN DYCC_DICTAMINADOR DICT ON DICT.NUMEMPLEADO = SERV.NUMEMPLEADODICT "
                + " LEFT OUTER JOIN DYCC_UNIDADADMVA U ON U.CLAVE_SIR = SERV.CLAVEADM "
                + " WHERE RFCCONTRIBUYENTE = ? ORDER BY " + campoOrdenar + " " + tipoOrdenamiento + ") TRAMS_RFC) "
                + " WHERE NUMERO_FILA >= ? AND NUMERO_FILA <= ?";

        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
        DycpServicioMapper mapper = new DycpServicioMapper();
        mapper.setMapperTipoTramite(mapperTipoTramite);
        mapper.setMapperDictaminador(mapperDictaminador);
        mapper.setMapperUnidadAdmva(mapperUnidadAdmva);

        return jdbcTemplateDYC.query(query, new Object[]{rfc, regInicial, regFinal}, mapper);
    }

    @Override
    public String getRFCOwner(String numControl) {
        LOG.debug("numControl ->" + numControl);
        try {
            String query
                    = "SELECT RFCCONTRIBUYENTE FROM DYCP_SERVICIO WHERE NUMCONTROL= ? ";

            return jdbcTemplateDYC.queryForObject(query, new Object[]{numControl}, String.class);
        } catch (DataAccessException dae) {
            LOG.error("error al consultar RFC" + "numControl: " + numControl);
            ManejadorLogException.getTraceError(dae);
        }
        return null;
    }

    @Override
    public DycpServicioDTO obtenerServicioSinDictaminador(String numeroControl) throws SIATException {

        String query = "SELECT * FROM DYCP_SERVICIO WHERE NUMCONTROL = ? AND NUMEMPLEADODICT IS NULL AND NUMCONTROL LIKE 'SA%'";
        DycpServicioDTO tramite = null;
        try {
            tramite = jdbcTemplateDYC.queryForObject(query, new Object[]{numeroControl}, new DycpServicioMapper());
        } catch (EmptyResultDataAccessException ex) {
            tramite = null;
        } catch (DataAccessException dae) {
            LOG.error("\nSe ha presentado un error en la ejecucion de : " + dae.getMessage() + " con el query : "
                    + query + " con los siguientes parametros : "
                    + "NUMERO_CONTROL " + numeroControl);
            throw new SIATException(dae);
        }

        return tramite;
    }

    @Override
    public DycpServicioDTO obtenerUltimoServicioXContribuyente(String rfcContribuyente, int tipoServicio) {

        DycpServicioDTO ultimoServicio = null;
        String query = (tipoServicio == DEVOLUCION) ? ULTIMO_SERVICIO_DEVOLUCION : ULTIMO_SERVICIO_COMPENSACION;

        try {
            ultimoServicio = jdbcTemplateDYC.queryForObject(query, new Object[]{rfcContribuyente}, new DycpServicioMapper());
        } catch (EmptyResultDataAccessException erde) {
            ultimoServicio = null;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + query + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + rfcContribuyente);
            throw dae;
        }

        return ultimoServicio;

    }
}
