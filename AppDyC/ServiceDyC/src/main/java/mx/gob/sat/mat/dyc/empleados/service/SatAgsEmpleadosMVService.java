/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.empleados.service;

import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author root
 */
public interface SatAgsEmpleadosMVService {

    String NO_REDIRECCIONAMIENTO = "";

    boolean getEstatusEmpleadoActivo(Object param) throws SIATException;

    String getEstatusEmpleado(Object param) throws SIATException;

    SatAgsEmpleadosMVDTO getEmpleadoAGS(Object param) throws SIATException;

    boolean aprobadorValido(Integer numEmpAprobador);

    void muestraMensajeAprobadorNoValido(String destino);

    void muestraMensajeAprobadorNoValido();

    /**void ejecutaJs(String cadenaJs);*/
}


