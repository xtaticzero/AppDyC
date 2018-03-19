package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper.DyccStatusProcesoMapper;
import mx.gob.sat.siat.dyc.dao.util.DyccStatusProcesoDAO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccStatusProcesoDAO")
public class DyccStatusProcesoDAOImpl implements DyccStatusProcesoDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DyccStatusProcesoDAOImpl.class);
    
    /**
     * Lista los status de los procesos, a este metodo se le pasa la cosulta como parametro por que 
     * se pretenden que se listen, todos o algunos de los estatus.
     *
     * @param consulta Query que se ejecutara en base de datos.
     * @return Lista de status
     * @throws SIATException
     */
    @Override
    public List<DyccStatusProcesoDTO> listarStatusProceso(String consulta) throws SIATException {
        List<DyccStatusProcesoDTO> listaStatusProceso = null;
        try {
            listaStatusProceso = jdbcTemplateDYC.query(consulta, new DyccStatusProcesoMapper());
        } catch (Exception e) {
            LOGGER.error("listarStatusProceso() ; Consulta: "+consulta+"; causa: "+e);
            throw new SIATException(e);
        }
        return listaStatusProceso;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
