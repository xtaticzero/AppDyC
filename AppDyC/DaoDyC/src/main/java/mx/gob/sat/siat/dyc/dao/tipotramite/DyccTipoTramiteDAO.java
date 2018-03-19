package mx.gob.sat.siat.dyc.dao.tipotramite;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccTipoTramiteDAO {
    DyccTipoTramiteDTO encontrar(Integer idTipoTramite) throws SIATException;

    /**
     * @param idOrigenSaldo
     * @return
     */
    List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdOrigen(int idOrigenSaldo, int competencia, boolean isAgace, int... rol);

    void insertar(DyccTipoTramiteDTO objeto) throws SIATException;

    List<DyccTipoTramiteDTO> obtieneTipoTramite(long idOrigenSaldo) throws SIATException;

    List<DyccTipoTramiteDTO> obtieneTodosTipoTramite(Integer tramite);

    List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdSubOrigenSaldo(int idOrigenSaldo);

    List<DyccTipoTramiteDTO> obtieneTipoTramitePorAnexo(int anexo);

    List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdConcepto(int idConcepto);

    List<DyccTipoTramiteDTO> selectXIdSuborigenSaldo(Integer idSuborigenSaldo);

    Integer selecXTipoTramite(int concepto, int rol, int competencia, int origenSaldo,
                              int tipoServicio) throws SIATException;

    boolean verificaSiEsResolucionAutomatica(int idTipoTramite) throws SIATException;

    Integer obtenerTipoTramiteXConceptoAvisos(int concepto);

    List<DyccTipoTramiteDTO> tramitesPagoDeLoIndebido() throws SQLException;

    void eliminarTramite(Integer idTipoTramite, Integer bandera) throws SIATException;

    DyccTipoTramiteDTO obtieneTipoTramite(int idTipoTrmite);

    /**
     * Actualiza los datos del tipo de tramite a traves de su ID
     *
     * @param objeto
     * @throws SIATException
     */
    void actualizarTipoTramite(DyccTipoTramiteDTO objeto) throws SIATException;

    /**
     * Tipo de tramite para aviso de compensacion
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param idConceptoOrigen
     * @param origenSaldo
     * @return
     */
    List<DyccTipoTramiteDTO> obtieneTipoTramitePorConceptoOrigen(int idConceptoOrigen, int origenSaldo,
                                                                 int tipoRol);

    /**
     * Valida si el ID del tramite ingresado en pantalla que se intenta dar de alta existe en base de datos
     *
     * @param idTramite
     * @return
     * @throws SIATException
     */
    Integer validarIdTramite(Integer idTramite) throws SIATException;
    
    List<DyccTipoTramiteDTO> selecOrderXImpuesto(DyccImpuestoDTO impuesto, String orderBy);
    
    List<DyccTipoTramiteDTO> selecOrderXTiposervicio(DyccTipoServicioDTO tipoServicio, String orderBy);

    List<DyccTipoTramiteDTO> selecOrderXTiposervicioImpuesto(DyccTipoServicioDTO tipoServicio, DyccImpuestoDTO impuesto, String orderBy);
    
    DyccTipoTramiteDTO obtieneTipoTramiteID(int idTipoTrmite); 
}
