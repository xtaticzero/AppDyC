/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;


/**
 *
 * @author GAER8674
 */
public interface DyctDevIvaDAO {
    List<DyctDevAutoIvaDTO> selectXNumeroLote(Long numeroLote);
    void actualizaEstado(DyctDevAutoIvaDTO dev, int estado);
    List<DyctDevAutoIvaDTO> selectNuevasDevoluciones();
}
