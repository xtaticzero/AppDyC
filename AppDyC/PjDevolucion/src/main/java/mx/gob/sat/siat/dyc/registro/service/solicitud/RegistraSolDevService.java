/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.solicitud;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *@author Paola Rivera
 */
public interface RegistraSolDevService {

    /**
     * @param rfcRetenedor
     * @return
     */
    ParamOutputTO<PersonaDTO> validaRFCRetenedor(String rfcRetenedor) throws SIATException;

    ParamOutputTO<SolicitudDevolucionRegistroVO> solicitudesPendientes(String rfc) throws SIATException;

    String validaRFCControlador(String rfcRetenedor) throws SIATException;
    boolean desdeTramitesYNoEstaAmparado(String rfc) ;
    boolean consultaSiRfcAmparado(String rfc) ;

}
