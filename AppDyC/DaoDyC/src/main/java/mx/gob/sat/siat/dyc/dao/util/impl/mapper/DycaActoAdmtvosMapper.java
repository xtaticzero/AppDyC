package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.documento.DycaActoAdmtvosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycaActoAdmtvosMapper implements RowMapper<DycaActoAdmtvosDTO> {
    
    @Override
    public DycaActoAdmtvosDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DycaActoAdmtvosDTO objetoActosAdmtvos = new DycaActoAdmtvosDTO();
        DyccMatDocumentosDTO objetoMatrizDocumentos = new DyccMatDocumentosDTO();

        objetoMatrizDocumentos.setIdPlantilla(rs.getInt("IDPLANTILLA"));
        objetoActosAdmtvos.setDyccMatDocumentosDTO(objetoMatrizDocumentos);
        objetoActosAdmtvos.setCveUnidadAdmtva(rs.getInt("CVEUNIDADADMTVA"));
        objetoActosAdmtvos.setCveActoAdmtvo(rs.getInt("cveactoadmtvo"));
        objetoActosAdmtvos.setPrefijo(rs.getString("PREFIJO"));
        objetoActosAdmtvos.setNombrePlantilla(rs.getString("NOMBREPLANTILLA"));
        objetoActosAdmtvos.setCveDocumentoTipo(rs.getString("CVE_DOCUMENTO_TIPO"));
        objetoActosAdmtvos.setCveActoAdmtvoFase2(rs.getInt("CVEACTOADMTVO_FASE2"));
        
        return objetoActosAdmtvos;
    }
}
