/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.service;

import mx.gob.sat.mat.rfcampl.client.DatosEntrada;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;

/**
 *
 * @author GAER8674
 */
public interface IRfcAmplService {
    /**
     * Para consumir el metodo getIdcInterno expuesto por el servicio RFC ampliado.
     * @param request
     * @return 
     */
    IdCInterno getIdcInterno(DatosEntrada request);
    /**
     * Para consultar los datos RFC ampliado (antesw IDC) del contribuyente.
     * @param rfc
     * @return
     * @throws RfcAmplExcepcion 
     */
    IdCInterno consultarXRfc(String rfc) throws RfcAmplExcepcion;
}
