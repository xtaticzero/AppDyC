package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class DyccInfoTramiteMapper implements RowMapper<DyccInfoTramiteDTO> {
    public DyccInfoTramiteMapper() {
        super();
    }

    @Override
    public DyccInfoTramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyccInfoTramiteDTO objeto = new DyccInfoTramiteDTO();
        
        DyccTipoTramiteDTO objetoTipoTramite = new DyccTipoTramiteDTO();
        objetoTipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objeto.setDyccTipoTramiteDTO(objetoTipoTramite);
        
        DyccInfoARequerirDTO objetoInfoAReq = new DyccInfoARequerirDTO();
        objetoInfoAReq.setIdInfoArequerir(rs.getInt("IDINFOAREQUERIR"));
        objeto.setDyccInfoARequerirDTO(objetoInfoAReq);
        
        objeto.setFechaFin(rs.getDate("FECHAFIN"));
                
        return objeto;
    }
}
