package mx.gob.sat.siat.dyc.dao.req.impl;

import mx.gob.sat.siat.dyc.dao.req.DycaMatranReqcompDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DycaMatranReqcompDAOImpl implements DycaMatranReqcompDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctAnexoReqDTO anexoReqcc)
    {
        String sentSQLInsert = " INSERT INTO DYCA_MATRANREQCOMP (IDANEXO,IDDOCUMENTOCOMP) VALUES(?, ?) ";
        Object[] parametros =  new Object[ConstantesDyCNumerico.VALOR_2];
        /**parametros[ConstantesDyCNumerico.VALOR_0] = anexoReqcc.getDyccMatrizAnexosDTO().getIdAnexo();
       // parametros[ConstantesDyCNumerico.VALOR_1] = anexoReqcc.getReqComp().getDocumentoComp().getIdDocumentoComp();*/
        this.getJdbcTemplateDYC().update(sentSQLInsert, parametros);
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
