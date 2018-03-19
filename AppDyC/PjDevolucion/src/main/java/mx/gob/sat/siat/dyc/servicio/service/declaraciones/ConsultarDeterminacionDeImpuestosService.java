/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.declaraciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionDeImpuestosCdiDpdifDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiDpdifAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiImpuestosDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDID3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDidDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma132DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma1EDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma22DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2a2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2aDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma32DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosDID2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosForma3DTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 31/07/2013
 *
 */
public interface ConsultarDeterminacionDeImpuestosService {

    List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImpuestosCdiDpdif(ConsultarDeterminacionDeImpuestosCdiDpdifDTO consultarDeterminacionDeImpuestosCdiDpdifDTO);

    List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionDeImpuestosCdiDpdifAnio(ConsultarDeterminacionImpuestoCdiDpdifAnioDTO determinacionImpuestoCdiDpdifAnioDTO);

    List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestos(ConsultarDeterminacionImpuestoCdiImpuestosDTO determinacionImpuestoCdiImpuestosDTO);

    List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13(ConsultarDeterminacionImpuestoForma13DTO determinacionImpuestoForma13DTO);

    List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13A(ConsultarDeterminacionImpuestoForma13ADTO determinacionImpuestoForma13ADTO);

    List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDid(ConsultarDeterminacionImpuestoDidDTO determinacionImpuestoDidDTO);

    List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2(ConsultarDeterminacionImpuestoForma2DTO determinacionImpuestoForma2DTO);

    List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2a(ConsultarDeterminacionImpuestoForma2aDTO determinacionImpuestoForma2aDTO);

    List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3(ConsultarDeterminacionImpuestosForma3DTO determinacionImpuestosForma3DTO);

    List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132(ConsultarDeterminacionImpuestoForma132DTO determinacionImpuestoForma132DTO);

    List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2(ConsultarDeterminacionImpuestosDID2DTO determinacionImpuestosDID2DTO);

    List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22(ConsultarDeterminacionImpuestoForma22DTO determinacionImpuestoForma22DTO);

    List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoforma2a2(ConsultarDeterminacionImpuestoForma2a2DTO determinacionImpuestoForma2a2DTO);

    List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32(ConsultarDeterminacionImpuestoForma32DTO determinacionImpuestoForma32DTO);

    List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3(ConsultarDeterminacionImpuestoDID3DTO determinacionImpuestoDID3DTO);

    List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1E(ConsultarDeterminacionImpuestoForma1EDTO determinacionImpuestoForma1EDTO);

}
