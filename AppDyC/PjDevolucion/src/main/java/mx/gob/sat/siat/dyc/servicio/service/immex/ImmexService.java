package mx.gob.sat.siat.dyc.servicio.service.immex;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;

/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */

public interface ImmexService {
    
    ImmexDTO obtienenImmexSP(ImmexDTO immexDTO);
    
}
