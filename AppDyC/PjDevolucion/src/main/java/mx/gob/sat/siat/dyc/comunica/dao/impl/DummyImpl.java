package mx.gob.sat.siat.dyc.comunica.dao.impl;

import java.sql.Date;

import mx.gob.sat.siat.dyc.comunica.dao.Dummy;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dummy")
public class DummyImpl implements Dummy {
    private static final Integer TIEMPO_ESPERA = 1000;
    private static final Logger LOGGER = Logger.getLogger(Dummy.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    @Override
    public void consultar() {
        try {
            jdbcTemplateDYC.queryForObject("SELECT SYSDATE FROM DUAL", Date.class);
            Thread.sleep(TIEMPO_ESPERA);
            
        } catch (Exception dae) {
            LOGGER.error("Error al hacer una consulta dummy");
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
