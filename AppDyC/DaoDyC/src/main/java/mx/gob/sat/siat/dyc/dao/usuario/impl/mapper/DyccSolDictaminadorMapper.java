/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DyccDictaminadorSolDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccDictaminadorDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 */
public class DyccSolDictaminadorMapper implements RowMapper<DyccDictaminadorSolDTO> {

    public DyccSolDictaminadorMapper() {
        super();
    }

    @Override
    public DyccDictaminadorSolDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccDictaminadorSolDTO dyccDictaminadorSol = new DyccDictaminadorSolDTO();

        dyccDictaminadorSol.setNumControl(rs.getString("NUMCONTROL"));
        dyccDictaminadorSol.setTipoTramite(rs.getString("TIPOTRAMITE"));
        dyccDictaminadorSol.setPuntosAsignar(rs.getString("PUNTOSASIGNACION"));
        dyccDictaminadorSol.setFechaRegistro(rs.getDate("FECHAPRESENTACION"));
        dyccDictaminadorSol.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        dyccDictaminadorSol.setNumEmpleado(rs.getInt("NUMEMPLEADO"));

        return dyccDictaminadorSol;

    }

}
