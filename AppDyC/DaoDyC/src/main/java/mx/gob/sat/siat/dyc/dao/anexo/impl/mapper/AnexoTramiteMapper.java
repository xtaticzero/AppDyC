package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class AnexoTramiteMapper implements RowMapper<DyccAnexoTramiteDTO>
{
   private DyccTipoTramiteDTO tipoTramite;
   private Boolean mapearAnexo = false;
   
   public DyccAnexoTramiteDTO mapRow(ResultSet rs, int rowNum) throws SQLException
   {
       DyccAnexoTramiteDTO anexoTramite = new DyccAnexoTramiteDTO();
       if(getTipoTramite() != null)
       {
           anexoTramite. setDyccTipoTramiteDTO(getTipoTramite());
       }
       else
       {
           DyccTipoTramiteDTO tte = new DyccTipoTramiteDTO();
           tte.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
           anexoTramite.setDyccTipoTramiteDTO(tte);
       }
       
       if(getMapearAnexo())
       {
           MatrizAnexosMapper mapper = new MatrizAnexosMapper();
           anexoTramite.setDyccMatrizAnexosDTO(mapper.mapRow(rs, rowNum));
       }
       else
       {
           DyccMatrizAnexosDTO anexo = new DyccMatrizAnexosDTO();
           anexo.setIdAnexo(rs.getInt("IDANEXO"));
           anexo.setNombreAnexo(rs.getString("NOMBREANEXO"));
           anexo.setDescripcionAnexo(rs.getString("DESCRIPCIONANEXO"));
           anexoTramite.setDyccMatrizAnexosDTO(anexo);
       }
       
       return anexoTramite;
   }

    public DyccTipoTramiteDTO getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(DyccTipoTramiteDTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Boolean getMapearAnexo() {
        return mapearAnexo;
    }

    public void setMapearAnexo(Boolean mapearAnexo) {
        this.mapearAnexo = mapearAnexo;
    }
}
