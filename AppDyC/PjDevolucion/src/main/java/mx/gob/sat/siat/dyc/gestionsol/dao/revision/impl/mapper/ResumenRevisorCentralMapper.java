package mx.gob.sat.siat.dyc.gestionsol.dao.revision.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.vo.revision.ResumenRevisionVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class ResumenRevisorCentralMapper implements RowMapper<ResumenRevisionVO> {
    public ResumenRevisorCentralMapper() {
        super();
    }

    @Override
    public ResumenRevisionVO mapRow(ResultSet rs, int i) throws SQLException {
        ResumenRevisionVO objeto = new ResumenRevisionVO();
        objeto.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        objeto.setNombreORazonSocial(rs.getString("NOMBREORAZONSOCIAL"));
        objeto.setTipoTramite(rs.getString("TIPOTRAMITE"));
        objeto.setTipoResolucion(rs.getString("TIPORESOLUCION"));
        
        objeto.setImporteSolicitado(rs.getBigDecimal("IMPORTESOLICITADO"));
        objeto.setImporteAutorizado(rs.getBigDecimal("IMPAUTORIZADO"));
        objeto.setImporteNeto(rs.getBigDecimal("IMPORTENETO"));
        objeto.setImporteNegado(rs.getBigDecimal("IMPNEGADO"));
        objeto.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        objeto.setNumControl(rs.getString("NUMCONTROL"));
        
        return objeto;
    }
}
