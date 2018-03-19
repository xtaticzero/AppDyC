/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.dycadministracion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion.SolicitudListaVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para CASO DE USO ADMINISTRACION SOLICITUDES DEVOLUCION.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 25, 2013
 * @since 0.1
 * <BR/>
 * <TABLE BORDER='1'>
 * <TR><TH> COLUMNA </TD><TD>NOMBRE</TH></TR>
 * <TR><TD>S.CLAVEADM </TD><TD> CLAVEADM</TD></TR>
 * <TR><TD>U.NOMBRE </TD><TD> ADMINISTRACION</TD></TR>
 * <TR><TD>D.NUMEMPLEADO  </TD><TD> NUMEMPLEADO</TD></TR>
 * <TR><TD>D.NOMBRECOMPLETO </TD><TD> DICTAMINADOR</TD></TR>
 * <TR><TD>S.NUMCONTROL </TD><TD> NUMCONTROL</TD></TR>
 * <TR><TD>S.RFCCONTRIBUYENTE </TD><TD> RFCCONTRIBUYENTE</TD></TR>
 * <TR><TD>'Solicitud de Devolución' </TD><TD> TRAMITE</TD></TR>
 * <TR><TD>S.IDTIPOTRAMITE||' - '||T.DESCRIPCION </TD><TD> TIPOTRAMITE</TD></TR>
 * <TR><TD>ES.IDESTADOSOLICITUD </TD><TD> IDESTADOSOLICITUD</TD></TR>
 * <TR><TD>ES.DESCRIPCION </TD><TD> ESTADO</TD></TR>
 * <TR><TD>S.FECHAPRESENTACION </TD><TD> FECHAPRESENTACION</TD></TR>
 * <TR><TD>0 </TD><TD> MONTO</TD></TR>
 * <TR><TD>S.FECHAPRESENTACION </TD><TD> FECHAPRESENTACION</TD></TR>
 * </TABLE>
 *
 * */
public class DycpSolicitudListaMapper implements RowMapper<SolicitudListaVO> {
    public DycpSolicitudListaMapper() {
        super();
    }

    @Override
    public SolicitudListaVO mapRow(ResultSet rs, int i) throws SQLException {
        SolicitudListaVO solicitud = new SolicitudListaVO();
        solicitud.setClaveAdm(rs.getInt("CLAVEADM"));
        solicitud.setUnidadAdmva(rs.getString("ADMINISTRACION"));
        solicitud.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        solicitud.setDictaminador(rs.getString("DICTAMINADOR"));
        solicitud.setNumControl(rs.getString("NUMCONTROL"));
        solicitud.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        solicitud.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        solicitud.setTipoServicio(rs.getString("TRAMITE"));
        solicitud.setTipoTramite(rs.getString("TIPOTRAMITE"));
        solicitud.setIdEstadoSolicitud(rs.getInt("IDESTADOSOLICITUD"));
        solicitud.setEstado(rs.getString("ESTADO"));
        solicitud.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        solicitud.setMonto(rs.getLong("IMPORTESOLICITADO"));

        return solicitud;
    }
}
