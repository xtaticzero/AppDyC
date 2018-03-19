/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.batch.dictautomaticas.service;

import java.util.List;
import mx.gob.sat.mat.batch.dictautomaticas.exception.DictAutomaticasException;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;

/**
 *
 * @author root
 */
public interface DyctDictAutomaticasService {

    List<DyctDictAutomaticaDTO> getAllTramitesNoProcesados();
    
    List<DyctDictAutomaticaDTO> getTramitesDictaminados();

    void procesarTramite(DyctDictAutomaticaDTO tramite) throws DictAutomaticasException;

}
