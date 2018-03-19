/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.periodovacacional;

import mx.gob.sat.siat.dyc.domain.periodovacacional.DyctReinicioSecParamDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Orlando Tepoz Z.
 */
public interface DyctReinicioSecParamDAO {

    DyctReinicioSecParamDTO buscarFechaReinicioActivo() throws SIATException;

    void inactivarFechaReinicioSecuencia(DyctReinicioSecParamDTO dTO) throws SIATException;

    DyctReinicioSecParamDTO insertarNuevaFechaReinicioSecuencia(DyctReinicioSecParamDTO dTO) throws SIATException;

}
