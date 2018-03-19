/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.ws;

import mx.gob.sat.mat.rfcampl.client.DatosEntrada;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;

/**
 * Capa de acceso a los datos.
 * @author GAER8674
 */
public interface IRfcAmplWs {
    /**
     * Para consumir el metodo getIdcInterno expuesto por el servicio RFC ampliado.
     * @param datosEntrada Request
     * @return 
     */
    IdCInterno getIdcInterno(DatosEntrada datosEntrada);
}
