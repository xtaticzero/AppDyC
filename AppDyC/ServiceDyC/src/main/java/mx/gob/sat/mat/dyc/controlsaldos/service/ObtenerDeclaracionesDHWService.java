/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.controlsaldos.service;

import java.util.List;

import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author christian.ventura
 */
public interface ObtenerDeclaracionesDHWService {

    List<DeclaracionDwhVO> execute(DyctSaldoIcepDTO saldoIcep) throws SIATException;
    
}
