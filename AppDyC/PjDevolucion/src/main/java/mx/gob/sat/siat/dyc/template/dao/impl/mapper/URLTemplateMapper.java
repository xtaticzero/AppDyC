/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.template.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.template.dto.URLTemplateDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Yolanda Martínez Sánchez
 * @since  12 th March, 2014
 *
 */


public class URLTemplateMapper implements RowMapper<URLTemplateDTO> {
    public URLTemplateMapper() {
        super();
    }


    @Override
    public URLTemplateDTO mapRow(ResultSet rs, int i) throws SQLException {
        URLTemplateDTO urlTemplateDTO = new URLTemplateDTO();


        urlTemplateDTO.setUrlOfConfiguration(rs.getString("URLCONFIGURADOR"));
        urlTemplateDTO.setUrlOfTemplate(rs.getString("URLPLANTILLA"));
        urlTemplateDTO.setNameOfTemplate(rs.getString("NOMBREDOCUMENTO"));

        return urlTemplateDTO;
    }


}
