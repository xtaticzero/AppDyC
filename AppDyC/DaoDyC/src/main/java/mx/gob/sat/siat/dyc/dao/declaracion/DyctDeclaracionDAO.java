/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.declaracion;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctDeclaraTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;


public interface DyctDeclaracionDAO {
    DyctDeclaracionDTO encontrar(Long id);

    void insertar(DyctDeclaracionDTO dyctDeclaracionDTO, String secuencia);

    void insertar(DyctDeclaracionDTO dyctDeclaracionDTO) throws DycDaoExcepcion;
    
    List<DyctDeclaracionDTO> buscarDeclaracionesNumControl(String numControl);

    void createDeclaracion(DyctDeclaracionDTO input);

    void createRelacionDeclaracion(Integer idDeclaracion, String numControl);

    void createDeclaracionTemp(DyctDeclaraTempDTO declaraTem);

    InformacionSaldoFavorTO findDeclaracionTemp(int folio) throws SQLException;

    List<DyctDeclaracionDTO> selecXServicio(DycpServicioDTO servicio);
    
    int selecXServicioNumOperacion(final String numOperacion);
    
    List<DyctDeclaracionDTO> selectXNumOperacion(String numeroOperacion, String rfc);

}
