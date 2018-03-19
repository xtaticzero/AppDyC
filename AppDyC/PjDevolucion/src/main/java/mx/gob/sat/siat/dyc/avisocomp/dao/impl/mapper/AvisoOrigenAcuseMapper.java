package mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;

import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;

import org.springframework.jdbc.core.RowMapper;


public class AvisoOrigenAcuseMapper implements RowMapper<DatosOrigenAcuseVO> {
    public AvisoOrigenAcuseMapper() {
        super();
    }

    @Override
    public DatosOrigenAcuseVO mapRow(ResultSet rs, int i) throws SQLException {
        DecimalFormat formatoDecimal = new DecimalFormat("$ ###,###.##");
        DatosOrigenAcuseVO origenAcuse = new DatosOrigenAcuseVO();
        
        origenAcuse.setDescOrigenSaldo(rs.getString("ORIGENSALDO"));
        origenAcuse.setDescConceptoOrigen(rs.getString("IDCONCEPTO") + "  " + rs.getString("DESCRIPCION"));
        origenAcuse.setDescTipoPeriodo(rs.getString("TIPOPERIODO"));
        origenAcuse.setDescEjercicio(rs.getString("IDEJERCICIO"));
        origenAcuse.setDescTipoDeclaracion(rs.getString("TIPODECLARACION"));
        origenAcuse.setNumControl(rs.getString("NUMCONTROL"));
        origenAcuse.setDescPeriodo(rs.getString("PERIODO"));
        origenAcuse.setNumOperacion(rs.getString("NUMOPERACION"));
        origenAcuse.setFechaPresentOrigen(rs.getString("FECHAPRESENTACION"));
        origenAcuse.setCantidadCompensa(formatoDecimal.format(rs.getDouble("CANTIDADCOMPENSA")));
        origenAcuse.setSaldoAFavor(formatoDecimal.format(rs.getDouble("SALDOAFAVOR")));
            
        return origenAcuse;
    }
}
