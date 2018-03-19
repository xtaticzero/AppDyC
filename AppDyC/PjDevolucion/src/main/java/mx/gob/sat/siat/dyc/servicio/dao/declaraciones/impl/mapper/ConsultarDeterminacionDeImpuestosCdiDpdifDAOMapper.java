package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionDeImpuestosCdiDpdifDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 26/07/2013
 *
 */
public class ConsultarDeterminacionDeImpuestosCdiDpdifDAOMapper implements RowMapper<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> {

    public ConsultarDeterminacionDeImpuestosCdiDpdifDAOMapper() {
        super();
    }

    public ConsultarDeterminacionDeImpuestosCdiDpdifDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionDeImpuestosCdiDpdifDTO currentDeterminacionDeImpuestosCdiDpdifDTO = new ConsultarDeterminacionDeImpuestosCdiDpdifDTO();
        
        currentDeterminacionDeImpuestosCdiDpdifDTO.setNEejercicio(resultSet.getInt("N_EJERCICIO"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setCNPeriodo(resultSet.getString("C_N_PERIODO"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setTdiepco1(resultSet.getString("TDIEPCO1"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setFperceh1(resultSet.getDate("FPERCEH1"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setNNumeroOperacion(resultSet.getInt("N_NUMERO_OPERACION"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setFORMULARIO(resultSet.getString("FORMULARIO"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setI111021(resultSet.getInt("I_111021"));
        currentDeterminacionDeImpuestosCdiDpdifDTO.setI111024(resultSet.getInt("I_111024"));

        return currentDeterminacionDeImpuestosCdiDpdifDTO;
    }
    
}