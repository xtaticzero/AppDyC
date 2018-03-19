/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.dao;

import java.util.List;
import mx.gob.sat.siat.plazostramite.vo.PlazoTramiteVO;

/**
 *
 * @author root
 */
public interface PlazosTramiteDao {

    void actualizarEstados(String numControl, String numControlDoc);

    void actualizarEstadoReq(String numControlDoc);

    List<PlazoTramiteVO> buscarTramitesPlazos();

    List<PlazoTramiteVO> buscarTramitesCompensacionesPlazos();

    void actualizarEstadosComp(String numControl, String numControlDoc);
}
