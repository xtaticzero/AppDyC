package mx.gob.sat.siat.dyc.template.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.template.dto.URLCompleteTemplateDTO;

import org.springframework.jdbc.core.RowMapper;


public class URLCompleteTemplateMapper implements RowMapper<URLCompleteTemplateDTO> {
    public URLCompleteTemplateMapper() {
        super();
    }

    public URLCompleteTemplateDTO mapRow(ResultSet rs, int i) throws SQLException {
        URLCompleteTemplateDTO urlCompleteTemplateDTO = new URLCompleteTemplateDTO();


        urlCompleteTemplateDTO.setUrlOfConfiguration(rs.getString("URLCONFIGURADOR"));
        urlCompleteTemplateDTO.setUrlOfDestinity(rs.getString("URL"));
        urlCompleteTemplateDTO.setUrlName(rs.getString("NOMBREARCHIVO"));

        return urlCompleteTemplateDTO;
    }
}
