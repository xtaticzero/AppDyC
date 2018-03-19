package mx.gob.sat.siat.dyc.dao.motivo.impl;

import mx.gob.sat.siat.dyc.dao.motivo.DyccMotivoRechazoDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccMotivoRechazoDAO")
public class DyccMotivoRechazoDAOImpl implements DyccMotivoRechazoDAO {
    
    private static final Logger LOGGER = Logger.getLogger(DyccMotivoRechazoDAOImpl.class);
    private static final String VALIDAR_ID_MOTIVO_RECHAZO = "SELECT COUNT(idMotivoRechazo) FROM dycc_motivorechazo where idMotivoRechazo=?";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Valida si un id de motivo de rechazo se encuentra dado de alta dentro de catalogo en base de datos.
     *
     * @param idMotivoRechazo Identificador obtenido del archivo enviado por TESOFE.
     * @return 1 si fue encontrado el identificador dentro del catalogo, 0 en caso contrario.
     * @throws SIATException
     */
    @Override
    public Integer validarIDMotivoRechazo(Integer idMotivoRechazo) throws SIATException {
        Integer conteo = null;
        try {
            conteo =
                    jdbcTemplateDYC.queryForObject(VALIDAR_ID_MOTIVO_RECHAZO, new Object[] { idMotivoRechazo }, Integer.class);
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                        VALIDAR_ID_MOTIVO_RECHAZO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idMotivoRechazo= " + 
                         idMotivoRechazo);
            throw new SIATException(e);
        }
        return conteo;
    }
}
