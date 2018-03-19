package mx.gob.sat.siat.dyc.dao.compensacion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;


public interface DyccEstadoCompDAO
{
    DyccEstadoCompDTO encontrar(Integer id);

    List<DyccEstadoCompDTO> obtenerLista();
}
