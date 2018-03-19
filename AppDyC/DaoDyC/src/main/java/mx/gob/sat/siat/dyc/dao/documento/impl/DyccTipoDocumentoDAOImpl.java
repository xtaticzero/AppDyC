package mx.gob.sat.siat.dyc.dao.documento.impl;

import mx.gob.sat.siat.dyc.dao.documento.DyccTipoDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.TipoDocumentoMapper;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccTipoDocumentoDAOImpl implements DyccTipoDocumentoDAO
{
    @Autowired    
    private JdbcTemplate jdbcTemplateDYC;
    
    @Override
    public DyccTipoDocumentoDTO encontrar(Integer id)
    {
        return this.getJdbcTemplateDYC().queryForObject( "SELECT * FROM DYCC_TIPODOCUMENTO WHERE IDTIPODOCUMENTO = ?", new Object[]{id}, new TipoDocumentoMapper());
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
