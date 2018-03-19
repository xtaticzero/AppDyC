/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.ags;

import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;

/**
 *
 * @author root
 */
public interface SatAgsEmpleadosMVDAO {


    String getEstatusEmpleado(Object param, boolean isNumerico);

    SatAgsEmpleadosMVDTO getEmpleadoAGS(Object param, boolean isNumerico);
    
    String getCentroCostoEmpleado(Object param);
    
}
