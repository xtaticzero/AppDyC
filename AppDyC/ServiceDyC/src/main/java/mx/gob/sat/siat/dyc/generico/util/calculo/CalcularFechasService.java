/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.generico.util.calculo;

import java.util.Date;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Federico Chopin Gachuz
 * @date Enero 21, 2014
 *
 *
 */
public interface CalcularFechasService {

    Date calcularFechaFinal(Date fechaInicial, int dias, int tipoDias) throws SIATException;

    boolean isDiaHabil(Date fecha);

    Date calculaFechaSugerida(Date fechaInicial, int dias) throws SIATException;

}
