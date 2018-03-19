package mx.gob.sat.siat.dyc.dao.archivo.impl;

import mx.gob.sat.siat.dyc.dao.archivo.DyctOtraArchivoDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctOtraArchivoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyctOtraArchivoDAO")
public class DyctOtraArchivoDAOImpl implements DyctOtraArchivoDAO {
    public DyctOtraArchivoDAOImpl() {
        super();
    }
    
    private static final String INSERTAR ="Insert into dyct_otraArchivo (IDOTRAINFOREQ,NUMEROARCHIVO,URL,NOMBREARCHIVO) values (?,?,?,?)";
    private static final Logger LOGGER = Logger.getLogger(DyctOtraArchivoDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Agrega otro registro a la tabla de DYCT_otraArchivo
     *
     * @param objeto 
     */
    @Override
    public void insertar(DyctOtraArchivoDTO objeto) throws SIATException{
        try {
            jdbcTemplateDYC.update(INSERTAR,
                                   new Object[] { objeto.getDyctOtraInfoReqDTO().getIdOtraInfoReq(), 
                                                  objeto.getNumeroArchivo(), 
                                                  objeto.getUrl(), 
                                                  objeto.getNombreArchivo() });
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "\n" +
                      "getIdOtraInfoReq: "+objeto.getDyctOtraInfoReqDTO().getIdOtraInfoReq() +
                      "getNumeroArchivo: "+objeto.getNumeroArchivo()+
                      "getUrl: "+objeto.getUrl()+ 
                      "getNombreArchivo: "+objeto.getNombreArchivo());
            throw new SIATException(e);
        }
    }
}
