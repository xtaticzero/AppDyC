package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DyctSaldoIcepAuxVO;

import org.springframework.jdbc.core.RowMapper;


public class DyctSaldoIcepAuxMapper implements RowMapper<DyctSaldoIcepAuxVO>{
    
    public DyctSaldoIcepAuxMapper(){
    super();
    }

    @Override
    public DyctSaldoIcepAuxVO mapRow(ResultSet resultSet, int i) throws SQLException {
        
        DyctSaldoIcepAuxVO dyctSaldoIcepAuxDTO = new DyctSaldoIcepAuxVO();
        
        dyctSaldoIcepAuxDTO.setRfc(resultSet.getString("RFC"));
        dyctSaldoIcepAuxDTO.setImpuesto(resultSet.getString("IMPUESTO"));
        dyctSaldoIcepAuxDTO.setConcepto(resultSet.getString("CONCEPTO"));
        dyctSaldoIcepAuxDTO.setTipoPeriodo(resultSet.getString("TIPOPERIODO"));
        dyctSaldoIcepAuxDTO.setPeriodo(resultSet.getString("PERIODO"));
        dyctSaldoIcepAuxDTO.setEjercicio(resultSet.getInt("EJERCICIO"));
        dyctSaldoIcepAuxDTO.setTipoSaldo(resultSet.getString("TIPOSALDO"));
        dyctSaldoIcepAuxDTO.setIdSaldoIcep(resultSet.getLong("IDSALDOICEP"));
        dyctSaldoIcepAuxDTO.setNumControl(resultSet.getString("NUMCONTROL"));
        dyctSaldoIcepAuxDTO.setRemanente(resultSet.getDouble("REMANENTE"));
        dyctSaldoIcepAuxDTO.setBloqueado(resultSet.getInt("BLOQUEADO"));
        
        return dyctSaldoIcepAuxDTO;
    }
}
