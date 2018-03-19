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

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Febrero 12, 2014
 *
 * */
public class DycpConsultarExpedienteCompMapper implements RowMapper<SolicitudConsultarExpedienteVO> {
    
    @Override
    public SolicitudConsultarExpedienteVO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        dyccEjercicioDTO.setIdEjercicio(rs.getInt("EJERCICIO"));
        
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setDyccEjercicioDTO(dyccEjercicioDTO);
        
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        
        SolicitudConsultarExpedienteVO sol = new SolicitudConsultarExpedienteVO();

        sol.setFechaPresentacion(rs.getDate("FECHAREGISTRO"));
        sol.setFechaPresentaDec(rs.getDate("FECHADECLARACION"));
        sol.setNumControl(rs.getString("NUMCONTROL"));
        sol.setDescTramite(rs.getString("TIPOTRAMITE"));
        sol.setEstadoSolicitud(rs.getString("ESTADOTRAMITE"));
        sol.setNombre(rs.getString("NOMBRE"));
        sol.setApPaterno(rs.getString("APELLIDOPATERNO"));
        sol.setApMaterno(rs.getString("APELLIDOMATERNO"));
        sol.setDycpServicioDTO(dycpServicioDTO);
        sol.setDescImpuesto(rs.getString("IMPUESTO"));
        sol.setDescConcepto(rs.getString("CONCEPTO"));
        sol.setDescPeriodo(rs.getString("PERIODO"));
        sol.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
        sol.setImporteSolicitado(rs.getBigDecimal("IMPORTECOMPENSADO"));
        sol.setDescDeclaracion(rs.getString("TIPODECLARACION"));

        return sol;
    }
    
}
