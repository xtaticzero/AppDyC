/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Paola Rivera
 */
public interface DyccTipoTramiteService {

    /**
     * @param idOrigenSaldo
     * @return
     */


    ParamOutputTO<CatalogoTO> obtieneTipoTramite(int idOrigenSaldo, int competencia, List<Integer> roles, boolean isAgace) throws SIATException;

    ParamOutputTO<CatalogoTO> tramitesPagoDeLoIndebido() throws SIATException;

    List<DyccTipoTramiteDTO> obtenerTramites(Integer tramite) throws SQLException;

    List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdSubOrigenSaldo(int idOrigenSaldo) throws SQLException;

    List<DyccTipoTramiteDTO> obtieneTipoTramitePorAnexo(int anexo) throws SQLException;


    Integer obtenerTipoTramiteXConceptoAvisos(int concepto);

    List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdConcepto(int idConcepto) throws SQLException;

    ParamOutputTO<DyccTipoTramiteDTO> getTramite(int idTipoTramite) throws SIATException;

    DyccTipoTramiteDTO encontrar(Integer idTipoTramite) throws SIATException;

    /**
     * Servicio para obtener tramite aviso de compensacion
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param idConcepto
     * @return
     * @throws SQLException
     */
    List<DyccTipoTramiteDTO> obtieneTipoTramitePorConceptoOrigen(int idConcepto, int origensaldo,
                                                                 int tipoRol) throws SQLException;


}
