package mx.gob.sat.siat.dyc.dao.usuario;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;

/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DyctContribuyenteDAO {
    DyctContribuyenteDTO encontrar(String numControl);
}
