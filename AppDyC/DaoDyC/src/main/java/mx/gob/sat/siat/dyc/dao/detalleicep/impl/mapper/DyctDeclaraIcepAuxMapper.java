package mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.vo.DyctDeclaraIcepAuxDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctDeclaraIcepAuxMapper implements RowMapper<DyctDeclaraIcepAuxDTO> {

    public DyctDeclaraIcepAuxMapper() {
        super();
    }


    @Override
    public DyctDeclaraIcepAuxDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyctDeclaraIcepAuxDTO dyctDeclaraIcepDTO      = new DyctDeclaraIcepAuxDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO             = new DyctSaldoIcepDTO();
        DyccTipoDeclaraDTO dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        
        dyctDeclaraIcepDTO.setIdDeclaraIcep          ( resultSet.getInt("IDDECLARAICEP"));
        dyctDeclaraIcepDTO.setFechaPresentacion      ( resultSet.getDate("FECHAPRESENTACION"));
        dyctDeclaraIcepDTO.setSaldoAfavor            ( resultSet.getBigDecimal("SALDOAFAVOR"));
        dyctDeclaraIcepDTO.setNumOperacion           ( resultSet.getLong("NUMOPERACION"));
        dyccTipoDeclaracionDTO.setIdTipoDeclaracion  ( resultSet.getInt("IDTIPODECLARACION"));
        dyccTipoDeclaracionDTO.setDescripcion        ( resultSet.getString("TIPODECLARACION"));
        dyctDeclaraIcepDTO.setDyccTipoDeclaraDTO     (  dyccTipoDeclaracionDTO );
        
        dyctSaldoIcepDTO.setIdSaldoIcep              ( resultSet.getInt("IDSALDOICEP"));
        dyctDeclaraIcepDTO.setAltaSaldo              ( resultSet.getBigDecimal("ALTASALDO"));
        dyctDeclaraIcepDTO.setActivo                 ( resultSet.getInt("ACTIVO"));
    
        dyctDeclaraIcepDTO.setDyctSaldoIcepDTO       (dyctSaldoIcepDTO);


        return dyctDeclaraIcepDTO;
    }
}
