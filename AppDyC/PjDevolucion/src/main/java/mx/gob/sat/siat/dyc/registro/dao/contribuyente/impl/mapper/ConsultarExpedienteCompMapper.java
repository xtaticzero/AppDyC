/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente.DeclaracionConsultarExpedienteVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Febrero 12, 2014
 *
 * */
public class ConsultarExpedienteCompMapper implements RowMapper<DeclaracionConsultarExpedienteVO>{
   
    @Override
    public DeclaracionConsultarExpedienteVO mapRow(ResultSet rs, int i) throws SQLException {
        DeclaracionConsultarExpedienteVO sol = new DeclaracionConsultarExpedienteVO();

        sol.setOrigenSaldo(rs.getString("ORIGENSALDO"));
        sol.setPeriodo(rs.getString("PERIODO"));
        sol.setEjercicio(rs.getInt("EJERCICIO"));
        sol.setFechaCausacion(rs.getDate("FECHACAUSACION"));
        sol.setConcepto(rs.getString("CONCEPTO"));
        sol.setSaldoAplicar(rs.getDouble("SALDOAPLICAR"));
        sol.setFechaPresDecl(rs.getDate("FECHAPRESDECL"));
        sol.setMontoSaldoFavor(rs.getDouble("MONTOSALDOAFAVOR"));
        sol.setRemanenteHistorico(rs.getDouble("REMANENTEHISTORICO"));
        sol.setRemanenteAct(rs.getDouble("REMANENTEACT"));
        sol.setPeriodicidad(rs.getString("PERIODICIDAD"));
    
        return sol;
    }
   
}
