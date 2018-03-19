package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.ICuadroDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl.mapper.CuadroMapper;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository 
public class CuadroDAOImpl implements ICuadroDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(CuadroDAOImpl.class.getName());

    @Override
    public List<CuadroBean> obtenerCuadros(String numControlAc) {
        List<CuadroBean> cuadros = new ArrayList<CuadroBean>();
        log.info(cuadros);
        
        try{
            cuadros = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTACUADRO_AVISOCOMP.toString(), new Object[]{numControlAc}, new CuadroMapper());
        }catch(DataAccessException dae){
                log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTACUADRO_AVISOCOMP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    "NUMCONTROLAC= "+numControlAc);
        }
        return cuadros;
    }
}
