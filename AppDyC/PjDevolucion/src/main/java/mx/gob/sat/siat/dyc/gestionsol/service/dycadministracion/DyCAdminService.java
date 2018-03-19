/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.service.dycadministracion;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.MatrizDictaminadorVO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion.AdmSolicitudesdycVO;


/**
 * Clase Interface service para administracion devoluciones y casos de compensacion.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
public interface DyCAdminService {

    List<AdmSolicitudesdycVO> obtenerSolicitudes(AdmSolicitudesdycVO solicitud, Integer tramite);

    List<AdmcUnidadAdmvaDTO> obtenerAdministraciones();
    
    List<MatrizDictaminadorVO> obtenerDictaminadores(AdmcUnidadAdmvaDTO adm);

    List<DyccTipoTramiteDTO> obtenerTipoTRamite(Integer tramite);

    List<DyccEstadoSolDTO> obtenerEstadoSol();
    
    List<DyccEstadoCompDTO> obtenerEstadoComp();

}
