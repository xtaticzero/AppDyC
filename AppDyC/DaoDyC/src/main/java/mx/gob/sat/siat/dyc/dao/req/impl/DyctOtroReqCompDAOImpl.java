package mx.gob.sat.siat.dyc.dao.req.impl;

import mx.gob.sat.siat.dyc.dao.req.DyctOtroReqCompDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctOtroReqCompDAOImpl implements DyctOtroReqCompDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    /**
    @Override
    public Long insertar(DyctOtroReqCompDTO otroReq) {
        Long id = this.jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDOTROREQCC.NEXTVAL FROM DUAL", Long.class);

        String sentSQLInsert =
            " INSERT INTO DYCT_OTROREQCOMP (IDOTROREQCC, DESCRIPCION, IDDOCUMENTOCOMP) VALUES(?, ?, ?) ";
        Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_3];
        parametros[ConstantesDyCNumerico.VALOR_0] = id;
        parametros[ConstantesDyCNumerico.VALOR_1] = otroReq.getDescripcion();
        parametros[ConstantesDyCNumerico.VALOR_2] = otroReq.getReqComp().getDocumentoComp().getIdDocumentoComp();
        this.jdbcTemplateDYC.update(sentSQLInsert, parametros);
        return id;
    }*/

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
