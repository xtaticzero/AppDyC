/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.devolucionaut.service.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyctDictAutomaticaService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyctDicAutomaticaDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DetalleTramGenerarRepoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author MLIL
 */
@Service(value = "dyctDictAutomaticaService")
public class DyctDictAutomaticaServiceImpl implements DyctDictAutomaticaService {

    private static final Logger LOG = Logger.getLogger(DyctDictAutomaticaServiceImpl.class);
    private static final String ERROR_CONEXION = "Error en la conexion";
    @Autowired
    private DyctDicAutomaticaDAO dictAutomaticaDAO;

    /**
     * Método para insertar valores en la tabla dyct_dictautomatica
     *
     * @param dictAutomaticoDTO
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     *
     *
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = SIATException.class)
    public void insertarDictAutomatica(DyctDictAutomaticaDTO dictAutomaticoDTO) throws SIATException {

        try {
            dictAutomaticaDAO.insertarDicAutomatica(dictAutomaticoDTO);
        } catch (DAOException ex) {
            LOG.error("Error al insertar un registro", ex);
            throw new SIATException("Error al insertar un registro", ex);
        }
    }

    @Override
    public DyctDictAutomaticaDTO buscarDictAutomatica(String numControl) throws SIATException{
        try {
            return dictAutomaticaDAO.buscarDicAutomatica(numControl);
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);            
        }
    }
    /**
     * Método que busca los tipos de modelo para generar un reporte      
     * @param fechaInicioGenerarReporte 
     * @param fechaFinGenerarReporte 
     * @param idModelo 
     * @return totalDeFiltros
     * @throws SIATException
     **/
    @Override
    public Integer buscarGenerarReportePorFiltro(int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte) throws SIATException {                
        try {
            return dictAutomaticaDAO.buscarGenerarReportePorFiltro(idModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte).intValue();
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);            
        }
    }

    /**
     * Método Service para buscar por motivo de riesgo
     * @param idModelo 
     * @param fechaInicioGenerarReporte 
     * @param fechaFinGenerarReporte 
     * @param idMarcResultado 
     * @return Integer
     * @throws SIATException
     **/
    @Override
    public Integer buscarGenerarReportePorFiltroMotivoRiesgo(int idModelo, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, String idMarcResultado) throws SIATException {
        try {
            return dictAutomaticaDAO.buscarGenerarReportePorFiltroMotivoRiesgo(idModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, idMarcResultado).intValue();
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);            
        }
    }

    /**
     * Método Service para buscar por motivo de riesgo
     * @param idModelo 
     * @param fechaInicioGenerarReporte 
     * @param fechaFinGenerarReporte 
     * @param idEstadoSol
     * @return Integer
     * @throws SIATException
     **/
    @Override
    public Integer buscarGenerarReportePorFiltroFechaRegistro(int idModelo, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, int idEstadoSol) throws SIATException {
        try {
            return dictAutomaticaDAO.buscarGenerarReportePorFiltroFechaRegistro(idModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, idEstadoSol).intValue();
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);            
        }
    }

    /**
     * Método Service para buscar por motivo de riesgo
     * @param idEstadoSol
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @return Integer
     * @throws SIATException
     **/
    @Override
    public Integer buscarPendientesDictaminar(int idEstadoSol, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte) throws SIATException {
        try {
            return dictAutomaticaDAO.buscarPendientesDictaminar(idEstadoSol, fechaInicioGenerarReporte, fechaFinGenerarReporte).intValue();
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);
        }
    }
     /**
     * Método que busca los tipos de modelo para generar un reporte y mostrar
     * una lista de ese filtro
     * @param fechaInicioGenerarReporte 
     * @param fechaFinGenerarReporte 
     * @param idModelo 
     * @param paginador 
     * @return totalDeFiltros
     * @throws SIATException
     **/
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarDetalleTramGenRepoPorFiltro(int idModelo, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, Paginador paginador) throws SIATException {
        try {
            return dictAutomaticaDAO.consultarDetalleTramiteGenRepoPorFiltro(idModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, paginador);
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);
        }
    }

    /**
     * Método que busca para generar un reporte y mostrar
     * una lista de ese filtro por motivo de riesgo
     * @param fechaInicioGenerarReporte 
     * @param fechaFinGenerarReporte 
     * @param idModelo 
     * @param idMarcResultado 
     * @param paginador 
     * @return totalDeFiltros
     * @throws SIATException
     **/
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarDetalleTramGenRepoPorFiltroMotivoRiesgo(int idModelo, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, String idMarcResultado, Paginador paginador) throws SIATException {
        try {
            return dictAutomaticaDAO.consultarDetalleTramiteGenRepoPorFiltroMotivoRiesgo(idModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, idMarcResultado, paginador);
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);
        }
    }

    /**
     * Método que busca para generar un reporte y mostrar
     * una lista de ese filtro por fecha de registro
     * @param fechaInicioGenerarReporte 
     * @param fechaFinGenerarReporte 
     * @param idModelo 
     * @param idEstadoSol
     * @param paginador 
     * @return totalDeFiltros
     * @throws SIATException
     **/
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarDetalleTramGenRepoPorFiltroFechaRegistro(int idModelo, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, int idEstadoSol, Paginador paginador) throws SIATException {
        try {
            return dictAutomaticaDAO.consultarDetalleTramiteGenRepoPorFiltroFechaRegistro(idModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, idEstadoSol, paginador);
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);
        }
    }

    /**
     * Método que busca para generar un reporte y mostrar
     * una lista de ese filtro por fecha de registro
     * @param fechaInicioGenerarReporte
     * @param fechaFinGenerarReporte
     * @param idEstadoSol
     * @param paginador
     * @return totalDeFiltros
     * @throws SIATException
     **/
    @Override
    public List<DetalleTramGenerarRepoDTO> consultarPendientesDictaminar(int idEstadoSol, Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, Paginador paginador) throws SIATException {
        try {
            return dictAutomaticaDAO.consultarPendientesDictaminar(idEstadoSol, fechaInicioGenerarReporte, fechaFinGenerarReporte, paginador);
        } catch (DAOException ex) {
            LOG.error(ERROR_CONEXION, ex);
            throw new SIATException(ERROR_CONEXION, ex);
        }
    }
}
