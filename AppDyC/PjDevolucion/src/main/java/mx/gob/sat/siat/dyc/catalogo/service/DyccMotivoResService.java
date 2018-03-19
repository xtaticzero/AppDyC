/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoResDTO;


/**
 * @author Federico Chopin Gachuz
 * @date Diciembre 11, 2013
 *
 * */
public interface DyccMotivoResService {

    List<DyccMotivoResDTO> buscarMotivosResolucion(int tipoResol);
    List<DyccMotivoResDTO> buscarMotivosResolucionDesistimiento(int tipoResol);

}
