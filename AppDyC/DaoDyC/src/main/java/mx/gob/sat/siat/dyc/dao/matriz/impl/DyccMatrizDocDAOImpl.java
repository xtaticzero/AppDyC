package mx.gob.sat.siat.dyc.dao.matriz.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.matriz.DyccMatrizDocDAO;
import mx.gob.sat.siat.dyc.dao.matriz.impl.mapper.DyccMatDocumentosMapper;
import mx.gob.sat.siat.dyc.dao.matriz.impl.mapper.DyccMatrizMapper;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Ericka Janeth Ibarra Ponce
 * @date Febreo 04, 2014
 *
 * */
@Repository(value = "dyccMatrizDocDAO")

public class DyccMatrizDocDAOImpl implements DyccMatrizDocDAO{
    @Autowired    
    private JdbcTemplate jdbcTemplateDYC;
    public DyccMatrizDocDAOImpl() {
        super();
    }
    
    @Override
    public DyccMatrizDocVO buscarMatrizDoc(int idPlantilla) throws SIATException {
        List<DyccMatrizDocVO> ejercicio = null;
            ejercicio =
                jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_MATRIZ_DOCUMENTO.toString(),
                                      new Object[] {idPlantilla}, new DyccMatrizMapper());
            
            return ejercicio.get(0);

        } 
    
    /**
     * Consulta la matriz de documentos y anexa informacion adicional del nombre del tipo de plantilla.
     *
     * @param idPlantilla Id de la plantilla a consultar.
     * @return
     */
    @Override
    public DyccMatDocumentosDTO consultarMatrizConTipoPlantilla(int idPlantilla) throws SIATException {
        
        DyccMatDocumentosDTO objetoMatriz = null;
        
        try {
            objetoMatriz = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_PLANTILLA_Y_TIPOPLANTILLA.toString(), new Object[] { idPlantilla }, new DyccMatDocumentosMapper());
            
        } catch (Exception e) {
            throw new SIATException(e);
        }
        
        return objetoMatriz;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
    
    /**
     * Obtiene el documento de requerimiento a traves de su ID
     *
     * @param idDocumentoReq id de documento de requerimiento
     * @return objeto DyccMatrizDocDTO
     */
    @Override
    public DyccMatrizDocVO consultarDocumentoRequerimientoXIdDocumentoReq(Long idDocumentoReq) throws SIATException {
        return (DyccMatrizDocVO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_NOMBREDOCUMENTOXIDDOCUMENTO.toString(), new Object[]{idDocumentoReq}, new BeanPropertyRowMapper(DyccMatrizDocVO.class));
    }
}
