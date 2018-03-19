/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.dao.regsolicitud.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.regsolicitud.DyccEstadoSolDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper.DyccEstadoSolMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "dyccEstadoSolDAO")
public class DyccEstadoSolDAOImpl implements DyccEstadoSolDAO {

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List<DyccEstadoSolDTO> obtenerLista() {
        String query = "SELECT * FROM DYCC_ESTADOSOL ";
        return jdbcTemplateDYC.query(query, new DyccEstadoSolMapper());
    }
}
