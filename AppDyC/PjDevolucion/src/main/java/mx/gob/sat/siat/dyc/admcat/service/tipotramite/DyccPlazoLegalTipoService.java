/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DyccPlazoLegalTipoDTO;


/**
 * Clase Interface service para la tabla [DYCC_PLAZOLEGAL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
public interface DyccPlazoLegalTipoService {

    /**
     * @return ArrayList DyccPlazoLegalTipoDTO
     */
    List<DyccPlazoLegalTipoDTO> obtenerListaPlazo();

}
