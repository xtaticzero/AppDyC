/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;


/**
 * @author Federico Chopin Gachuz
 * @date Diciembre 11, 2013
 *
 * */
public interface DyccTipoResolDAO {
    
    List<DyccTipoResolDTO> buscarTiposResolucion();
    
    List<DyccTipoResolDTO> buscarTiposResolucionPreautorizada();
    
    List<DyccTipoResolDTO> buscarTiposResolucion(int idTipoServicio);    
    
}
