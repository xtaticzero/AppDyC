package mx.gob.sat.siat.dyc.dao.parametros.impl;

import mx.gob.sat.siat.dyc.dao.parametros.DyccNivelParamDAO;
import mx.gob.sat.siat.dyc.dao.parametros.impl.mapper.DyccNivelParamMapper;
import mx.gob.sat.siat.dyc.domain.DyccNivelParamDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * <html><body>
 * Se utiliza esta clase para obtener la relacion del monto m&aacute;ximo que tiene permitido un aprobador al momento
 * de intentar aprobar una solicitud de devoluci&oacute;n o compensaci&oacute;n
 * </body></html>
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccNivelParamDAO")
public class DyccNivelParamDAOImpl implements DyccNivelParamDAO {
    public DyccNivelParamDAOImpl() {
        super();
    }
    
    private static final Logger LOGGER = Logger.getLogger(DyccNivelParamDAOImpl.class);
    
    private static final String CONSULTAR_X_CLAVEADM_CLAVENIVEL="SELECT * FROM DYCC_NIVELPARAM WHERE CLAVEADM=? AND CLAVENIVEL=? AND DEVOLUCIONES=?";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * <html><body>
     * Obtiene el identificador de parametro a traves de la clave ADM y la clave de nivel del empleado.
     * Esta consulta se realiza para despues consultar la tabla de dycc_parametrosapp y extraer el monto
     * limite que tiene un aprobador a partir del nivel.
     * </body></html>
     *
     * @param claveADM Clave de administrador a la cual pertenece el aprobador
     * @param claveNivel Nivel que tiene el aprobador
     * @return Objeto con el registro completo
     * @throws SIATException
     */
    @Override
    public DyccNivelParamDTO obtenerXClaveADMyNivel(Integer claveADM, Integer claveNivel, Integer devolucion) throws SIATException {
        DyccNivelParamDTO objeto = null;
        
        try {
            objeto =jdbcTemplateDYC.queryForObject(CONSULTAR_X_CLAVEADM_CLAVENIVEL, new Object[]{claveADM, claveNivel, devolucion},
                                                   new DyccNivelParamMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR_X_CLAVEADM_CLAVENIVEL + ConstantesDyC1.TEXTO_3_ERROR_DAO + " claveADM: "+claveADM + 
                      ", claveNivel:"+claveNivel);
            throw new SIATException(e);
        }
        
        return objeto;
    }
}
