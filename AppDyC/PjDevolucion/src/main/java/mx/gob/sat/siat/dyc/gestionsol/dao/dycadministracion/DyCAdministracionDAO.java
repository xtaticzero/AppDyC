/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.dycadministracion;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion.AdmSolicitudesdycVO;


/**
 * Clase Interface DAO para la tabla [DYCP_SOLICITUD]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 24, 2013
 * @since 0.1
 *
 * */
public interface DyCAdministracionDAO {

    /**
     * @return Lista ArrayList [DycpSolicitudListaDTO]
     * @throws Exception
     */
    /**List<SolicitudListaVO> obtenerListaSolicitud();*/

    /**
     * @param solicitud
     * @return Lista ArrayList [DycpSolicitudListaDTO]
     * @throws Exception
     */
    List<AdmSolicitudesdycVO> obtenerListaSolicitud(AdmSolicitudesdycVO solicitud) throws SQLException;
    
    List<AdmSolicitudesdycVO> obtenerListaCompensaciones(AdmSolicitudesdycVO solicitud) throws SQLException;
    
    List<AdmSolicitudesdycVO> obtenerListaGeneral(AdmSolicitudesdycVO solicitud) throws SQLException;
}
