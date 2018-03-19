/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.devolucionaut.DyctDicAutomaticaDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.impl.mapper.DyctDictAutomaticaMapper;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DetalleTramGenerarRepoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.ACTUALIZAR_TRAMITE_PROCESADO;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.CONSULTAR_TRAMITES_DICTAMINADOS;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_AUTORIZADA;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_MOTIVO_RIESGO;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DYCT_DICAUTOMATICA_INSERT;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DYCT_DICTAUTOMATICA_SELECT;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DYCT_DICTAUTOMATICA_SELECT_GENERAR_REPORTE_POR_FILTRO;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.DYCT_DICTAUTOMATICA_SELECT_GENERAR_REPORTE_POR_FILTRO_MOTIVO_RIESGO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author MLIL
 */
@Repository(value = "dyctDicAutomaticaDAO")
public class DyctDicAutomaticaDAOImpl implements DyctDicAutomaticaDAO {

    private static final Logger LOG = Logger.getLogger(DyctDicAutomaticaDAOImpl.class);

    private static final String ERROR_CONSULTA = "ERROR AL CONSULTAR DETALLES DEL TRAMITE";
    private static final String LISTA_VACIA = "consultarTodos lista vacia:: ";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Método para insertar un arreglo de Objetos en la tabla
     * dyct_dictautomatica
     *
     * @param dicAutomatiocaDTO
     * @throws DAOException
     *
     */
    @Override
    public void insertarDicAutomatica(DyctDictAutomaticaDTO dicAutomatiocaDTO) throws DAOException {
        try {
            jdbcTemplateDYC.update(DYCT_DICAUTOMATICA_INSERT.toString(),
                                   new Object[] { dicAutomatiocaDTO.getNumControl(), dicAutomatiocaDTO.getNumLote(),
                                                  dicAutomatiocaDTO.getRfc(), dicAutomatiocaDTO.getImpuesto(),
                                                  dicAutomatiocaDTO.getConcepto(), dicAutomatiocaDTO.getNumOperacion(),
                                                  dicAutomatiocaDTO.getImporteSaldoF(),
                                                  dicAutomatiocaDTO.getFechaProcModelo(),
                                                  dicAutomatiocaDTO.getFechaRegistro(),
                                                  dicAutomatiocaDTO.getIdModelo(),
                                                  dicAutomatiocaDTO.getIdMarcResultado(),
                                                  dicAutomatiocaDTO.getMotRiesgo(),
                                                  dicAutomatiocaDTO.getFechaProceso() });
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL INSERTAR LOS REGISTROS";
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método para buscar en la tabla dyct_dictautomatica
     *
     * @param numControl
     * @return DyctDictAutomaticaDTOMio
     * @throws mx.gob.sat.siat.dyc.util.excepcion.DAOException
     *
     */
    @Override
    public DyctDictAutomaticaDTO buscarDicAutomatica(String numControl) throws DAOException {
        try {
            return (DyctDictAutomaticaDTO)jdbcTemplateDYC.queryForObject(DYCT_DICTAUTOMATICA_SELECT.toString(),
                                                                         new Object[] { numControl },
                                                                         new BeanPropertyRowMapper(DyctDictAutomaticaDTO.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA + numControl;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }

    }

    @Override
    public List<DyctDictAutomaticaDTO> getTramitesDictaminados() {
        List<DyctDictAutomaticaDTO> lstTramites = null;

        try {
            lstTramites =
                    jdbcTemplateDYC.query(CONSULTAR_TRAMITES_DICTAMINADOS.toString(), new DyctDictAutomaticaMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR_TRAMITES_DICTAMINADOS + ConstantesDyC1.TEXTO_3_ERROR_DAO);
        }
        return lstTramites;
    }

    @Override
    public int actualizarMarca(String numControl) {
        int val = 0;
        try {
            val = jdbcTemplateDYC.update(ACTUALIZAR_TRAMITE_PROCESADO.toString(), new Object[] { numControl });

        } catch (Exception dae) {
            LOG.error("Error al actualizar tramite en Dyct_DictAutomaticas, numControl=" + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
        }
        return val;

    }

    @Override
    public List<DyctDictAutomaticaDTO> getAllTramitesNoProcesados() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método para buscar por filtro de Generar Reporte
     *
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idModelo
     * @return Integer
     * @throws DAOException
     *
     */
    @Override
    public Long buscarGenerarReportePorFiltro(int idModelo, Date fechaInicioGenerarReporte,
                                              Date fechaFinGenerarReporte) throws DAOException {

        try {
            return jdbcTemplateDYC.queryForObject(DYCT_DICTAUTOMATICA_SELECT_GENERAR_REPORTE_POR_FILTRO.toString(),
                                                  new Object[] { idModelo, fechaInicioGenerarReporte,
                                                                 fechaFinGenerarReporte }, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }

    }


    /**
     * Método utilizado para buscar las dictaminaciones por modelo, CON y SIN
     * riesgo
     *
     * @param idModelo
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idMarcResultado
     * @return Long
     * @throws DAOException
     *
     *
     */
    @Override
    public Long buscarGenerarReportePorFiltroMotivoRiesgo(int idModelo, Date fechaInicioGenerarReporte,
                                                          Date fechaFinGenerarReporte,
                                                          String idMarcResultado) throws DAOException {
        try {
            return jdbcTemplateDYC.queryForObject(DYCT_DICTAUTOMATICA_SELECT_GENERAR_REPORTE_POR_FILTRO_MOTIVO_RIESGO.toString(),
                                                  new Object[] { idModelo, fechaInicioGenerarReporte,
                                                                 fechaFinGenerarReporte, idMarcResultado },
                                                  Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método utilizado para buscar las dictaminaciones por fechaRegistro
     *
     * @param idModelo
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idEstadoSol
     * @return Long
     * @throws DAOException
     *
     *
     */
    @Override
    public Long buscarGenerarReportePorFiltroFechaRegistro(int idModelo, Date fechaInicioGenerarReporte,
                                                           Date fechaFinGenerarReporte,
                                                           int idEstadoSol) throws DAOException {
        try {
            String query = "SELECT count(1)  " +
                    "FROM DYCT_DICTAUTOMATICA DIC  " +
                    "INNER JOIN DYCP_SOLICITUD SOL ON DIC.NUMCONTROL = SOL.NUMCONTROL " +
                    "WHERE DIC.IDMODELO = ? " +
                    "AND SOL.FECHAPRESENTACION BETWEEN ? AND ? " +
                    "AND DIC.FECHAREGISTRO IS NOT NULL " +
                    "AND SOL.IDESTADOSOLICITUD = ?";

            return jdbcTemplateDYC.queryForObject(query,
                                                  new Object[] { idModelo, fechaInicioGenerarReporte,
                                                                 fechaFinGenerarReporte, idEstadoSol }, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método utilizado para buscar las dictaminaciones por fechaRegistro
     *
     * @param idEstadoSol
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @return Long
     * @throws DAOException
     *
     *
     */
    @Override
    public Long buscarPendientesDictaminar(int idEstadoSol, Date fechaInicioGenerarReporte,
                                                           Date fechaFinGenerarReporte) throws DAOException {
        try {
            String query = "SELECT COUNT(1) " +
                    "FROM DYCP_SERVICIO SER " +
                    "INNER JOIN DYCC_DICTAMINADOR DIC ON SER.NUMEMPLEADODICT = DIC.NUMEMPLEADO " +
                    "INNER JOIN DYCC_TIPOSERVICIO TIPSER ON SER.IDTIPOSERVICIO = TIPSER.IDTIPOSERVICIO " +
                    "INNER JOIN DYCC_TIPOTRAMITE TIPTRA ON SER.IDTIPOTRAMITE = TIPTRA.IDTIPOTRAMITE " +
                    "INNER JOIN DYCP_SOLICITUD DICSOL ON SER.NUMCONTROL = DICSOL.NUMCONTROL " +
                    "INNER JOIN DYCC_ESTADOSOL ESSOL ON DICSOL.IDESTADOSOLICITUD = ESSOL.IDESTADOSOLICITUD " +
                    "LEFT JOIN DYCT_DICTAUTOMATICA DICA ON DICA.NUMCONTROL = DICSOL.NUMCONTROL " +
                    "WHERE DICSOL.IDESTADOSOLICITUD = ? " +
                    "AND DICSOL.FECHAPRESENTACION BETWEEN ? AND ? " +
                    "AND DICA.RFC IS NULL";
            return jdbcTemplateDYC.queryForObject(query,
                                                  new Object[] { idEstadoSol, fechaInicioGenerarReporte,
                                                                 fechaFinGenerarReporte }, Long.class);
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método para buscar por filtro de Generar Reporte y mostrar una lista de
     * los detalles
     *
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idModelo
     * @param paginador
     * @return List
     * @throws DAOException
     *
     */
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarDetalleTramiteGenRepoPorFiltro(int idModelo,
                                                                                   Date fechaInicioGenerarReporte,
                                                                                   Date fechaFinGenerarReporte,
                                                                                   Paginador paginador) throws DAOException {
        try {
            return (List<DetalleTramGenerarRepoDTO>)jdbcTemplateDYC.query(DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO.toString(),
                                                                          new Object[] { idModelo,
                                                                                         fechaInicioGenerarReporte,
                                                                                         fechaFinGenerarReporte,
                                                                                         paginador.getFrom(),
                                                                                         paginador.getTo() },
                                                                          new BeanPropertyRowMapper(DetalleTramGenerarRepoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info(LISTA_VACIA + e.getMessage());
            return new ArrayList<DetalleTramGenerarRepoDTO>();
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método utilizado para buscar las dictaminaciones por modelo, CON y SIN
     * riesgo
     *
     * @param idModelo
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idMarcResultado
     * @param paginador
     * @return List
     * @throws DAOException
     *
     *
     */
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarDetalleTramiteGenRepoPorFiltroMotivoRiesgo(int idModelo,
                                                                                               Date fechaInicioGenerarReporte,
                                                                                               Date fechaFinGenerarReporte,
                                                                                               String idMarcResultado,
                                                                                               Paginador paginador) throws DAOException {
        try {
            return (List<DetalleTramGenerarRepoDTO>)jdbcTemplateDYC.query(DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_MOTIVO_RIESGO.toString(),
                                                                          new Object[] { idModelo,
                                                                                         fechaInicioGenerarReporte,
                                                                                         fechaFinGenerarReporte,
                                                                                         idMarcResultado,
                                                                                         paginador.getFrom(),
                                                                                         paginador.getTo() },
                                                                          new BeanPropertyRowMapper(DetalleTramGenerarRepoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info(LISTA_VACIA + e.getMessage());
            return new ArrayList<DetalleTramGenerarRepoDTO>();
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método utilizado para buscar las dictaminaciones por fechaRegistro
     *
     * @param idModelo
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idEstadoSol
     * @param paginador
     * @return List
     * @throws DAOException
     *
     *
     */
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarDetalleTramiteGenRepoPorFiltroFechaRegistro(int idModelo,
                                                                                                Date fechaInicioGenerarReporte,
                                                                                                Date fechaFinGenerarReporte,
                                                                                                int idEstadoSol,
                                                                                                Paginador paginador) throws DAOException {
        try {
            String query = DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_AUTORIZADA.toString();
            return (List<DetalleTramGenerarRepoDTO>)jdbcTemplateDYC.query(query,
                                                                          new Object[] { idModelo, fechaInicioGenerarReporte,
                                                                                         fechaFinGenerarReporte,
                                                                                         idEstadoSol,
                                                                                         paginador.getFrom(),
                                                                                         paginador.getTo() },
                                                                          new BeanPropertyRowMapper(DetalleTramGenerarRepoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info(LISTA_VACIA + e.getMessage());
            return new ArrayList<DetalleTramGenerarRepoDTO>();
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    /**
     * Método utilizado para buscar las dictaminaciones por fechaRegistro
     *
     * @param idEstadoSol
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param paginador
     * @return List
     * @throws DAOException
     *
     *
     */
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarPendientesDictaminar(int idEstadoSol,
                                                                                                Date fechaInicioGenerarReporte,
                                                                                                Date fechaFinGenerarReporte,
                                                                                                Paginador paginador) throws DAOException {
        try {
            String query = "SELECT * FROM ( SELECT DIC.NUMEMPLEADO, DIC.NOMBRE, DIC.APELLIDOPATERNO, DIC.APELLIDOMATERNO, " +
                    "DICSOL.NUMCONTROL, SER.RFCCONTRIBUYENTE AS RFC, TIPSER.DESCRIPCION AS TRAMITE, TIPTRA.DESCRIPCION AS TIPOTRAMITE, " +
                    "ESSOL.DESCRIPCION AS ESTADOTRAMITE, DICSOL.FECHAINICIOTRAMITE, DICSOL.IMPORTESOLICITADO AS IMPORTESALDOF, " +
                    "ROW_NUMBER() OVER (ORDER BY DICSOL.NUMCONTROL) RN " +
                    "FROM DYCP_SERVICIO SER  " +
                    "INNER JOIN DYCC_DICTAMINADOR DIC ON SER.NUMEMPLEADODICT = DIC.NUMEMPLEADO " +
                    "INNER JOIN DYCC_TIPOSERVICIO TIPSER ON SER.IDTIPOSERVICIO = TIPSER.IDTIPOSERVICIO " +
                    "INNER JOIN DYCC_TIPOTRAMITE TIPTRA ON SER.IDTIPOTRAMITE = TIPTRA.IDTIPOTRAMITE " +
                    "INNER JOIN DYCP_SOLICITUD DICSOL ON SER.NUMCONTROL = DICSOL.NUMCONTROL " +
                    "INNER JOIN DYCC_ESTADOSOL ESSOL ON DICSOL.IDESTADOSOLICITUD = ESSOL.IDESTADOSOLICITUD " +
                    "LEFT JOIN DYCT_DICTAUTOMATICA DICA ON DICA.NUMCONTROL = DICSOL.NUMCONTROL " +
                    "WHERE DICSOL.IDESTADOSOLICITUD = ? " +
                    "AND DICSOL.FECHAPRESENTACION BETWEEN ? AND ? " +
                    "AND DICA.RFC IS NULL ) WHERE RN BETWEEN ? AND ? ORDER BY RN";
            return (List<DetalleTramGenerarRepoDTO>)jdbcTemplateDYC.query(query,
                                                                          new Object[] { idEstadoSol, fechaInicioGenerarReporte,
                                                                                         fechaFinGenerarReporte,
                                                                                         paginador.getFrom(),
                                                                                         paginador.getTo() },
                                                                          new BeanPropertyRowMapper(DetalleTramGenerarRepoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info(LISTA_VACIA + e.getMessage());
            return new ArrayList<DetalleTramGenerarRepoDTO>();
        } catch (DataAccessException ex) {
            String msgError = ERROR_CONSULTA;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

}
