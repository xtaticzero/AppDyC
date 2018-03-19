package mx.gob.sat.siat.dyc.admcat.service.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.ConsultaTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DatosTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.GuardadoTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.support.DyccTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.utility.InformacionAModificarTipoTramite;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface CatalogoTipoTramiteService {
    
    /**
     * Consulta los tipos de tramite dados de alta en base de datos.
     *
     * @return lista de los tipos de tramite dados de alta en base de datos.
     */
    List<DyccTipoTramiteVO> obtieneTipoTramite(long idOrigenSaldo) throws SIATException;
    /**
     * Consulta todos los datos necesarios para las altas o modificaciones de los tipos de tramite
     *
     * @return 
     */
    DatosTipoTramiteVO listarDatosParaTipoDeTramite() throws SIATException;
    
    /**
     * Guarda un nuevo tipo de tramite en todas las tablas asociadas.
     *
     * @param objeto
     * @throws SIATException
     */
    void guardarNuevoTramite(GuardadoTipoTramiteVO objeto) throws SIATException;
    
    /**
     * Retorna los origenes del saldo a partir del tipo de servicio
     *
     * @param tipoServicio
     * @return
     * @throws SIATException
     */
    List<DyccOrigenSaldoDTO> obtenerOrigenSaldo(Integer tipoServicio) throws SIATException;
    
    /**
     * Al eliminar el tramite lo unico que hace la aplicacion es poner fechaFin del tramite igual a nulo.
     *
     * @param idTipoTramite
     */
    void eliminarTramite(Integer idTipoTramite, Integer bandera) throws SIATException;
    
    /**
     * Consulta los datos de un tramite a partir del id del tramite. 
     * Este metodo se utiliza para la pantalla de consulta de tramites.
     *
     * @param idTipoTramite identificador del tramite con el cual se van a buscar los datos en BD.
     * @return Un objeto de todos los datos que se asocian con el ID del tramite.
     * @throws SIATException
     */
    ConsultaTipoTramiteVO consultarDatosDeUnTramite(Integer idTipoTramite) throws SIATException;
    
    /**
     * Consulta los datos que se dieron de alta en el tramite para la pantalla de modificacion de tramites.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    DatosTipoTramiteVO consultarDatosDeUnTramiteParaModificacion(Integer idTipoTramite) throws SIATException;
    
    /**
     * Obtiene una lista de los conceptos a partir del impuesto
     *
     * @param impuesto
     * @return
     * @throws SIATException
     */
    List<DyccConceptoDTO> obtenerConcepto(Integer impuesto) throws SIATException;
    
    /**
     * Modifica un tramite dado de alta previamente en base de datos
     *
     * @param objeto
     * @throws SIATException
     */
    void modificarTramite(InformacionAModificarTipoTramite objeto, GuardadoTipoTramiteVO datosTramite) throws SIATException;
    
    /**
     * Valida si el ID del tramite ingresado en pantalla que se intenta dar de alta existe en base de datos
     *
     * @param idTramite
     * @return
     * @throws SIATException
     */
    Integer validarIdTramite (Integer idTramite) throws SIATException;
    
    DyccTipoTramiteDTO obtieneTipoTramitePorId(int idTipoTramite) throws SIATException;
    
    
    DyccTipoTramiteDTO obtieneTipoTramiteID(int idTipoTrmite); 
}
