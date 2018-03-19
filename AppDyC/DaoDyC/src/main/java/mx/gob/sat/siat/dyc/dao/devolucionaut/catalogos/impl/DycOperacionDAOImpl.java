package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl;

import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycOperacionDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycOperacionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mario Lizaola Ruiz
 */
@Repository(value = "operacionDAO")
public class DycOperacionDAOImpl implements DycOperacionDAO{
    private static final Logger LOGGER = Logger.getLogger(DycOperacionDAOImpl.class.getName());
    private static final String INSERTAR = "INSERT INTO SIAT_DYC_ADMIN.DYCC_OPERACION (IDOPERACION,DESCRIPCION,FECHAFIN) VALUES (SIAT_DYC_ADMIN.DYCQ_OPERACION.NEXTVAL,?,?)";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    @Override
    public void insertar(DycOperacionDTO operacion) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             operacion.getDescripcion(),
                                             operacion.getFechaFin()                                            
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(operacion));
            throw new SIATException(e);
        }
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
