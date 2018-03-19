/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 11, 2013
 *
 * */
public class AdministrarSolicitudesMapper implements RowMapper<AdministrarSolicitudesVO> {
    
    @Override
    public AdministrarSolicitudesVO mapRow(ResultSet resultSet, int i) throws SQLException{
       
        AdministrarSolicitudesVO administrarSolicitudesVO = new AdministrarSolicitudesVO();

        administrarSolicitudesVO.setDiasHabiles(resultSet.getInt("DIASHABILES"));
        administrarSolicitudesVO.setFechaNotificacion(resultSet.getDate("FECHANOTIFICACION"));
        administrarSolicitudesVO.setFechaSolventacion(resultSet.getDate("FECHASOLVENTACION"));
        
        return administrarSolicitudesVO;
       
    }
    
}
