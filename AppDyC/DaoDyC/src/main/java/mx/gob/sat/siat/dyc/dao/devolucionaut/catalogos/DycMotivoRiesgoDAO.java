package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DycMotivoRiesgoDAO {
    void insertar(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException;
    void modificar(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException;
    void inactivar(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException;
    List<DycMotivoRiesgoDTO> consultarTodos() throws SIATException;
    List<DycMotivoRiesgoDTO> consultarFiltro(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException;
    List<DycMotivoRiesgoDTO> consultarModelo() throws SIATException;
    List<DycMotivoRiesgoDTO> existe(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException;
}
