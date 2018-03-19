package mx.gob.sat.siat.dyc.dao.anexo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DycaSolAnexoTramDAO;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.DycaSolAnexoTramMapper;
import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco.
 */
@Repository(value = "dycaSolAnexoTramDAO")
public class DycaSolAnexoTramDAOImpl implements DycaSolAnexoTramDAO {
    public DycaSolAnexoTramDAOImpl() {
        super();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DycaSolAnexoTramDAOImpl.class);

    /**
     * <html>
     * <body>
     * Consulta los diferentes anexos a los tramites. Para evitar el uso de reglas de negocio, se le tienen que enviar
     * la consulta para evitar reglas de negocio implementada dentro de la capa de negocio.
     * </body>
     * </html>
     *
     * @param query
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycaSolAnexoTramDTO> listarTramites(String query) throws SIATException {
        List<DycaSolAnexoTramDTO> lista = null;
        
        try {
            lista = jdbcTemplateDYC.query(query, new DycaSolAnexoTramMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      query);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Una vez que han sido leidos los registros se procede a marcarlos como procesados
     *
     * @param numControl
     * @throws SIATException
     */
     @Override
    public void actualizarAProcesado(String numControl) throws SIATException {
        String sql = "update Dyca_SolAnexoTram set procesado=1 where numcontrol = ? and idanexo in (4, 6, 7)";
        try {
            LOGGER.info("JAHO - ACTUALIZACIONES: "+jdbcTemplateDYC.update(sql, new Object[] { numControl }));
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl: " + numControl);
            throw new SIATException(e);
        }
    }
}
