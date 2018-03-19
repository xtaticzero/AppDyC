/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.controlsaldos.icep.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author David Guerrero Reyes
 * @version 1.0
 * @see
 * @date  $Date$
 */
public class IcepConsultaPagosMapper implements RowMapper<DeclaracionDwhVO>
{
    @Override
    public DeclaracionDwhVO mapRow(ResultSet rs, int i) throws SQLException {
        
        DeclaracionDwhVO declaracionDwhVO = new DeclaracionDwhVO();

        declaracionDwhVO.setEstatusDeclaracion (rs.getInt(ConstantesDyCNumerico.VALOR_1));
        declaracionDwhVO.setTipoDeclaracion (rs.getInt(ConstantesDyCNumerico.VALOR_2));
        declaracionDwhVO.setFechaPresentacion (rs.getDate(ConstantesDyCNumerico.VALOR_3));
        declaracionDwhVO.setNumOperacion (rs.getLong (ConstantesDyCNumerico.VALOR_4));
        declaracionDwhVO.setSaldo (rs.getDouble (ConstantesDyCNumerico.VALOR_5));
        
        return declaracionDwhVO;
    }

}
