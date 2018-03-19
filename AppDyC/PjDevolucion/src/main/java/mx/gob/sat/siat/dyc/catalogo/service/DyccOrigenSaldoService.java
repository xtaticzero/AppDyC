/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccOrigenSaldoService {

    List<DyccOrigenSaldoDTO> obtieneOrigenesSaldo();

    List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion();
    
    /**
     * @see {@link mx.gob.sat.siat.dyc.dao.impl.DyccOrigenSaldoDAOImpl.obtieneOrigenesDeSaldosPorAvisoCompensacion}
     */
    List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion(final String param1) ;

    ParamOutputTO<CatalogoTO> obtieneOrigenesSaldo(String registrador) throws SIATException;

    ParamOutputTO<CatalogoTO> getOrigenSaldo(int idOrigen) throws SIATException;
    
   DyccOrigenSaldoDTO encontrar(Integer id);
}
