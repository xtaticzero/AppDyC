package mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.vo.DyctResolucionAuxVO;

import org.springframework.jdbc.core.RowMapper;


public class DyctMovResolucionAuxMapper implements RowMapper<DyctResolucionAuxVO>{
    public DyctMovResolucionAuxMapper() {
        super();
    }
    
    @Override
    public DyctResolucionAuxVO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyctResolucionAuxVO dyctResolucionAuxDTO = new DyctResolucionAuxVO();
                                
        DycpSolicitudDTO dycpSolicitudDTO = new DycpSolicitudDTO();    
        dycpSolicitudDTO.setNumControl  (rs.getString("NUMCONTROL"));
        DyccTipoResolDTO dyccTipoResolDTO = new DyccTipoResolDTO();   
        dyccTipoResolDTO.setIdTipoResol(rs.getInt("IDTIPORESOL"));
        dyccTipoResolDTO.setDescripcion(rs.getString("RESOLUCION"));        
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();        
        dyctSaldoIcepDTO.setRemanente(rs.getBigDecimal("REMANENTEFAVOR"));
        
        dyctResolucionAuxDTO.setDycpSolicitudDTO  (dycpSolicitudDTO);
        dyctResolucionAuxDTO.setDyccTipoResolDTO  (dyccTipoResolDTO);
        dyctResolucionAuxDTO.setDyctSaldoIcepDTO  (dyctSaldoIcepDTO);
        
        dyctResolucionAuxDTO.setFechaRegistroRes  (rs.getString("FECHARESOLUCION"));        
        dyctResolucionAuxDTO.setImporteSolicitado (rs.getBigDecimal("IMPORTEDECLARADO"));
        dyctResolucionAuxDTO.setImpAutorizado     (rs.getBigDecimal("IMPORTEAUTORIZADO"));
        dyctResolucionAuxDTO.setImporteNegado     (rs.getBigDecimal("IMPORTENEGADO"));
        dyctResolucionAuxDTO.setImpActualizacion  (rs.getBigDecimal("ACTUALIZACION"));
        dyctResolucionAuxDTO.setIntereses         (rs.getBigDecimal("INTERESES"));       
        dyctResolucionAuxDTO.setTipoMovimiento    (rs.getString("TIPOMOVIMIENTO"));
            
        return dyctResolucionAuxDTO;
    }
}
