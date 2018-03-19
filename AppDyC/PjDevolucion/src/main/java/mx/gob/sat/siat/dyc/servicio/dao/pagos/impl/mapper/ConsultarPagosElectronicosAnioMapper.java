/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.pagos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Israel Chavez
 * @since 09/07/2013
 *
 */
public class ConsultarPagosElectronicosAnioMapper implements RowMapper<ConsultarPagosElectronicosAnioDTO> {

    public ConsultarPagosElectronicosAnioMapper() {
        super();
    }

    public ConsultarPagosElectronicosAnioDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarPagosElectronicosAnioDTO pagosElectronicosAnioDto = new ConsultarPagosElectronicosAnioDTO();
        
        pagosElectronicosAnioDto.setVFechaCausacion(resultSet.getString("v_fecha_causacion"));
        pagosElectronicosAnioDto.setVFperceh1(resultSet.getDate("v_fperceh1"));
        pagosElectronicosAnioDto.setVIapidne1(resultSet.getInt("v_iapidne1"));
        pagosElectronicosAnioDto.setVIapfdne1(resultSet.getInt("v_iapfdne1"));
        pagosElectronicosAnioDto.setVImporteAcargo(resultSet.getBigDecimal("v_importe_acargo"));
        pagosElectronicosAnioDto.setVImporteAfavor(resultSet.getBigDecimal("v_importe_afavor"));
        pagosElectronicosAnioDto.setVCompensacion(resultSet.getBigDecimal("v_compensacion"));
        pagosElectronicosAnioDto.setVNNumeroOperacion(resultSet.getBigDecimal("v_n_numero_operacion"));
        pagosElectronicosAnioDto.setVTdiepco1(resultSet.getInt("v_tdiepco1"));
        
        return pagosElectronicosAnioDto;
    }
    
}
