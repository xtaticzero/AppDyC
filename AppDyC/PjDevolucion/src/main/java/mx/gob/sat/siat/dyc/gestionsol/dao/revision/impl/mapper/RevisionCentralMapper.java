package mx.gob.sat.siat.dyc.gestionsol.dao.revision.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.vo.revision.RevisionCentralVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @autor Jesus Alfredo Hernandez Orozco
 */
public class RevisionCentralMapper implements RowMapper<RevisionCentralVO> {
    public RevisionCentralMapper() {
        super();
    }

    @Override
    public RevisionCentralVO mapRow(ResultSet rs, int i) throws SQLException {
        RevisionCentralVO objeto = new RevisionCentralVO();
        objeto.setDictaminado(rs.getString("DICTAMINADO"));
        objeto.setDictaminador(rs.getString("DICTAMINADOR"));
        objeto.setNombreDocumento(rs.getString("NOMBREDOCUMENTO"));
        objeto.setNumControl(rs.getString("NUMCONTROL"));
        objeto.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        objeto.setTipoTramite(rs.getString("TIPOTRAMITE"));
        objeto.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        objeto.setFechaVencimiento(rs.getDate("FECHAVENCIMIENTO"));        
        objeto.setNumControlDoc(rs.getString("NUMCONTROLDOC")); 
        objeto.setImporteSolicitado(rs.getBigDecimal("IMPORTESOLICITADO")); 
        return objeto;
    }
}
