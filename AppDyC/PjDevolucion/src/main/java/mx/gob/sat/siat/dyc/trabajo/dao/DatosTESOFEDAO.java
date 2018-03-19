package mx.gob.sat.siat.dyc.trabajo.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.tesofe.dto.DatosEMP;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DatosTESOFEDAO {
    List<DatosEMP> listarDatosTESOFE (String claves, Integer rechazo, Boolean esAutomaticaISR) throws SIATException;
    List<DatosEMP> listarDatosTESOFEConClaveDeRastreo (String clavesDeRastreo);    
    List<DatosEMP> contarListaDatosTESOFE (String claves, Integer rechazo, Boolean esAutomaticaISR) throws SIATException;
    Integer obtenerSecuencia() throws SIATException;
    Integer obtenerMotivoRechazo(String claveRastreo) throws SIATException;
    Integer obtenerNumerDeArchivo() throws SIATException;
}