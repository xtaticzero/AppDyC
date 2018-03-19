package mx.gob.sat.siat.dyc.servicio.dao.immex;

import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */
public interface ImmexDAO {
     
     /**
     *
     * @param immexDTO
     * @return
     */
    ImmexDTO obtieneImmexSP(ImmexDTO immexDTO);
}
