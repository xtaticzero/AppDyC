package mx.gob.sat.siat.dyc.dao.resolucion.impl;

import mx.gob.sat.siat.dyc.dao.resolucion.DyccEstResolDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.EstatusResolucionMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstResolDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccEstResolDAOImpl implements DyccEstResolDAO
{
    @Autowired    
    private JdbcTemplate jdbcTemplateDYC;
    
    public DyccEstResolDTO encontrar(Integer id)
    {
        return (DyccEstResolDTO)this.getJdbcTemplateDYC().queryForObject( "SELECT * FROM DYCC_ESTRESOL WHERE IDESTRESOL = ?", new Object[]{id}, new EstatusResolucionMapper());
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
