/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.periodovacacional.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;


import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing. I. Ismael Castillo Justo
 */
public class DycpPeriodoVacMapper implements RowMapper<DycpPeriodoVacDTO> {
    public DycpPeriodoVacDTO mapRow(ResultSet rs, int i) throws SQLException {

        DycpPeriodoVacDTO periodo = new DycpPeriodoVacDTO();

        periodo.setIdPeriodo(Integer.parseInt(rs.getString("IDPERIODO")));
        periodo.setInicioPeriodo(rs.getDate("INICIOPERIODO"));
        periodo.setFinPeriodo(rs.getDate("FINPERIODO"));
        periodo.setEstado(rs.getBoolean("ESTADO"));
        periodo.setFechaMovimiento(rs.getDate("FECHAMOVIMIENTO"));
        periodo.setMensaje(rs.getString("MENSAJE"));
        periodo.setInicioHoraInhabServ(rs.getTimestamp("INICIOHORAINHABSERV"));
        periodo.setFinHoraInhabServ(rs.getTimestamp("FINHORAINHABSERV"));
               
        return periodo;
    }
    
}
