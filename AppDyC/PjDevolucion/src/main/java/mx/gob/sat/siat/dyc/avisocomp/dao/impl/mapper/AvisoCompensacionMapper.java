/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.avisocomp.vo.PendienteVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el VO PendienteVO
 * @author  Alfredo Ramirez
 * @since   20/11/2013
 */
public class AvisoCompensacionMapper implements RowMapper<PendienteVO> {

    @Override
    public PendienteVO mapRow(ResultSet rs, int i) throws SQLException {

        PendienteVO pendienteVO = new PendienteVO();

        pendienteVO.setImpuesto(rs.getString("impuesto"));
        pendienteVO.setConcepto(rs.getString("concepto"));
        pendienteVO.setEjercicio(rs.getInt("ejercicio"));
        pendienteVO.setPeriodo(rs.getString("periodo"));
        pendienteVO.setImporte(rs.getDouble("importe"));
        pendienteVO.setFechaCompensacion(rs.getDate("fechainiciotramite"));
        pendienteVO.setFolioAvisoTemp(rs.getInt("folioAvisoTemp"));

        return pendienteVO;
    }

}
