/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.dao.regsolicitud.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpSolicitudMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DyctSaldoIcepMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ConceptoMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ImpuestoMapper;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.PeriodoMapper;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper.DycpConsultarExpedienteCompMapper;
import mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper.DycpSolicitudAdministrarSolMapper;
import mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper.DycpSolicitudConsultarExpedienteMapper;
import mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper.DycpSolicitudDevolucionMapper;
import mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper.DyctSolTemporalMapper;
import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.DyctResolucionMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.ContribuyenteMapper;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.UtilsDominio;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteArchivoTemp;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DycpSolicitudDevolucionDTO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DycpSolicitudDAOImpl implements DycpSolicitudDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DycpSolicitudDAOImpl.class.getName());
    private static final String PIPE = " | ";
    private static final String LOG_FORMAT_GENERAL_STRING = "%s %s %s %s %s %s";
    private static final String FORMAT_SEARCH_STRING = "%%%s%%";
    private static final String FIN_PARENTESIS = ")";
    private static final String FIN_ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR_PAGINADOR = " ) WHERE RN BETWEEN ? AND ? ORDER BY RN";
    private static final String TEXTO_INFO_NUMCONTROL = ", numControl=";
    
    /**
     * Consulta la solicitud y la deposita en un objeto DycpSolicitudDTO a
     * traves de su numero de control.
     *
     * @param query
     * @param numControl numero sobre el cual se consulta la solicitud
     * @return objeto DycpSolicitudDTO
     * @throws SIATException
     */
    @Override
    @Deprecated
    public DycpSolicitudDTO consultarSolicitud(String query, String numControl) throws SIATException {
        DycpSolicitudDTO objetoSolicitud = null;
        try {
            objetoSolicitud =
                    jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, new DycpSolicitudMapper());

        } catch (Exception dae) {
            LOG.error("consultarSolicitud(); parametros: query= " + query + TEXTO_INFO_NUMCONTROL + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae);
            throw new SIATException(dae);
        }
        return objetoSolicitud;
    }
    
    /**
     * Consulta la solicitud y la deposita en un objeto DycpSolicitudDTO a
     * traves de su numero de control.
     *
     * @param numControl numero sobre el cual se consulta la solicitud
     * @return objeto DycpSolicitudDTO
     * @throws SIATException
     */
    @Override
    public DycpSolicitudDTO consultarSolicitud(String numControl) throws SIATException {
        String query = "SELECT * FROM DYCP_SOLICITUD WHERE NUMCONTROL = ?";
        try {
            return (DycpSolicitudDTO) jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, new DycpSolicitudMapper());

        } catch (EmptyResultDataAccessException dae) {
            LOG.info("No se encontro la solicitud " + dae.getMessage());
            return null;
        } catch (DataAccessException dae) {
            LOG.error("consultarSolicitud(); parametros: " + TEXTO_INFO_NUMCONTROL + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae);
            throw new SIATException(dae);
        }
    }

    /**
     * Actualiza el estatus del tramite de la solicitud
     *
     * @param status valor a actualizar
     * @param numControl numero de control de la solicitud
     * @return 
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public int actualizarStatus(Integer status, String numControl) throws SIATException {
        int val = 0;
        try {
            val = jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_TRAMITESOLICITUD.toString(), new Object[] { status, numControl });

        } catch (Exception dae) {
            LOG.error("actualizarStatus(); parametros: status= " + status + TEXTO_INFO_NUMCONTROL + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return val;
    }

    /**
     * Actualiza la fecha fin del tramite
     *
     * @param fecha es la fecha de termino del tramite
     * @param numControl numero de control del documento
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public void actualizarFechaFinTramite(Date fecha, String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_FECHAFINSOLICITUD.toString(), new Object[] { fecha, numControl });

        } catch (Exception dae) {
            LOG.error("actualizarFechaFinTramite(); parametros: fecha= " + fecha + TEXTO_INFO_NUMCONTROL + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     * Obtiene un solo registro de tipo DycpSolicitudDTO usando el NumControl
     * como filtro de la tabla de trabajo DYCP_SOLICITUD
     *
     * @param numControl numero de control
     * @return Objeto dycpSolicitudDTO SolicitudConsultarExpedienteVO>
     *
     *
     */
    @Override
    public SolicitudConsultarExpedienteVO buscarNumcontrol(String numControl) {

        List<SolicitudConsultarExpedienteVO> ldycpSolicitudDTO;
        SolicitudConsultarExpedienteVO dycpSolicitudDTO = null;

        try {

            ldycpSolicitudDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARTRAMITEPORNUMERODECONTROL.toString(),
                                          new Object[] { numControl }, new DycpSolicitudConsultarExpedienteMapper());

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARTRAMITEPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

        if (ldycpSolicitudDTO.size() > 0) {
            dycpSolicitudDTO = ldycpSolicitudDTO.get(0);
        }

        return dycpSolicitudDTO;
    }

    @Override
    public SolicitudConsultarExpedienteVO buscarNumcontrolComp(String numControl) {

        List<SolicitudConsultarExpedienteVO> ldycpSolicitudDTO;
        SolicitudConsultarExpedienteVO dycpSolicitudDTO = null;

        try {

            ldycpSolicitudDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARTRAMITEPORNUMERODECONTROLCOMP.toString(),
                                          new Object[] { numControl }, new DycpConsultarExpedienteCompMapper());

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARTRAMITEPORNUMERODECONTROLCOMP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

        if (ldycpSolicitudDTO.size() > 0) {
            dycpSolicitudDTO = ldycpSolicitudDTO.get(0);
        }

        return dycpSolicitudDTO;

    }

    @Override
    public void insertaServicioSolicitud(DycpSolicitudDTO input) throws SIATException, SQLException {
        LOG.info(ConstanteArchivoTemp.INSERT + input.getFechaInicioTramite() + PIPE + input.getNumControl() + PIPE +
                 input.getDyctSaldoIcepDTO().getIdSaldoIcep());
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_SERVICO.toString(),
                                   new Object[] { input.getNumControl(), input.getDyccDictaminadorDTO().getNumEmpleado(),
                                                  input.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo(),
                                                  input.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                  input.getDycpServicioDTO().getRfcContribuyente(),
                                                  input.getDycpServicioDTO().getClaveAdm(),
                                                  input.getDycpServicioDTO().getBoid() });

            String sentSQL =
                null != input.getFechaInicioTramite() ? SQLOracleDyC.INSERTARSOLICITUD.toString() : SQLOracleDyC.INSERTARSOLICITUD1.toString();
            jdbcTemplateDYC.update(sentSQL, getDatosSolicitud(input),
                                   getTypes(null != input.getFechaInicioTramite() ? 1 : 0));
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTARSOLICITUD.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            LOG.error(ConstanteArchivoTemp.INSERT + input.getFechaInicioTramite() + PIPE + input.getNumControl() + PIPE +
                      input.getDyctSaldoIcepDTO().getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }

    @Override
    public void insertaServicioSol(DycpSolicitudDTO input, Integer numEmpleado) throws SIATException, SQLException {
        LOG.info(ConstanteArchivoTemp.INSERT + input.getFechaInicioTramite() + PIPE + input.getNumControl() + PIPE +
                 input.getDyctSaldoIcepDTO().getIdSaldoIcep());
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_SERVICO_DYC.toString(),
                                   new Object[] { input.getNumControl(), numEmpleado,
                                                  input.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo(),
                                                  input.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                  input.getDycpServicioDTO().getRfcContribuyente(),
                                                  input.getDycpServicioDTO().getClaveAdm(),
                                                  input.getDycpServicioDTO().getBoid() });

            String sentSQL =
                null != input.getFechaInicioTramite() ? SQLOracleDyC.INSERTARSOLICITUD.toString() : SQLOracleDyC.INSERTARSOLICITUD1.toString();
            jdbcTemplateDYC.update(sentSQL, getDatosSolicitud(input),
                                   getTypes(null != input.getFechaInicioTramite() ? 1 : 0));
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTARSOLICITUD.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + input);
            throw new SIATException(dae);
        }
    }

    @Override
    public void insertaSolicitud(DycpSolicitudDTO input) throws DycDaoExcepcion {
        LOG.info(ConstanteArchivoTemp.INSERT + input.getFechaInicioTramite() + PIPE + input.getNumControl() + PIPE +
                 input.getDyctSaldoIcepDTO().getIdSaldoIcep());
        try {
            Object[] parametros =
                new Object[] { input.getNumControl(), input.getFechaInicioTramite(), input.getImporteSolicitado(),
                               input.getInfoAdicional(), input.getDiotNumOperacion(), input.getDiotFechaPresenta(),
                               input.getRetenedorRfc(), input.getAltexConstancia(), input.getEsCertificado(),
                               input.getDyccEstadoSolDTO().getIdEstadoSolicitud(),
                               input.getDyccActividadDTO().getDyccSubOrigSaldoDTO().getIdSuborigenSaldo(),
                               input.getDyccSubtramiteDTO().getIdSubTipoTramite(),
                               input.getDyccActividadDTO().getIdActividad(), input.getResolAutomatica(),
                               input.getDyctSaldoIcepDTO().getIdSaldoIcep(), input.getCadenaOriginal(),
                               input.getSelloDigital(), input.getFechaPresentacion() };

            int[] types =
                new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP,
                            Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                            Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                            Types.TIMESTAMP };

            jdbcTemplateDYC.update(SQLOracleDyC.INSERTARSOLICITUD_GENERAL.toString(), parametros, types);
        } catch (DataAccessException dae) {
            throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_SOLICITUD_INSERT_GENERAL, null, dae);
        }
    }

    @Override
    public Long countSolicitudesDictaminador(String numEmpleado, String estados, String numControl, String rfc) {
        Long count = 0L;
        String query = SQLOracleDyC.ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVDICTAMINADOR_1.toString() +  estados
                + FIN_PARENTESIS + SQLOracleDyC.ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVDICTAMINADOR_2.toString()
                + estados + FIN_PARENTESIS;
        try {
            count
                    = jdbcTemplateDYC.queryForObject(query,
                            new Object[]{numEmpleado, 
                                String.format(FORMAT_SEARCH_STRING, numControl),
                                String.format(FORMAT_SEARCH_STRING, rfc), numEmpleado,
                                String.format(FORMAT_SEARCH_STRING, numControl),
                                String.format(FORMAT_SEARCH_STRING, rfc)}, Long.class);
        } catch (DataAccessException dae) {
            LOG.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                    ConstantesDyC1.TEXTO_2_ERROR_DAO,
                    query, ConstantesDyC1.TEXTO_3_ERROR_DAO, numEmpleado));
        }

        return count;
    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesDictaminador(String numEmpleado, int estado) {
        try {
            List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR.toString(),
                                      new Object[] { numEmpleado, estado, numEmpleado, estado },
                                      new DycpSolicitudAdministrarSolMapper());
            return lSolicitudAdministrarSolVO;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de empleado: " + numEmpleado + "estado: " + estado);
            throw dae;
        }

    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesDictaminadorPaginador(String numEmpleado, String estado,
                                                                                  String numControl, String rfc,
                                                                                  Paginador paginador) {
        String query = 
        SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR_PAGINADOR_1.toString() +
                estado + FIN_PARENTESIS
                + SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR_PAGINADOR_2.toString()
                + estado + FIN_PARENTESIS + FIN_ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR_PAGINADOR;
        try {
            return jdbcTemplateDYC.query(query,
                                         new Object[] { numEmpleado,
                                                        String.format(FORMAT_SEARCH_STRING, numControl),
                                                        String.format(FORMAT_SEARCH_STRING, rfc), numEmpleado, 
                                                        String.format(FORMAT_SEARCH_STRING, numControl),
                                                        String.format(FORMAT_SEARCH_STRING, rfc), paginador.getFrom(),
                                                        paginador.getTo() }, new DycpSolicitudAdministrarSolMapper());
        } catch (DataAccessException dae) {
            LOG.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                    ConstantesDyC1.TEXTO_2_ERROR_DAO,
                                    query,
                                    ConstantesDyC1.TEXTO_3_ERROR_DAO, numEmpleado));
            throw dae;
        }
    }

    @Override
    public Long countSolicitudesAdministrador(int unidad, String numControl, String rfc) {
        Long count = 0L;

        try {
            count =
jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVADMINISTRADOR.toString(),
                               new Object[] { unidad, String.format(FORMAT_SEARCH_STRING, numControl),
                                              String.format(FORMAT_SEARCH_STRING, rfc), unidad,
                                              String.format(FORMAT_SEARCH_STRING, numControl),
                                              String.format(FORMAT_SEARCH_STRING, rfc) }, Long.class);
        } catch (DataAccessException dae) {
            LOG.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                    ConstantesDyC1.TEXTO_2_ERROR_DAO,
                                    SQLOracleDyC.ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVADMINISTRADOR.toString(),
                                    ConstantesDyC1.TEXTO_3_ERROR_DAO, unidad));
        }

        return count;
    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesAdministrador(int unidad) {

        try {
            List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADOR.toString(),
                                      new Object[] { unidad, unidad }, new DycpSolicitudAdministrarSolMapper());
            return lSolicitudAdministrarSolVO;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Unidad: " + unidad);
            throw dae;
        }

    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesAdministradorPaginador(int unidad, String numControl,
                                                                                   String rfc, Paginador paginador) {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADOR_PAGINADOR.toString(),
                                         new Object[] { unidad, String.format(FORMAT_SEARCH_STRING, numControl),
                                                        String.format(FORMAT_SEARCH_STRING, rfc), unidad,
                                                        String.format(FORMAT_SEARCH_STRING, numControl),
                                                        String.format(FORMAT_SEARCH_STRING, rfc), paginador.getFrom(),
                                                        paginador.getTo() }, new DycpSolicitudAdministrarSolMapper());
        } catch (DataAccessException dae) {
            LOG.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                    ConstantesDyC1.TEXTO_2_ERROR_DAO,
                                    SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADOR_PAGINADOR.toString(),
                                    ConstantesDyC1.TEXTO_3_ERROR_DAO, unidad));
            throw dae;
        }
    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesAdministradorFacultades(int unidad, String numempleado) {

        try {
            List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADORFACULTADES.toString(),
                                      new Object[] { numempleado },
                                      new DycpSolicitudAdministrarSolMapper());
            return lSolicitudAdministrarSolVO;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADORFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Unidad: " + unidad + " Num empleado: " + numempleado);
            throw dae;
        }
    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesFiscalizador(int unidad) {

        try {
            List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVFISCALIZADOR.toString(),
                                      new Object[] { unidad, unidad }, new DycpSolicitudAdministrarSolMapper());
            return lSolicitudAdministrarSolVO;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVFISCALIZADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Unidad: " + unidad);
            throw dae;
        }

    }

    @Override
    public String consultaDevolucionSimultaneas(String rfc, int idConcepto, int idEjercicio, int idPeriodo) {
        LOG.debug("rfc ->" + rfc);

        try {
            String query =
                " select de.descripcion from dycp_servicio s, dycp_solicitud d, dyct_saldoicep i,dycc_estadosol de " +
                " where s.numcontrol = d.numcontrol and i.idsaldoicep = d.idsaldoicep and s.rfccontribuyente = ? and  i.idconcepto = ? and i.idejercicio = ? " +
                " and  i.idperiodo = ? and de.idestadosolicitud = d.idestadosolicitud and d.idestadosolicitud in (12,9,11,4,3,7) and rownum = 1";

            return jdbcTemplateDYC.queryForObject(query, new Object[] { rfc, idConcepto, idEjercicio, idPeriodo },
                                                  String.class);
        } catch (DataAccessException dae) {
            LOG.error("error al consultarDevolucionesSimultaneas" + "RFC: " + rfc + " Concepto ->" + idConcepto +
                      "|E" + idEjercicio + "P" + idPeriodo);
            ManejadorLogException.getTraceError(dae);
        }
        return null;
    }

    /**
     * Obtienes la lista de facturas para la tabla [DYCT_FACTURA]
     *
     * @author J. Isaac Carbajal Bernal
     * @param rfcProveedor y String rfcProveedor;
     * @return Objeto lDycpSolicitudDevolucionDTO
     *
     *
     */
    @Override
    public List<DycpSolicitudDevolucionDTO> buscarFacturas(String rfcProveedor) {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.CONSULTAPROVICIONALFACTURAS.toString(),
                                         new Object[] { rfcProveedor }, new DycpSolicitudDevolucionMapper());
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CONSULTAPROVICIONALFACTURAS.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.RFC_PROV + rfcProveedor);
            throw e;
        }
    }

    @Override
    public void borrarFactura(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO) {
        LOG.debug("DELETE " + dycpSolicitudDevolucionDTO.getRfcProveedor());
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.DELETEFACTURA.toString(),
                                   new Object[] { dycpSolicitudDevolucionDTO.getRfcProveedor(),
                                                  dycpSolicitudDevolucionDTO.getNumFactura() });
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DELETEFACTURA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      dycpSolicitudDevolucionDTO);
            throw e;
        }
    }

    @Override
    public boolean buscarNumFactura(String numFactura, String rfcProveedor) {
        boolean existe = Boolean.FALSE;
        try {
            jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTAFACTURASRFCPROVEEDORNUMFACTURA.toString(),
                                           new Object[] { numFactura, rfcProveedor },
                                           new DycpSolicitudDevolucionMapper());
            existe = Boolean.TRUE;
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.CONSULTAFACTURASRFCPROVEEDORNUMFACTURA.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "Num. de Factura: " + numFactura + ConstantesDyC1.RFC_PROV +
                      rfcProveedor);
            throw e;
        }
        return existe;
    }

    @Override
    public boolean insertaFactura(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO) {
        boolean inserto = false;
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAFACTURA.toString(),
                                   new Object[] { dycpSolicitudDevolucionDTO.getNumFactura(),
                                                  dycpSolicitudDevolucionDTO.getRfcProveedor(),
                                                  dycpSolicitudDevolucionDTO.getFechaFactura(),
                                                  dycpSolicitudDevolucionDTO.getConcepto(),
                                                  dycpSolicitudDevolucionDTO.getImporte(),
                                                  dycpSolicitudDevolucionDTO.getIvaTrasladado(),
                                                  dycpSolicitudDevolucionDTO.getNumControl(),
                                                  dycpSolicitudDevolucionDTO.getTotal() });
            inserto = Boolean.TRUE;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAFACTURA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      dycpSolicitudDevolucionDTO);

        }
        return inserto;
    }

    @Override
    public Integer obtenRFCBancario(String cuentaBancario, String rfc) {
        Integer resulRFCBancario = ConstantesDyCNumerico.VALOR_0;
        try {
            String result =
                jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENRFCBANCARIO.toString(), new Object[] { rfc, cuentaBancario },
                                               String.class);
            resulRFCBancario = Integer.parseInt(result);
            return resulRFCBancario;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.OBTENRFCBANCARIO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + cuentaBancario + rfc);
        }
        return resulRFCBancario;
    }

    @Override
    public boolean existenFacturas(String rfcProveedor) {
        boolean existFac = false;
        try {
            List<DycpSolicitudDevolucionDTO> lDycpSolicitudDevolucionDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTAPROVICIONALFACTURAS.toString(),
                                      new Object[] { rfcProveedor }, new DycpSolicitudDevolucionMapper());
            existFac = lDycpSolicitudDevolucionDTO.size() > 0;
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CONSULTAPROVICIONALFACTURAS.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.RFC_PROV + rfcProveedor);
            throw e;
        }
        return existFac;
    }

    @Override
    public void createSolicitudTemp(DyctSolicitudTempDTO solTem) throws SIATException {
        int[] types =
        { Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR,
          Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
          Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER,
          Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER };
        /**
         * "INSERT INTO
         * dyct_solicitudtemp(TIPOSALDO,FECHAPRESENTACION,IMPORTESOLICITADO,RFCCONTRIBUYENTE,INFOADICIONAL,DIOTNUMOPERACION,DIOTFECHAPRESENTA,"
         * +
         * "DATOSCORRECTOS,RETENEDORRFC,ALTEXCONSTANCIA,IDESTADOSOLICITUD,IDPERIODO,IDTIPOTRAMITE,IDSUBORIGENSALDO,IDORIGENSALDO,IDSUBTIPOTRAMITE,IDIMPUESTO,"
         * +
         * "IDCONCEPTO,IDEJERCICIO,CLAVEADM,CLABEBANCARIA,IDACTIVIDAD,MANIFIESTAEDOCTA,PROTESTA,SALDOICEP,FOLIOSERVTEMP)\n"
         * + "VALUES
         * (?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         *
         */
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_SOLICITUD_TEMP.toString(),
                                   new Object[] { solTem.getTipoSaldo(), solTem.getImporteSolicitado(),
                                                  solTem.getRfcContribuyente(), solTem.getInfoAdicional(),
                                                  solTem.getDiotNumOperacion(), solTem.getDiotFechaPresenta(),
                                                  solTem.getDatosCorrectos(), solTem.getRetenedorRfc(),
                                                  solTem.getAltexConstancia(), solTem.getIdEstadosolicitud(),
                                                  solTem.getIdPeriodo(), solTem.getIdTipotramite(),
                                                  solTem.getIdSuborigensaldo(), solTem.getIdOrigensaldo(),
                                                  solTem.getIdSubtipotramite(), solTem.getIdImpuesto(),
                                                  solTem.getIdConcepto(), solTem.getIdEjercicio(),
                                                  solTem.getClaveAdm(), solTem.getClabeBancaria(),
                                                  solTem.getIdActividad(), solTem.getManifiestaEdocta(),
                                                  solTem.getProtesta(), solTem.getSaldoIcep(),
                                                  solTem.getInfoAgropecuario(), solTem.getAplicaFacilidad(),
                                                  solTem.getEstadoPatron(), solTem.getFolioServtemp() }, types);
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CREATE_SOLICITUD_TEMP + ConstantesDyC1.TEXTO_3_ERROR_DAO + solTem);
            throw new SIATException(dae);
        }
    }

    @Override
    public DyctSolicitudTempDTO findSolicitudTemp(int folio) throws SIATException {

        List<DyctSolicitudTempDTO> solicitudTemp = null;
        try {
            solicitudTemp =
                    jdbcTemplateDYC.query(SQLOracleDyC.FIND_SOLICITUD_TEMP.toString(), new Object[] { folio }, new DyctSolTemporalMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FIND_SOLICITUD_TEMP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " Numero de Folio " + folio);
            throw new SIATException(dae);
        }
        if (!solicitudTemp.isEmpty()) {
            return solicitudTemp.get(0);
        }
        return null;
    }

    @Override
    public void deleteSolicitudTep(String folio) {
        if (null != folio) {
            LOG.info("INIT UPDATE SOLICITUD TEMPORAL " + folio);
            try {
                jdbcTemplateDYC.update(SQLOracleDyC.DELETE_SERVICIO_TEMP.toString(), new Object[] { folio });
            } catch (DataAccessException dae) {
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getCause() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                          " Numero de Folio " + folio);
                throw dae;
            }
            LOG.info("END DELETE SOLICITUD TEMPORAL");
        }
    }

    @Override
    public DycpSolicitudDTO encontrar(String numControl) {
        try {
            String query =
                " SELECT SOL.*, SERV.*, CONTTE.*, SALDO.*, CON.DESCRIPCION, PER.*, CON.*, IMP.*, PER.DESCRIPCION as DESCRIPCION_PERIODO,"
                    + " IMP.DESCRIPCION  as   DESCRIPCION_IMPUESTO, ESOL.DESCRIPCION, " + UtilsDominio.obtenerAliasColumnas("DYCT_RESOLUCION",
                                                                                                 "RES",
                                                                                                 jdbcTemplateDYC) +
                " \n" +
                " FROM DYCP_SOLICITUD SOL " +
                " INNER JOIN DYCC_ESTADOSOL ESOL ON SOL.IDESTADOSOLICITUD = ESOL.IDESTADOSOLICITUD " +
                " INNER JOIN DYCP_SERVICIO SERV ON SOL.NUMCONTROL = SERV.NUMCONTROL " +
                " INNER JOIN DYCT_CONTRIBUYENTE CONTTE ON SOL.NUMCONTROL = CONTTE.NUMCONTROL " +
                " INNER JOIN DYCT_SALDOICEP SALDO ON SALDO.IDSALDOICEP = SOL.IDSALDOICEP " +
                " LEFT OUTER JOIN DYCT_RESOLUCION RES ON RES.NUMCONTROL = SOL.NUMCONTROL " +
                " INNER JOIN DYCC_PERIODO PER ON PER.IDPERIODO = SALDO.IDPERIODO " +
                " INNER JOIN DYCC_CONCEPTO CON ON CON.IDCONCEPTO = SALDO.IDCONCEPTO " +
                " INNER JOIN DYCC_IMPUESTO IMP ON IMP.IDIMPUESTO = CON.IDIMPUESTO " +
                " WHERE SOL.NUMCONTROL = ? ORDER BY SALDO.IDSALDOICEP DESC";

            ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
            DycpServicioMapper mapperServicio = new DycpServicioMapper();
            mapperServicio.setMapperContribuyente(mapperContribuyente);
            DyctSaldoIcepMapper mapperSaldoIcep = new DyctSaldoIcepMapper();
            DyctResolucionMapper mapperResolucion = new DyctResolucionMapper();
            DycpSolicitudMapper mapper = new DycpSolicitudMapper();
            PeriodoMapper mapperPeriodo = new PeriodoMapper();
            ConceptoMapper mapperConcepto = new ConceptoMapper();
            ImpuestoMapper mapperImpuesto = new ImpuestoMapper();
            mapperConcepto.setMapperImpuesto(mapperImpuesto);
            mapperSaldoIcep.setMapperConcepto(mapperConcepto);
            mapperSaldoIcep.setMapperPeriodo(mapperPeriodo);
            mapper.setMapperServicio(mapperServicio);
            mapper.setMapperSaldoIcep(mapperSaldoIcep);
            mapper.setMapperResolucion(mapperResolucion);

            return jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, mapper);
        } catch (EmptyResultDataAccessException erdae) {
            LOG.info("NO se encontraron registros en la tabla DYCP_SOLICITUD para el numControl ->" + numControl +
                     "<-");
            return null;
        }
    }
    
    @Override
    public DycpSolicitudDTO encontrar(String numControl, String rfc, Integer idSaldoIcep) throws SIATException {
        try {
            String query =
                " SELECT SOL.*, SERV.*, CONTTE.*, SALDO.*, ESOL.DESCRIPCION, " + UtilsDominio.obtenerAliasColumnas("DYCT_RESOLUCION",
                                                                                                 "RES",
                                                                                                 jdbcTemplateDYC) +
                " \n" +
                " FROM DYCP_SOLICITUD SOL " +
                " INNER JOIN DYCC_ESTADOSOL ESOL ON SOL.IDESTADOSOLICITUD = ESOL.IDESTADOSOLICITUD " +
                " INNER JOIN DYCP_SERVICIO SERV ON SOL.NUMCONTROL = SERV.NUMCONTROL " +
                " INNER JOIN DYCT_CONTRIBUYENTE CONTTE ON SOL.NUMCONTROL = CONTTE.NUMCONTROL " +
                " INNER JOIN DYCT_SALDOICEP SALDO ON SALDO.IDSALDOICEP = SOL.IDSALDOICEP " +
                " LEFT OUTER JOIN DYCT_RESOLUCION RES ON RES.NUMCONTROL = SOL.NUMCONTROL " +
                " WHERE SOL.NUMCONTROL = ? AND SERV.RFCCONTRIBUYENTE = ? AND SOL.IDSALDOICEP = ? ORDER BY SALDO.IDSALDOICEP DESC";

            ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
            DycpServicioMapper mapperServicio = new DycpServicioMapper();
            mapperServicio.setMapperContribuyente(mapperContribuyente);
            DyctSaldoIcepMapper mapperSaldoIcep = new DyctSaldoIcepMapper();
            DyctResolucionMapper mapperResolucion = new DyctResolucionMapper();
            DycpSolicitudMapper mapper = new DycpSolicitudMapper();
            mapper.setMapperServicio(mapperServicio);
            mapper.setMapperSaldoIcep(mapperSaldoIcep);
            mapper.setMapperResolucion(mapperResolucion);

            return jdbcTemplateDYC.queryForObject(query, new Object[] { numControl, rfc, idSaldoIcep }, mapper);
        } catch (EmptyResultDataAccessException erdae) {
            LOG.info("NO se encontraron registros para el NUMCONTROL:: " + numControl + " RFC:: " + rfc + " IDSALDOICEP:: " + idSaldoIcep, erdae);
            return null;
        } catch (DataAccessException erdae) {
             throw new SIATException("ERROR AL OBTENER LA SOLICITUD NUMCONTROL:: " + numControl + " RFC:: " + rfc + " IDSALDOICEP:: " + idSaldoIcep, erdae);
        }
    }

    /**
     * Actualiza resolucion automatica
     *
     * @param solAutomatic actualiza a Resolucion Autorizada Total
     * @param numControl numero de control de la solicitud
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public void actualizarResolucionAutomatica(Integer solAutomatic, String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_SOLICITUD_AUTOMATICA.toString(),
                                   new Object[] { solAutomatic, numControl });

        } catch (DataAccessException dae) {
            LOG.error("actualizar  resolautomatica; parametros: numControl" + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw (dae);
        }
    }

    @Override
    public boolean existeXId(String numControl) throws SIATException {
        boolean existe = false;
        int countReg = 0;

        String query = "SELECT COUNT(*) FROM DYCP_SOLICITUD WHERE NUMCONTROL=?";

        try {
            countReg = this.jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, Integer.class);
        } catch (DataAccessException dae) {
            LOG.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
        }

        if (countReg > 0) {
            existe = Boolean.TRUE;
        }
        return existe;
    }

    private int[] getTypes(int flag) {
        int[] types = null;
        if (flag != 0) {
            types =
new int[] { Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR,
            Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
            Types.VARCHAR, Types.TIMESTAMP };
        } else {

            types =
new int[] { Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR,
            Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
            Types.VARCHAR };
        }
        return types;
    }

    private Object[] getDatosSolicitud(DycpSolicitudDTO input) {
        /**
         * (NUMCONTROL,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,
         * DIOTNUMOPERACION,\n" +
         * "DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP,FECHAPRESENTACI
         * O N )
         */

        if (null != input.getFechaInicioTramite()) {
            return new Object[] { input.getNumControl(), input.getImporteSolicitado(), input.getInfoAdicional(),
                                  input.getDiotNumOperacion(), input.getDiotFechaPresenta(), input.getRetenedorRfc(),
                                  input.getAltexConstancia(), input.getEsCertificado(),
                                  input.getDyccEstadoSolDTO().getIdEstadoSolicitud(),
                                  input.getDyccActividadDTO().getDyccSubOrigSaldoDTO().getIdSuborigenSaldo(),
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() != 0 ?
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() : null,
                                  input.getDyccActividadDTO().getIdActividad(),
                                  input.getDyctSaldoIcepDTO().getIdSaldoIcep(), input.getCadenaOriginal(),
                                  input.getSelloDigital(), input.getFechaInicioTramite() };
        } else {
            return new Object[] { input.getNumControl(), input.getImporteSolicitado(), input.getInfoAdicional(),
                                  input.getDiotNumOperacion(), input.getDiotFechaPresenta(), input.getRetenedorRfc(),
                                  input.getAltexConstancia(), input.getEsCertificado(),
                                  input.getDyccEstadoSolDTO().getIdEstadoSolicitud(),
                                  input.getDyccActividadDTO().getDyccSubOrigSaldoDTO().getIdSuborigenSaldo(),
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() != 0 ?
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() : null,
                                  input.getDyccActividadDTO().getIdActividad(),
                                  input.getDyctSaldoIcepDTO().getIdSaldoIcep(), input.getCadenaOriginal(),
                                  input.getSelloDigital() };

        }
    }

    @Override
    public boolean existeTramite(String rfc, int idTipoTramite) throws SIATException {

        int countReg = 0;

        String query = "SELECT COUNT(*) FROM DYCP_SERVICIO WHERE RFCCONTRIBUYENTE=? AND IDTIPOTRAMITE= ?";

        try {
            countReg = this.jdbcTemplateDYC.queryForObject(query, new Object[] { rfc, idTipoTramite }, Integer.class);
        } catch (DataAccessException dae) {
            LOG.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + "rf " + rfc + " idTipoTramite " + idTipoTramite);
        }

        return (countReg > 0);

    }

    @Override
    public List<DycpSolicitudDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        String query =
            " SELECT SERV.*, S.*, R.*, ESOL.DESCRIPCION, R.NUMCONTROL AS NUMCONTROL_RESOLUCION " 
                + " FROM DYCP_SOLICITUD S "
                + " INNER JOIN DYCC_ESTADOSOL ESOL ON S.IDESTADOSOLICITUD = ESOL.IDESTADOSOLICITUD " 
                + " LEFT OUTER JOIN DYCP_SERVICIO SERV ON SERV.NUMCONTROL = S.NUMCONTROL" 
                + " LEFT OUTER JOIN DYCT_RESOLUCION R ON S.NUMCONTROL = R.NUMCONTROL " 
                + " WHERE IDSALDOICEP = ? ORDER BY IDSALDOICEP DESC";
        try {
            DyctResolucionMapper mapperResolucion = new DyctResolucionMapper();
            DycpServicioMapper mapperServicio = new DycpServicioMapper();

            DycpSolicitudMapper mapper = new DycpSolicitudMapper();
            mapper.setMapperResolucion(mapperResolucion);
            mapper.setSaldoIcep(saldoIcep);
            mapper.setMapperServicio(mapperServicio);
            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
        } catch (DataAccessException dae) {
            LOG.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + saldoIcep.getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }

    @Override
    public List<Map<String, Object>> obtenerNoControlParaHistorico(int maximoTramitesProcesar) throws SIATException {
        try {
            return jdbcTemplateDYC.queryForList(SQLOracleDyC.OBTENERNOCONTROLPARAHISTORICO.toString(), new Object[] {maximoTramitesProcesar });

        } catch (EmptyResultDataAccessException erdae) {
            List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
            LOG.error("No es encontraron valores de busqueda: " + erdae.getCause());
            return lista;
        }

    }
    @Override
    public List<Map<String, Object>> obtenerNoControlNYVProcesado(int maximoNYVRegresar) throws SIATException {
        try {
            return jdbcTemplateDYC.queryForList(SQLOracleDyC.OBTENERNOCONTROLNYVPROCESADO.toString(), new Object[] {maximoNYVRegresar });

        } catch (EmptyResultDataAccessException erdae) {
            List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
            LOG.error("No es encontraron valores de busqueda: " + erdae.getCause());
            return lista;
        }

    }
   
    @Override
    public int actualizarStatusPago(Integer status, String numControl) throws SIATException {
        int val = 0;
        try {
            String where = " where numControl in (" + numControl + ")";
            val = jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_TRAMITESOLICITUD_PAGO + where, new Object[]{status});

        } catch (Exception dae) {
            LOG.error("actualizarStatus(); parametros: status= " + status + TEXTO_INFO_NUMCONTROL + numControl
                    + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return val;
    }

    @Override
    public void actualizarIdEstadoSol(final int idEstadoSol, final String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.UPTADE_DYCP_SOLICITUD_IDESTADOSOL_WHERE_NUMCONTROL, new Object[] { idEstadoSol, numControl });

        } catch (Exception dae) {
            LOG.error("actualizarIdEstadoSol()" + SQLOracleDyC.UPTADE_DYCP_SOLICITUD_IDESTADOSOL_WHERE_NUMCONTROL + "  parametros: fecha= " + idEstadoSol + TEXTO_INFO_NUMCONTROL + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }
    
    @Override
    public void actualizarIdEstadoSolDic(final int idEstadoSol, final String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.UPTADE_DYCP_SOLICITUD_DIC_IDESTADOSOL_WHERE_NUMCONTROL, new Object[] { idEstadoSol, numControl });

        } catch (Exception dae) {
            LOG.error("actualizarIdEstadoSolDic()" + SQLOracleDyC.UPTADE_DYCP_SOLICITUD_DIC_IDESTADOSOL_WHERE_NUMCONTROL + "  parametros: fecha= " + idEstadoSol + TEXTO_INFO_NUMCONTROL + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }
}
