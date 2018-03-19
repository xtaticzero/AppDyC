package mx.gob.sat.siat.dyc.dao.req.impl;

import mx.gob.sat.siat.dyc.dao.req.DycaInfreqReqcompDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DycaInfreqReqcompDAOImpl implements DycaInfreqReqcompDAO
{
    @Autowired    
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctInfoRequeridaDTO infoReq)
    {
        String sentSQLInsert = " INSERT INTO DYCA_INFREQREQCOMP VALUES(?, ?) ";
        Object[] parametros =  new Object[ConstantesDyCNumerico.VALOR_2];
        /**parametros[ConstantesDyCNumerico.VALOR_0] = infoReq.getInfoARequerir().getIdInfoARequerir();
        //parametros[ConstantesDyCNumerico.VALOR_1] = infoReq.getReqComp().getDocumentoComp().getIdDocumentoComp();*/
        this.getJdbcTemplateDYC().update(sentSQLInsert, parametros);
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
