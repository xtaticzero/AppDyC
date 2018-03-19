/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaInconsistTempDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;


/**
 * Interface DAO para consulta de inconsistencias
 * @author Federico Chopin Gachuz
 *
 * */
public interface DyccInconsistenciaDAO {

    DyccInconsistDTO buscarInconsistencia(int idInconsistencia);

    List<DycaSolInconsistDTO> buscarSolicitudDev(String numControl);

    List<DyctDictAutomaticaDTO> buscarMotivosRiesgo(String numControl);

    void insertarInconsistencia(DycaSolInconsistDTO dycaSolInconsistDTO);

    List<DycaSolInconsistDTO> findInconsistenciasTemp(int folio);

    void createInconsitenciasTemp(DycaInconsistTempDTO inputSolTemp);

}
