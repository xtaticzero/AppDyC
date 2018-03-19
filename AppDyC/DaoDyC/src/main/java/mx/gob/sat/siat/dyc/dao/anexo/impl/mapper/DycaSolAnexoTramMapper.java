package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class DycaSolAnexoTramMapper implements RowMapper<DycaSolAnexoTramDTO> {
    public DycaSolAnexoTramMapper() {
        super();
    }

    @Override
    public DycaSolAnexoTramDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycaSolAnexoTramDTO objeto = new DycaSolAnexoTramDTO();
        DyccAnexoTramiteDTO objAnexoTramite = new DyccAnexoTramiteDTO();
        DyccMatrizAnexosDTO objMatrizAnexos = new DyccMatrizAnexosDTO();
        DyccTipoTramiteDTO  objTipoTramite  = new DyccTipoTramiteDTO();
        DycpSolicitudDTO    objSolicitud    = new DycpSolicitudDTO();
        
        objMatrizAnexos.setIdAnexo(rs.getInt("IDANEXO"));
        objTipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objSolicitud.setNumControl(rs.getString("NUMCONTROL"));
        
        objAnexoTramite.setDyccMatrizAnexosDTO(objMatrizAnexos);
        objAnexoTramite.setDyccTipoTramiteDTO(objTipoTramite);
        objeto.setDyccAnexoTramiteDTO(objAnexoTramite);
        objeto.setDycpSolicitudDTO(objSolicitud);
        objeto.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        objeto.setUrl(rs.getString("URL"));
        objeto.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        objeto.setProcesado(rs.getInt("PROCESADO"));
        
        return objeto;
    }
}
