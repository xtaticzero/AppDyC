package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.DyccEstadoCompDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.EstadoCompensacionMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DyccEstadoCompDAOImpl implements DyccEstadoCompDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DyccEstadoCompDTO encontrar(Integer id) {
        return jdbcTemplateDYC.queryForObject("SELECT * FROM DYCC_ESTADOCOMP WHERE IDESTADOCOMP = ?", new Object[]{id}, new EstadoCompensacionMapper());
    }

    @Override
    public List<DyccEstadoCompDTO> obtenerLista() {
        return jdbcTemplateDYC.query("SELECT * FROM DYCC_ESTADOCOMP ", new EstadoCompensacionMapper());
    }
}
