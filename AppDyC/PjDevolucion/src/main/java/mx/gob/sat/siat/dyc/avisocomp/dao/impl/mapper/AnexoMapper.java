/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el VO AnexoVO
 * @author  Alfredo Ramirez
 * @since   20/05/2014
 */
public class AnexoMapper implements RowMapper<AnexoVO> {

    public AnexoMapper() {
        super();
    }

    @Override
    public AnexoVO mapRow(ResultSet rs, int i) throws SQLException {

        AnexoVO anexo = new AnexoVO();

        anexo.setIdAnexo(rs.getInt("idanexo"));
        anexo.setNombreAnexo(rs.getString("nombreanexo"));
        anexo.setDescripcionAnexo(rs.getString("descripcionanexo"));
        anexo.setFechaInicio(rs.getDate("fechainicio"));
        anexo.setFechaFin(rs.getDate("fechafin"));
        anexo.setUrl(rs.getString("url"));
        anexo.setTipoTramite(rs.getInt("idtipotramite"));

        return anexo;
    }
}
