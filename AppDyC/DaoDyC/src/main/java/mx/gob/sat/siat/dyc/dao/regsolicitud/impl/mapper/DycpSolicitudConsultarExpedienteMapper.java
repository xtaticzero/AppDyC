/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Octubre 08, 2013
 *
 * */
public class DycpSolicitudConsultarExpedienteMapper implements RowMapper<SolicitudConsultarExpedienteVO> {
    public DycpSolicitudConsultarExpedienteMapper() {
        super();
    }

    @Override
    public SolicitudConsultarExpedienteVO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        dyccEjercicioDTO.setIdEjercicio(rs.getInt("IDEJERCICIO"));
        
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setDyccEjercicioDTO(dyccEjercicioDTO);
        
        SolicitudConsultarExpedienteVO sol = new SolicitudConsultarExpedienteVO();
        sol.setNumControl(rs.getString("NUMCONTROL"));
        sol.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        sol.setDescTramite(rs.getString("DESCTRAMITE"));
        sol.setEstadoSolicitud(rs.getString("DESCSOLICITUD"));
        sol.setNombre(rs.getString("NOMBRE"));
        sol.setApPaterno(rs.getString("APELLIDOPATERNO"));
        sol.setApMaterno(rs.getString("APELLIDOMATERNO"));
        sol.setDescImpuesto(rs.getString("DESCIMPUESTO"));
        sol.setDescConcepto(rs.getString("DESCCONCEPTO"));
        sol.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
        sol.setDescPeriodo(rs.getString("DESCPERIODO"));
        sol.setClabe(rs.getString("CLABE"));
        sol.setDescBanco(rs.getString("DESCBANCO"));
        sol.setDescSubOrigSaldo(rs.getString("DESCSUBORIGSALDO"));
        sol.setDescTipoServicio(rs.getString("DESCTIPOSERVICIO"));
        sol.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
        sol.setOrigenDevolucion(rs.getString("ORIGEN"));

        return sol;
    }
}
