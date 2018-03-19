/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Paola Rivera
 */
public interface DyccTipoPeriodoService {

    /**
     * @return
     */
    List<DyccTipoPeriodoDTO> obtieneTipoPeriodo();

    ParamOutputTO<DyccTipoPeriodoDTO> tipoPeriodo(int tramite) throws SIATException;

    ParamOutputTO<CatalogoTO> findTipoPeriodo(int idPeriodo) throws SIATException;
    
    DyccTipoPeriodoDTO encontrar(String id);
    
  }
