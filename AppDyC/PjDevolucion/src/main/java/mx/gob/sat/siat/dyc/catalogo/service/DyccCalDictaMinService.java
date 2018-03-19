/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador.MantenerCalendarioIndividualDTO;
import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 *
 * @author Alfredo Ramirez
 * @since  16/08/2013
 *
 */
public interface DyccCalDictaMinService {

    List<MantenerCalendarioIndividualDTO> consultarDictaminador(long numEmpDictaminador);

    List<DyccCalDictaminDTO> existenInhabiles(DyccCalDictaminDTO dyccCalDictaMinDto);

    void adicionarDiaInhabil(DyccCalDictaminDTO dyccCalDictaminDto);

    void modificarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDto);

    void eliminarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDto);

    List<MantenerCalendarioIndividualDTO> consultarCalendarioGeneral(int idunidadadmva);

    void eliminarDiaGeneral(MantenerCalendarioIndividualDTO mantenerCalendarioIndividualDto);

    int validarReasiganacion(Integer numeEmpleado);

    /**
     * @param movimiento
     * @param observacion
     */
    void registraPAuditoria(int movimiento, String observacion);

    void registraAcceso(AccesoUsr accesoUsr);
    
    void testMejora(Integer numeEmpleado);

}
