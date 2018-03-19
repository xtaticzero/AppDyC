package mx.gob.sat.siat.dyc.dao.ags.impl;

import mx.gob.sat.siat.dyc.dao.ags.DyccDeptAgsDAO;
import mx.gob.sat.siat.dyc.dao.ags.impl.mapper.DyccDeptAgsMapper;
import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccDeptAgsDAOImpl implements DyccDeptAgsDAO
{
    private static final Logger LOG = Logger.getLogger(DyccDeptAgsDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DyccDeptAgsDTO encontrar(String deptId) 
    {
        LOG.debug("deptId ->" + deptId);
        try 
        {
            String query = "SELECT * FROM DYCC_DEPTAGS WHERE DEPTID = ?";
            return this.getJdbcTemplateDYC().queryForObject(query, new Object[] { deptId },
                                                             new DyccDeptAgsMapper());
        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
        }
        return null;
    }
    
    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
