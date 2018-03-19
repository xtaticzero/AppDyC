package mx.gob.sat.siat.dyc.dao.util.impl;

import mx.gob.sat.siat.dyc.dao.util.DBLinkDAO;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteAvisoCompensacion;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository("bBLinkDAO")
public class DBLinkDAOImpl implements DBLinkDAO {
    private static Logger log = Logger.getLogger("loggerDYC");
    
    private static final Integer MAXIMO_INTENTOS=3;

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    public boolean dbLinkConexion() {
        return this.dbLinkConexion(ConstanteAvisoCompensacion.DB_LINK_QUERY);
    }
    
    public boolean dbLinkConexion(String dbLinkTable) {
        boolean bandera      = Boolean.FALSE;
        for (int i=0;i<MAXIMO_INTENTOS; i++){
            try {
                jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DUAL.replace(ConstanteAvisoCompensacion.DB_LINK_TABLE,
                                                                                          dbLinkTable), Integer.class);
                bandera=Boolean.TRUE;
                break;
            } catch (Exception e) {
                log.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                          SQLOracleDyC.CONSULTA_DUAL.replace(ConstanteAvisoCompensacion.DB_LINK_TABLE, dbLinkTable));
            }
        }
        return bandera;
    }
}
