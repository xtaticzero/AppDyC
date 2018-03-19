/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador.MantenerCalendarioIndividualDTO;
import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 16/08/2013
 *
 */
public interface DyccCalDictaMinDAO {

    List<MantenerCalendarioIndividualDTO> consultarDictaminador(long numEmpDictaminador);

    List<DyccCalDictaminDTO> existenInhabiles(DyccCalDictaminDTO dyccCalDictaminDto);

    void adicionarDiaInhabil(DyccCalDictaminDTO dyccCalDictaminDto);

    void modificarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDto);

    void eliminarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDto);

    List<MantenerCalendarioIndividualDTO> consultarCalendarioGeneral(int idunidadadmva);

    void eliminarDiaGeneral(MantenerCalendarioIndividualDTO mantenerCalendarioIndividualDto);

    boolean verificarAsistenciaDictaminador(long numeEmpleado, Date fechaRegistro);

    boolean consultarDisponibilidadDictaminador(long numeEmpleado, Date fechaRegistro);
    
    boolean consultarDisponibilidadAlRegresoDictaminador(long numeEmpleado,
                                                         Date fechaRegistro);

    boolean validarRegresoDictamin(long numeEmpleado);
    
    boolean validarRegresoDelDictaminador(long numeEmpleado, Date fechaRegistro);
    
    /**
     * 
     * Validaciones con cambio de tabla dycc_caldictamin ReasignarManual
     */
    boolean validarDiaActual(Integer numeEmpleado);
    
    boolean validarRegresoDictaminador(Integer numeEmpleado);
    
    boolean valida4DiasInhabilesContinuos(Integer numeEmpleado);
    
    /**
     * Validaciones con cambio de tabla dycc_caldictamin ReasignarAutomatica
     */
    boolean validarDiaActualA(Integer numeEmpleado, Date fechaRegistro);
    
    boolean validarRegresoDictaminadorA(Integer numeEmpleado, Date fechaRegistro);
    
    boolean valida4DiasInhabilesContinuosA(Integer numeEmpleado, Date fechaRegistro);

}
