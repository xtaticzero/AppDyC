package mx.gob.sat.siat.dyc.dao.devolucionaut.impl;

import java.sql.Types;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyctWsAutomaticaDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctWsAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mario Lizaola Ruiz
 */
@Repository(value = "wsAutomaticaDAO")
public class DyctWsAutomaticaDAOImpl implements DyctWsAutomaticaDAO{
    private static final Logger LOGGER = Logger.getLogger(DyctWsAutomaticaDAOImpl.class.getName());
    private static final String INSERTAR = "INSERT INTO SIAT_DYC_ADMIN.DYCT_WSAUTOMATICA (IDWSAUTOMATICA,IDOPERACION,FECHAREGISTRO,REQUEST,RESPONSE,MENSAJE) VALUES (SIAT_DYC_ADMIN.DYCQ_WSAUTOMATICA.NEXTVAL,?,?,?,?,?)";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    public SqlLobValue xmlTypeRequest(DyctWsAutomaticaDTO wsAutomatica){
        LobHandler lobHandler = new DefaultLobHandler();
            SqlLobValue xmlType = null;

            if (null != wsAutomatica.getXmlRequest()) {
                xmlType = new SqlLobValue(wsAutomatica.getXmlRequest(), lobHandler);
            } else {
                xmlType = new SqlLobValue("", lobHandler);
            }
            return xmlType;
    }
    
    public SqlLobValue xmlTypeResponse(DyctWsAutomaticaDTO wsAutomatica){
        LobHandler lobHandler = new DefaultLobHandler();
            SqlLobValue xmlType = null;

            if (null != wsAutomatica.getXmlResponse()) {
                xmlType = new SqlLobValue(wsAutomatica.getXmlResponse(), lobHandler);
            } else {
                xmlType = new SqlLobValue("", lobHandler);
            }
            return xmlType;
    }
    
    @Override
    public void insertar(DyctWsAutomaticaDTO wsAutomatica) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,new Object[] {
                                             wsAutomatica.getIdOperacion(),
                                             wsAutomatica.getFechaRegistro(),
                                             xmlTypeRequest(wsAutomatica),
                                             xmlTypeResponse(wsAutomatica),
                                             wsAutomatica.getMensaje() 
            },new int[] { Types.INTEGER,Types.TIMESTAMP, 
                        Types.CLOB, Types.CLOB,Types.VARCHAR });
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(wsAutomatica));
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
