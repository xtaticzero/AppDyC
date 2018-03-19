/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.registro.dao.contribuyente;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;

/**
 *
 * @author GAER8674
 */
public interface ContribuyenteDAO {
    void createContribuyenteDYCT(String query, DyctContribuyenteDTO input);

    DyctContribTempDTO findContrinbuyenteTemp(int folio);
    
    void createContribuyente(DyctContribuyenteDTO input) throws DycDaoExcepcion;
}
