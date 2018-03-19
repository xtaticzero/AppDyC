package mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DycpSolicitudDevolucionDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla [DYCT_FACTURA]
 * @author J. Isaac Carbajal Bernal
 * @date Noviembre 20, 2013
 *
 * */

public class DycpSolicitudDevolucionMapper implements RowMapper<DycpSolicitudDevolucionDTO>{
    public DycpSolicitudDevolucionMapper() {
        super();
    }

    @Override
    public DycpSolicitudDevolucionDTO mapRow(ResultSet resultSet, int i) throws SQLException{
        DycpSolicitudDevolucionDTO fac = new DycpSolicitudDevolucionDTO();
        fac.setRfcProveedor(resultSet.getString("RFCPROVEEDOR"));
        fac.setNumFactura(resultSet.getString("NUMEROFACTURA"));
        fac.setFechaFactura(resultSet.getDate("FECHAEMISION"));
        fac.setConcepto(resultSet.getString("CONCEPTO"));
        fac.setImporte(resultSet.getBigDecimal("IMPORTE"));
        fac.setIvaTrasladado(resultSet.getBigDecimal("IVATRASLADADO"));
        fac.setNumControl(resultSet.getString("NUMCONTROL"));
        fac.setTotal(resultSet.getBigDecimal("TOTAL"));

        return fac;
        
    }
}
