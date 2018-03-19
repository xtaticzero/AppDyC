/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;


/**
 * Interface service UnidadTramite
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 9, 2013
 * @since 0.1
 *
 * */
public interface DyccUnidadTramiteService {

    /**
     * @return Lista ArrayList [DyccUnidadTramiteDTO]
     */
    List<DyccUnidadTramiteDTO> obtieneLista();

    /**
     * @param dyccUnidadTramiteDTO
     */
    void insertarUnidadTramite(DyccUnidadTramiteDTO dyccUnidadTramiteDTO);

    /**
     * @param listaUnidadt
     */
    void insertarUnidadTramite(List<DyccUnidadTramiteDTO> listaUnidadt);

}
