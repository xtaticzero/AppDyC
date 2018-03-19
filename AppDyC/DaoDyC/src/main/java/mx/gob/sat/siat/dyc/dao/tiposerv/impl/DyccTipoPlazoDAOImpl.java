package mx.gob.sat.siat.dyc.dao.tiposerv.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.tiposerv.DyccTipoPlazoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper.DyccTipoPlazoMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Afredo Hernandez Orozco
 * @since 08/12/2014
 */
@Repository(value = "dyccTipoPlazoDAO")
public class DyccTipoPlazoDAOImpl implements DyccTipoPlazoDAO{
    
    private static final Logger LOGGER = Logger.getLogger(DyccTipoPlazoDAOImpl.class);
    
    private static final String CONSULTA_TIPO_PLAZO_X_ID = "select idtipoPlazo, descripcion, fechainicio, fechafin from dycc_tipoPlazo where fechaFin is null and idtipoPlazo = ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Devuelve la lista de todos los tipos de plazo que hay insertados en la base de datos.
     *
     * @return lista de todos los tipos de plazo que hay insertados en la base de datos.
     * @throws SIATException
     */
    @Override
    public List<DyccTipoPlazoDTO> consultar() throws SIATException {
        List<DyccTipoPlazoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_TIPO_PLAZO.toString(), new DyccTipoPlazoMapper());
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_TIPO_PLAZO + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consultar por idTipoPlazo
     *
     * @param idTipoPlazo
     * @return
     */
    @Override
    public DyccTipoPlazoDTO consultar(Integer idTipoPlazo) throws SIATException {
        DyccTipoPlazoDTO objeto = null;
        try {
            objeto = jdbcTemplateDYC.queryForObject(CONSULTA_TIPO_PLAZO_X_ID, new Object[]{idTipoPlazo}, new DyccTipoPlazoMapper());
            
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_TIPO_PLAZO_X_ID + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(e);
        }
        return objeto;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
