package mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctSaldoIcepDisponibleMapper implements RowMapper<DyctSaldoIcepDTO> {

    public DyctSaldoIcepDisponibleMapper() {
        super();
    }


    @Override
    public DyctSaldoIcepDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        
        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();
        dyccConceptoDTO.setIdConcepto        (resultSet.getInt("IDCONCEPTO"));
        dyccConceptoDTO.setDescripcion       (resultSet.getString("CONCEPTO"));
        
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        dyccEjercicioDTO.setIdEjercicio      (resultSet.getInt("IDEJERCICIO"));
        
        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();
        dyccPeriodoDTO.setIdPeriodo          (resultSet.getInt("IDPERIODO"));

        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setRfc              (resultSet.getString("RFC"));
        dyctSaldoIcepDTO.setIdSaldoIcep      (resultSet.getInt("IDSALDOICEP"));
        dyctSaldoIcepDTO.setDyccConceptoDTO  (dyccConceptoDTO);
        dyctSaldoIcepDTO.setDyccEjercicioDTO (dyccEjercicioDTO);
        dyctSaldoIcepDTO.setDyccPeriodoDTO   (dyccPeriodoDTO);
        dyctSaldoIcepDTO.setRemanente        (resultSet.getBigDecimal("REMANENTE"));
        dyctSaldoIcepDTO.setBloqueado        (resultSet.getInt("BLOQUEADO"));
        
        return dyctSaldoIcepDTO;
    }
}
