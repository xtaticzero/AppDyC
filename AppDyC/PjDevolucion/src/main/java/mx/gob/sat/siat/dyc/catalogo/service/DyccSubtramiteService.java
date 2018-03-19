/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;


/**
 * @author Paola Rivera
 */
public interface DyccSubtramiteService {

    /**
     * @return
     */
    List<DyccSubTramiteDTO> obtieneSubtramite();
}