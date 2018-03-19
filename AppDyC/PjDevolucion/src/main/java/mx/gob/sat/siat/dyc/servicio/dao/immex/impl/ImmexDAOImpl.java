package mx.gob.sat.siat.dyc.servicio.dao.immex.impl;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dao.immex.ImmexDAO;
import mx.gob.sat.siat.dyc.servicio.dao.immex.procedures.ConsultarImmexStoreProcedure;
import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */

@Repository(value = "immexDAO")
public class ImmexDAOImpl implements ImmexDAO, SQLInformixSIAT {
    
    private Logger log = Logger.getLogger(ImmexDAOImpl.class.getName());
    
    @Autowired
    private JdbcTemplate jdbcTemplateInformix;
        
    public ImmexDAOImpl() {
        super();
    }
    

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
    
    /**
     *
     * @param immexDTO
     * @return Retorna un valor de tipo objeto llamado immexDTO
     */
    public ImmexDTO obtieneImmexSP(ImmexDTO immexDTO) {
        
        ConsultarImmexStoreProcedure immexSP;
        immexSP = new ConsultarImmexStoreProcedure(jdbcTemplateInformix, STORE_PROCEDURE_BUSCA_IMMEX);
        ImmexDTO immex = new ImmexDTO();
        log.info(immex);
        try{
            immex = immexSP.buscaImmex(immexDTO);
        }catch(DataAccessException dae){
            
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesErrorTextos.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_IMMEX + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(immexDTO));
            
        } catch (SQLException e) {
            
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_IMMEX + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(immexDTO));
        }

        return immex;
    }
}
