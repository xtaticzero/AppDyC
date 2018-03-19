package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 *
 * @author Jose Luis Aguilar
 */
public interface MotivoRiesgoService  {

    void registrar(DycMotivoRiesgoDTO motivoRiesgoDTO, AccesoUsr accesoUsr) throws SIATException;
    
    void actualizar(DycMotivoRiesgoDTO motivoRiesgoDTO, AccesoUsr accesoUsr) throws SIATException;
    
    void inActivar(DycMotivoRiesgoDTO motivoRiesgoDTO, AccesoUsr accesoUsr) throws SIATException;
    
    List<DycMotivoRiesgoDTO> consultarTodos() throws SIATException;
    
    List<DycMotivoRiesgoDTO> consultarFiltro(DycMotivoRiesgoDTO motivoRiesgoDTO) throws SIATException;
    
    List<DycMotivoRiesgoDTO> consultarModelo() throws SIATException;
    
    List<DycMotivoRiesgoDTO> existe(DycMotivoRiesgoDTO motivoRiesgoDTO) throws SIATException;
    
}