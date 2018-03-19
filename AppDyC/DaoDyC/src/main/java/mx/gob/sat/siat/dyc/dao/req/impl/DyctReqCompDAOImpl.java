package mx.gob.sat.siat.dyc.dao.req.impl;

import mx.gob.sat.siat.dyc.dao.compensacion.DyctReqCompDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctReqCompDAOImpl implements DyctReqCompDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    /**
    @Override
    public void insertar(DyctReqCompDTO reqComp)
    {
        String sentSQLInsert = " INSERT INTO DYCT_REQCOMP (FECHACREACION, IDDOCUMENTOCOMP) VALUES(?, ?) ";
        Object[] parametros =  new Object[ConstantesDyCNumerico.VALOR_2];
        parametros[ConstantesDyCNumerico.VALOR_0] = reqComp.getFechaCreacion();
        parametros[ConstantesDyCNumerico.VALOR_1] = reqComp.getDocumentoComp().getIdDocumentoComp();
        this.getJdbcTemplateDYC().update(sentSQLInsert, parametros);
    }*/

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
