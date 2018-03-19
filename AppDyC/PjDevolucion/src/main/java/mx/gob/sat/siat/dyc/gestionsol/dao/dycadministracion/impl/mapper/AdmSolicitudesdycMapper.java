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

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion.AdmSolicitudesdycVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para caso de uso administrar solicitudes devoluciones compensaciones.
 * Clase Implementacion DAO para la tabla [DYCC_APROBADOR].
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1 , Enero 30, 2014
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
public class AdmSolicitudesdycMapper implements RowMapper<AdmSolicitudesdycVO> {
    public AdmSolicitudesdycMapper() {
        super();
    }

    @Override
    public AdmSolicitudesdycVO mapRow(ResultSet rs, int i) throws SQLException {
        AdmSolicitudesdycVO sol = new AdmSolicitudesdycVO();
        DyccEstadoSolDTO dyccEstadoSol = new DyccEstadoSolDTO();
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        DyctSaldoIcepDTO dycSaldoIcepDTO = new DyctSaldoIcepDTO();
        
        sol.setNumControl(rs.getString("NUMCONTROL"));
        sol.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        sol.setImporteSolicitado(rs.getBigDecimal("IMPORTESOLICITADO"));
        
        sol.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        
        sol.setIdTipoServicio(rs.getInt("IDTIPOSERVICIO"));        
        sol.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));        
        sol.setClaveAdm(rs.getInt("CLAVEADM"));
        dyccEstadoSol.setIdEstadoSolicitud(rs.getInt("IDESTADOSOLICITUD"));
        sol.setDyccEstadoSolDTO(dyccEstadoSol);
        
        dyccEjercicioDTO.setIdEjercicio(rs.getInt("EJERCICIO"));
        dycSaldoIcepDTO.setDyccEjercicioDTO(dyccEjercicioDTO);
        sol.setDyctSaldoIcepDTO(dycSaldoIcepDTO);
        sol.setRolDictaminado(rs.getString("ROLDICTAMINADO"));
        sol.setRolGranContribuyente(rs.getInt("ROLGRANCONTRIB"));
        sol.setTipoTramite(rs.getString("TIPOTRAMITE"));
        sol.setEstadoNotificacion(rs.getString("ESTADONOTIFICACION"));
        sol.setEstadoSolicitud(rs.getString("ESTADOSOLICITUD"));
        sol.setNumEmpleado(rs.getLong("NUMEMPLEADODICT"));
        sol.setNombre(rs.getString("NOMBRE"));
        sol.setApPaterno(rs.getString("APELLIDOPATERNO"));
        sol.setApMaterno(rs.getString("APELLIDOMATERNO"));
        sol.setDias(rs.getInt("DIAS"));
        sol.setTipoDia(rs.getInt("TIPODIA"));
        sol.setNumRequerimiento(rs.getInt("NUMREQUERIMIENTO"));
        sol.setPeriodo(rs.getString("PERIODO"));
        sol.setNumControlDoc(rs.getString("NUMCONTROLDOC"));

        sol.setEstadoDyC(rs.getString("ESTADO_DYC"));
        sol.setAdmNombre(rs.getString("ADMINISTRACION"));

        return sol;
    }
}
