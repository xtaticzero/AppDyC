package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccMatrizAnexosDTO
 * @author  Alfredo Ramirez
 * @since   22/08/2013
 */
public class DyccMatrizAnexosMapper implements RowMapper<DyccMatrizAnexosDTO> {

    public DyccMatrizAnexosMapper() {
        super();
    }

    @Override
    public DyccMatrizAnexosDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccMatrizAnexosDTO dyccMatrizAnexos = new DyccMatrizAnexosDTO();
        DyccAnexoTramiteDTO dyccAnexoTramiteDTO = new DyccAnexoTramiteDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();

        dyccMatrizAnexos.setIdAnexo(rs.getInt("idanexo"));
        dyccMatrizAnexos.setNombreAnexo(rs.getString("nombreanexo"));
        dyccMatrizAnexos.setDescripcionAnexo(rs.getString("descripcionanexo"));
        dyccMatrizAnexos.setFechaInicio(rs.getDate("fechainicio"));
        dyccMatrizAnexos.setFechaFin(rs.getDate("fechafin"));
        dyccMatrizAnexos.setUrl(rs.getString("url"));
        
        dyccTipoTramiteDTO.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        dyccAnexoTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        dyccMatrizAnexos.setAnexoTramite(dyccAnexoTramiteDTO);
        
        return dyccMatrizAnexos;
    }

}
