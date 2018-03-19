package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DyctAnexoDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctAnexoMapper implements RowMapper<DyctAnexoDTO> {


    @Override
    public DyctAnexoDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyctAnexoDTO dycaDocumentoDTO = new DyctAnexoDTO();


        dycaDocumentoDTO.setNombre(resultSet.getString("NOMBRE"));
        dycaDocumentoDTO.setIdAnexo(resultSet.getInt("IDANEXO"));
        dycaDocumentoDTO.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));
        dycaDocumentoDTO.setUrl(resultSet.getString("URL"));
        dycaDocumentoDTO.setNumControl(resultSet.getString("NUMCONTROL"));
        dycaDocumentoDTO.setIdTipoTramite(resultSet.getInt("IDESTADODOC"));

        return dycaDocumentoDTO;

    }
}
