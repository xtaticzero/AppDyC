/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.cpr;

import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;

/**
 * @author  Israel Chavez
 */
public interface CPRDAO {

    /**
     * @param icep
     * @return
     */
    CPRDTO obtenerCPR(CPRDTO cprdto);
    
}