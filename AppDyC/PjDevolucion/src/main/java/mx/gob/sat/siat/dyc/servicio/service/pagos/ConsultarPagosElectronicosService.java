/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.pagos;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf02DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoORADTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 18/07/2013
 *
 */
public interface ConsultarPagosElectronicosService {

    List<ConsultarPagosElectronicosAnioDTO> consultaRegistrosPorAnno(ConsultarPagosElectronicosAnioDTO consultarPagosElectronicosAnioDto);

    List<InformacionDePagoInf01DTO> informacionDePagoInf01(InformacionDePagoInf01DTO consultarInformacionDePagoInf01Dto);

    List<InformacionDePagoInf02DTO> informacionDePagoInf02(InformacionDePagoInf02DTO consultarInformacionDePagoInf02Dto);

    List<InformacionDePagoORADTO> informacionDePagoOra(InformacionDePagoORADTO consultarInformacionDePagoORADto);

}
